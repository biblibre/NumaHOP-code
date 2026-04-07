package org.numahop.numahop.repository.document.conditionreport;

import org.numahop.numahop.domain.document.conditionreport.Description;
import org.numahop.numahop.domain.document.conditionreport.DescriptionProperty;
import org.numahop.numahop.domain.document.conditionreport.DescriptionValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, String> {

	Long countByProperty(DescriptionProperty property);

	Long countByValue(DescriptionValue value);

}
