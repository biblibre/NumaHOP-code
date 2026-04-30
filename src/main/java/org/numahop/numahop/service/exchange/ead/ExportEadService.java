package org.numahop.numahop.service.exchange.ead;

import org.numahop.numahop.domain.jaxb.ead.Archdesc;
import org.numahop.numahop.domain.jaxb.ead.AvLevel;
import org.numahop.numahop.domain.jaxb.ead.C;
import org.numahop.numahop.domain.jaxb.ead.Did;
import org.numahop.numahop.domain.jaxb.ead.Dsc;
import org.numahop.numahop.domain.jaxb.ead.Ead;
import org.numahop.numahop.domain.jaxb.ead.Eadheader;
import org.numahop.numahop.domain.jaxb.ead.Eadid;
import org.numahop.numahop.domain.jaxb.ead.Filedesc;
import org.numahop.numahop.domain.jaxb.ead.ObjectFactory;
import org.numahop.numahop.domain.jaxb.ead.Titleproper;
import org.numahop.numahop.domain.jaxb.ead.Titlestmt;
import org.numahop.numahop.domain.jaxb.ead.Unitid;
import org.numahop.numahop.service.storage.FileStorageManager;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

/**
 * Created by Sébastien on 31/05/2017.
 */
@Service
public class ExportEadService {

	private static final Logger LOG = LoggerFactory.getLogger(ExportEadService.class);

	// private static final String EAD_SCHEMA_VALIDATION =
	// "http://www.loc.gov/ead/ead.xsd";
	private static final String NS_EAD = "urn:isbn:1-931666-22-9";

	private static final String EAD_XML_FILE = "ead.xml";

	// Stockage des EAD
	@Value("${uploadPath.ead}")
	private String workingDir;

	private final FileStorageManager fm;

	@Autowired
	public ExportEadService(final FileStorageManager fm) {
		this.fm = fm;
	}

	@PostConstruct
	public void initialize() {
		fm.initializeStorage(workingDir);
	}

	/**
	 * Exporte les éléments fournis en paramètres dans un fichier EAD
	 * @param docUnitId
	 * @param eadheader
	 * @param c
	 */
	public void exportEad(final String docUnitId, final Eadheader eadheader, final C c) {
		if (docUnitId != null && c != null) {
			try {
				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final Ead ead = getEad(docUnitId, eadheader, c);
				writeEad(out, ead);

				final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
				final Path root = Paths.get(workingDir);
				LOG.debug("Export EAD dans le répertoire {}", root.toAbsolutePath());
				fm.copyInputStreamToFileWithOtherDirs(in, root.toFile(), Arrays.asList(docUnitId), EAD_XML_FILE, true,
						true);

			}
			catch (IOException | JAXBException | SAXException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Récupère le fichier EAD lié à l'unité documentaire
	 * @param docUnitId
	 * @return
	 */
	public File retrieveEad(final String docUnitId) {
		final Path root = Paths.get(workingDir);
		if (root != null) {
			return fm.retrieveFileWithOtherDirs(root.toFile(), Arrays.asList(docUnitId), EAD_XML_FILE);
		}
		return null;
	}

	/**
	 * L'unité documentaire est-elle liée à un fichier EAD sur le serveur ?
	 * @param docUnitId
	 * @return
	 */
	public boolean hasEadExport(final String docUnitId) {
		final File file = retrieveEad(docUnitId);
		return file != null && file.exists();
	}

	/**
	 * Écriture du XML EAD dans out
	 * @param out
	 * @param ead
	 * @throws JAXBException
	 * @throws MalformedURLException
	 * @throws SAXException
	 */
	private void writeEad(final OutputStream out, final Ead ead)
			throws JAXBException, MalformedURLException, SAXException {
		// Écriture du XML dans le flux de sortie
		final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class); // EAD
		final Marshaller m = context.createMarshaller();
		// Validation
		// final SchemaFactory sf =
		// SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// m.setSchema(sf.newSchema(new URL(EAD_SCHEMA_VALIDATION)));
		m.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		m.marshal(ead, out);
	}

	/**
	 * Construction de l'objet EAD à partir de eadHeader et de c
	 * @param identifier
	 * @param c
	 * @param eadheader
	 * @return
	 */
	private Ead getEad(final String identifier, final Eadheader eadheader, final C c) {
		final Dsc dsc = new Dsc();
		dsc.getCAndThead().add(c);

		final Did did = new Did();
		final Unitid unitid = new Unitid();
		unitid.setId(identifier);
		did.getMDid().add(unitid);

		final Archdesc archDesc = new Archdesc();
		archDesc.setDid(did);
		archDesc.setLevel(AvLevel.OTHERLEVEL);
		archDesc.getMDescFull().add(dsc);

		final Ead ead = new Ead();
		ead.setEadheader(eadheader != null ? eadheader : getDefaultEadheader(identifier));
		ead.setArchdesc(archDesc);

		return ead;
	}

	private Eadheader getDefaultEadheader(final String identifier) {
		final Eadheader eadheader = new Eadheader();
		final Eadid eadId = new Eadid();
		eadId.setContent(identifier);
		eadheader.setEadid(eadId);

		final Filedesc filedesc = new Filedesc();
		final Titlestmt titlestmt = new Titlestmt();
		final Titleproper titleproper = new Titleproper();
		titleproper.getContent().add(EAD_XML_FILE);
		titlestmt.getTitleproper().add(titleproper);
		filedesc.setTitlestmt(titlestmt);
		eadheader.setFiledesc(filedesc);

		return eadheader;
	}

}
