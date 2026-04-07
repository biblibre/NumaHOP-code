package org.numahop.numahop.repository.document;

import org.numahop.numahop.domain.document.DocCheckHistory;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DocCheckHistoryRepositoryCustom {

	List<DocCheckHistory> findDocCheckHistories(List<String> libraries, List<String> projects, List<String> lots,
			List<String> deliveries, LocalDate fromDate, LocalDate toDate);

}
