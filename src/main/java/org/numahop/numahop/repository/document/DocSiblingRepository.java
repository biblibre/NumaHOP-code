package org.numahop.numahop.repository.document;

import org.numahop.numahop.domain.document.DocSibling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocSiblingRepository extends JpaRepository<DocSibling, String> {

	@Query("""
			select s from DocSibling s
			join s.docUnits d
			where d.identifier = ?1
			""")
	DocSibling findByDocUnitsIdentifier(String identifier);

}
