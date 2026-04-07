package org.numahop.numahop.service.administration.mapper;

import org.numahop.numahop.domain.administration.viewsformat.ViewsFormatConfiguration;
import org.numahop.numahop.domain.dto.administration.viewsFormat.ViewsFormatConfigurationDTO;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleLibraryMapper.class })
public abstract class ViewsFormatConfigurationMapper {

	public static final ViewsFormatConfigurationMapper INSTANCE = Mappers
		.getMapper(ViewsFormatConfigurationMapper.class);

	public abstract ViewsFormatConfigurationDTO formatConfigToDto(ViewsFormatConfiguration formatConfiguration);

	public abstract ViewsFormatConfiguration dtoToFormatConfig(ViewsFormatConfigurationDTO formatConfigDto);

	@AfterMapping
	protected void updateDto(final ViewsFormatConfiguration conf,
			@MappingTarget final ViewsFormatConfigurationDTO dto) {

		if (conf.getDefaultFormats() != null) {
			dto.setThumbDefaultValue(String.valueOf(conf.getDefaultFormats().getDefThumbHeight())
				.concat(" x ")
				.concat(String.valueOf(conf.getDefaultFormats().getDefThumbWidth())));

			dto.setViewDefaultValue(String.valueOf(conf.getDefaultFormats().getDefViewHeight())
				.concat(" x ")
				.concat(String.valueOf(conf.getDefaultFormats().getDefViewWidth())));

			dto.setPrintDefaultValue(String.valueOf(conf.getDefaultFormats().getDefPrintHeight())
				.concat(" x ")
				.concat(String.valueOf(conf.getDefaultFormats().getDefPrintWidth())));
		}
	}

}
