package org.numahop.numahop.repository.administration;

import org.numahop.numahop.domain.administration.SftpConfiguration;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SftpConfigurationRepositoryCustom {

	Page<SftpConfiguration> search(String search, final List<String> libraries, Pageable pageable);

}
