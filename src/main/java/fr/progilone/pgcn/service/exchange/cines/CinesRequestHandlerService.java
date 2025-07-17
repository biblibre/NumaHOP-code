package fr.progilone.pgcn.service.exchange.cines;

import fr.progilone.pgcn.domain.administration.MailboxConfiguration;
import fr.progilone.pgcn.domain.document.DocUnit;
import fr.progilone.pgcn.domain.exchange.cines.CinesReport;
import fr.progilone.pgcn.domain.jaxb.aip.PacType;
import fr.progilone.pgcn.domain.jaxb.avis.PacAvisType;
import fr.progilone.pgcn.domain.library.Library;
import fr.progilone.pgcn.exception.PgcnTechnicalException;
import fr.progilone.pgcn.service.administration.MailboxConfigurationService;
import fr.progilone.pgcn.service.document.DocUnitService;
import fr.progilone.pgcn.service.exchange.mail.MailboxService;
import fr.progilone.pgcn.service.storage.FileStorageManager;
import fr.progilone.pgcn.service.util.DateUtils;
import fr.progilone.pgcn.service.util.transaction.TransactionService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.BodyPart;
import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service gérant l'évolution des éléments exportés vers le(s) serveur(s) CINES
 * <p>
 * Created by Sébastien on 04/01/2017.
 */
@Service
public class CinesRequestHandlerService {

	public static final String SIP_XML_FILE = "sip.xml";

	public static final String AIP_XML_FILE = "aip.xml";

	public static final String METS_XML_FILE = "mets.xml";

	public static final String CINES_SUBJECT_ARCHIVAGE = "AVIS_PAC";

	private static final Logger LOG = LoggerFactory.getLogger(CinesRequestHandlerService.class);

	// Id of pac_avis responses.
	protected static final String REP_ACCUSE_RECEPTION_DE_VERSEMENT = "ACCUSE_RECEPTION_DE_VERSEMENT";

	protected static final String REP_CERTIFICAT_ARCHIVAGE = "CERTIFICAT_ARCHIVAGE";

	protected static final String REP_REJET_VERSEMENT = "REJET_VERSEMENT";

	@Value("${instance.libraries}")
	private String[] instanceLibraries;

	@Value("${services.cines.updating.cronenabled}")
	private boolean cinesUpdatingEnabled;

	// Stockage des AIP
	@Value("${services.cines.aip}")
	private String workingDir;

	// Stockage temp des SIP
	@Value("${services.cines.cache}")
	private String cacheDir;

	private final CinesReportService cinesReportService;

	private final ExportCinesService exportCinesService;

	private final MailboxService mailboxService;

	private final MailboxConfigurationService mailboxConfigurationService;

	private final FileStorageManager fm;

	private final DocUnitService docUnitService;

	private final TransactionService transactionService;

	@Autowired
	public CinesRequestHandlerService(final ExportCinesService exportCinesService,
			final CinesReportService cinesReportService, final MailboxService mailboxService,
			final MailboxConfigurationService mailboxConfigurationService, final FileStorageManager fm,
			final DocUnitService docUnitService, final TransactionService transactionService) {
		this.cinesReportService = cinesReportService;
		this.exportCinesService = exportCinesService;
		this.mailboxService = mailboxService;
		this.mailboxConfigurationService = mailboxConfigurationService;
		this.fm = fm;
		this.docUnitService = docUnitService;
		this.transactionService = transactionService;
	}

	@PostConstruct
	public void initialize() {

		Arrays.asList(instanceLibraries).forEach(lib -> {
			try {
				FileUtils.forceMkdir(new File(workingDir, lib));
			}
			catch (final IOException ex) {
				LOG.error(ex.getMessage(), ex);
			}
		});

	}

	@Scheduled(cron = "${cron.cinesExport}")
	public void cinesExportCron() {

		LOG.info("Lancement du cronjob cinesExport ...");
		final List<String> docsToExport = exportCinesService.findDocUnitsReadyForCinesExport();
		docsToExport.forEach(id -> {
			LOG.info("Debut export CINES - DocUnit[{}]", id);
			final CinesReport report = exportCinesService.exportDocToCines(id, true);
			LOG.info("Fin export CINES - DocUnit[{}] - Message: {}", id, report.getMessage());
		});
	}

