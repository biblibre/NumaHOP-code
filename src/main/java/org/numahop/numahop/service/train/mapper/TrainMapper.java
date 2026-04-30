package org.numahop.numahop.service.train.mapper;

import org.numahop.numahop.domain.dto.train.TrainDTO;
import org.numahop.numahop.domain.train.Train;
import org.numahop.numahop.service.document.mapper.PhysicalDocumentMapper;
import org.numahop.numahop.service.project.mapper.SimpleProjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleProjectMapper.class, PhysicalDocumentMapper.class })
public interface TrainMapper {

	TrainMapper INSTANCE = Mappers.getMapper(TrainMapper.class);

	TrainDTO trainToTrainDTO(Train train);

}
