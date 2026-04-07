package org.numahop.numahop.service.exchange.internetarchive.mapper;

import org.numahop.numahop.domain.dto.exchange.InternetArchiveReportDTO;
import org.numahop.numahop.domain.exchange.internetarchive.InternetArchiveReport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InternetArchiveReportMapper {

	InternetArchiveReportMapper INSTANCE = Mappers.getMapper(InternetArchiveReportMapper.class);

	InternetArchiveReportDTO internetArchiveReportToInternetArchiveReportDTO(InternetArchiveReport report);

}
