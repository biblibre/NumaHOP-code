package org.numahop.numahop.web.rest.audit;

import static org.numahop.numahop.web.rest.document.security.AuthorizationConstants.*;

import com.codahale.metrics.annotation.Timed;
import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.domain.dto.audit.AuditDocUnitRevisionDTO;
import org.numahop.numahop.service.audit.AuditDocUnitService;
import org.numahop.numahop.service.document.DocUnitService;
import org.numahop.numahop.web.rest.AbstractRestController;
import org.numahop.numahop.web.util.AccessHelper;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rest/audit/docunit")
public class AuditDocUnitController extends AbstractRestController {

	private final AccessHelper accessHelper;

	private final AuditDocUnitService auditDocUnitService;

	private final DocUnitService docUnitService;

	@Autowired
	public AuditDocUnitController(final AccessHelper accessHelper, final AuditDocUnitService auditDocUnitService,
			final DocUnitService docUnitService) {
		this.accessHelper = accessHelper;
		this.auditDocUnitService = auditDocUnitService;
		this.docUnitService = docUnitService;
	}

	@RequestMapping(value = "/{id}", params = { "rev" }, method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(DOC_UNIT_HAB0)
	public ResponseEntity<DocUnit> getEntity(@PathVariable(value = "id") final String id,
			@RequestParam(value = "rev", defaultValue = "1") final int rev) {
		// Chargement du docUnit existant
		final DocUnit docUnit = docUnitService.findOne(id);
		// Non trouvé
		if (docUnit == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Vérification des droits d'accès par rapport à la bibliothèque de
		// l'utilisateur, pour le docUnit existant
		if (!accessHelper.checkDocUnit(id)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		// Réponse
		return createResponseEntity(auditDocUnitService.getEntity(id, rev));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(DOC_UNIT_HAB0)
	public ResponseEntity<List<AuditDocUnitRevisionDTO>> getRevisions(@PathVariable(value = "id") final String id) {
		// Chargement du docUnit existant
		final DocUnit docUnit = docUnitService.findOne(id);
		// Non trouvé
		if (docUnit == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Vérification des droits d'accès par rapport à la bibliothèque de
		// l'utilisateur, pour le docUnit existant
		if (!accessHelper.checkDocUnit(id)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		// Réponse
		return new ResponseEntity<>(auditDocUnitService.getRevisions(id), HttpStatus.OK);
	}

}
