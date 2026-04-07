package org.numahop.numahop.repository.lot;

import org.numahop.numahop.domain.dto.lot.SimpleLotDTO;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.repository.lot.helper.LotSearchBuilder;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LotRepositoryCustom {

	/**
	 * récupère les lots attachés aux projets.
	 * @param projectIds
	 * @return
	 */
	List<SimpleLotDTO> findAllIdentifiersInProjectIds(Collection<String> projectIds);

	List<Object[]> getLotGroupByStatus(List<String> libraries, List<String> projects);

	/**
	 * Recherche rapide de lots
	 * @param searchBuilder
	 * @param pageable
	 * @return
	 */
	Page<Lot> search(LotSearchBuilder searchBuilder, Pageable pageable);

	List<Lot> findLotsForWidget(LocalDate fromDate, List<String> libraries, List<String> projects,
			List<Lot.LotStatus> statuses);

}
