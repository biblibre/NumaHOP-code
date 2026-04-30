package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.DigitalDocument;
import org.numahop.numahop.domain.dto.document.DigitalDocumentDTO;
import org.numahop.numahop.domain.dto.document.SimpleDigitalDocumentDTO;
import org.numahop.numahop.domain.dto.document.SimpleListDigitalDocumentDTO;
import org.numahop.numahop.service.check.mapper.AutomaticCheckResultMapper;
import org.numahop.numahop.service.delivery.mapper.DeliveryMapper;
import org.numahop.numahop.service.lot.mapper.SimpleLotMapper;
import org.numahop.numahop.service.project.mapper.SimpleProjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AutomaticCheckResultMapper.class, SimpleDocUnitMapper.class, SimpleLotMapper.class,
		SimpleProjectMapper.class, DeliveredDocumentMapper.class, DeliveryMapper.class })
public interface DigitalDocumentMapper {

	DigitalDocumentMapper INSTANCE = Mappers.getMapper(DigitalDocumentMapper.class);

	@Mapping(target = "docUnit", ignore = true)
	SimpleDigitalDocumentDTO digitalDocumentToSimpleDigitalDocumentDTO(DigitalDocument dd);

	DigitalDocumentDTO digitalDocumentToDigitalDocumentDTO(DigitalDocument dd);

	@Mappings({ @Mapping(source = "dd.docUnit.pgcnId", target = "pgcnId"),
			@Mapping(source = "dd.docUnit.label", target = "label"),
			@Mapping(source = "dd.docUnit.lot", target = "lot"),
			@Mapping(source = "dd.docUnit.project", target = "project"),
			@Mapping(source = "dd.deliveries", target = "deliveries") })
	SimpleListDigitalDocumentDTO digitalDocumentToSimpleListDigitalDocumentDTO(DigitalDocument dd);

}
