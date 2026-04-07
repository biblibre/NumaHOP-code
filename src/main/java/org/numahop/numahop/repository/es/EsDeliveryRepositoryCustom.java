package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.delivery.EsDelivery;
import org.numahop.numahop.repository.es.helper.EsSearchOperation;
import org.numahop.numahop.repository.es.helper.SearchResultPage;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface EsDeliveryRepositoryCustom {

	/**
	 * Recherche paginée
	 */
	SearchResultPage<EsDelivery> search(final EsSearchOperation[] search, final List<String> libraries,
			final boolean fuzzy, final EsSearchOperation[] filters, final PageRequest pageable, final boolean facet);

}
