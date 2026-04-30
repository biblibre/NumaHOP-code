package org.numahop.numahop.service.exportftpconfiguration.mapper;

import org.numahop.numahop.domain.dto.exportftpconfiguration.ExportFTPConfigurationDTO;
import org.numahop.numahop.domain.dto.exportftpconfiguration.SimpleExportFTPConfDTO;
import org.numahop.numahop.domain.exportftpconfiguration.ExportFTPConfiguration;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleLibraryMapper.class })
public interface ExportFTPConfigurationMapper {

	ExportFTPConfigurationMapper INSTANCE = Mappers.getMapper(ExportFTPConfigurationMapper.class);

	ExportFTPConfigurationDTO objectToDto(ExportFTPConfiguration config);

	ExportFTPConfiguration dtoToObject(ExportFTPConfigurationDTO dto);

	SimpleExportFTPConfDTO objectToSimpleDto(ExportFTPConfiguration config);

}
