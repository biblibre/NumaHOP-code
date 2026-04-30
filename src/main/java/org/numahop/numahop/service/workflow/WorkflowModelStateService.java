package org.numahop.numahop.service.workflow;

import org.numahop.numahop.domain.workflow.WorkflowModelState;
import org.numahop.numahop.domain.workflow.WorkflowModelStateType;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.exception.message.PgcnList;
import org.numahop.numahop.repository.workflow.WorkflowModelStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowModelStateService {

	private final WorkflowModelStateRepository repository;

	@Autowired
	public WorkflowModelStateService(final WorkflowModelStateRepository repository) {
		this.repository = repository;
	}

	/**
	 * Sauvegarde un modele
	 * @param state
	 * @return
	 * @throws PgcnValidationException
	 */
	@Transactional
	public WorkflowModelState save(final WorkflowModelState state) throws PgcnValidationException {
		validate(state);
		return repository.save(state);
	}

	/**
	 * Retourne un modele
	 * @param identifier
	 * @return
	 */
	@Transactional(readOnly = true)
	public WorkflowModelState getOne(final String identifier) {
		return repository.getOne(identifier);
	}

	/**
	 * Suppression de modele
	 * @param identifier
	 */
	@Transactional
	public void delete(final String identifier) {
		repository.deleteById(identifier);
	}

	/**
	 * Validation
	 * @param state
	 * @return
	 * @throws PgcnValidationException
	 */
	@Transactional(readOnly = true)
	public PgcnList<PgcnError> validate(final WorkflowModelState state) throws PgcnValidationException {
		final PgcnList<PgcnError> errors = new PgcnList<>();
		final PgcnError.Builder builder = new PgcnError.Builder();

		// le groupe est obligatoire ssi l'étape est de type REQUIRED
		if (state.getGroup() == null && WorkflowModelStateType.REQUIRED.equals(state.getType())) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.WORKFLOW_MODEL_STATE_GROUP_MANDATORY).build());
		}

		/** Retour **/
		if (!errors.isEmpty()) {
			state.setErrors(errors);
			throw new PgcnValidationException(state, errors);
		}
		return errors;
	}

}
