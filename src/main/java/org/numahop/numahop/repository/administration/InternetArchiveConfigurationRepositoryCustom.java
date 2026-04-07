package org.numahop.numahop.repository.administration;

import org.numahop.numahop.domain.administration.InternetArchiveConfiguration;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InternetArchiveConfigurationRepositoryCustom {

	Page<InternetArchiveConfiguration> search(String search, final List<String> libraries, Pageable pageable);

}
