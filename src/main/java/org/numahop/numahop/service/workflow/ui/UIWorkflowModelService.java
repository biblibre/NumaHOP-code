package org.numahop.numahop.service.workflow.ui;

import org.numahop.numahop.domain.dto.workflow.SimpleWorkflowModelDTO;
import org.numahop.numahop.domain.dto.workflow.WorkflowModelDTO;
import org.numahop.numahop.domain.workflow.WorkflowModel;
import org.numahop.numahop.exception.PgcnBusinessException;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.service.util.transaction.VersionValidationService;
import org.numahop.numahop.service.workflow.WorkflowModelService;
import org.numahop.numahop.service.workflow.mapper.SimpleWorkflowMapper;
import org.numahop.numahop.service.workflow.mapper.UIWorkflowModelMapper;
import org.numahop.numahop.service.workflow.mapper.WorkflowMapper;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de gestion des vues de modèle de workflow
 *
 * @author jbrunet Créé le 19 juil. 2017
 */
@Service
public class UIWorkflowModelService {

	private final WorkflowModelService service;

	private final UIWorkflowModelMapper mapper;

	@Autowired
	public UIWorkflowModelService(final WorkflowModelService service, final UIWorkflowModelMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	@Transactional
	public WorkflowModelDTO create(final WorkflowModelDTO dto) throws PgcnValidationException {
		final WorkflowModel model = new WorkflowModel();
		mapper.mapInto(dto, model);
		try {
			return WorkflowMapper.INSTANCE.modelToModelDTO(service.save(model));
		}
		catch (PgcnBusinessException e) {
			e.getErrors().forEach(semanthequeError -> dto.addError(buildError(semanthequeError.getCode())));
			throw new PgcnValidationException(dto);
		}
	}

	/**
	 * Mise à jour d'un lot
	 * @param dto un objet contenant les informations necessaires à l'enregistrement d'un
	 * lot
	 * @return le lot nouvellement créée ou mise à jour
	 * @throws PgcnValidationException
	 */
	@Transactional
	public WorkflowModelDTO update(final WorkflowModelDTO dto) throws PgcnValidationException {
		final WorkflowModel model = service.getOne(dto.getIdentifier());

		// Contrôle d'accès concurrents
		VersionValidationService.checkForStateObject(model, dto);

		mapper.mapInto(dto, model);
		try {
			return WorkflowMapper.INSTANCE.modelToModelDTO(service.save(model));
		}
		catch (PgcnBusinessException e) {
			e.getErrors().forEach(semanthequeError -> dto.addError(buildError(semanthequeError.getCode())));
			throw new PgcnValidationException(dto);
		}
	}

	private PgcnError buildError(PgcnErrorCode pgcnErrorCode) {
		final PgcnError.Builder builder = new PgcnError.Builder();
		switch (pgcnErrorCode) {
			case WORKFLOW_GROUP_DUPLICATE_NAME:
				builder.setCode(pgcnErrorCode).setField("name");
				break;
			default:
				break;
		}
		return builder.build();
	}

	@Transactional(readOnly = true)
	public WorkflowModelDTO getOne(String id) {
		return WorkflowMapper.INSTANCE.modelToModelDTO(service.getOne(id));
	}

	@Transactional(readOnly = true)
	public Page<SimpleWorkflowModelDTO> search(String search, String initiale, List<String> libraries, Integer page,
			Integer size, List<String> sorts) {
		final Page<WorkflowModel> models = service.search(search, initiale, libraries, page, size, sorts);
		return models.map(SimpleWorkflowMapper.INSTANCE::modelToSimpleModelDTO);
	}

	@Transactional(readOnly = true)
	public Collection<SimpleWorkflowModelDTO> findAllForLibrary(String identifier) {
		return service.findAllForLibrary(identifier)
			.stream()
			.map(SimpleWorkflowMapper.INSTANCE::modelToSimpleModelDTO)
			.collect(Collectors.toList());
	}

}
