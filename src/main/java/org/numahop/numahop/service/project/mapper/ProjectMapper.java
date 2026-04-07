package org.numahop.numahop.service.project.mapper;

import org.numahop.numahop.domain.dto.project.ProjectDTO;
import org.numahop.numahop.domain.dto.project.ProjectSearchDTO;
import org.numahop.numahop.domain.dto.statistics.StatisticsProjectDTO;
import org.numahop.numahop.domain.project.Project;
import org.numahop.numahop.service.administration.mapper.CinesPACMapper;
import org.numahop.numahop.service.administration.mapper.InternetArchiveCollectionMapper;
import org.numahop.numahop.service.administration.mapper.OmekaConfigurationMapper;
import org.numahop.numahop.service.administration.mapper.OmekaListMapper;
import org.numahop.numahop.service.administration.mapper.SimpleViewsFormatConfigurationMapper;
import org.numahop.numahop.service.checkconfiguration.mapper.SimpleCheckConfigurationMapper;
import org.numahop.numahop.service.document.mapper.SimpleDocUnitMapper;
import org.numahop.numahop.service.exportftpconfiguration.mapper.ExportFTPConfigurationMapper;
import org.numahop.numahop.service.ftpconfiguration.mapper.SimpleFTPConfigurationMapper;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.numahop.numahop.service.lot.mapper.LotMapper;
import org.numahop.numahop.service.train.mapper.SimpleTrainMapper;
import org.numahop.numahop.service.user.mapper.AddressMapper;
import org.numahop.numahop.service.user.mapper.UserMapper;
import org.numahop.numahop.service.workflow.mapper.SimpleWorkflowMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AddressMapper.class, SimpleLibraryMapper.class, SimpleDocUnitMapper.class, LotMapper.class,
		SimpleTrainMapper.class, SimpleFTPConfigurationMapper.class, ExportFTPConfigurationMapper.class,
		SimpleCheckConfigurationMapper.class, SimpleViewsFormatConfigurationMapper.class, SimpleWorkflowMapper.class,
		UserMapper.class, InternetArchiveCollectionMapper.class, CinesPACMapper.class, OmekaListMapper.class,
		OmekaConfigurationMapper.class })
public interface ProjectMapper {

	ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

	ProjectSearchDTO projectToProjectSearchDTO(Project project);

	ProjectDTO projectToProjectDTO(Project project);

	@Mappings({ @Mapping(target = "nbDocUnits", ignore = true) })
	StatisticsProjectDTO projectToStatProjectDTO(Project project);

	@AfterMapping
	default void calculateDocUnits(Project project, @MappingTarget StatisticsProjectDTO dto) {
		dto.setNbDocUnits(project.getDocUnits().size());
	}

}
