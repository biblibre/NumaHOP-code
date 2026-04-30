package org.numahop.numahop.repository.checkconfiguration;

import org.numahop.numahop.domain.checkconfiguration.AutomaticCheckRule;
import org.numahop.numahop.domain.checkconfiguration.CheckConfiguration;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ert Créé le 14 sept. 2017
 */
public interface AutomaticCheckRuleRepository extends JpaRepository<AutomaticCheckRule, String> {

	List<AutomaticCheckRule> findByCheckConfiguration(CheckConfiguration config);

}
