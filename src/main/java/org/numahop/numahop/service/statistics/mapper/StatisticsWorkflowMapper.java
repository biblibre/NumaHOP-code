package org.numahop.numahop.service.statistics.mapper;

import org.numahop.numahop.domain.dto.statistics.WorkflowProfileActivityDTO;
import org.numahop.numahop.domain.dto.statistics.WorkflowUserActivityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatisticsWorkflowMapper {

	WorkflowProfileActivityDTO userToProfileDto(WorkflowUserActivityDTO userDto);

}
