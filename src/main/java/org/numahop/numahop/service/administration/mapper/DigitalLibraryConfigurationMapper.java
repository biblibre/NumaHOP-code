package org.numahop.numahop.service.administration.mapper;

import org.numahop.numahop.domain.administration.digitallibrary.DigitalLibraryConfiguration;
import org.numahop.numahop.domain.dto.administration.digitallibrary.DigitalLibraryConfigurationDTO;
import java.util.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DigitalLibraryConfigurationMapper {

	DigitalLibraryConfigurationDTO configurationToDto(DigitalLibraryConfiguration conf);

	Collection<DigitalLibraryConfigurationDTO> configurationsToDtos(Collection<DigitalLibraryConfiguration> confs);

	DigitalLibraryConfiguration dtoToConfiguration(DigitalLibraryConfigurationDTO dto);

}
