package org.numahop.numahop.service.exchange.mapper;

import org.numahop.numahop.domain.dto.exchange.CSVMappingDTO;
import org.numahop.numahop.domain.exchange.CSVMapping;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Sebastien on 07/12/2016.
 */
@Mapper(uses = { SimpleLibraryMapper.class })
public interface CSVMappingMapper {

	CSVMappingMapper INSTANCE = Mappers.getMapper(CSVMappingMapper.class);

	CSVMappingDTO mappingToDto(CSVMapping mapping);

	Set<CSVMappingDTO> mappingToDtos(Set<CSVMapping> mappings);

}
