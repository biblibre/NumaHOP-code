package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.delivery.EsDelivery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsDeliveryRepository extends ElasticsearchRepository<EsDelivery, String>, EsDeliveryRepositoryCustom {

}
