package org.numahop.numahop.service.lot.mapper;

import org.numahop.numahop.domain.dto.lot.LotDTO;
import org.numahop.numahop.domain.dto.lot.LotListDTO;
import org.numahop.numahop.domain.dto.lot.SimpleLotDTO;
import org.numahop.numahop.domain.dto.lot.SimpleLotForDeliveryDTO;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.service.administration.mapper.CinesPACMapper;
import org.numahop.numahop.service.administration.mapper.InternetArchiveCollectionMapper;
import org.numahop.numahop.service.administration.mapper.OmekaListMapper;
import org.numahop.numahop.service.administration.mapper.SimpleViewsFormatConfigurationMapper;
import org.numahop.numahop.service.checkconfiguration.mapper.SimpleCheckConfigurationMapper;
import org.numahop.numahop.service.document.mapper.SimpleDocUnitMapper;
import org.numahop.numahop.service.exportftpconfiguration.mapper.ExportFTPConfigurationMapper;
import org.numahop.numahop.service.ftpconfiguration.mapper.SimpleFTPConfigurationMapper;
import org.numahop.numahop.service.ocrlangconfiguration.mapper.OcrLanguageMapper;
import org.numahop.numahop.service.project.mapper.ProjectMapper;
import org.numahop.numahop.service.project.mapper.SimpleProjectMapper;
import org.numahop.numahop.service.user.mapper.AddressMapper;
import org.numahop.numahop.service.user.mapper.UserMapper;
import org.numahop.numahop.service.workflow.mapper.SimpleWorkflowMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AddressMapper.class, SimpleDocUnitMapper.class, SimpleProjectMapper.class, ProjectMapper.class,
		SimpleFTPConfigurationMapper.class, SimpleCheckConfigurationMapper.class,
		SimpleViewsFormatConfigurationMapper.class, SimpleWorkflowMapper.class, UserMapper.class,
		InternetArchiveCollectionMapper.class, CinesPACMapper.class, OmekaListMapper.class, OcrLanguageMapper.class,
		ExportFTPConfigurationMapper.class })
public interface LotMapper {

	LotMapper INSTANCE = Mappers.getMapper(LotMapper.class);

	LotDTO lotToLotDTO(Lot lot);

	@Mappings({ @Mapping(target = "projectIdentifier", source = "project.identifier") })
	LotListDTO lotToLotListDTO(Lot lot);

	@Mappings({ @Mapping(target = "providerLogin", source = "provider.login") })
	SimpleLotDTO lotToSimpleLotDTO(Lot lot);

	SimpleLotForDeliveryDTO lotToSimpleLotForDeliveryDTO(Lot lot);

}
