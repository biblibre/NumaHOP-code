package org.numahop.numahop.service.sample.mapper;

import org.numahop.numahop.domain.document.sample.Sample;
import org.numahop.numahop.domain.dto.sample.SampleDTO;
import org.numahop.numahop.service.delivery.mapper.DeliveryMapper;
import org.numahop.numahop.service.document.mapper.DocPageMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DocPageMapper.class, DeliveryMapper.class })
public interface SampleMapper {

	SampleMapper INSTANCE = Mappers.getMapper(SampleMapper.class);

	SampleDTO sampleToSampleDTO(Sample sample);

}
