package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.train.EsTrain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsTrainRepository extends ElasticsearchRepository<EsTrain, String>, EsTrainRepositoryCustom {

}
