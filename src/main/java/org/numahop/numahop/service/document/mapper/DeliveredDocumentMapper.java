package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.delivery.DeliveredDocument;
import org.numahop.numahop.domain.dto.document.DeliveredDigitalDocumentDTO;
import org.numahop.numahop.domain.dto.document.LightDeliveredDigitalDocDTO;
import org.numahop.numahop.domain.dto.document.SimpleDeliveredDigitalDocDTO;
import org.numahop.numahop.domain.dto.document.ValidatedDeliveredDocumentDTO;
import org.numahop.numahop.service.delivery.mapper.DeliveryMapper;
import org.numahop.numahop.service.ftpconfiguration.mapper.SimpleFTPConfigurationMapper;
import org.numahop.numahop.service.lot.mapper.LotMapper;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DigitalDocumentMapper.class, DeliveryMapper.class, LotMapper.class,
		SimpleFTPConfigurationMapper.class })
public interface DeliveredDocumentMapper {

	DeliveredDocumentMapper INSTANCE = Mappers.getMapper(DeliveredDocumentMapper.class);

	@Mappings({ @Mapping(target = "identifier", source = "digitalDocument.identifier"),
			@Mapping(target = "digitalId", source = "digitalDocument.digitalId"),
			@Mapping(target = "automaticCheckResults", source = "digitalDocument.automaticCheckResults"),
			@Mapping(target = "pages", source = "digitalDocument.pages") })
	DeliveredDigitalDocumentDTO docToDTO(DeliveredDocument document);

	@Mappings({ @Mapping(target = "identifier", source = "digitalDocument.identifier"),
			@Mapping(target = "digitalId", source = "digitalDocument.digitalId"),
			@Mapping(target = "docUnitId", source = "digitalDocument.docUnit.identifier") })
	SimpleDeliveredDigitalDocDTO docToSimpleDTO(DeliveredDocument document);

	@Mappings({ @Mapping(target = "digitalId", source = "digitalDocument.digitalId"),
			@Mapping(target = "method", source = "delivery.method"),
			@Mapping(target = "folderPath", source = "delivery.folderPath"),
			@Mapping(target = "address", source = "delivery.lot.activeFTPConfiguration.address"),
			@Mapping(target = "login", source = "delivery.lot.activeFTPConfiguration.login"),
			@Mapping(target = "password", source = "delivery.lot.activeFTPConfiguration.password"),
			@Mapping(target = "deliveryFolder", source = "delivery.lot.activeFTPConfiguration.deliveryFolder") })
	ValidatedDeliveredDocumentDTO validatedToDTO(DeliveredDocument document);

	@Mappings({ @Mapping(target = "identifier", source = "digitalDocument.identifier"),
			@Mapping(target = "digitalId", source = "digitalDocument.digitalId"),
			@Mapping(target = "deliveryId", source = "delivery.identifier"),
			@Mapping(target = "deliveryStatus", source = "delivery.status"),
			@Mapping(target = "deliveryDate", source = "createdDate") })
	LightDeliveredDigitalDocDTO docToLightDto(DeliveredDocument document);

	Set<DeliveredDigitalDocumentDTO> docToDTOs(Set<DeliveredDocument> documents);

	Set<SimpleDeliveredDigitalDocDTO> docToSimpleDTOs(Set<DeliveredDocument> documents);

	Set<ValidatedDeliveredDocumentDTO> validatedToDTOs(Set<DeliveredDocument> documents);

	Set<LightDeliveredDigitalDocDTO> docToLightDtos(Set<DeliveredDocument> documents);

}
