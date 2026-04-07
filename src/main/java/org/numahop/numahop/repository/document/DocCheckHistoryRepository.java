package org.numahop.numahop.repository.document;

import org.numahop.numahop.domain.document.DocCheckHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocCheckHistoryRepository
		extends JpaRepository<DocCheckHistory, String>, DocCheckHistoryRepositoryCustom {

	List<DocCheckHistory> findByPgcnIdAndLibraryIdAndProjectIdAndLotIdAndDeliveryIdAndEndCheckDateIsNull(String pgcnId,
			String libraryId, String projectId, String lotId, String deliveryId);

	List<DocCheckHistory> findByPgcnIdAndLibraryIdAndProjectIdAndLotIdAndTrainIdAndDeliveryIdAndEndCheckDateIsNull(
			String pgcnId, String libraryId, String projectId, String lotId, String trainId, String deliveryId);

}
