package org.numahop.numahop.service.document.ui;

import org.numahop.numahop.domain.document.Check;
import org.numahop.numahop.domain.dto.document.CheckDTO;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.service.document.CheckService;
import org.numahop.numahop.service.document.mapper.CheckMapper;
import org.numahop.numahop.service.document.mapper.UICheckMapper;
import org.numahop.numahop.service.util.transaction.VersionValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service dédié à les gestion des vues des contrôles (visuels)
 *
 * @author jbrunet
 */
@Service
public class UICheckService {

	private final CheckService checkService;

	private final UICheckMapper uiCheckMapper;

	@Autowired
	public UICheckService(final CheckService checkService, final UICheckMapper uiCheckMapper) {
		this.checkService = checkService;
		this.uiCheckMapper = uiCheckMapper;
	}

	@Transactional
	public CheckDTO create(final CheckDTO dto) throws PgcnValidationException {
		final Check check = new Check();
		uiCheckMapper.mapInto(dto, check);
		Check saved = checkService.save(check);
		return CheckMapper.INSTANCE.checkToCheckDTO(saved);
	}

	@Transactional
	public CheckDTO update(CheckDTO dto) {
		Check check = checkService.findOne(dto.getIdentifier());

		// Contrôle d'accès concurrents
		VersionValidationService.checkForStateObject(check, dto);

		uiCheckMapper.mapInto(dto, check);

		return CheckMapper.INSTANCE.checkToCheckDTO(checkService.save(check));
	}

}
