package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.document.EsDocUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsDocUnitRepository extends ElasticsearchRepository<EsDocUnit, String>, EsDocUnitRepositoryCustom {

}
