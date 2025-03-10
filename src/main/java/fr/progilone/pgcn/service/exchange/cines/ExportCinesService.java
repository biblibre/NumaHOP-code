package fr.progilone.pgcn.service.exchange.cines;

import fr.progilone.pgcn.domain.administration.SftpConfiguration;
import fr.progilone.pgcn.domain.document.*;
import fr.progilone.pgcn.domain.dto.document.BibliographicRecordDcDTO;
import fr.progilone.pgcn.domain.dto.document.DocPropertyDTO;
import fr.progilone.pgcn.domain.exchange.cines.CinesReport;
import fr.progilone.pgcn.domain.library.Library;
import fr.progilone.pgcn.domain.library.LibraryParameter;
import fr.progilone.pgcn.domain.library.LibraryParameterValueCines.LibraryParameterValueCinesType;
import fr.progilone.pgcn.domain.lot.Lot;
import fr.progilone.pgcn.domain.storage.CheckSummedStoredFile;
import fr.progilone.pgcn.domain.storage.StoredFile;
import fr.progilone.pgcn.domain.workflow.DocUnitState;
import fr.progilone.pgcn.domain.workflow.WorkflowStateKey;
import fr.progilone.pgcn.domain.workflow.WorkflowStateStatus;
import fr.progilone.pgcn.exception.ExportCinesException;
import fr.progilone.pgcn.exception.PgcnTechnicalException;
import fr.progilone.pgcn.exception.PgcnUncheckedException;
import fr.progilone.pgcn.repository.lot.LotRepository;
import fr.progilone.pgcn.service.administration.SftpConfigurationService;
import fr.progilone.pgcn.service.document.DocUnitService;
import fr.progilone.pgcn.service.document.mapper.UIExportDataMapper;
import fr.progilone.pgcn.service.document.ui.UIBibliographicRecordService;
import fr.progilone.pgcn.service.es.EsDocUnitService;
import fr.progilone.pgcn.service.exchange.ssh.SftpService;
import fr.progilone.pgcn.service.library.LibraryParameterService;
import fr.progilone.pgcn.service.library.LibraryService;
import fr.progilone.pgcn.service.storage.BinaryStorageManager;
import fr.progilone.pgcn.service.storage.FileStorageManager;
import fr.progilone.pgcn.service.util.FileUtils.CheckSumType;
import fr.progilone.pgcn.service.util.TarUtils;
import fr.progilone.pgcn.service.util.transaction.TransactionService;
import fr.progilone.pgcn.service.util.transaction.TransactionalJobRunner;
import fr.progilone.pgcn.web.util.LibraryAccesssHelper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.MarshalException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

/**
 * Created by Sébastien on 27/12/2016.
 */
@Service
public class ExportCinesService {

	private static final Logger LOG = LoggerFactory.getLogger(ExportCinesService.class);

	private static final String FILE_METS_XML = "mets.xml";

	private static final String FILE_SIP_XML = "sip.xml";

	private static final String DIR_DEPOT = "DEPOT";

	private static final String DIR_DESC = "DESC";

	@Value("${instance.libraries}")
	private String[] instanceLibraries;

	@Value("${services.cines.cache}")
	private String workingDir;

	@Value("${services.cines.aip}")
	private String cinesAipDir;

	private final LibraryService libraryService;

	private final ExportMetsService exportMetsService;

	private final ExportSipService exportSipService;

	private final BinaryStorageManager bm;

	private final DocUnitService docUnitService;

	private final UIExportDataMapper uiExportDataMapper;

	private final UIBibliographicRecordService uiBibliographicRecordService;

	private final SftpConfigurationService sftpConfigurationService;

	private final CinesReportService cinesReportService;

	private final SftpService sftpService;

	private final FileStorageManager fm;

	private final LibraryParameterService libraryParameterService;

	private final TransactionService transactionService;

	private final LotRepository lotRepository;

	private final LibraryAccesssHelper libraryAccesssHelper;

	private final EsDocUnitService esDocUnitService;

