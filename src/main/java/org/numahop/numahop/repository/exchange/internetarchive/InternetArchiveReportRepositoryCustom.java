package org.numahop.numahop.repository.exchange.internetarchive;

import org.numahop.numahop.domain.exchange.internetarchive.InternetArchiveReport;
import java.time.LocalDate;
import java.util.List;

public interface InternetArchiveReportRepositoryCustom {

	List<InternetArchiveReport> findAll(List<String> libraries, LocalDate fromDate, boolean failures);

}
