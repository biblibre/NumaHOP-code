package org.numahop.numahop.repository.exchange.template;

import org.numahop.numahop.domain.exchange.template.Name;
import org.numahop.numahop.domain.exchange.template.Template;
import org.numahop.numahop.domain.library.Library;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TemplateRepository extends JpaRepository<Template, String> {

	@Query("""
			select t from Template t
			join fetch t.library
			where t.identifier = ?1
			""")
	Template findByIdentifier(String identifier);

	@Query("""
			select t from Template t
			join fetch t.library l
			""")
	List<Template> findAll();

	@Query("""
			select t from Template t
			join fetch t.library l
			where l = ?1
			""")
	List<Template> findByLibrary(Library library);

	@Query("""
			select t from Template t
			join fetch t.library l
			where t.name = ?1 and l.identifier = ?2
			""")
	List<Template> findByNameAndLibraryIdentifier(Name name, String libraryId);

	Long countByNameAndLibraryIdentifier(Name name, String libraryId);

	Long countByNameAndLibraryIdentifierAndIdentifierNot(Name name, String libraryId, String identifier);

}
