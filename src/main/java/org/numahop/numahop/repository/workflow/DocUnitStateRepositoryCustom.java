package org.numahop.numahop.repository.workflow;

import org.numahop.numahop.domain.workflow.DocUnitState;
import org.numahop.numahop.repository.workflow.helper.DocUnitWorkflowSearchBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocUnitStateRepositoryCustom {

	/**
	 * Recherche de DocUnitState
	 * @param searchBuilder
	 * @param pageable
	 * @return
	 */
	Page<DocUnitState> findDocUnitStates(DocUnitWorkflowSearchBuilder searchBuilder, Pageable pageable);

}
