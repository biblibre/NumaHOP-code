package org.numahop.numahop.web.rest.check;

import static org.numahop.numahop.web.rest.document.security.AuthorizationConstants.*;

import org.numahop.numahop.domain.check.AutomaticCheckType.AutoCheckType;
import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.service.check.AutomaticCheckService;
import org.numahop.numahop.service.document.DocUnitService;
import org.numahop.numahop.web.util.LibraryAccesssHelper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur gérant les contrôles automatiques
 *
 * @author jbrunet Créé le 14 févr. 2017
 */
@RestController
@RequestMapping(value = "/api/rest/check/auto")
public class AutomaticCheckController {

	private final AutomaticCheckService autoCheckService;

	private final DocUnitService docUnitService;

	private final LibraryAccesssHelper libraryAccesssHelper;

	@Autowired
	public AutomaticCheckController(final AutomaticCheckService autoCheckService, final DocUnitService docUnitService,
			final LibraryAccesssHelper libraryAccesssHelper) {
		this.autoCheckService = autoCheckService;
		this.docUnitService = docUnitService;
		this.libraryAccesssHelper = libraryAccesssHelper;
	}

	/**
	 * Lance un traitement asynchrone vers FACILE du Cines
	 * @param request
	 * @param docUnitId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, params = { "facile" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@RolesAllowed({ DOC_UNIT_HAB4 })
	public ResponseEntity<?> checkFacile(final HttpServletRequest request,
			@RequestParam(value = "docUnit") final String docUnitId) {
		final DocUnit docUnit = docUnitService.findOneWithAllDependencies(docUnitId, true);
		// Non trouvé
		if (docUnit == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Vérification de la bibliothèque de l'utilisateur
		// 1/ sur l'unité documentaire
		if (!libraryAccesssHelper.checkLibrary(request, docUnit, DocUnit::getLibrary)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		// Traitement
		autoCheckService.asyncUnitCheck(AutoCheckType.FACILE, docUnit, docUnit.getLibrary().getIdentifier());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
