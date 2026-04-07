package org.numahop.numahop.repository.ftpconfiguration;

import org.numahop.numahop.domain.ftpconfiguration.FTPConfiguration;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lebouchp on 03/02/2017.
 */
public interface FTPConfigurationRepositoryCustom {

	Page<FTPConfiguration> search(String search, final List<String> libraries, Pageable pageable);

}
