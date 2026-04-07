package org.numahop.numahop.domain.dto.workflow;

import org.numahop.numahop.domain.dto.AbstractDTO;

public class SimpleWorkflowGroupDTO extends AbstractDTO {

	private String identifier;

	private String name;

	public SimpleWorkflowGroupDTO() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
