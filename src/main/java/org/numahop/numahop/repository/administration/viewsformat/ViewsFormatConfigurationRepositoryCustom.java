package org.numahop.numahop.repository.administration.viewsformat;

import org.numahop.numahop.domain.administration.viewsformat.ViewsFormatConfiguration;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ViewsFormatConfigurationRepositoryCustom {

	Page<ViewsFormatConfiguration> search(String search, List<String> libraries, Pageable pageable);

}
