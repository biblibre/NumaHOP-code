package org.numahop.numahop.repository.check;

import org.numahop.numahop.domain.check.AutomaticCheckResult;
import org.numahop.numahop.domain.check.AutomaticCheckType.AutoCheckType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author jbrunet Créé le 13 févr. 2017
 */
public interface AutomaticCheckResultRepository extends JpaRepository<AutomaticCheckResult, String> {

	@Query("""
			select distinct res from AutomaticCheckResult res
			left join fetch res.page
			where res.docUnit.identifier = ?1 and res.digitalDocument.identifier = ?2 and res.check.type = ?3
			""")
	List<AutomaticCheckResult> findAllByDocUnitAndDigitalDocumentAndCheckType(String docUnit, String digitalDocument,
			AutoCheckType type);

}
