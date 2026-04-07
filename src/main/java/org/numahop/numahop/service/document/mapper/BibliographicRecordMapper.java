package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.BibliographicRecord;
import org.numahop.numahop.domain.dto.document.BibliographicRecordDTO;
import org.numahop.numahop.domain.dto.document.DocUnitBibliographicRecordDTO;
import org.numahop.numahop.domain.dto.document.SimpleBibliographicRecordDTO;
import org.numahop.numahop.service.library.mapper.SimpleLibraryMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DocPropertyMapper.class, SimpleDocUnitMapper.class, SimpleLibraryMapper.class })
@DecoratedWith(BibliographicRecordMapperDecorator.class)
public interface BibliographicRecordMapper {

	BibliographicRecordMapper INSTANCE = Mappers.getMapper(BibliographicRecordMapper.class);

	BibliographicRecordDTO bibliographicRecordToBibliographicRecordDTO(BibliographicRecord record);

	SimpleBibliographicRecordDTO bibliographicRecordToSimpleBibliographicRecordDTO(BibliographicRecord record);

	DocUnitBibliographicRecordDTO bibliographicRecordToDocUnitBibliographicRecordDTO(BibliographicRecord record);

}
