package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.DocPage;
import org.numahop.numahop.domain.dto.document.DocPageDTO;
import org.numahop.numahop.domain.dto.document.SimpleDocPageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DigitalDocumentMapper.class })
public interface DocPageMapper {

	DocPageMapper INSTANCE = Mappers.getMapper(DocPageMapper.class);

	SimpleDocPageDTO docPageToSimpleDocPageDTO(DocPage docPage);

	DocPageDTO docPageToDocPageDTO(DocPage docPage);

}
