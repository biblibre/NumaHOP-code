package org.numahop.numahop.domain.dto.workflow;

import org.numahop.numahop.domain.dto.AbstractVersionedDTO;
import org.numahop.numahop.domain.workflow.WorkflowModelStateType;
import org.numahop.numahop.domain.workflow.WorkflowStateKey;
import java.time.Duration;

public class WorkflowModelStateDTO extends AbstractVersionedDTO {

	private String identifier;

	private WorkflowStateKey key;

	private SimpleWorkflowGroupDTO group;

	private Duration duration;

	private WorkflowModelStateType type;

	public WorkflowModelStateDTO() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public WorkflowStateKey getKey() {
		return key;
	}

	public void setKey(WorkflowStateKey key) {
		this.key = key;
	}

	public SimpleWorkflowGroupDTO getGroup() {
		return group;
	}

	public void setGroup(SimpleWorkflowGroupDTO group) {
		this.group = group;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public WorkflowModelStateType getType() {
		return type;
	}

	public void setType(WorkflowModelStateType type) {
		this.type = type;
	}

}