	@Scheduled(cron = "${cron.cinesUpdateStatus}")
	public void updateExportedDocUnitsCron() {

		if (cinesUpdatingEnabled) {
			LOG.info("Lancement du cronjob updateExportedDocUnitsCron...");
			final Set<MailboxConfiguration> confs = mailboxConfigurationService.findAll(true);
			try {
				updateExportedDocUnits(confs);

			}
			catch (final PgcnTechnicalException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Vérification des boite mails à la recherche de messages concernant les exports en
	 * attente de réponse de la part de CINES
	 * @param mailboxConfigurations
	 * @throws PgcnTechnicalException
	 */
	public void updateExportedDocUnits(final Collection<MailboxConfiguration> mailboxConfigurations)
			throws PgcnTechnicalException {

		for (final MailboxConfiguration conf : mailboxConfigurations) {

			final long nbPending = cinesReportService.countPendingByLibrary(conf.getLibrary());
			// Si des exports CINES sont en attente de réponse, on vérifie la boite mail
			if (nbPending > 0) {

				mailboxService.readMailbox(conf, messages -> {

					if (messages.length > 0) {

						transactionService.executeInNewTransaction(() -> {
				    		for (final Message message : messages) {

								try {
									final String subject = message.getSubject();
									if (subject == null || !subject.contains(CINES_SUBJECT_ARCHIVAGE)) {
                                        // Ignore all messages that are not from the CINES.
										message.setFlag(Flags.Flag.SEEN, true);
									}
									else {
										final Boolean shouldBeRead = parseCinesMessage(message).map(resp -> updateExport(resp, conf.getLibrary())).orElse(false);
									    LOG.debug("Status d'export mis a jour.");
										message.setFlag(Flags.Flag.SEEN, shouldBeRead);
									}
								}
								catch (final MessagingException e) {
                                    // We log the error and continue to other messages.
									LOG.warn("Vérification des boite mails CINES - Problem: {}", e.getMessage(), e);
                                    e.printStackTrace();
								}
							}
						});
					}
				});

			}
			else {
				LOG.debug("CINES - Aucun export en attente de reponse pour la bibliothèque {}",
						conf.getLibrary().getIdentifier());
			}

		}
	}

	/**
	 * Parse le message à la recherche d'un corps au format avis.xsd et/ou d'une pièce
	 * jointe au format aip.xsd
	 * @param message
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @throws JAXBException
	 */
	protected Optional<CinesResponse> parseCinesMessage(final Message message)
        throws MessagingException {
        CinesResponse resp = new CinesResponse();
        resp.msgDate = Optional.ofNullable(message.getSentDate())
            .map(DateUtils::convertToLocalDateTime)
            .orElse(LocalDateTime.now()) ;
        String message_desc = message.getSubject().concat(" at ").concat(resp.msgDate.toString());
		LOG.debug("Reading mail: {}", message_desc);
        Object content;
        try {
            content = message.getContent();
        } catch (IOException e) {
            LOG.error("Could not get content of mail: {}.", message_desc);
            e.printStackTrace();
            return Optional.empty();
        }

        if (content instanceof Multipart) { // If the content is a multipart the mail is a certification
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart part = multipart.getBodyPart(i);
                if (isPartAvis(part)) {

                    LOG.trace("Found avis part.");
                    InputStream partIs;
					try { partIs = part.getInputStream(); } catch (IOException e) {
                        LOG.error("Could not get avis part content of the mail: {}", message_desc);
						e.printStackTrace();
                        return Optional.empty();
					}

                    parseXml(partIs, fr.progilone.pgcn.domain.jaxb.avis.ObjectFactory.class)
                        .map(elt -> {
							@SuppressWarnings("unchecked")
							JAXBElement<PacAvisType> jaxbElement = (JAXBElement<PacAvisType>) elt;
							return jaxbElement.getValue();
						})
                        .ifPresentOrElse(
                            avis -> resp.setAvis(avis),
                            () -> resp.setErrorMessage("The avis content of the mail could not be parsed."));

                } else if (isPartAip(part)) {

                    LOG.trace("Found aip part.");
                    InputStream partIs;
					try { partIs = part.getInputStream(); } catch (IOException e) {

                        LOG.error("Could not get the aip part content of the mail: {}", message_desc);
						e.printStackTrace();
                        return Optional.empty();

					}

                    parseXml(partIs, fr.progilone.pgcn.domain.jaxb.aip.ObjectFactory.class)
                        .map(elt -> {
							@SuppressWarnings("unchecked")
							JAXBElement<PacType> jaxbElement = (JAXBElement<PacType>) elt;
							return jaxbElement.getValue();
						})
                        .ifPresentOrElse(
                            aip -> {
                                try {
                                    handleAip(aip, part.getInputStream());
                                } catch (MessagingException | IOException e) {
                                    LOG.error("Could not store the aip part content of the mail: {}.", message_desc);
                                    e.printStackTrace();
                                }
                                final String certif = "Archivé le " + aip.getDocMeta().getDateArchivage()
                                        + " - Identifiant versement : " + aip.getDocMeta().getIdentifiantVersement()
                                        + " - Identifiant docPac : " + aip.getDocMeta().getIdentifiantDocPac();
                                resp.setCertificate(certif.getBytes());
                                resp.setAip(aip);
                            },
                            () -> resp.setErrorMessage("The aip file from the mail could not be parsed."));

                }
            }
        } else if (content instanceof String) { // If the content is a string its an receipt or a rejection.
            LOG.trace("Found avis");

            String avisContent = (String) content;
            parseXml(new ByteArrayInputStream(avisContent.getBytes()),
                fr.progilone.pgcn.domain.jaxb.avis.ObjectFactory.class)
                .map(elt -> {
					@SuppressWarnings("unchecked")
					JAXBElement<PacAvisType> jaxbElement = (JAXBElement<PacAvisType>) elt;
					return jaxbElement.getValue();
				})
                .ifPresentOrElse(
                    avis -> resp.setAvis(avis),
                    () -> resp.setErrorMessage("The avis content of the mail could not be parsed."));
        }
        return Optional.of(resp);
    }

    /**
     * Detect if the part in a multipart mail is containig the api.xml.
     * @param part the actual part to check for.
     * @return true if the part contains the aip.xml.
     */
    private boolean isPartAip(BodyPart part) throws MessagingException {
        return Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())
            && StringUtils.equals(part.getFileName(), "aip.xml");
    }

    /**
     * Detect if the part in a multipart mail is containig the avis.xml.
     * @param part the actual part to check for.
     * @return true if the part contains the avis.xml.
     */
    private boolean isPartAvis(BodyPart part) throws MessagingException {
        LOG.trace(part.getContentType());
        LOG.trace(part.getDisposition());

        return part.getDisposition() == null && part.getContentType().contains("xml");
    }

	/**
	 * Parse part content from the provided jaxb class generated from a schema.
	 * @param part
	 * @param jaxbBoundedClasses
	 * @return Optional of the associated jaxb class if the parsing was successfull empty otherwise.
	 */
	private Optional<?> parseXml(final InputStream part, final Class<?>... jaxbBoundedClass) {
		try {
			final JAXBContext context = JAXBContext.newInstance(jaxbBoundedClass);
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			return Optional.of(unmarshaller.unmarshal(part));
		}
		catch (final JAXBException e) {
            // XmlSchema schema = jaxbBoundedClasses.getClass().getPackage().getAnnotation(XmlSchema.class);
            LOG.error("Could not parse xml from part of mail with the current schema : {}.", jaxbBoundedClass.getClass().getName());
            e.printStackTrace();

			return Optional.empty();
		}
	}

	/**
	 * Mise à jour du rapport d'export à partir de la réponse extraite du mail.
	 * @param response
	 * @return treated : Vrai si msg lu et rapprt mis à jour.
	 */
	private boolean updateExport(final CinesResponse response, final Library library) {

		boolean treated = false;

		// Recherche de l'export CINES correspondant à l'identifiant de versement
		// précisé dans la réponse
		// cet id correspond au pgcnId de la docUnit
		final String idVersement;
		if (response.getAvis() != null) {
			idVersement = response.getAvis().getIdVersement();
		}
		else {
			idVersement = response.getAip().getDocMeta().getIdentifiantVersement();
		}
		final CinesReport report = cinesReportService.findByIdVersement(idVersement);
		if (report == null) {
			LOG.error("L'export CINES avec l'identifiant de versement {} n'a pas été trouvé", idVersement);
			return treated;
		}
		if (report.getDocUnit() == null || report.getDocUnit().getLibrary() == null
				|| !StringUtils.equals(library.getIdentifier(), report.getDocUnit().getLibrary().getIdentifier())) {
			LOG.info("L'export CINES avec l'identifiant de versement {} ne depend pas de cette librairie...",
					idVersement);
			return treated;
		}

		// AR de versement
		if (response.hasId(REP_ACCUSE_RECEPTION_DE_VERSEMENT)) {
			if (report.getStatus() == CinesReport.Status.SENT) {
				LOG.debug("L'export {} passe au statut {}", idVersement, CinesReport.Status.AR_RECEIVED);
				cinesReportService.setReportArReceived(report, response.getMsgDate());
				treated = true;
			}
		}
		// Rejet du versement
		else if (response.hasId(REP_REJET_VERSEMENT)) {
			if (report.getStatus() == CinesReport.Status.SENT || report.getStatus() == CinesReport.Status.AR_RECEIVED) {
				String motive = response.getAvis().getCommentaire();
				if (StringUtils.isBlank(motive)) {
					motive = response.getAvis().getErreurValidation();
				}
				LOG.debug("L'export {} passe au statut {} - motif : {}", idVersement, CinesReport.Status.REJECTED,
						motive);
				cinesReportService.setReportRejected(report, response.getMsgDate(), motive);
				treated = true;
			}
		}
		// Certificat d'archivage
		else if (response.hasId(REP_CERTIFICAT_ARCHIVAGE)) {
			if (report.getStatus() == CinesReport.Status.SENT || report.getStatus() == CinesReport.Status.AR_RECEIVED) {
				LOG.debug("L'export {} passe au statut {}", idVersement, CinesReport.Status.ARCHIVED);
				cinesReportService.setReportArchived(report, response.getMsgDate(), response.getCertificateAsString());
				treated = true;
			}
		}
		// Non géré
		else {
			LOG.warn("Le type de réponse {} n'est pas géré", response.getAvis().getId());
			treated = false;
		}
		return treated;
	}

	/**
	 * Assure le stockage du fichier aip.xml Unique par docUnit
	 * @param aip class with aip data.
	 * @param partIs input stream of the content of the part containing the aip.xml.
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void handleAip(final PacType aip, final InputStream partIs) throws IOException, MessagingException {
        final String idVersement = aip.getDocMeta() != null ? aip.getDocMeta().getIdentifiantDocProducteur() : null;
        if (idVersement == null) {
            return;
        }
        Optional.ofNullable(docUnitService.findOneByPgcnIdAndState(idVersement, DocUnit.State.AVAILABLE))
            .or(() -> Optional.ofNullable(docUnitService.findOneByPgcnIdAndState(idVersement, DocUnit.State.CLOSED)))
            .ifPresentOrElse(foundDoc -> {
                final Path root = Paths.get(workingDir,
                    exportCinesService.getDocLibraryId(foundDoc.getIdentifier()), foundDoc.getIdentifier());
                if (root != null) {
                    fm.copyInputStreamToFile(partIs, root.toFile(), AIP_XML_FILE, true, false);
                }
            }, () -> LOG.error("DocUnit non trouve - pgcnID = {}", idVersement));

	}

	/**
	 * Récupération du fichier aip.xml stocké pour un docUnit donné
	 * @param docUnit
	 * @return
	 */
	public File retrieveAip(final String docUnit) {
		if (docUnit != null) {
			final Path root = Paths.get(workingDir, exportCinesService.getDocLibraryId(docUnit), docUnit);
			if (root != null) {
				return fm.retrieveFile(root.toFile(), AIP_XML_FILE);
			}
		}
		return null;
	}

	/**
	 * Récupération du fichier sip.xml le + recent stocké en cache ou backuppé pour un
	 * docUnit donné.
	 * @param docUnitId
	 * @return
	 */
	public File retrieveSip(final String docUnitId, final boolean exportError) {

		if (docUnitId != null) {
			if (exportError) {
				final DocUnit docUnit = docUnitService.findOne(docUnitId);
				final Path root = Paths.get(cacheDir, exportCinesService.getDocLibraryId(docUnitId),
						docUnit.getPgcnId());
				if (root != null) {
					return fm.retrieveFile(root.toFile(), SIP_XML_FILE);
				}
			}
			else {

				final Path root = Paths.get(workingDir, exportCinesService.getDocLibraryId(docUnitId), docUnitId);
				if (root != null) {
					return fm.retrieveFile(root.toFile(), SIP_XML_FILE);
				}
			}
		}
		return null;
	}

	/**
	 * Récupération du fichier mets.xml le + recent stocké en cache ou backuppé pour un
	 * docUnit donné.
	 * @param docUnitId
	 * @return
	 */
	public File retrieveMets(final String docUnitId, final boolean exportError) {

		if (docUnitId != null) {
			if (exportError) {
				final DocUnit docUnit = docUnitService.findOne(docUnitId);
				final Path root = Paths.get(cacheDir, exportCinesService.getDocLibraryId(docUnitId),
						docUnit.getPgcnId());
				if (root != null) {
					return fm.retrieveFile(root.toFile(), METS_XML_FILE);
				}
			}
			else {

				final Path root = Paths.get(workingDir, exportCinesService.getDocLibraryId(docUnitId), docUnitId);
				if (root != null) {
					return fm.retrieveFile(root.toFile(), METS_XML_FILE);
				}
			}
		}
		return null;
	}

	/**
	 * Classe représentant une réponse de la part du CINES
	 */
	protected static class CinesResponse {

		private LocalDateTime msgDate;

		/**
		 * Corps du mail parsé suivant le schéma avis.xsd
		 */
		private PacAvisType avis;

		/**
		 * Fichier aip.xml parsé suivant le schéma aip.xsd
		 */
		private PacType aip;

		/**
		 * Contenu brut du certificat issu de l'avis correspondant
		 */
		private byte[] certificate;

		/**
		 * Message d'erreur eventuel.
		 */
		private String errorMessage;

		/**
		 * La réponse correspond-t-elle au type passé en paramètre ?
		 * @param repId
		 * @return
		 */
		public boolean hasId(final String repId) {
			return avis != null && StringUtils.equals(avis.getId(), repId);
		}

		public LocalDateTime getMsgDate() {
			return msgDate;
		}

		public void setMsgDate(final LocalDateTime msgDate) {
			this.msgDate = msgDate;
		}

		public PacAvisType getAvis() {
			return avis;
		}

		public void setAvis(final PacAvisType avis) {
			this.avis = avis;
		}

		public PacType getAip() {
			return aip;
		}

		public void setAip(final PacType aip) {
			this.aip = aip;
		}

		public String getCertificateAsString() {
			return getCertificateAsString(StandardCharsets.UTF_8);
		}

		public String getCertificateAsString(final Charset charEncoding) {
			return new String(this.certificate, charEncoding);
		}

		public void setCertificate(final byte[] certificate) {
			this.certificate = certificate;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(final String errorMessage) {
			this.errorMessage = errorMessage;
		}

	}

}
