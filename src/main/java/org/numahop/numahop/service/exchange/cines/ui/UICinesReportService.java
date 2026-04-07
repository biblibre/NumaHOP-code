package org.numahop.numahop.service.exchange.cines.ui;

import org.numahop.numahop.domain.dto.exchange.CinesReportDTO;
import org.numahop.numahop.domain.dto.statistics.StatisticsProcessedDocUnitDTO;
import org.numahop.numahop.domain.exchange.cines.CinesReport;
import org.numahop.numahop.service.exchange.cines.CinesReportService;
import org.numahop.numahop.service.exchange.cines.mapper.CinesReportMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service dédié à les gestion des vues des unités documentaires
 *
 * @author jbrunet
 */
@Service
public class UICinesReportService {

	@Autowired
	private CinesReportService cinesReportService;

	/**
	 * Récupère la liste des rapports CINES liés à une unité doc
	 * @param docUnitId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<CinesReportDTO> findAllByDocUnitIdentifier(String docUnitId) {
		List<CinesReport> reports = cinesReportService.findByDocUnit(docUnitId);
		return reports.stream()
			.map(CinesReportMapper.INSTANCE::cinesReportToCinesReportDTO)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<StatisticsProcessedDocUnitDTO> findAll(final List<String> libraries, final LocalDate fromDate,
			final boolean failures) {
		return cinesReportService.findAll(libraries, fromDate, failures).stream().map(cinesReport -> {
			final StatisticsProcessedDocUnitDTO dto = new StatisticsProcessedDocUnitDTO();
			dto.setStatus(cinesReport.getStatus().name());
			dto.setDate(cinesReport.getDateSent());
			dto.setMessage(cinesReport.getMessage());

			dto.setIdentifier(cinesReport.getDocUnit().getIdentifier());
			dto.setPgcnId(cinesReport.getDocUnit().getPgcnId());

			return dto;
		}).collect(Collectors.toList());
	}

}
