package org.numahop.numahop.repository.checkconfiguration;

import org.numahop.numahop.domain.checkconfiguration.CheckConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface CheckConfigurationRepository
		extends JpaRepository<CheckConfiguration, String>, CheckConfigurationRepositoryCustom {

	@Query("""
			select c from CheckConfiguration c
			join fetch c.library
			where c.identifier = ?1
			""")
	CheckConfiguration findOneWithDependencies(String identifier);

}
