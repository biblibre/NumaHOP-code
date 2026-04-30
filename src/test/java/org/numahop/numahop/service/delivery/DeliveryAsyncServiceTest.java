package org.numahop.numahop.service.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.numahop.numahop.repository.document.PhysicalDocumentRepository;
import org.numahop.numahop.repository.imagemetadata.ImageMetadataRepository;
import org.numahop.numahop.repository.storage.BinaryRepository;
import org.numahop.numahop.service.check.AutomaticCheckService;
import org.numahop.numahop.service.check.MetaDatasCheckService;
import org.numahop.numahop.service.delivery.mapper.PrefixedDocumentsMapper;
import org.numahop.numahop.service.document.BibliographicRecordService;
import org.numahop.numahop.service.document.DigitalDocumentService;
import org.numahop.numahop.service.document.DocPageService;
import org.numahop.numahop.service.document.DocPropertyTypeService;
import org.numahop.numahop.service.document.DocUnitService;
import org.numahop.numahop.service.document.SlipService;
import org.numahop.numahop.service.document.ui.UIDigitalDocumentService;
import org.numahop.numahop.service.es.EsDeliveryService;
import org.numahop.numahop.service.lot.LotService;
import org.numahop.numahop.service.sample.SampleService;
import org.numahop.numahop.service.storage.BinaryStorageManager;
import org.numahop.numahop.service.storage.ImageMagickService;
import org.numahop.numahop.service.storage.TesseractService;
import org.numahop.numahop.service.util.DeliveryProgressService;
import org.numahop.numahop.service.util.transaction.TransactionService;
import org.numahop.numahop.service.workflow.WorkflowService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeliveryAsyncServiceTest {

	private static final String RESOURCE_FOLDER = "src/test/resources/delivery";

	@Mock
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	@Mock
	private DeliveryService deliveryService;

	@Mock
	private EsDeliveryService esDeliveryService;

	@Mock
	private PhysicalDocumentRepository physicalDocumentRepository;

	@Mock
	private DocPageService docPageService;

	@Mock
	private DigitalDocumentService digitalDocumentService;

	@Mock
	private AutomaticCheckService autoCheckService;

	@Mock
	private BinaryStorageManager bm;

	@Mock
	private TransactionService transactionService;

	@Mock
	private PrefixedDocumentsMapper mapper;

	@Mock
	private MetaDatasCheckService metaDatasCheckService;

	@Mock
	private LotService lotService;

	@Mock
	private DeliveryReportingService reportService;

	@Mock
	private ImageMagickService imService;

	@Mock
	private SampleService sampleService;

	@Mock
	private WorkflowService workflowService;

	@Mock
	private BinaryRepository binaryRepository;

	@Mock
	private DeliveryProgressService deliveryProgressService;

	@Mock
	private SlipService slipService;

	@Mock
	private DocPropertyTypeService docPropertyTypeService;

	@Mock
	private BibliographicRecordService bibliographicRecordService;

	@Mock
	private TesseractService tesseractService;

	@Mock
	private DocUnitService docUnitService;

	@Mock
	private UIDigitalDocumentService uidigitalDocumentService;

	@Mock
	private ImageMetadataRepository imageMetadataRepository;

	private DeliveryAsyncService service;

	@BeforeEach
	public void setUp() {
		service = new DeliveryAsyncService(deliveryService, esDeliveryService, physicalDocumentRepository,
				docPageService, digitalDocumentService, transactionService, autoCheckService, bm, mapper,
				metaDatasCheckService, lotService, reportService, imService, sampleService, workflowService,
				binaryRepository, deliveryProgressService, slipService, docPropertyTypeService,
				bibliographicRecordService, tesseractService, docUnitService, uidigitalDocumentService,
				imageMetadataRepository);
	}

	@Test
	public void getFormatFilterTest() {
		final List<File> files = new ArrayList<>();
		final String expectedFormat = "TIFF";
		final File baseDirectory = new File(RESOURCE_FOLDER + "/format");

		final File file1 = new File(baseDirectory, "IHE14001000.tiff");
		final File file2 = new File(baseDirectory, "IHE14001000.TIFF");
		final File file3 = new File(baseDirectory, "IHE14001000.png");
		final File file4 = new File(baseDirectory, "IHE14001000.pdf");

		files.addAll(FileUtils.listFiles(baseDirectory, service.getFormatFilter(expectedFormat), TrueFileFilter.TRUE));
		assertEquals(2, files.size());
	}

	@Test
	public void getPrefixFilterTest() {
		final List<File> files = new ArrayList<>();
		final String prefix = "BSG_120587445";
		final String seqSeparator = "_";
		final File baseDirectory = new File(RESOURCE_FOLDER + "/prefix");

		final File file1 = new File(baseDirectory, "BSG_120587445_001.tiff");
		final File file2 = new File(baseDirectory, "BSG_120587445_002.TIFF");
		final File file3 = new File(baseDirectory, "BSG_120587445_003.png");
		final File file4 = new File(baseDirectory, "BSG_120587445_004.pdf");

		files.addAll(FileUtils.listFiles(baseDirectory, service.getPrefixFilter(prefix, seqSeparator, false, false),
				TrueFileFilter.TRUE));
		assertEquals(4, files.size());
	}

}
