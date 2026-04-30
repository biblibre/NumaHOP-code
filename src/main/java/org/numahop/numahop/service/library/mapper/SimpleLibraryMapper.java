package org.numahop.numahop.service.library.mapper;

import org.numahop.numahop.domain.dto.library.SimpleLibraryDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.service.user.mapper.RoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { RoleMapper.class })
public interface SimpleLibraryMapper {

	SimpleLibraryMapper INSTANCE = Mappers.getMapper(SimpleLibraryMapper.class);

	SimpleLibraryDTO libraryToSimpleLibraryDTO(Library library);

	@Mappings({ @Mapping(ignore = true, target = "defaultRole") })
	Library dtoToLibrary(SimpleLibraryDTO dto);

}
