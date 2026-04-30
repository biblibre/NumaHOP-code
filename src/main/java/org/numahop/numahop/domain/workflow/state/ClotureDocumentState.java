package org.numahop.numahop.domain.workflow.state;

import org.numahop.numahop.domain.user.User;
import org.numahop.numahop.domain.workflow.DocUnitState;
import org.numahop.numahop.domain.workflow.WorkflowStateKey;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = WorkflowStateKey.Values.CLOTURE_DOCUMENT)
public class ClotureDocumentState extends DocUnitState {

	@Override
	public WorkflowStateKey getKey() {
		return WorkflowStateKey.CLOTURE_DOCUMENT;
	}

	@Override
	public void process(User user) {
		processEndDate();
		processUser(user);
		processStatus();
	}

	@Override
	protected List<DocUnitState> getNextStates() {
		return new ArrayList<>();
	}

	@Override
	public void reject(User user) {
		// TODO Auto-generated method stub

	}

}
