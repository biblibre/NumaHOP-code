package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.DocPropertyType;
import org.numahop.numahop.domain.dto.document.DocPropertyTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {})
public interface DocPropertyTypeMapper {

	DocPropertyTypeMapper INSTANCE = Mappers.getMapper(DocPropertyTypeMapper.class);

	DocPropertyTypeDTO docPropertyTypeToDocPropertyTypeDTO(DocPropertyType propertyType);

}
