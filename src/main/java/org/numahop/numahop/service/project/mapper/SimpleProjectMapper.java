package org.numahop.numahop.service.project.mapper;

import org.numahop.numahop.domain.dto.project.SimpleProjectDTO;
import org.numahop.numahop.domain.project.Project;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleLibraryMapper.class })
public interface SimpleProjectMapper {

	SimpleProjectMapper INSTANCE = Mappers.getMapper(SimpleProjectMapper.class);

	SimpleProjectDTO projectToSimpleProjectDTO(Project project);

}
