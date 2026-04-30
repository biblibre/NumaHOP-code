package org.numahop.numahop.service.library.mapper;

import org.numahop.numahop.domain.dto.library.LibraryDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.service.administration.mapper.SimpleViewsFormatConfigurationMapper;
import org.numahop.numahop.service.checkconfiguration.mapper.SimpleCheckConfigurationMapper;
import org.numahop.numahop.service.ftpconfiguration.mapper.SimpleFTPConfigurationMapper;
import org.numahop.numahop.service.ocrlangconfiguration.mapper.OcrLangConfigurationMapper;
import org.numahop.numahop.service.user.mapper.AddressMapper;
import org.numahop.numahop.service.user.mapper.RoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AddressMapper.class, SimpleFTPConfigurationMapper.class, SimpleCheckConfigurationMapper.class,
		SimpleViewsFormatConfigurationMapper.class, RoleMapper.class, OcrLangConfigurationMapper.class })
public interface LibraryMapper {

	LibraryMapper INSTANCE = Mappers.getMapper(LibraryMapper.class);

	LibraryDTO libraryToLibraryDTO(Library library);

}
