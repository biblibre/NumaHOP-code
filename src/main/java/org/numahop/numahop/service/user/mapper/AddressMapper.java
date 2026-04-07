package org.numahop.numahop.service.user.mapper;

import org.numahop.numahop.domain.dto.user.AddressDTO;
import org.numahop.numahop.domain.user.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

	AddressDTO addressToAddressDTO(Address address);

}
