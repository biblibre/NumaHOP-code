package org.numahop.numahop.repository.library;

import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.library.LibraryParameter;
import org.numahop.numahop.domain.library.LibraryParameter.LibraryParameterType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LibraryParameterRepository extends JpaRepository<LibraryParameter, String> {

	@EntityGraph(value = "LibraryParameter.values")
	LibraryParameter getOneByTypeAndLibrary(LibraryParameterType type, Library library);

	@Query("""
			 select lp from LibraryParameter lp
			join fetch lp.values
			where lp.type = ?1 and lp.library = ?2
			""")
	LibraryParameter getByTypeAndLibraryWithValues(LibraryParameterType type, Library library);

	@EntityGraph(value = "LibraryParameter.values")
	LibraryParameter getOneByIdentifier(String identifier);

	@Modifying
	void deleteByLibrary(Library library);

}
