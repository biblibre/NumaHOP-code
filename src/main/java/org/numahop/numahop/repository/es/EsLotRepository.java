package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.lot.EsLot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsLotRepository extends ElasticsearchRepository<EsLot, String>, EsLotRepositoryCustom {

}
