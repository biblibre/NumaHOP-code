package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.conditionreport.ConditionReportAttachment;
import org.numahop.numahop.domain.dto.document.conditionreport.ConditionReportAttachmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConditionReportAttachmentMapper {

	ConditionReportAttachmentMapper INSTANCE = Mappers.getMapper(ConditionReportAttachmentMapper.class);

	ConditionReportAttachmentDTO attachmentToDTO(ConditionReportAttachment atachment);

}
