package org.numahop.numahop.service.check.mapper;

import org.numahop.numahop.domain.check.AutomaticCheckType;
import org.numahop.numahop.domain.dto.check.AutomaticCheckTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by lebouchp on 03/02/2017.
 */
@Mapper
public interface AutomaticCheckTypeMapper {

	AutomaticCheckTypeMapper INSTANCE = Mappers.getMapper(AutomaticCheckTypeMapper.class);

	AutomaticCheckTypeDTO objToDto(AutomaticCheckType type);

	AutomaticCheckType dtoToObj(AutomaticCheckTypeDTO dto);

}
