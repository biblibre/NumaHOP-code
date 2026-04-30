package org.numahop.numahop.service.ftpconfiguration.mapper;

import org.numahop.numahop.domain.dto.ftpconfiguration.FTPConfigurationDTO;
import org.numahop.numahop.domain.ftpconfiguration.FTPConfiguration;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by lebouchp on 03/02/2017.
 */
@Mapper(uses = { SimpleLibraryMapper.class })
public interface FTPConfigurationMapper {

	FTPConfigurationMapper INSTANCE = Mappers.getMapper(FTPConfigurationMapper.class);

	FTPConfigurationDTO ftpConfigurationToFTPConfigurationDTO(FTPConfiguration ftpConfiguration);

}
