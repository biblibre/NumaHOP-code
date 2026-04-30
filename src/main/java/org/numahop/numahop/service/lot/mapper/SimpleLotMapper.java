package org.numahop.numahop.service.lot.mapper;

import org.numahop.numahop.domain.dto.lot.SimpleLotDTO;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.service.ocrlangconfiguration.mapper.OcrLanguageMapper;
import org.numahop.numahop.service.project.mapper.SimpleProjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleProjectMapper.class, OcrLanguageMapper.class })
public interface SimpleLotMapper {

	SimpleLotMapper INSTANCE = Mappers.getMapper(SimpleLotMapper.class);

	SimpleLotDTO lotToSimpleLotDTO(Lot lot);

}
