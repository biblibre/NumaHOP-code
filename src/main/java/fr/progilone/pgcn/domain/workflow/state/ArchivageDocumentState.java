package fr.progilone.pgcn.domain.workflow.state;

import fr.progilone.pgcn.domain.user.User;
import fr.progilone.pgcn.domain.workflow.DocUnitState;
import fr.progilone.pgcn.domain.workflow.WorkflowStateKey;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = WorkflowStateKey.Values.ARCHIVAGE_DOCUMENT)
public class ArchivageDocumentState extends DocUnitState {

	@Override
	public WorkflowStateKey getKey() {
		return WorkflowStateKey.ARCHIVAGE_DOCUMENT;
	}

	@Override
	public void process(User user) {
		processEndDate();
		processUser(user);
		processStatus();

		// Initialisation de la prochaine étape si applicable (aucune étape en cours)
		if (this.getWorkflow().getCurrentStates().isEmpty()) {
			getNextStates().forEach(state -> state.initializeState(null, null, null));
		}
	}

	@Override
	protected List<DocUnitState> getNextStates() {
		List<DocUnitState> states = new ArrayList<>();
		states.add(this.getWorkflow().getFutureOrRunningByKey(WorkflowStateKey.CLOTURE_DOCUMENT));
		cleanNullStates(states);
		return states;
	}

	@Override
	public void reject(User user) {
		// TODO Auto-generated method stub

	}

}
