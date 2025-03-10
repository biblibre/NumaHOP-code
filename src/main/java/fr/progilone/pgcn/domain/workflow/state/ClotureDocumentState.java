package fr.progilone.pgcn.domain.workflow.state;

import fr.progilone.pgcn.domain.user.User;
import fr.progilone.pgcn.domain.workflow.DocUnitState;
import fr.progilone.pgcn.domain.workflow.WorkflowStateKey;
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
