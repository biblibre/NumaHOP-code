package org.numahop.numahop.domain.dto.workflow;

import org.numahop.numahop.domain.dto.AbstractDTO;

public class SimpleWorkflowModelStateDTO extends AbstractDTO {

	private String identifier;

	private String key;

	public SimpleWorkflowModelStateDTO() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
