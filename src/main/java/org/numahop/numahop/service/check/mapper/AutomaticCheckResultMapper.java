package org.numahop.numahop.service.check.mapper;

import org.numahop.numahop.domain.check.AutomaticCheckResult;
import org.numahop.numahop.domain.check.AutomaticCheckType;
import org.numahop.numahop.domain.dto.check.AutomaticCheckResultDTO;
import org.numahop.numahop.domain.dto.check.AutomaticCheckTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutomaticCheckResultMapper {

	AutomaticCheckResultMapper INSTANCE = Mappers.getMapper(AutomaticCheckResultMapper.class);

	AutomaticCheckTypeDTO automaticCheckTypeToAutomaticCheckTypeDTO(AutomaticCheckType type);

	AutomaticCheckResultDTO automaticCheckResultToAutomaticCheckResultDTO(AutomaticCheckResult result);

}
