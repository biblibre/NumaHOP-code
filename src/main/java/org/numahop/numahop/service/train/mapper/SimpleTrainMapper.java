package org.numahop.numahop.service.train.mapper;

import org.numahop.numahop.domain.dto.train.SimpleTrainDTO;
import org.numahop.numahop.domain.train.Train;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleTrainMapper {

	SimpleTrainMapper INSTANCE = Mappers.getMapper(SimpleTrainMapper.class);

	SimpleTrainDTO trainToSimpleTrainDTO(Train train);

}
