package org.numahop.numahop.service.delivery.mapper;

import org.numahop.numahop.domain.delivery.Delivery;
import org.numahop.numahop.domain.dto.delivery.DeliveryDTO;
import org.numahop.numahop.domain.dto.delivery.SimpleDeliveryDTO;
import org.numahop.numahop.domain.dto.delivery.SimpleDeliveryForViewerDTO;
import org.numahop.numahop.domain.dto.delivery.SimpleDeliveryLotDTO;
import org.numahop.numahop.service.check.mapper.AutomaticCheckResultMapper;
import org.numahop.numahop.service.lot.mapper.LotMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { LotMapper.class, AutomaticCheckResultMapper.class })
public interface DeliveryMapper {

	DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

	@Mappings({ @Mapping(target = "multiLotsDelId", source = "multiLotsDelivery.identifier"),
			@Mapping(target = "multiLotsDelLabel", source = "multiLotsDelivery.label") })
	DeliveryDTO deliveryToDeliveryDTO(Delivery delivery);

	SimpleDeliveryForViewerDTO delivToDtoForViewer(Delivery delivery);

	SimpleDeliveryDTO deliveryToSimpleDeliveryDTO(Delivery delivery);

	SimpleDeliveryLotDTO deliveryToSimpleDeliveryLotDTO(Delivery delivery);

}
