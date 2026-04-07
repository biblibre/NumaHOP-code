package org.numahop.numahop.repository.exchange;

import org.numahop.numahop.domain.exchange.ImportReport;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImportReportRepositoryCustom {

	/**
	 * Recherche rapide d'imports
	 */
	Page<ImportReport> search(String search, List<String> users, List<ImportReport.Status> status,
			List<String> libraries, Pageable pageRequest);

}
