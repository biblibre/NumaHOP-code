package org.numahop.numahop.repository.es;

import org.numahop.numahop.domain.es.conditionreport.EsConditionReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsConditionReportRepository
		extends ElasticsearchRepository<EsConditionReport, String>, EsConditionReportRepositoryCustom {

}
