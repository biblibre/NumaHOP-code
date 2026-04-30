package org.numahop.numahop.repository.multilotsdelivery;

import org.numahop.numahop.domain.delivery.Delivery;
import org.numahop.numahop.domain.multilotsdelivery.MultiLotsDelivery;
import org.numahop.numahop.domain.workflow.WorkflowStateKey;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MultiLotsDeliveryRepositoryCustom {

	/**
	 * Recherche rapide de livraisons
	 * @param search
	 * @param projects
	 * @param lots
	 * @param deliveries
	 * @param status
	 * @param dateFrom
	 * @param dateTo
	 * @param docUnitPgcnId
	 * @param docUnitStates
	 * @param pageable
	 * @return
	 */
	Page<MultiLotsDelivery> search(String search, List<String> libraries, List<String> projects, List<String> lots,
			List<String> deliveries, List<String> providers, List<Delivery.DeliveryStatus> status, LocalDate dateFrom,
			LocalDate dateTo, List<WorkflowStateKey> docUnitStates, Pageable pageable);

}
