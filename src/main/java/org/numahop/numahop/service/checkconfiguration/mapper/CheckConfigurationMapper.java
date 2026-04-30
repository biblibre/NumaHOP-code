package org.numahop.numahop.service.checkconfiguration.mapper;

import org.numahop.numahop.domain.checkconfiguration.CheckConfiguration;
import org.numahop.numahop.domain.dto.checkconfiguration.CheckConfigurationDTO;
import org.numahop.numahop.service.check.mapper.AutomaticCheckTypeMapper;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by lebouchp on 03/02/2017.
 */
@Mapper(uses = { AutomaticCheckRuleMapper.class, AutomaticCheckTypeMapper.class, SimpleLibraryMapper.class })
public interface CheckConfigurationMapper {

	CheckConfigurationMapper INSTANCE = Mappers.getMapper(CheckConfigurationMapper.class);

	CheckConfigurationDTO checkConfigurationToCheckConfigurationDTO(CheckConfiguration checkConfiguration);

}
