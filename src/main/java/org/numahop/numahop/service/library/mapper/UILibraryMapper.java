package org.numahop.numahop.service.library.mapper;

import org.numahop.numahop.domain.dto.library.LibraryDTO;
import org.numahop.numahop.domain.dto.user.AddressDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.user.Address;
import org.numahop.numahop.repository.administration.viewsformat.ViewsFormatConfigurationRepository;
import org.numahop.numahop.repository.checkconfiguration.CheckConfigurationRepository;
import org.numahop.numahop.repository.ftpconfiguration.FTPConfigurationRepository;
import org.numahop.numahop.repository.ocrlangconfiguration.OcrLangConfigurationRepository;
import org.numahop.numahop.repository.user.AddressRepository;
import org.numahop.numahop.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper pour Library
 *
 * @author jbrunet
 */
@Component
public class UILibraryMapper {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private FTPConfigurationRepository ftpConfigurationRepository;

	@Autowired
	private CheckConfigurationRepository checkConfigurationRepository;

	@Autowired
	private ViewsFormatConfigurationRepository viewsFormatConfigurationRepository;

	@Autowired
	private OcrLangConfigurationRepository ocrLangConfigurationRepository;

	@Autowired
	private RoleRepository roleRepository;

	public void mapInto(final LibraryDTO libraryDTO, final Library library) {

		library.setActive(libraryDTO.isActive());
		// Address
		final AddressDTO addressDTO = libraryDTO.getAddress();
		if (addressDTO != null) {
			Address address;
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
			library.setAddress(address);
		}
		library.setEmail(libraryDTO.getEmail());
		library.setName(libraryDTO.getName());
		library.setNumber(libraryDTO.getNumber());
		library.setPhoneNumber(libraryDTO.getPhoneNumber());
		library.setPrefix(libraryDTO.getPrefix());
		library.setWebsite(libraryDTO.getWebsite());
		library.setCinesService(libraryDTO.getCinesService());
		library.setInstitution(libraryDTO.getInstitution());

		if (libraryDTO.getActiveFTPConfiguration() != null) {
			library.setActiveFTPConfiguration(
					ftpConfigurationRepository.findById(libraryDTO.getActiveFTPConfiguration().getIdentifier())
						.orElse(null));
		}
		if (libraryDTO.getActiveCheckConfiguration() != null) {
			library.setActiveCheckConfiguration(
					checkConfigurationRepository.findById(libraryDTO.getActiveCheckConfiguration().getIdentifier())
						.orElse(null));
		}
		if (libraryDTO.getActiveFormatConfiguration() != null) {
			library.setActiveFormatConfiguration(viewsFormatConfigurationRepository
				.findById(libraryDTO.getActiveFormatConfiguration().getIdentifier())
				.orElse(null));
		}
		if (libraryDTO.getActiveOcrLangConfiguration() != null) {
			library.setActiveOcrLangConfiguration(
					ocrLangConfigurationRepository.findById(libraryDTO.getActiveOcrLangConfiguration().getIdentifier())
						.orElse(null));
		}
		if (libraryDTO.getDefaultRole() != null) {
			library.setDefaultRole(roleRepository.findById(libraryDTO.getDefaultRole().getIdentifier()).orElse(null));
		}
		if (libraryDTO.getLibRespName() != null) {
			library.setLibRespName(libraryDTO.getLibRespName());
		}
		if (libraryDTO.getLibRespPhone() != null) {
			library.setLibRespPhone(libraryDTO.getLibRespPhone());
		}
		if (libraryDTO.getLibRespEmail() != null) {
			library.setLibRespEmail(libraryDTO.getLibRespEmail());
		}
	}

}
