package org.numahop.numahop.web.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.domain.document.conditionreport.ConditionReportDetail.Type;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.domain.lot.Lot.LotStatus;
import org.numahop.numahop.domain.workflow.WorkflowModel;
import org.numahop.numahop.domain.workflow.WorkflowStateKey;
import org.numahop.numahop.service.document.DocUnitService;
import org.numahop.numahop.service.lot.LotService;
import org.numahop.numahop.service.workflow.WorkflowGroupService;
import org.numahop.numahop.service.workflow.WorkflowService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WorkflowAccessHelperTest {

	private static final String IDENTIFIER = "71d96e0e-27e5-4ae6-a2fa-4ba95acfb30a";

	@Mock
	private DocUnitService docUnitService;

	@Mock
	private WorkflowGroupService workflowGroupService;

	@Mock
	private LotService lotService;

	@Mock
	private HttpServletRequest request;

	@Mock
	private WorkflowService service;

	private WorkflowAccessHelper accessHelper;

	@BeforeEach
	public void setUp() {
		accessHelper = new WorkflowAccessHelper(lotService, service, workflowGroupService);
	}

	@Test
	public void testDeleteConstat() {
		final DocUnit doc = new DocUnit();

		when(service.isStateSkippedOrRunning(IDENTIFIER, WorkflowStateKey.VALIDATION_CONSTAT_ETAT))
			.thenReturn(true, true)
			.thenReturn(false);
		when(service.isStateSkippedOrRunning(IDENTIFIER, WorkflowStateKey.CONSTAT_ETAT_AVANT_NUMERISATION))
			.thenReturn(true)
			.thenReturn(false)
			.thenReturn(true);
		when(service.isStateSkippedOrRunning(IDENTIFIER, WorkflowStateKey.CONSTAT_ETAT_APRES_NUMERISATION))
			.thenReturn(true);
		when(accessHelper.isDocUnitLockedByWorkflow(IDENTIFIER)).thenReturn(true).thenReturn(true).thenReturn(true);

		// états non passés
		boolean actual = accessHelper.canConstatBeDeleted(IDENTIFIER);
		assertTrue(actual);

		// CONSTAT_ETAT_AVANT_NUMERISATION validé
		actual = accessHelper.canConstatBeDeleted(IDENTIFIER);
		assertFalse(actual);

		// VALIDATION_CONSTAT_ETAT validé
		actual = accessHelper.canConstatBeDeleted(IDENTIFIER);
		assertFalse(actual);
	}

	@Test
	public void testUpdateConstatLeaving() {
		final DocUnit doc = new DocUnit();

		when(service.isStateSkippedOrRunning(IDENTIFIER, WorkflowStateKey.VALIDATION_CONSTAT_ETAT)).thenReturn(true)
			.thenReturn(true)
			.thenReturn(false);
		when(accessHelper.isDocUnitLockedByWorkflow(IDENTIFIER)).thenReturn(true).thenReturn(true).thenReturn(true);

		// états non passés
		boolean actual = accessHelper.canConstatDetailBeModified(IDENTIFIER, Type.LIBRARY_LEAVING);
		assertTrue(actual);

		// CONSTAT_ETAT_AVANT_NUMERISATION validé : ne change rien (même si impossible
		// en vrai)
		actual = accessHelper.canConstatDetailBeModified(IDENTIFIER, Type.LIBRARY_LEAVING);
		assertTrue(actual);

		// VALIDATION_CONSTAT_ETAT validé : modification impossible
		actual = accessHelper.canConstatDetailBeModified(IDENTIFIER, Type.LIBRARY_LEAVING);
		assertFalse(actual);
	}

	@Test
	public void testLotAccess() {
		final Lot lot = new Lot();
		lot.setStatus(LotStatus.CREATED);
		lot.addDocUnit(new DocUnit());

		when(lotService.findByIdentifier(IDENTIFIER)).thenReturn(lot);
		when(lotService.getWorkflowModel(lot)).thenReturn(new WorkflowModel());

		// état CREATED doit pouvoir être validé
		boolean actual = accessHelper.canLotBeValidated(IDENTIFIER);
		assertTrue(actual);

		// Un lot ONGOING ne peut pas être validé
		lot.setStatus(LotStatus.ONGOING);
		actual = accessHelper.canLotBeValidated(IDENTIFIER);
		assertFalse(actual);
	}

}
