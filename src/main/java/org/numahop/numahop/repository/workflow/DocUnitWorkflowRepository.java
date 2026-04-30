package org.numahop.numahop.repository.workflow;

import org.numahop.numahop.domain.workflow.DocUnitState;
import org.numahop.numahop.domain.workflow.DocUnitWorkflow;
import org.numahop.numahop.domain.workflow.WorkflowModel;
import org.numahop.numahop.domain.workflow.WorkflowStateKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocUnitWorkflowRepository
		extends JpaRepository<DocUnitWorkflow, String>, DocUnitWorkflowRepositoryCustom {

	List<DocUnitWorkflow> findByDocUnitIdentifierIn(List<String> docUnitIds);

	Long countByModel(WorkflowModel model);

	@Query("""
			select s from DocUnitState s
			left join s.workflow w
			where s.discriminator in ?2 AND w.docUnit.identifier = ?1
			""")
	List<DocUnitState> findDocUnitStatesByKey(final String docUnit, final WorkflowStateKey... stateKeys);

}
