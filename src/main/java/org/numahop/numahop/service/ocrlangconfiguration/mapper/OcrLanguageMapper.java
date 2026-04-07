package org.numahop.numahop.service.ocrlangconfiguration.mapper;

import org.numahop.numahop.domain.dto.ocrlangconfiguration.OcrLanguageDTO;
import org.numahop.numahop.domain.ocrlangconfiguration.OcrLanguage;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OcrLanguageMapper {

	OcrLanguageMapper INSTANCE = Mappers.getMapper(OcrLanguageMapper.class);

	OcrLanguageDTO objToDTO(OcrLanguage object);

	OcrLanguage dtoToObject(OcrLanguageDTO dto);

	List<OcrLanguageDTO> objsToDtos(List<OcrLanguage> objects);

}
