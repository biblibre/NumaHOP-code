package org.numahop.numahop.repository.workflow;

import org.numahop.numahop.domain.workflow.WorkflowModelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowModelStateRepository
		extends JpaRepository<WorkflowModelState, String>, WorkflowModelStateRepositoryCustom {

}
