package org.numahop.numahop.service.lot.mapper;

import org.numahop.numahop.domain.dto.lot.LotWithConfigRulesDTO;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.service.administration.mapper.CinesPACMapper;
import org.numahop.numahop.service.administration.mapper.InternetArchiveCollectionMapper;
import org.numahop.numahop.service.administration.mapper.OmekaConfigurationMapper;
import org.numahop.numahop.service.administration.mapper.OmekaListMapper;
import org.numahop.numahop.service.administration.mapper.SimpleViewsFormatConfigurationMapper;
import org.numahop.numahop.service.checkconfiguration.mapper.CheckConfigurationMapper;
import org.numahop.numahop.service.document.mapper.SimpleDocUnitMapper;
import org.numahop.numahop.service.ftpconfiguration.mapper.SimpleFTPConfigurationMapper;
import org.numahop.numahop.service.ocrlangconfiguration.mapper.OcrLanguageMapper;
import org.numahop.numahop.service.project.mapper.SimpleProjectMapper;
import org.numahop.numahop.service.user.mapper.AddressMapper;
import org.numahop.numahop.service.user.mapper.UserMapper;
import org.numahop.numahop.service.workflow.mapper.SimpleWorkflowMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AddressMapper.class, SimpleDocUnitMapper.class, SimpleProjectMapper.class,
		SimpleFTPConfigurationMapper.class, CheckConfigurationMapper.class, SimpleViewsFormatConfigurationMapper.class,
		UserMapper.class, OcrLanguageMapper.class, InternetArchiveCollectionMapper.class, CinesPACMapper.class,
		SimpleWorkflowMapper.class, OmekaListMapper.class, OmekaConfigurationMapper.class })
public interface LotWithConfigRulesMapper {

	LotWithConfigRulesMapper INSTANCE = Mappers.getMapper(LotWithConfigRulesMapper.class);

	LotWithConfigRulesDTO lotToLotWithConfigRulesDTO(Lot lot);

}
