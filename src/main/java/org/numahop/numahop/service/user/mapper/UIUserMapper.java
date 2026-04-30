package org.numahop.numahop.service.user.mapper;

import org.numahop.numahop.domain.dto.library.SimpleLibraryDTO;
import org.numahop.numahop.domain.dto.user.AddressDTO;
import org.numahop.numahop.domain.dto.user.RoleDTO;
import org.numahop.numahop.domain.dto.user.SimpleUserAccountDTO;
import org.numahop.numahop.domain.dto.user.UserCreationDTO;
import org.numahop.numahop.domain.dto.user.UserDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.user.Address;
import org.numahop.numahop.domain.user.Authorization;
import org.numahop.numahop.domain.user.Role;
import org.numahop.numahop.domain.user.User;
import org.numahop.numahop.domain.user.User.Category;
import org.numahop.numahop.repository.user.AddressRepository;
import org.numahop.numahop.service.library.LibraryService;
import org.numahop.numahop.service.user.RoleService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UIUserMapper {

	private final RoleService roleService;

	private final LibraryService libraryService;

	private final AddressRepository addressRepository;

	@Autowired
	public UIUserMapper(final RoleService roleService, final LibraryService libraryService,
			final AddressRepository addressRepository) {
		this.roleService = roleService;
		this.libraryService = libraryService;
		this.addressRepository = addressRepository;
	}

	public void mapInto(final UserDTO userDTO, final User user) {
		user.setActive(userDTO.getActive());
		// Category
		if (userDTO.getCategory() != null) {
			user.setCategory(Category.valueOf(userDTO.getCategory()));
		}
		// PROVIDER fields only
		if (Category.PROVIDER.equals(user.getCategory())) {
			// Address
			final AddressDTO addressDTO = userDTO.getAddress();
			Address address;
			if (addressDTO != null) {
				if (addressDTO.getIdentifier() == null) {
					address = new Address();
				}
				else {
					address = addressRepository.findById(addressDTO.getIdentifier()).orElseThrow();
				}
				address.setLabel(addressDTO.getLabel());
				address.setAddress1(addressDTO.getAddress1());
				address.setAddress2(addressDTO.getAddress2());
				address.setAddress3(addressDTO.getAddress3());
				address.setComplement(addressDTO.getComplement());
				address.setCity(addressDTO.getCity());
				address.setPostcode(addressDTO.getPostcode());
				address.setCountry(addressDTO.getCountry());
				user.setAddress(address);
			}
			user.setCompanyName(userDTO.getCompanyName());
		}
		user.setEmail(userDTO.getEmail());
		user.setFirstname(userDTO.getFirstname());
		user.setFunction(userDTO.getFunction());
		// Library
		final SimpleLibraryDTO library = userDTO.getLibrary();
		if (library != null && library.getIdentifier() != null) {
			final Library lib = libraryService.findOne(library.getIdentifier());
			user.setLibrary(lib);
		}
		user.setLogin(userDTO.getLogin());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setSurname(userDTO.getSurname());
		// Role
		final RoleDTO roleDTO = userDTO.getRole();
		if (roleDTO != null && roleDTO.getIdentifier() != null) {
			final Role role = roleService.findOne(roleDTO.getIdentifier());
			user.setRole(role);
		}
		// handle password if creation
		if (userDTO instanceof UserCreationDTO) {
			user.setPassword(((UserCreationDTO) userDTO).getPassword());
		}
	}

	public SimpleUserAccountDTO userToSimpleUserAccountDTO(final User user) {
		return new SimpleUserAccountDTO.Builder().reinit()
			.setFirstname(user.getFirstname())
			.setIdentifier(user.getIdentifier())
			.setLogin(user.getLogin())
			.setSurname(user.getSurname())
			.setDashboard(user.getDashboard() != null ? user.getDashboard().getDashboard() : null)
			.setLibrary(user.getLibrary() != null ? user.getLibrary().getIdentifier() : null)
			.setCategory(user.getCategory() != null ? user.getCategory().name() : null)
			.setRoles(user.getRole() != null ? user.getRole()
				.getAuthorizations()
				.stream()
				.map(Authorization::getCode)
				.collect(Collectors.toList()) : null)
			.build();
	}

}
