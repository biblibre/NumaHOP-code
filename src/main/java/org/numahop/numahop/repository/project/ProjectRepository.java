package org.numahop.numahop.repository.project;

import org.numahop.numahop.domain.administration.viewsformat.ViewsFormatConfiguration;
import org.numahop.numahop.domain.checkconfiguration.CheckConfiguration;
import org.numahop.numahop.domain.exportftpconfiguration.ExportFTPConfiguration;
import org.numahop.numahop.domain.ftpconfiguration.FTPConfiguration;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.project.Project;
import org.numahop.numahop.domain.user.User;
import org.numahop.numahop.domain.workflow.WorkflowModel;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, String>, ProjectRepositoryCustom {

	List<Project> findByIdentifierIn(Iterable<String> ids);

	@Query("""
			select distinct p from Project p
			left join fetch p.library
			left join fetch p.associatedLibraries
			left join fetch p.lots
			left join fetch p.associatedPlatforms
			left join fetch p.associatedUsers
			left join fetch p.activeFTPConfiguration
			where p.identifier in ?1
			""")
	List<Project> findByIdentifierIn(Iterable<String> ids, Sort sort);

	@Query("""
			select p from Project p
			left join fetch p.library
			left join fetch p.associatedLibraries
			left join fetch p.trains
			left join fetch p.lots
			left join fetch p.associatedPlatforms
			left join fetch p.docUnits
			left join fetch p.associatedUsers
			left join fetch p.activeFTPConfiguration
			left join fetch p.activeFormatConfiguration
			where p.identifier = ?1
			""")
	Project findOneWithDependencies(String identifier);

	@Query("""
			select p from Project p
			left join fetch p.trains
			left join fetch p.lots l
			left join fetch l.docUnits
			where p.identifier = ?1
			""")
	Project findOneWithLightDependencies(String identifier);

	@Query("""
			select distinct p from Project p
			left join fetch p.library
			left join fetch p.associatedLibraries
			left join fetch p.trains
			left join fetch p.lots
			left join fetch p.associatedPlatforms
			left join fetch p.docUnits
			left join fetch p.associatedUsers
			left join fetch p.activeFTPConfiguration
			where p.id in ?1
			""")
	List<Project> findAllWithDependencies(final Collection<String> ids);

	@Query("""
			select p from Project p
			left join fetch p.library
			left join fetch p.associatedLibraries
			left join fetch p.trains
			left join fetch p.lots
			left join fetch p.associatedPlatforms
			left join fetch p.docUnits
			left join fetch p.associatedUsers
			left join fetch p.activeFTPConfiguration
			where p.name = ?1
			""")
	Project findOneByNameWithDependencies(String name);

	Project findOneByIdentifier(String identifier);

	List<Project> findAllByActive(boolean active);

	@Query("""
			select p from Project p
			left join fetch p.library q
			left join fetch q.ftpConfigurations f
			left join fetch q.checkConfigurations c
			left join fetch q.viewsFormatConfigurations
			where p.identifier = ?1
			""")
	Project findOneWithFTPConfiguration(String id);

	@Query("""
			select p from Project p
			left join fetch p.library q
			left join fetch q.exportFtpConfigurations
			where p.identifier = ?1
			""")
	Project findOneWithExportFTPConfiguration(String id);

	List<Project> findAllByActiveAndLibraryIdentifierIn(boolean active, List<String> libraries);

	List<Project> findAllByLibraryIdentifierIn(List<String> libraries);

	List<Project> findAllByLibraryIdentifier(String library);

	List<Project> findAllByAssociatedLibraries(Library library);

	List<Project> findAllByAssociatedUsers(User user);

	Long countByActiveFTPConfiguration(FTPConfiguration conf);

	Long countByActiveExportFTPConfiguration(ExportFTPConfiguration conf);

	Long countByActiveCheckConfiguration(CheckConfiguration conf);

	Long countByActiveFormatConfiguration(ViewsFormatConfiguration conf);

	Long countByLibrary(Library library);

	Long countByProvider(User user);

	Long countByWorkflowModel(WorkflowModel model);

	@Query("""
			select p.status, count(p.identifier) from Project p group by p.status
			""")
	List<Object[]> getProjectGroupByStatus();

	@Query("""
			select p.status, count(p.identifier) from Project p
			where p.library.identifier in ?1 group by p.status
			""")
	List<Object[]> getProjectGroupByStatus(List<String> libraries);

	@Query("""
			select p from Project p
			left join fetch p.library l
			where p.status = 'CLOSED' and p.filesArchived = false and l.identifier = ?1 and p.realEndDate < ?2
			""")
	List<Project> getClosedProjectsByLibrary(String libraryId, LocalDate dateTo);

	@Query("""
			select p from Project p
			left join fetch p.docUnits du
			left join fetch p.lots
			left join fetch p.trains
			where du.identifier = ?1
			""")
	Project findOneByDocUnitId(String identifier);

	@Query("""
			select p from Project p
			left join fetch p.lots lo
			left join fetch p.trains
			where lo.identifier = ?1
			""")
	Project findOneByLotId(String identifier);

	@Query("""
			select p.identifier from Project p
			""")
	List<String> findAllIdentifiers();

}
