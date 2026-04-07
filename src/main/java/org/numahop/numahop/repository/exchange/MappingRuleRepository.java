package org.numahop.numahop.repository.exchange;

import org.numahop.numahop.domain.document.DocPropertyType;
import org.numahop.numahop.domain.exchange.MappingRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MappingRuleRepository extends JpaRepository<MappingRule, String> {

	Integer countByProperty(DocPropertyType type);

}
