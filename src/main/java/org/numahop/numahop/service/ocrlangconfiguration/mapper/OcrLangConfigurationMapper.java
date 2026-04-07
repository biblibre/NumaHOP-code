package org.numahop.numahop.service.ocrlangconfiguration.mapper;

import org.numahop.numahop.domain.dto.ocrlangconfiguration.SimpleOcrLangConfigDTO;
import org.numahop.numahop.domain.ocrlangconfiguration.OcrLangConfiguration;
import org.numahop.numahop.service.library.mapper.LibraryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { OcrLanguageMapper.class, LibraryMapper.class })
public interface OcrLangConfigurationMapper {

	OcrLangConfigurationMapper INSTANCE = Mappers.getMapper(OcrLangConfigurationMapper.class);

	SimpleOcrLangConfigDTO ocrLangConfigToSimpleDto(OcrLangConfiguration conf);

}
