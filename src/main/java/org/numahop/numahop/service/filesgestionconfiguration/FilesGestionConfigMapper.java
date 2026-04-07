package org.numahop.numahop.service.filesgestionconfiguration;

import org.numahop.numahop.domain.dto.filesgestionconfiguration.FilesGestionConfigDTO;
import org.numahop.numahop.domain.filesgestionconfiguration.FilesGestionConfig;
import org.numahop.numahop.service.exportftpconfiguration.mapper.ExportFTPConfigurationMapper;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleLibraryMapper.class, ExportFTPConfigurationMapper.class })
public interface FilesGestionConfigMapper {

	FilesGestionConfigMapper INSTANCE = Mappers.getMapper(FilesGestionConfigMapper.class);

	FilesGestionConfigDTO configToConfigDto(FilesGestionConfig filesGestionConfig);

	List<FilesGestionConfigDTO> configsToConfigsDTO(List<FilesGestionConfig> configs);

	FilesGestionConfig configDtoToObj(FilesGestionConfigDTO dto);

}
