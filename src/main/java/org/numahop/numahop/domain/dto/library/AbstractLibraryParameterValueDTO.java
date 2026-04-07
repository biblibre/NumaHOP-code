package org.numahop.numahop.domain.dto.library;

import org.numahop.numahop.domain.dto.AbstractDTO;

public class AbstractLibraryParameterValueDTO extends AbstractDTO {

	private String identifier;

	public AbstractLibraryParameterValueDTO() {
	}

	public final String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
