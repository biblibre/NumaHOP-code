package org.numahop.numahop.repository.document.conditionreport;

import org.numahop.numahop.domain.document.conditionreport.DescriptionProperty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionPropertyRepository extends JpaRepository<DescriptionProperty, String> {

	List<DescriptionProperty> findAllByOrderByOrderAsc();

}
