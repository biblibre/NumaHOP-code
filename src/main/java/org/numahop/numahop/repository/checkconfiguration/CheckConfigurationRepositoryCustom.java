package org.numahop.numahop.repository.checkconfiguration;

import org.numahop.numahop.domain.checkconfiguration.CheckConfiguration;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lebouchp on 03/02/2017.
 */
public interface CheckConfigurationRepositoryCustom {

	Page<CheckConfiguration> search(String search, List<String> libraries, Pageable pageable);

}
