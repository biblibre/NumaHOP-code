package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.PhysicalDocument;
import org.numahop.numahop.domain.dto.document.PhysicalDocumentDTO;
import org.numahop.numahop.service.train.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UIPhysicalDocumentMapper {

	@Autowired
	private TrainService trainService;

	public void mapInto(final PhysicalDocumentDTO docDTO, final PhysicalDocument doc) {
		doc.setDigitalId(docDTO.getDigitalId());
		doc.setTotalPage(docDTO.getTotalPage());
		doc.setName(docDTO.getName());
		if (docDTO.getTrain() != null) {
			doc.setTrain(trainService.getOne(docDTO.getTrain().getIdentifier()));
		}
	}

}
