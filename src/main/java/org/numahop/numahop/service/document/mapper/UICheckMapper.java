package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.Check;
import org.numahop.numahop.domain.dto.document.CheckDTO;
import org.numahop.numahop.service.document.DocPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UICheckMapper {

	@Autowired
	private DocPageService docPageService;

	public void mapInto(final CheckDTO checkDTO, final Check check) {
		check.setErrorLabel(Check.ErrorLabel.valueOf(checkDTO.getErrorLabel()));
		check.setErrorType(Check.ErrorType.valueOf(checkDTO.getErrorType()));
		check.setPage(docPageService.findOne(checkDTO.getPage().getIdentifier()));
	}

}
