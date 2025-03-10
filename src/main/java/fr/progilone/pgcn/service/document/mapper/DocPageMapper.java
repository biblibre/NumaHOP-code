package fr.progilone.pgcn.service.document.mapper;

import fr.progilone.pgcn.domain.document.DocPage;
import fr.progilone.pgcn.domain.dto.document.DocPageDTO;
import fr.progilone.pgcn.domain.dto.document.SimpleDocPageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DigitalDocumentMapper.class })
public interface DocPageMapper {

	DocPageMapper INSTANCE = Mappers.getMapper(DocPageMapper.class);

	SimpleDocPageDTO docPageToSimpleDocPageDTO(DocPage docPage);

	DocPageDTO docPageToDocPageDTO(DocPage docPage);

}
