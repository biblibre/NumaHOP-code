package org.numahop.numahop.service.workflow.mapper;

import org.numahop.numahop.domain.dto.workflow.WorkflowModelDTO;
import org.numahop.numahop.domain.dto.workflow.WorkflowModelStateDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.workflow.WorkflowGroup;
import org.numahop.numahop.domain.workflow.WorkflowModel;
import org.numahop.numahop.domain.workflow.WorkflowModelState;
import org.numahop.numahop.security.SecurityUtils;
import org.numahop.numahop.service.library.LibraryService;
import org.numahop.numahop.service.util.transaction.VersionValidationService;
import org.numahop.numahop.service.workflow.WorkflowGroupService;
import org.numahop.numahop.service.workflow.WorkflowModelStateService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UIWorkflowModelMapper {

	private final WorkflowModelStateService workflowModelStateService;

	private final LibraryService libraryService;

	private final WorkflowGroupService workflowGroupService;

	@Autowired
	public UIWorkflowModelMapper(final WorkflowModelStateService workflowModelStateService,
			final LibraryService libraryService, final WorkflowGroupService workflowGroupService) {
		this.workflowModelStateService = workflowModelStateService;
		this.libraryService = libraryService;
		this.workflowGroupService = workflowGroupService;
	}

	@Transactional(readOnly = true)
	public void mapInto(final WorkflowModelDTO dto, final WorkflowModel domainObject) {
		domainObject.setIdentifier(dto.getIdentifier());
		domainObject.setName(dto.getName());
		domainObject.setDescription(dto.getDescription());
		domainObject.setActive(dto.isActive());

		// States
		List<WorkflowModelStateDTO> statesDTO = dto.getStates();
		if (statesDTO != null) {
			Set<WorkflowModelState> states = new HashSet<>();
			statesDTO.forEach(stateDTO -> {
				WorkflowModelState state = null;
				if (stateDTO.getIdentifier() != null) {
					state = workflowModelStateService.getOne(stateDTO.getIdentifier());
					VersionValidationService.checkForStateObject(state, stateDTO);
				}
				else {
					state = new WorkflowModelState();
					state.setModel(domainObject);
				}
				mapInto(stateDTO, state);
				states.add(state);
			});
			domainObject.setModelStates(states);
		}
		else {
			domainObject.setModelStates(null);
		}

		// Bibliothèque
		Library library = null;
		if (dto.getLibrary() != null) {
			library = libraryService.findByIdentifier(dto.getLibrary().getIdentifier());
		}
		else {
			if (SecurityUtils.getCurrentUser().getLibraryId() != null) {
				library = libraryService.findByIdentifier(SecurityUtils.getCurrentUser().getLibraryId());
			}
		}
		domainObject.setLibrary(library);

	}

	@Transactional(readOnly = true)
	public void mapInto(final WorkflowModelStateDTO dto, final WorkflowModelState domainObject) {
		domainObject.setType(dto.getType());
		WorkflowGroup group = null;
		if (dto.getGroup() != null) {
			group = workflowGroupService.getOne(dto.getGroup().getIdentifier());
		}
		domainObject.setGroup(group);
		// Cas pour création
		if (domainObject.getIdentifier() == null) {
			domainObject.setKey(dto.getKey());
		}
	}

}
