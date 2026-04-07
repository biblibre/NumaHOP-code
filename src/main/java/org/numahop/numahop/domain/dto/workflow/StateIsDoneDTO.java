package org.numahop.numahop.domain.dto.workflow;

import org.numahop.numahop.domain.dto.AbstractVersionedDTO;

public class StateIsDoneDTO extends AbstractVersionedDTO {

	private boolean isDone;

	public StateIsDoneDTO() {

	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

}
