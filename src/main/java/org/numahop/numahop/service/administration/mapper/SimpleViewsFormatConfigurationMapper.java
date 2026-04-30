package org.numahop.numahop.service.administration.mapper;

import org.numahop.numahop.domain.administration.viewsformat.ViewsFormatConfiguration;
import org.numahop.numahop.domain.dto.administration.viewsFormat.SimpleViewsFormatConfigurationDTO;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleViewsFormatConfigurationMapper {

	SimpleViewsFormatConfigurationMapper INSTANCE = Mappers.getMapper(SimpleViewsFormatConfigurationMapper.class);

	SimpleViewsFormatConfigurationDTO formatConfigToDto(ViewsFormatConfiguration formatConfiguration);

	Set<SimpleViewsFormatConfigurationDTO> formatConfigToDtos(Set<ViewsFormatConfiguration> confs);

}
