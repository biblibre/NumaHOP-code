package org.numahop.numahop.service.ftpconfiguration.mapper;

import org.numahop.numahop.domain.dto.ftpconfiguration.SimpleFTPConfigurationDTO;
import org.numahop.numahop.domain.ftpconfiguration.FTPConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by lebouchp on 06/02/2017.
 */
@Mapper
public interface SimpleFTPConfigurationMapper {

	SimpleFTPConfigurationMapper INSTANCE = Mappers.getMapper(SimpleFTPConfigurationMapper.class);

	SimpleFTPConfigurationDTO ftpConfigurationToSimpleFTPConfigurationDTO(FTPConfiguration ftpConfiguration);

}
