package org.numahop.numahop.service.checkconfiguration.mapper;

import org.numahop.numahop.domain.checkconfiguration.AutomaticCheckRule;
import org.numahop.numahop.domain.dto.checkconfiguration.AutomaticCheckRuleDTO;
import org.numahop.numahop.service.check.mapper.AutomaticCheckTypeMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by ert on 15/09/2017.
 */
@Mapper(uses = AutomaticCheckTypeMapper.class)
public interface AutomaticCheckRuleMapper {

	AutomaticCheckRuleMapper INSTANCE = Mappers.getMapper(AutomaticCheckRuleMapper.class);

	AutomaticCheckRuleDTO checkRuleToCheckRuleDTO(AutomaticCheckRule rule);

	AutomaticCheckRule checkRuleDtoToCheckRule(AutomaticCheckRuleDTO dto);

	List<AutomaticCheckRuleDTO> checkRulesToCheckRulesDTO(List<AutomaticCheckRule> rules);

	List<AutomaticCheckRule> checkRulesDTOToCheckRules(List<AutomaticCheckRuleDTO> rulesDto);

}
