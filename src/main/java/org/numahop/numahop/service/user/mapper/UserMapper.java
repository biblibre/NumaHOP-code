package org.numahop.numahop.service.user.mapper;

import org.numahop.numahop.domain.dto.user.SimpleUserDTO;
import org.numahop.numahop.domain.dto.user.UserDTO;
import org.numahop.numahop.domain.user.User;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.numahop.numahop.service.workflow.mapper.SimpleWorkflowMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AddressMapper.class, RoleMapper.class, SimpleLibraryMapper.class, SimpleWorkflowMapper.class })
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDTO userToUserDTO(User user);

	SimpleUserDTO userToSimpleUserDTO(User user);

}
