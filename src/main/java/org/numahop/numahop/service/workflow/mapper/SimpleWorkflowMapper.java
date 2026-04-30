package org.numahop.numahop.service.workflow.mapper;

import org.numahop.numahop.domain.dto.workflow.SimpleWorkflowGroupDTO;
import org.numahop.numahop.domain.dto.workflow.SimpleWorkflowModelDTO;
import org.numahop.numahop.domain.dto.workflow.SimpleWorkflowModelStateDTO;
import org.numahop.numahop.domain.workflow.WorkflowGroup;
import org.numahop.numahop.domain.workflow.WorkflowModel;
import org.numahop.numahop.domain.workflow.WorkflowModelState;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleWorkflowMapper {

	SimpleWorkflowMapper INSTANCE = Mappers.getMapper(SimpleWorkflowMapper.class);

	SimpleWorkflowGroupDTO groupToSimpleGroupDTO(WorkflowGroup group);

	SimpleWorkflowModelDTO modelToSimpleModelDTO(WorkflowModel model);

	SimpleWorkflowModelStateDTO modelStateToSimpleModelState(WorkflowModelState state);

}
