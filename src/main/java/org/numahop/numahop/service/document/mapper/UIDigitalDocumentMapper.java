package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.DigitalDocument;
import org.numahop.numahop.domain.dto.document.DigitalDocumentDTO;
import org.springframework.stereotype.Service;

@Service
public class UIDigitalDocumentMapper {

	public void mapInto(final DigitalDocumentDTO docDTO, final DigitalDocument doc) {
		doc.setDigitalId(docDTO.getDigitalId());
		doc.setCheckNotes(docDTO.getCheckNotes());
		doc.getDocUnit().setDigitizingNotes(docDTO.getDocUnit().getDigitizingNotes());
	}

}
