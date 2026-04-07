package org.numahop.numahop.service.document.ui;

import static org.junit.jupiter.api.Assertions.*;

import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.repository.lot.LotRepository;
import org.numahop.numahop.service.LockService;
import org.numahop.numahop.service.delivery.DeliveryService;
import org.numahop.numahop.service.document.DigitalDocumentService;
import org.numahop.numahop.service.document.DocUnitService;
import org.numahop.numahop.service.document.mapper.UIDocUnitMapper;
import org.numahop.numahop.service.es.EsDocUnitService;
import org.numahop.numahop.service.exchange.cines.CinesRequestHandlerService;
import org.numahop.numahop.service.exchange.cines.ExportMetsService;
import org.numahop.numahop.service.exchange.cines.ui.UICinesReportService;
import org.numahop.numahop.service.exchange.ead.ExportEadService;
import org.numahop.numahop.service.exchange.internetarchive.ui.UIInternetArchiveReportService;
import org.numahop.numahop.service.exchange.ssh.SftpService;
import org.numahop.numahop.service.exportftpconfiguration.ExportFTPConfigurationService;
import org.numahop.numahop.service.storage.AltoService;
import org.numahop.numahop.service.storage.BinaryStorageManager;
import org.numahop.numahop.service.util.CryptoService;
import org.numahop.numahop.service.workflow.WorkflowService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UIDocUnitServiceTest {

	@Mock
	private DocUnitService docUnitService;

	@Mock
	private ExportEadService exportEadService;

	@Mock
	private UIDocUnitMapper uiDocUnitMapper;

	@Mock
	private UICinesReportService uiCinesReportService;

	@Mock
	private UIInternetArchiveReportService uiIAReportService;

	@Mock
	private ExportMetsService exportMetsService;

	@Mock
	private BinaryStorageManager bm;

	@Mock
	private UIBibliographicRecordService uiBibliographicRecordService;

	@Mock
	private LockService lockService;

	@Mock
	private WorkflowService workflowService;

	@Mock
	private EsDocUnitService esDocUnitService;

	@Mock
	private CinesRequestHandlerService cinesRequestHandlerService;

	@Mock
	private ExportFTPConfigurationService exportFTPConfigurationService;

	@Mock
	private SftpService sftpService;

	@Mock
	private CryptoService cryptoService;

	@Mock
	private LotRepository lotRepository;

	@Mock
	private DeliveryService deliveryService;

	@Mock
	private DigitalDocumentService digitalDocumentService;

	@Mock
	private AltoService altoService;

	private UIDocUnitService service;

	@BeforeEach
	public void setUp() {
		service = new UIDocUnitService(docUnitService, exportEadService, uiDocUnitMapper, uiCinesReportService,
				uiIAReportService, exportMetsService, bm, uiBibliographicRecordService, lockService, workflowService,
				esDocUnitService, cinesRequestHandlerService, exportFTPConfigurationService, sftpService, cryptoService,
				lotRepository, deliveryService, digitalDocumentService, altoService);

	}

	@Test
	public void isLotRenumTest() {

		final Lot lot = getInitialLot();

		assertFalse(service.isLotRenum(Collections.emptySet(), lot));

		final DocUnit renumDoc = new DocUnit();
		renumDoc.setIdentifier("id_1");
		renumDoc.setLot(lot);
		final Set<DocUnit> renumDus = new HashSet<>();
		renumDus.add(renumDoc);

		assertFalse(service.isLotRenum(renumDus, lot));

		final Lot lot2 = new Lot();
		lot2.setIdentifier("another");
		final DocUnit renumDoc2 = new DocUnit();
		renumDoc2.setIdentifier("id_2");
		renumDoc2.setLot(lot2);

		renumDus.add(renumDoc2);

		assertTrue(service.isLotRenum(renumDus, lot));

	}

	private Lot getInitialLot() {

		final Lot lot = new Lot();
		lot.setIdentifier("id_lot_1");

		final DocUnit doc1 = new DocUnit();
		doc1.setIdentifier("id_doc_unit_1");
		doc1.setLot(lot);

		final DocUnit doc2 = new DocUnit();
		doc2.setIdentifier("id_doc_unit_2");
		doc2.setLot(lot);

		final Set<DocUnit> docUnits = new HashSet<>();
		docUnits.add(doc1);
		docUnits.add(doc2);

		lot.setDocUnits(docUnits);

		return lot;
	}

}
