package org.numahop.numahop.repository.administration.omeka;

import org.numahop.numahop.domain.administration.omeka.OmekaConfiguration;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OmekaConfigurationRepositoryCustom {

	Page<OmekaConfiguration> search(String search, final List<String> libraries, final Boolean omekas,
			Pageable pageable);

}
