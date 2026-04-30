package org.numahop.numahop.service.exchange.cines.mapper;

import org.numahop.numahop.domain.dto.exchange.CinesReportDTO;
import org.numahop.numahop.domain.exchange.cines.CinesReport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CinesReportMapper {

	CinesReportMapper INSTANCE = Mappers.getMapper(CinesReportMapper.class);

	CinesReportDTO cinesReportToCinesReportDTO(CinesReport report);

}
