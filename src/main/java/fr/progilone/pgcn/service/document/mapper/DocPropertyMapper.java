package fr.progilone.pgcn.service.document.mapper;

import fr.progilone.pgcn.domain.document.DocProperty;
import fr.progilone.pgcn.domain.dto.document.DocPropertyDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DocPropertyTypeMapper.class })
public interface DocPropertyMapper {

	DocPropertyMapper INSTANCE = Mappers.getMapper(DocPropertyMapper.class);

	DocPropertyDTO docPropertyToDocPropertyDTO(DocProperty property);

	List<DocPropertyDTO> docPropsToDto(List<DocProperty> props);

}
