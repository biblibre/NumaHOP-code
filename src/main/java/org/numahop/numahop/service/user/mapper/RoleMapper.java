package org.numahop.numahop.service.user.mapper;

import org.numahop.numahop.domain.dto.user.RoleDTO;
import org.numahop.numahop.domain.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	RoleDTO roleToRoleDTO(Role role);

}
