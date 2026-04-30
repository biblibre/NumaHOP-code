package org.numahop.numahop.service.exchange.cines;

import static org.junit.jupiter.api.Assertions.*;

import org.numahop.numahop.domain.document.BibliographicRecord;
import org.numahop.numahop.domain.document.DocProperty;
import org.numahop.numahop.domain.document.DocPropertyType;
import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.exception.ExportCinesException;
import org.numahop.numahop.exception.PgcnTechnicalException;
import org.numahop.numahop.repository.lot.LotRepository;
import org.numahop.numahop.service.administration.SftpConfigurationService;
import org.numahop.numahop.service.document.DocUnitService;
import org.numahop.numahop.service.document.mapper.UIExportDataMapper;
import org.numahop.numahop.service.document.ui.UIBibliographicRecordService;
import org.numahop.numahop.service.es.EsDocUnitService;
import org.numahop.numahop.service.exchange.ssh.SftpService;
import org.numahop.numahop.service.library.LibraryParameterService;
import org.numahop.numahop.service.library.LibraryService;
import org.numahop.numahop.service.storage.BinaryStorageManager;
import org.numahop.numahop.service.storage.FileStorageManager;
import org.numahop.numahop.service.util.transaction.TransactionService;
import org.numahop.numahop.web.util.LibraryAccesssHelper;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXException;

/**
 * Created by Sébastien on 27/12/2016.
 */
@ExtendWith(MockitoExtension.class)
public class ExportCinesServiceTest {

	private static final String WORKING_DIR = FileUtils.getTempDirectoryPath() + "/pgcn_test";

	@Mock
	private ExportMetsService exportMetsService;

	@Mock
	private ExportSipService exportSipService;

	@Mock
	private BinaryStorageManager bm;

	@Mock
	private DocUnitService docUnitService;

	@Mock
	private UIExportDataMapper uiExportDataMapper;

	@Mock
	private UIBibliographicRecordService uiBibliographicRecordService;

	@Mock
	private SftpConfigurationService sftpConfigurationService;

	@Mock
	private CinesReportService cinesReportService;

	@Mock
	private EsDocUnitService esDocUnitService;

	@Mock
	private SftpService sftpService;

	@Mock
	private FileStorageManager fm;

	@Mock
	private LibraryService libraryService;

	@Mock
	private LibraryParameterService libraryParameterService;

	@Mock
	private TransactionService transactionService;

	@Mock
	private LotRepository lotRepository;

	@Mock
	private LibraryAccesssHelper libraryAccesssHelper;

	private ExportCinesService service;

	@BeforeAll
	public static void init() throws IOException {
		FileUtils.forceMkdir(new File(WORKING_DIR));
	}

	@AfterAll
	public static void clean() {
		FileUtils.deleteQuietly(new File(WORKING_DIR));
	}

	@BeforeEach
	public void setUp() {
		service = new ExportCinesService(exportMetsService, exportSipService, bm, docUnitService, uiExportDataMapper,
				uiBibliographicRecordService, libraryService, sftpConfigurationService, cinesReportService, sftpService,
				fm, libraryParameterService, transactionService, lotRepository, libraryAccesssHelper, esDocUnitService);
		ReflectionTestUtils.setField(service, "workingDir", WORKING_DIR);
	}

	@Test
	public void testExportDocUnit() throws IOException, JAXBException, PgcnTechnicalException, SAXException,
			NoSuchAlgorithmException, ExportCinesException {
		final DocUnit docUnit = getDocUnit();
		service.exportDocUnit(docUnit, false, null, false, true);

		assertTrue(Files.exists(Paths.get(WORKING_DIR, docUnit.getPgcnId(), "DEPOT")));
		assertTrue(Files.exists(Paths.get(WORKING_DIR, docUnit.getPgcnId(), "DEPOT", "DESC")));
		assertTrue(Files.exists(Paths.get(WORKING_DIR, docUnit.getPgcnId(), "DEPOT", "DESC", "mets.xml")));
		assertTrue(Files.exists(Paths.get(WORKING_DIR, docUnit.getPgcnId(), "sip.xml")));
	}

	@Test
	public void testTarDepot() throws IOException, JAXBException, PgcnTechnicalException, SAXException,
			NoSuchAlgorithmException, ExportCinesException {

		final DocUnit docUnit = getDocUnit();
		final Path depot = service.exportDocUnit(docUnit, false, null, false, true);

		final Path newPath = Paths.get(depot.getParent().toFile().getAbsolutePath(), depot.toFile().getName());
		final Path tpath = service.tarDirectory(newPath);
		assertTrue(Files.exists(tpath)); // il existe
		assertTrue(Files.exists(Paths.get(WORKING_DIR, docUnit.getPgcnId() + ".tar"))); // et
																						// bien
																						// là
																						// ou
																						// on
																						// l'attend
																						// en
																						// +...
	}

	private DocUnit getDocUnit() {
		final DocPropertyType dcCreator = new DocPropertyType();
		dcCreator.setIdentifier("creator");

		final DocProperty creator = new DocProperty();
		creator.setType(dcCreator);
		creator.setValue("Bret Easton Ellis");

		final DocPropertyType dcTitle = new DocPropertyType();
		dcTitle.setIdentifier("title");
		final DocProperty title = new DocProperty();
		title.setType(dcTitle);
		title.setValue("American Psycho");

		final BibliographicRecord record1 = new BibliographicRecord();
		record1.setIdentifier("REC-001");
		record1.addProperty(creator);
		record1.addProperty(title);

		final BibliographicRecord record2 = new BibliographicRecord();
		record2.setIdentifier("REC-002");
		record2.addProperty(creator);
		record2.addProperty(title);

		final Library lib = new Library();
		lib.setIdentifier("");

		final DocUnit docUnit = new DocUnit();
		docUnit.setLibrary(lib);
		docUnit.setIdentifier("DOC-UNIT-001");
		docUnit.setPgcnId("ID-DOC-UNIT-001");
		docUnit.setLabel("toto fait du vélo");
		docUnit.addRecord(record1);
		docUnit.addRecord(record2);
		return docUnit;
	}

}
