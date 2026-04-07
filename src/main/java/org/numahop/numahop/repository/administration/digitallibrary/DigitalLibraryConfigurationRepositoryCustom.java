package org.numahop.numahop.repository.administration.digitallibrary;

import org.numahop.numahop.domain.administration.digitallibrary.DigitalLibraryConfiguration;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DigitalLibraryConfigurationRepositoryCustom {

	Page<DigitalLibraryConfiguration> search(String search, final List<String> libraries, Pageable pageable);

}
