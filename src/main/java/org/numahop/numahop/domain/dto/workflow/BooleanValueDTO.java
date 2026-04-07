package org.numahop.numahop.domain.dto.workflow;

import org.numahop.numahop.domain.dto.AbstractDTO;

public class BooleanValueDTO extends AbstractDTO {

	private boolean value;

	public BooleanValueDTO() {

	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

}
