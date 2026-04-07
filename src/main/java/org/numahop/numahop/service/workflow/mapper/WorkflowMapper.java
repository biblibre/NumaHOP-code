package org.numahop.numahop.service.workflow.mapper;

import org.numahop.numahop.domain.dto.workflow.DocUnitStateDTO;
import org.numahop.numahop.domain.dto.workflow.DocUnitWorkflowDTO;
import org.numahop.numahop.domain.dto.workflow.WorkflowGroupDTO;
import org.numahop.numahop.domain.dto.workflow.WorkflowModelDTO;
import org.numahop.numahop.domain.dto.workflow.WorkflowModelStateDTO;
import org.numahop.numahop.domain.workflow.DocUnitState;
import org.numahop.numahop.domain.workflow.DocUnitWorkflow;
import org.numahop.numahop.domain.workflow.WorkflowGroup;
import org.numahop.numahop.domain.workflow.WorkflowModel;
import org.numahop.numahop.domain.workflow.WorkflowModelState;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.numahop.numahop.service.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleLibraryMapper.class, UserMapper.class, SimpleWorkflowMapper.class })
public interface WorkflowMapper {

	WorkflowMapper INSTANCE = Mappers.getMapper(WorkflowMapper.class);

	WorkflowGroupDTO groupToGroupDTO(WorkflowGroup group);

	@Mapping(target = "states", source = "model.modelStates")
	WorkflowModelDTO modelToModelDTO(WorkflowModel model);

	WorkflowModelStateDTO modelStateToModelStateDTO(WorkflowModelState state);

	DocUnitWorkflowDTO workflowToWorkflowDTO(DocUnitWorkflow workflow);

	DocUnitStateDTO workflowStateToWorkflowStateDTO(DocUnitState state);

}
