package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.project.EsProject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsProjectRepository extends ElasticsearchRepository<EsProject, String>, EsProjectRepositoryCustom {

}
