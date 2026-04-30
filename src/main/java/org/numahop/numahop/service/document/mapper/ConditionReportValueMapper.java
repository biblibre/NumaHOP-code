package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.conditionreport.Description;
import org.numahop.numahop.domain.dto.document.conditionreport.ConditionReportValueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConditionReportValueMapper {

	ConditionReportValueMapper INSTANCE = Mappers.getMapper(ConditionReportValueMapper.class);

	@Mappings({ @Mapping(source = "property.identifier", target = "propertyId"),
			@Mapping(source = "property.code", target = "propertyCode"),
			@Mapping(source = "property.label", target = "propertyLabel"),
			@Mapping(source = "property.type", target = "propertyType"),
			@Mapping(source = "property.order", target = "propertyOrder"),
			@Mapping(source = "value.label", target = "value") })
	ConditionReportValueDTO bindingToDTO(Description description);

}
