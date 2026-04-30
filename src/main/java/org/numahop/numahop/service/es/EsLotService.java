package org.numahop.numahop.service.es;

import org.numahop.numahop.domain.es.lot.EsLot;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.repository.es.EsLotRepository;
import org.numahop.numahop.repository.es.helper.EsSearchOperation;
import org.numahop.numahop.repository.es.helper.EsSort;
import org.numahop.numahop.repository.lot.LotRepository;
import org.numahop.numahop.service.util.transaction.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class EsLotService extends AbstractElasticsearchOperations<Lot, EsLot> {

	private final LotRepository lotRepository;

	private final EsLotRepository esLotRepository;

	@Autowired
	public EsLotService(final LotRepository lotRepository, final EsLotRepository esLotRepository,
			final TransactionService transactionService, final ElasticsearchOperations elasticsearchOperations,
			@Value("${elasticsearch.bulk_size}") final Integer bulkSize) {
		super(transactionService, elasticsearchOperations, bulkSize, EsLot.class, lotRepository, esLotRepository);
		this.lotRepository = lotRepository;
		this.esLotRepository = esLotRepository;
	}

	/**
	 * Recherche d'unités documentaires
	 */
	public Page<EsLot> search(final String[] rawSearches, final String[] rawFilters, final List<String> libraries,
			final boolean fuzzy, final Integer page, final Integer size, final String[] rawSorts, final boolean facet) {
		final EsSearchOperation[] searches = EsSearchOperation.fromRawSearches(rawSearches);
		final EsSearchOperation[] filters = EsSearchOperation.fromRawFilters(rawFilters);
		final Sort sort = EsSort.fromRawSorts(rawSorts, SearchEntity.LOT);
		return esLotRepository.search(searches, libraries, fuzzy, filters, PageRequest.of(page, size, sort), facet);
	}

	@Override
	protected EsLot convertToEsObject(final Lot domainObject) {
		return EsLot.from(domainObject);
	}

	@Override
	protected List<String> findAllIdentifiersToIndex() {
		return lotRepository.findAllIdentifiers();
	}

}
