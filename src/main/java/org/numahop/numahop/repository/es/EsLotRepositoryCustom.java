package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.lot.EsLot;
import org.numahop.numahop.repository.es.helper.EsSearchOperation;
import org.numahop.numahop.repository.es.helper.SearchResultPage;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface EsLotRepositoryCustom {

	/**
	 * Recherche paginée
	 * @param search
	 * @param libraries
	 * @param fuzzy
	 * @param filters
	 * @param pageable
	 * @param facet
	 */
	SearchResultPage<EsLot> search(final EsSearchOperation[] search, final List<String> libraries, final boolean fuzzy,
			final EsSearchOperation[] filters, final PageRequest pageable, final boolean facet);

}
