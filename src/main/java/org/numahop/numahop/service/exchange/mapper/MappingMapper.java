package org.numahop.numahop.service.exchange.mapper;

import org.numahop.numahop.domain.dto.exchange.MappingDTO;
import org.numahop.numahop.domain.exchange.Mapping;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Sebastien on 07/12/2016.
 */
@Mapper(uses = { SimpleLibraryMapper.class })
public interface MappingMapper {

	MappingMapper INSTANCE = Mappers.getMapper(MappingMapper.class);

	MappingDTO mappingToDto(Mapping mapping);

	Set<MappingDTO> mappingToDtos(Set<Mapping> mappings);

}
