package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.Check;
import org.numahop.numahop.domain.dto.document.CheckDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DocPageMapper.class })
public interface CheckMapper {

	CheckMapper INSTANCE = Mappers.getMapper(CheckMapper.class);

	CheckDTO checkToCheckDTO(Check check);

}
