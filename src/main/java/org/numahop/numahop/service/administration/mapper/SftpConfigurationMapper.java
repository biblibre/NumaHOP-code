package org.numahop.numahop.service.administration.mapper;

import org.numahop.numahop.domain.administration.SftpConfiguration;
import org.numahop.numahop.domain.dto.administration.SftpConfigurationDTO;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Sébastien on 30/12/2016.
 */
@Mapper(uses = { SimpleLibraryMapper.class, CinesPACMapper.class })
public interface SftpConfigurationMapper {

	SftpConfigurationMapper INSTANCE = Mappers.getMapper(SftpConfigurationMapper.class);

	SftpConfigurationDTO configurationSftpToDto(SftpConfiguration sftpConfiguration);

	Set<SftpConfigurationDTO> configurationSftpToDtos(Set<SftpConfiguration> sftpConfiguration);

}