	@Autowired
	public ExportCinesService(final ExportMetsService exportMetsService, final ExportSipService exportSipService,
			final BinaryStorageManager bm, final DocUnitService docUnitService,
			final UIExportDataMapper uiExportDataMapper,
			final UIBibliographicRecordService uiBibliographicRecordService, final LibraryService libraryService,
			final SftpConfigurationService sftpConfigurationService, final CinesReportService cinesReportService,
			final SftpService sftpService, final FileStorageManager fm,
			final LibraryParameterService libraryParameterService, final TransactionService transactionService,
			final LotRepository lotRepository, final LibraryAccesssHelper libraryAccesssHelper,
			final EsDocUnitService esDocUnitService) {

		this.exportMetsService = exportMetsService;
		this.exportSipService = exportSipService;
		this.bm = bm;
		this.docUnitService = docUnitService;
		this.uiExportDataMapper = uiExportDataMapper;
		this.uiBibliographicRecordService = uiBibliographicRecordService;
		this.libraryService = libraryService;
		this.sftpConfigurationService = sftpConfigurationService;
		this.cinesReportService = cinesReportService;
		this.sftpService = sftpService;
		this.fm = fm;
		this.libraryParameterService = libraryParameterService;
		this.transactionService = transactionService;
		this.lotRepository = lotRepository;
		this.libraryAccesssHelper = libraryAccesssHelper;
		this.esDocUnitService = esDocUnitService;
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

	public String getDocLibraryId(final String docUnitId) {
		final DocUnit doc = docUnitService.findOneWithLibrary(docUnitId);
		return doc.getLibrary().getIdentifier();
	}

	/**
	 * Export unitaire manuel. Génération du répertoire d'export et upload sur le serveur
	 * CINES
	 */
	@Transactional
	public ResponseEntity<CinesReport> exportDocUnitToCines(final HttpServletRequest request, final String docUnitId,
			final BibliographicRecordDcDTO metaDc, final boolean reversion, final boolean metaEad,
			SftpConfiguration conf) {

		final DocUnit docUnit = docUnitService.findOneWithAllDependencies(docUnitId, true);
		// Non trouvé
		if (docUnit == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Vérification de la bibliothèque de l'utilisateur
		// 1/ sur l'unité documentaire
		if (!libraryAccesssHelper.checkLibrary(request, docUnit, DocUnit::getLibrary)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		// 2/ sur la configuration SFTP
		if (conf != null) {
			conf = sftpConfigurationService.findOne(conf.getIdentifier());
			if (!libraryAccesssHelper.checkLibrary(request, conf, SftpConfiguration::getLibrary)) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		}
		else {
			// TODO: définir une configuration SFTP par défaut par type d'export
			final Set<SftpConfiguration> confs = sftpConfigurationService.findByLibrary(docUnit.getLibrary(), true);
			if (!confs.isEmpty()) {
				conf = confs.iterator().next();
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		// Traitement
		CinesReport report = cinesReportService.createCinesReport(docUnit);
		try {
			// Génération des fichiers / répertoires CINES
			final Path path = exportDocUnit(docUnit, reversion, metaDc, metaEad, true);

			// PAC V3 : on tar a partir du repertoire racine
			final Path tarpath = tarDirectory(path);

			// Tranferts du répertoire généré
			report = cinesReportService.setReportSending(report);
			sftpService.sftpPut(conf, tarpath);
			report = cinesReportService.setReportSent(report);

			// sauvegarde des fichiers sip.xml et mets.xml avant nettoyage
			keepLastCopySip(path, docUnit.getIdentifier());
			keepLastCopyMets(path, docUnit.getIdentifier());

			// Suppression du répertoire généré, après le traitement
			LOG.debug("Suppression du répertoire {}", path.toAbsolutePath().toString());
			FileUtils.deleteQuietly(path.toFile());
			FileUtils.deleteQuietly(tarpath.toFile());

			// pas d'erreur, on peut versionner
			docUnitService.incrementDocUnitVersion(docUnit);

			// Indexation
			esDocUnitService.indexAsync(report.getIdentifier());

			return new ResponseEntity<>(report, HttpStatus.OK);

		}
		catch (final MarshalException e) {
			if (e.getCause() != null) {
				return failReport(e.getCause(), report);
			}
			else {
				return failReport(e, report);
			}
		}
		catch (final Exception e) {
			return failReport(e, report);
		}
	}

	/**
	 * Export en masse. Génération du répertoire d'export et upload sur le serveur CINES
	 */
	@Transactional
	public ResponseEntity<CinesReport> exportDocUnitToCines(final HttpServletRequest request, final String docUnitId) {

		final DocUnit docUnit = docUnitService.findOneWithAllDependencies(docUnitId, true);
		final SftpConfiguration conf;
		// Non trouvé
		if (docUnit == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (docUnit.getRecords().isEmpty()) {
			LOG.debug("Impossible d'exporter l'unité documentaire [{}] : Pas de notice", docUnit.getPgcnId());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Vérification de la bibliothèque de l'utilisateur
		// Vérifier accès
		if (!libraryAccesssHelper.checkLibrary(request, docUnit, DocUnit::getLibrary)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		// Trouver une configuration SFTP
		final Set<SftpConfiguration> confs = sftpConfigurationService.findByLibrary(docUnit.getLibrary(), true);
		if (!confs.isEmpty()) {
			conf = confs.iterator().next();
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// Traitement
		CinesReport report = cinesReportService.createCinesReport(docUnit);
		try {
			// Génération des fichiers / répertoires CINES
			final BibliographicRecordDcDTO metaDc = getExportData(docUnit, true);
			final Path path = exportDocUnit(docUnit, true, metaDc, false, true);

			// PAC V3 : on tar a partir du repertoire racine
			final Path tarpath = tarDirectory(path);

			// Tranferts du répertoire généré
			report = cinesReportService.setReportSending(report);
			sftpService.sftpPut(conf, tarpath);
			report = cinesReportService.setReportSent(report);

			// sauvegarde des fichiers sip.xml et mets.xml avant nettoyage
			keepLastCopySip(path, docUnit.getIdentifier());
			keepLastCopyMets(path, docUnit.getIdentifier());

			// Suppression du répertoire généré, après le traitement
			LOG.debug("Suppression du répertoire {}", path.toAbsolutePath().toString());
			FileUtils.deleteQuietly(path.toFile());
			FileUtils.deleteQuietly(tarpath.toFile());

			// pas d'erreur, on peut versionner
			docUnitService.incrementDocUnitVersion(docUnit);

			// Indexation
			esDocUnitService.indexAsync(report.getDocUnit().getIdentifier());

			// pas d'erreur, on peut versionner
			docUnitService.incrementDocUnitVersion(docUnit);

			return new ResponseEntity<>(report, HttpStatus.OK);

		}
		catch (final MarshalException e) {
			if (e.getCause() != null) {
				return failReport(e.getCause(), report);
			}
			else {
				return failReport(e, report);
			}
		}
		catch (final Exception e) {
			return failReport(e, report);
		}
	}

	/**
	 * @param e
	 * @param report
	 * @return
	 */
	private ResponseEntity<CinesReport> failReport(final Throwable e, CinesReport report) {
		LOG.error(e.getMessage(), e);
		report = cinesReportService.failReport(report, e.getMessage());
		esDocUnitService.indexAsync(report.getDocUnit().getIdentifier());
		return new ResponseEntity<>(report, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * <h1>Export CINES</h1>
	 * <p>
	 * La PGCN génère un dossier SIP (Submit information Package) contenant les
	 * métadonnées du document (mets.xml), l'ensemble des fichiers masters et le fichier
	 * sip.xml.
	 * </p>
	 * <p>
	 * Les fichiers suivants sont à générer dans un dossier par document numérique (le nom
	 * du dossier est l’identifiant) :
	 * <ul>
	 * <li>Fichier <strong>sip.xml</strong>: métadonnées du document à archiver</li>
	 * <li>Dossier <strong>DEPOT</strong>:
	 * <ul>
	 * <li>Fichiers à archiver (images, PDF…)</li>
	 * </ul>
	 * </li>
	 * <li>Dossier <strong>DESC</strong>
	 * <ul>
	 * <li>Fichier <strong>mets.xml</strong>: métadonnées de pérennisation, format DC</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * </p>
	 * <p>
	 * L'envoi doit se faire en fonction des protocoles utilisés par les spécifications de
	 * la fonctionnalité “Transfert d’archives” de la PAC, via un transfert SFTP. Suite à
	 * l’envoi et à l’archivage, la PGCN récupèrera les informations suivantes afin de les
	 * communiquer à l’utilisateur :
	 * <ul>
	 * <li>après l’envoi : récupération et stockage de l’accusé de réception;</li>
	 * <li>après l’archivage :
	 * <ul>
	 * <li>en cas de succès : mise à jour dans le document numérique des informations
	 * contenues dans l’AIP (Archival Information Package): identifiant de l'archive, la
	 * date de l'archivage. Le certificat d’archivage lui-même sera stocké sous forme de
	 * fichier attaché au document numérique.</li>
	 * <li>en cas d’échec : la cause de l’échec (signalée par la PAC à l'utilisateur) est
	 * intégrée à la PGCN et affichée à l’utilisateur sous forme de warning, avec le motif
	 * de rejet.</li>
	 * </ul>
	 * </ul>
	 * </p>
	 * <p>
	 * Chaque bibliothèque est considérée comme un propre service versant au CINES. Ainsi,
	 * elle a son propre espace de connexion.
	 * </p>
	 */
	public Path exportDocUnit(final DocUnit docUnit, final boolean reversion, final BibliographicRecordDcDTO metaDc,
			final boolean metaEad, final boolean generateSip) throws IOException, JAXBException, PgcnTechnicalException,
			SAXException, NoSuchAlgorithmException, ExportCinesException {

		final boolean verifiedReversion;
		// on est a priori en MAJ => on verifie si c'est coherent.
		if (!reversion && cinesReportService.findByDocUnit(docUnit.getIdentifier())
			.stream()
			.noneMatch(cr -> CinesReport.Status.ARCHIVED == cr.getStatus()
					|| CinesReport.Status.AR_RECEIVED == cr.getStatus())) {
			// pas de maj possible => reversion
			verifiedReversion = true;
		}
		else {
			verifiedReversion = reversion;
		}

		save(docUnit, metaDc);
		final String libraryId = docUnit.getLibrary().getIdentifier();

		Path root = Paths.get(workingDir, libraryId, docUnit.getPgcnId());
		// Suppression du répertoire pre-existant
		if (Files.exists(root)) {
			LOG.warn("Le répertoire {} est supprimé car il existe déjà", root);
			FileUtils.deleteDirectory(root.toFile());
		}
		// Création du répertoire
		LOG.debug("Création du répertoire {}", root);
		root = Files.createDirectory(root);
		// DEPOT: fichiers à archiver
		final Path depotPath = createArchiveDirectory(root, DIR_DEPOT);
		// Ajout des fichiers à archiver
		try {
			final List<CheckSummedStoredFile> listWithCheckSummedStoredFiles = addDepotFiles(docUnit, depotPath,
					libraryId, verifiedReversion);
			// DEPOT/DESC: métadonnées de pérennisation, format DC, lien des fichiers
			final String checkSumMets = createDocUnitMetaData(docUnit, metaDc, metaEad, depotPath,
					listWithCheckSummedStoredFiles, DIR_DESC, FILE_METS_XML);
			if (generateSip) {
				// SIP.xml: métadonnées du document à archiver
				createDocUnitMetadata(docUnit, metaDc, listWithCheckSummedStoredFiles, root, checkSumMets, FILE_SIP_XML,
						verifiedReversion);
			}

		}
		catch (final UncheckedIOException e) {
			throw new IOException(e);
		}
		catch (final PgcnUncheckedException e) {
			throw new PgcnTechnicalException(e);
		}

		return root;
	}

	/**
	 * sip.xml: métadonnées du document à archiver.
	 * @param docUnit
	 * @param metaDc
	 * @param list
	 * @param root
	 * @param checkSumMets
	 * @param fileSip
	 * @param reversion
	 * @throws IOException
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws PgcnTechnicalException
	 * @throws ExportCinesException
	 */
	private void createDocUnitMetadata(final DocUnit docUnit, final BibliographicRecordDcDTO metaDc,
			final List<CheckSummedStoredFile> list, final Path root, final String checkSumMets, final String fileSip,
			final boolean reversion)
			throws IOException, JAXBException, SAXException, PgcnTechnicalException, ExportCinesException {

		final Path sipPath = Files.createFile(root.resolve(fileSip));
		LOG.debug("Écriture du fichier {}", sipPath.toString());

		try (final OutputStream out = new FileOutputStream(sipPath.toFile());
				final OutputStream bufOut = new BufferedOutputStream(out)) {
			exportSipService.writeMetadata(bufOut, metaDc, docUnit, list, checkSumMets, reversion);
			bufOut.flush();
		}
	}

	/**
	 * DEPOT: fichiers à archiver
	 * @param root
	 * @param dirDepot
	 * @return Path du répertoire créé
	 * @throws IOException
	 */
	private Path createArchiveDirectory(final Path root, final String dirDepot) throws IOException {
		final Path depotPath = Files.createDirectory(root.resolve(dirDepot));
		LOG.debug("Répertoire {} créé", depotPath.toString());
		return depotPath;
	}

	/**
	 * DEPOT : ajout des fichiers à archiver
	 * @param docUnit
	 * @param depotPath
	 * @return la liste de checksum permettant d'éviter un recalcul
	 */
	private List<CheckSummedStoredFile> addDepotFiles(final DocUnit docUnit, final Path depotPath,
			final String libraryId, final boolean reversion) {

		final List<CheckSummedStoredFile> checkSums = new ArrayList<>();
		docUnit.getDigitalDocuments().forEach(digitalDoc -> {
			// # 2503 filtre car on ne veut pas un éventuel pdf associé.
			List<DocPage> pages = digitalDoc.getOrderedPages()
				.stream()
				.filter(page -> page.getNumber() != null)
				.collect(Collectors.toList());
			if (docUnit.getLot().getRequiredFormat().equalsIgnoreCase("PDF")) {
				pages = digitalDoc.getOrderedPages()
					.stream()
					.filter(page -> page.getNumber() == null)
					.collect(Collectors.toList());
			}
			pages.forEach(page -> {
				if (page.getMaster().isPresent()) {
					final StoredFile masterStoredFile = page.getMaster().get();
					final File sourceFile = bm.getFileForStoredFile(masterStoredFile, libraryId);
					final Path sourcePath = Paths.get(sourceFile.getAbsolutePath());
					try {
						if (reversion || docUnit.getCinesVersion() == null || docUnit.getCinesVersion() < 2) {
							final Path destPath = Files.createFile(depotPath.resolve(masterStoredFile.getFilename()));
							Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
						}
						// On remplit la map pour optimiser le traitement ultérieur des
						// métadonnées
						checkSums.add(exportMetsService.getCheckSummedStoredFile(masterStoredFile, sourceFile));
					}
					catch (final IOException e) {
						throw new UncheckedIOException(e);
					}
				}
			});
		});
		return checkSums;
	}

	/**
	 * DESC: métadonnées de pérennisation, format DC
	 * @param docUnit
	 * @param root
	 * @param listStoredFiles
	 * @param dirDesc
	 * @param fileMets
	 * @throws IOException
	 * @throws SAXException
	 * @throws NoSuchAlgorithmException
	 */
	private String createDocUnitMetaData(final DocUnit docUnit, final BibliographicRecordDcDTO metaDc,
			final boolean metaEad, final Path root, final List<CheckSummedStoredFile> listStoredFiles,
			final String dirDesc, final String fileMets)
			throws IOException, JAXBException, SAXException, NoSuchAlgorithmException {

		final Path descPath = Files.createDirectory(root.resolve(dirDesc));
		LOG.debug("Répertoire {} créé", descPath.toString());

		final Path metsPath = Files.createFile(descPath.resolve(fileMets));
		LOG.debug("Écriture du fichier {}", metsPath.toString());

		try (final OutputStream out = new FileOutputStream(metsPath.toFile());
				final OutputStream bufOut = new BufferedOutputStream(out)) {

			exportMetsService.writeMetadata(bufOut, docUnit, metaDc, metaEad, listStoredFiles);
			bufOut.flush();
		}
		return fr.progilone.pgcn.service.util.FileUtils.checkSum(metsPath.toFile(), CheckSumType.MD5);
	}

	@Transactional
	public BibliographicRecordDcDTO getExportData(final String identifier) {
		final DocUnit du = docUnitService.findOne(identifier);
		return getExportData(du, true);
	}

	@Transactional
	public BibliographicRecordDcDTO getExportData(final DocUnit docUnit, final boolean cinesExport) {
		final ExportData ed = docUnit.getExportData();
		final BibliographicRecordDcDTO dto;
		if (ed != null) {
			dto = new BibliographicRecordDcDTO();
			ed.getProperties()
				.stream()
				.filter(p -> p.getType().getSuperType() == DocPropertyType.DocPropertySuperType.DC)
				.sorted(Comparator.comparing(ExportProperty::getRank))
				.forEach(p -> {
					try {
						final String dcProperty = p.getType().getIdentifier();
						@SuppressWarnings("unchecked")
						final List<String> current = (List<String>) PropertyUtils.getSimpleProperty(dto, dcProperty);
						current.add(p.getValue());

					}
					catch (final ReflectiveOperationException | IllegalArgumentException e) {
						LOG.error(e.getMessage(), e);
					}
				});

		}
		else {
			final BibliographicRecord record = docUnit.getRecords().iterator().next();

			dto = uiBibliographicRecordService.getOneDc(record.getIdentifier());
			if (cinesExport) {
				final List<DocPropertyDTO> customProps = dto.getCustomProperties()
					.stream()
					.filter(p -> !StringUtils.equals(p.getType().getSuperType(),
							DocPropertyType.DocPropertySuperType.CUSTOM_ARCHIVE.name())
							&& !StringUtils.equals(p.getType().getSuperType(),
									DocPropertyType.DocPropertySuperType.CUSTOM_OMEKA.name()))
					.collect(Collectors.toList());
				dto.setCustomProperties(customProps);
			}
		}

		setDefaultValues(docUnit, dto);
		return dto;
	}

	@Transactional
	public void save(final String docUnitId, final BibliographicRecordDcDTO metaDc) {
		final DocUnit docUnit = docUnitService.findOne(docUnitId);
		save(docUnit, metaDc);
	}

	private void save(final DocUnit docUnit, final BibliographicRecordDcDTO metaDc) {
		final ExportData exportData = new ExportData();
		uiExportDataMapper.mapInto(metaDc, exportData);
		exportData.setDocUnit(docUnit);
		docUnit.setExportData(exportData);
		docUnitService.save(docUnit);
	}

	private void setDefaultValues(final DocUnit docUnit, final BibliographicRecordDcDTO dto) {
		// Vérification de la présence de configuration
		final LibraryParameter libraryParameter = libraryParameterService
			.findCinesParameterForLibrary(docUnit.getLibrary());
		// Si elle est présente alors elle est initialisée (entityGraph)
		if (libraryParameter != null) {
			if (dto.getCreator().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getCreator(), libraryParameter,
						LibraryParameterValueCinesType.CREATOR_DEFAULT_VALUE);
			}
			if (dto.getPublisher().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getPublisher(), libraryParameter,
						LibraryParameterValueCinesType.PUBLISHER_DEFAULT_VALUE);
			}
			if (dto.getSubject().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getSubject(), libraryParameter,
						LibraryParameterValueCinesType.SUBJECT_DEFAULT_VALUE);
			}
			if (dto.getTitle().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getTitle(), libraryParameter,
						LibraryParameterValueCinesType.TITLE_DEFAULT_VALUE);
			}
			if (dto.getDescription().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getDescription(), libraryParameter,
						LibraryParameterValueCinesType.DESCRIPTION_DEFAULT_VALUE);
			}
			if (dto.getType().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getType(), libraryParameter,
						LibraryParameterValueCinesType.TYPE_DEFAULT_VALUE);
			}
			if (dto.getFormat().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getFormat(), libraryParameter,
						LibraryParameterValueCinesType.FORMAT_DEFAULT_VALUE);
			}
			if (dto.getRights().isEmpty()) {
				exportSipService.handleDefaultValueForType(dto.getRights(), libraryParameter,
						LibraryParameterValueCinesType.RIGHTS_DEFAULT_VALUE);
			}
			if (dto.getDate().isEmpty()) {
				dto.getDate().add("s.d.");
			}
		}
	}

	/**
	 * Sauvegarde une copie du fichier SIP avant le nettoyage du depot du cache en fin
	 * d'export CINES.
	 */
	public void keepLastCopySip(final Path srcPath, final String docUnit) {

		final File sip = fm.retrieveFile(srcPath.toFile(), FILE_SIP_XML);
		final Path destPath = Paths.get(cinesAipDir, getDocLibraryId(docUnit), docUnit);

		try (final BufferedInputStream input = new BufferedInputStream(new FileInputStream(sip))) {
			fm.copyInputStreamToFile(input, destPath.toFile(), FILE_SIP_XML, true, false);

		}
		catch (final FileNotFoundException e) {
			LOG.error("Fichier {} non trouvé.", sip.getName(), e);
		}
		catch (final IOException e) {
			LOG.error("Erreur {} lors du traitement du fichier {}.", e.getMessage(), sip.getName(), e);
		}
	}

	/**
	 * Sauvegarde une copie du fichier METS avant le nettoyage du depot du cache en fin
	 * d'export CINES.
	 */
	public void keepLastCopyMets(final Path srcPath, final String docUnit) {

		final Path metsPath = Paths.get(srcPath.toFile().getAbsolutePath(), DIR_DEPOT, DIR_DESC);
		final File mets = fm.retrieveFile(metsPath.toFile(), FILE_METS_XML);
		final Path destPath = Paths.get(cinesAipDir, getDocLibraryId(docUnit), docUnit);

		try (final BufferedInputStream input = new BufferedInputStream(new FileInputStream(mets))) {
			fm.copyInputStreamToFile(input, destPath.toFile(), FILE_METS_XML, true, false);

		}
		catch (final FileNotFoundException e) {
			LOG.error("Fichier {} non trouvé.", mets.getName(), e);
		}
		catch (final IOException e) {
			LOG.error("Erreur {} lors du traitement du fichier {}.", e.getMessage(), mets.getName(), e);
		}
	}

	/**
	 * Sauvegarde une copie du fichier METS avant le nettoyage du depot du cache pour le
	 * rattrapage de génération mets.
	 */
	public void keepLastCopyMetsAdmin(final Path srcPath, final String docUnit, final String libraryId) {

		final Path metsPath = Paths.get(srcPath.toFile().getAbsolutePath(), DIR_DEPOT, DIR_DESC);
		final File mets = fm.retrieveFile(metsPath.toFile(), FILE_METS_XML);
		final Path destPath = Paths.get(cinesAipDir, libraryId, docUnit);

		try (final BufferedInputStream input = new BufferedInputStream(new FileInputStream(mets))) {
			fm.copyInputStreamToFileAdmin(input, destPath.toFile(), FILE_METS_XML, true);

		}
		catch (final FileNotFoundException e) {
			LOG.error("Fichier {} non trouvé.", mets.getName(), e);
		}
		catch (final IOException e) {
			LOG.error("Erreur {} lors du traitement du fichier {}.", e.getMessage(), mets.getName(), e);
		}
	}

	/**
	 * Retrouve les docUnit candidates pour l'export vers CINES.
	 */
	public List<String> findDocUnitsReadyForCinesExport() {

		final List<String> docsToExport = new ArrayList<>();

		final List<Library> libraries = libraryService.findAllByActive(true);
		libraries.stream()
			.filter(lib -> CollectionUtils.isNotEmpty(sftpConfigurationService.findByLibrary(lib, true)))
			.forEach(lib -> {

				final List<String> archivablesIds = docUnitService
					.findDocUnitByLibraryForCinesExport(lib.getIdentifier());

				final TransactionalJobRunner<String> job = new TransactionalJobRunner<>(archivablesIds,
						transactionService);
				job.setCommit(1)
					.setReadOnly(true)
					.setElementName("DocUnit (Export Cines " + lib.getName() + ")")
					.forEach(id -> {
						final DocUnit doc = docUnitService.findOne(id);

						final boolean notArchived = cinesReportService.findByDocUnit(doc.getIdentifier())
							.stream()
							.filter(cr -> CinesReport.Status.ARCHIVED == cr.getStatus()
									|| CinesReport.Status.AR_RECEIVED == cr.getStatus()
									|| CinesReport.Status.SENT == cr.getStatus())
							.collect(Collectors.toList())
							.isEmpty();

						if (notArchived
								&& (doc.getWorkflow().getCurrentStateByKey(WorkflowStateKey.ARCHIVAGE_DOCUMENT) != null
										&& doc.getWorkflow()
											.getCurrentStateByKey(WorkflowStateKey.ARCHIVAGE_DOCUMENT)
											.isCurrentState())) {
							docsToExport.add(doc.getIdentifier());
						}

						return true;
					})
					.process();

			});
		return docsToExport;
	}

	public CinesReport exportDocToCines(final String docUnitId, final boolean reversion) {

		// On ouvre une transation en fin d'export du doc si success.
		final TransactionStatus status = transactionService.startTransaction(false);

		final DocUnit doc = docUnitService.findOneWithAllDependencies(docUnitId, true);
		// Traitement
		CinesReport report = cinesReportService.createCinesReport(doc);

		// get SFTP conf.
		final SftpConfiguration conf = sftpConfigurationService.findByLibrary(doc.getLibrary(), true)
			.stream()
			.findFirst()
			.orElse(null);
		if (conf == null) {
			LOG.trace("SFTP Conf. introuvable => DocUnit[{}] - Export Cines impossible.", doc.getIdentifier());
			transactionService.rollbackTransaction(status);
			report = cinesReportService.failReport(report, "Configuration SFTP introuvable - export Cines impossible");
			return report;
		}

		// Recup données depuis exportData, ou sinon depuis la notice.
		final BibliographicRecordDcDTO metaDC = getExportData(doc, true);

		try {
			// Génération des fichiers / répertoires CINES
			final Path path = exportDocUnit(doc, reversion, metaDC, false, true);

			// PAC V3 : on tar a partir du repertoire racine
			final Path tarpath = tarDirectory(path);

			// Tranferts du répertoire généré
			report = cinesReportService.setReportSending(report);
			sftpService.sftpPut(conf, tarpath);
			report = cinesReportService.setReportSent(report);

			// sauvegarde des fichiers sip.xml et mets.xml avant nettoyage
			keepLastCopySip(path, doc.getIdentifier());
			keepLastCopyMets(path, doc.getIdentifier());

			// Suppression du répertoire généré, après le traitement
			LOG.debug("Suppression du répertoire {}", path.toAbsolutePath().toString());
			FileUtils.deleteQuietly(path.toFile());
			FileUtils.deleteQuietly(tarpath.toFile());

			docUnitService.incrementDocUnitVersion(doc);

			transactionService.commitTransaction(status);

		}
		catch (final MarshalException e) {
			transactionService.rollbackTransaction(status);
			final String msg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LOG.error("ERREUR EXPORT CINES : {}", msg, e);
			report = cinesReportService.failReport(report, msg);

		}
		catch (final Exception e) {
			transactionService.rollbackTransaction(status);
			LOG.error(e.getMessage(), e);
			report = cinesReportService.failReport(report, e.getMessage());
		}
		finally {
			// Indexation
			esDocUnitService.indexAsync(report.getDocUnit().getIdentifier());
		}
		return report;
	}

	public Path tarDirectory(final Path depot) throws ExportCinesException, IOException {

		final File tarDirectory = depot.getParent().toFile();
		final String tarName = depot.getFileName() + ".tar";
		final List<File> files = Files.walk(depot).map(Path::toFile).collect(Collectors.toList());
		final File[] tabFiles = files.toArray(new File[files.size()]);

		try {
			TarUtils.compress(tarDirectory.getAbsolutePath() + File.separator + tarName, depot, tabFiles);
		}
		catch (final IOException e) {
			LOG.error("Erreur lors de la compression du tar {}", tarName, e);
			throw new ExportCinesException("Erreur lors de la compression du tar " + tarName);
		}

		final Path tarPath = Paths.get(tarDirectory.getAbsolutePath(), tarName);

		return tarPath;
	}

	public void regenerateMetsforArchivedDocUnits(final String libraryId) {

		// lots concernés : actifs ou non mais pas encore archivés (on a besoin des
		// fichiers)...
		final Set<String> idLots = lotRepository.findAllByProjectLibraryIdentifierIn(Arrays.asList(libraryId))
			.stream()
			.filter(lo -> !lo.isFilesArchived())
			.map(Lot::getIdentifier)
			.collect(Collectors.toSet());

		idLots.forEach(idLot -> {
			// docUnits : les docUnits qui ont été archivés au Cines
			final Set<String> docIds = docUnitService.findAllByLotId(idLot)
				.stream()
				.filter(doc -> doc.getCinesVersion() != null) // on veut les docUnits déjà
																// archivés
				.map(DocUnit::getIdentifier)
				.collect(Collectors.toSet());

			LOG.info("Regenerate Mets for Archived DocUnits - lot {}", idLot);
			docIds.forEach(id -> {
				transactionService.executeInNewTransaction(() -> {

					// recup docUnit bien lourde
					final DocUnit docUnit = docUnitService.findOneWithAllDependencies(id, true);
					if (docUnit != null && docUnit.getWorkflow() != null) {

						final List<DocUnitState> statesForKey = docUnit.getWorkflow()
							.getByKey(WorkflowStateKey.ARCHIVAGE_DOCUMENT);
						if (statesForKey.size() == 1) {

							final DocUnitState state = statesForKey.get(0);
							if (state.isDone() && state.getStatus() == WorkflowStateStatus.FINISHED) {

								try {
									// Génération des fichiers / répertoires CINES
									final BibliographicRecordDcDTO metaDc = getExportData(docUnit, true);
									final Path path = exportDocUnit(docUnit, true, metaDc, false, false);

									// Ce pourquoi on est la !!
									keepLastCopyMetsAdmin(path, docUnit.getIdentifier(), libraryId);

									// Suppression du répertoire généré, après le
									// traitement
									LOG.debug("Suppression du répertoire {}", path.toAbsolutePath().toString());
									FileUtils.deleteQuietly(path.toFile());

								}
								catch (final Exception e) {
									LOG.error("Erreur Marshalling:  ", e);
								}
							}
						}
					}
				});
			});
		});

	}

}
