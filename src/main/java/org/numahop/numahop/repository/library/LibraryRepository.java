package org.numahop.numahop.repository.library;

import org.numahop.numahop.domain.administration.viewsformat.ViewsFormatConfiguration;
import org.numahop.numahop.domain.checkconfiguration.CheckConfiguration;
import org.numahop.numahop.domain.exportftpconfiguration.ExportFTPConfiguration;
import org.numahop.numahop.domain.ftpconfiguration.FTPConfiguration;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.ocrlangconfiguration.OcrLangConfiguration;
import org.numahop.numahop.domain.user.Role;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LibraryRepository extends JpaRepository<Library, String>, LibraryRepositoryCustom {

	@Query("""
			select l from Library l
			where l.name = ?1
			""")
	Library findByName(String name);

	@Query("""
			select distinct l from Library l
			where l.identifier in ?1
			""")
	List<Library> findByIdentifierIn(Iterable<String> ids, Sort sort);

	@Query("""
			select l from Library l
			left join fetch l.address
			left join fetch l.platforms
			left join fetch l.activeFTPConfiguration
			left join fetch l.activeOcrLangConfiguration
			where l.identifier = ?1
			""")
	Library findOneWithDependencies(String identifier);

	@Query("""
			select distinct l from Library l
			where l.superuser = false and l.active = ?1
			""")
	List<Library> findAllByActive(boolean active);

	@Query("""
			select lib from Library lib
			left join fetch lib.activeOcrLangConfiguration conf
			left join fetch conf.activatedOcrLanguages langs
			left join fetch langs.ocrLanguage
			where lib.identifier = ?1
			""")
	Library findOneWithActifsOcrLanguages(String libraryId);

	Long countByActiveFTPConfiguration(FTPConfiguration conf);

	Long countByActiveExportFTPConfiguration(ExportFTPConfiguration conf);

	Long countByActiveCheckConfiguration(CheckConfiguration conf);

	Long countByActiveOcrLangConfiguration(OcrLangConfiguration conf);

	Long countByActiveFormatConfiguration(ViewsFormatConfiguration conf);

	Long countByDefaultRole(Role role);

}
