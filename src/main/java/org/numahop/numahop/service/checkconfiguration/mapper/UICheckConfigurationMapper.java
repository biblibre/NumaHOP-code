package org.numahop.numahop.service.checkconfiguration.mapper;

import org.numahop.numahop.domain.checkconfiguration.AutomaticCheckRule;
import org.numahop.numahop.domain.checkconfiguration.CheckConfiguration;
import org.numahop.numahop.domain.dto.checkconfiguration.CheckConfigurationDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.repository.library.LibraryRepository;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lebouchp on 03/02/2017.
 */
@Service
public class UICheckConfigurationMapper {

	@Autowired
	private LibraryRepository libraryRepository;

	private AutomaticCheckRuleMapper ruleMapper = Mappers.getMapper(AutomaticCheckRuleMapper.class);

	public void mapInto(CheckConfigurationDTO checkConfigurationDTO, CheckConfiguration checkConfiguration) {

		checkConfiguration.setMajorErrorRate(checkConfigurationDTO.getMajorErrorRate());
		checkConfiguration.setMinorErrorRate(checkConfigurationDTO.getMinorErrorRate());
		checkConfiguration.setDefinitionErrorRate(checkConfigurationDTO.getDefinitionErrorRate());
		checkConfiguration.setSampleRate(checkConfigurationDTO.getSampleRate());
		checkConfiguration.setSampleMode(checkConfigurationDTO.getSampleMode());
		checkConfiguration.setLabel(checkConfigurationDTO.getLabel());
		checkConfiguration
			.setSeparators(checkConfigurationDTO.getSeparators() == null ? "_" : checkConfigurationDTO.getSeparators());

		checkConfiguration
			.setSeparators(checkConfigurationDTO.getSeparators() == null ? "_" : checkConfigurationDTO.getSeparators());

		if (checkConfigurationDTO.getAutomaticCheckRules() != null) {
			final List<AutomaticCheckRule> checkRules = new ArrayList<>();
			checkConfigurationDTO.getAutomaticCheckRules().stream().forEach(ruleDto -> {
				final AutomaticCheckRule rule = ruleMapper.checkRuleDtoToCheckRule(ruleDto);
				rule.setCheckConfiguration(checkConfiguration);
				checkRules.add(rule);
			});
			checkConfiguration.setAutomaticCheckRules(checkRules);
		}

		if (checkConfigurationDTO.getLibrary() != null) {
			Library library = libraryRepository.getOne(checkConfigurationDTO.getLibrary().getIdentifier());
			checkConfiguration.setLibrary(library);
		}

	}

}
