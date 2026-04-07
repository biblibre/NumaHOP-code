package org.numahop.numahop.service.user.mapper;

import org.numahop.numahop.domain.dto.user.UserDTO;
import org.numahop.numahop.domain.user.User;

public abstract class UserMapperDecorator implements UserMapper {

	private final UserMapper delegate;

	public UserMapperDecorator(UserMapper delegate) {
		this.delegate = delegate;
	}

	@Override
	public UserDTO userToUserDTO(User user) {
		UserDTO dto = delegate.userToUserDTO(user);
		return dto;
	}

}
