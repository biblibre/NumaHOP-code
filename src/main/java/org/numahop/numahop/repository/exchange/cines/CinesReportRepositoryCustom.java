package org.numahop.numahop.repository.exchange.cines;

import org.numahop.numahop.domain.exchange.cines.CinesReport;
import java.time.LocalDate;
import java.util.List;

public interface CinesReportRepositoryCustom {

	List<CinesReport> findAll(List<String> libraries, LocalDate fromDate, boolean failures);

}
