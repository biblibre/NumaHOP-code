package org.numahop.numahop.web.rest.document.conditionreport;

import static org.numahop.numahop.web.rest.document.security.AuthorizationConstants.*;

import com.codahale.metrics.annotation.Timed;
import org.numahop.numahop.domain.document.conditionreport.DescriptionProperty;
import org.numahop.numahop.domain.document.conditionreport.PropertyConfiguration;
import org.numahop.numahop.domain.dto.document.conditionreport.PropertyConfigurationDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.project.Project;
import org.numahop.numahop.exception.PgcnException;
import org.numahop.numahop.service.document.conditionreport.ui.UiPropertyConfigurationService;
import org.numahop.numahop.web.rest.AbstractRestController;
import org.numahop.numahop.web.util.AccessHelper;
import org.numahop.numahop.web.util.LibraryAccesssHelper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rest/condreport_prop_conf")
public class PropertyConfigurationController extends AbstractRestController {

	private final LibraryAccesssHelper libraryAccesssHelper;

	private final AccessHelper accessHelper;

	private final UiPropertyConfigurationService propertyConfigurationService;

	@Autowired
	public PropertyConfigurationController(final LibraryAccesssHelper libraryAccesssHelper,
			final AccessHelper accessHelper, final UiPropertyConfigurationService propertyConfigurationService) {
		this.libraryAccesssHelper = libraryAccesssHelper;
		this.accessHelper = accessHelper;
		this.propertyConfigurationService = propertyConfigurationService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@Timed
	@RolesAllowed(COND_REPORT_HAB5)
	public ResponseEntity<PropertyConfigurationDTO> create(final HttpServletRequest request,
			@RequestBody final PropertyConfigurationDTO value) throws PgcnException {
		// Vérification des droits d'accès par rapport à la bibliothèque demandée
		if (!libraryAccesssHelper.checkLibrary(request, value.getLibrary().getIdentifier())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(propertyConfigurationService.save(value), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, params = { "library" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({ COND_REPORT_HAB0 })
	public ResponseEntity<List<PropertyConfigurationDTO>> findByLibrary(final HttpServletRequest request,
			@RequestParam(name = "library") final Library library,
			@RequestParam(name = "project") final Project project) {
		// Vérification des droits d'accès par rapport à la bibliothèque demandée
		if (!libraryAccesssHelper.checkLibrary(request, library)
				&& !accessHelper.checkProject(project.getIdentifier())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return createResponseEntity(propertyConfigurationService.findByLibrary(library));
	}

	@RequestMapping(method = RequestMethod.GET, params = { "desc", "library" },
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({ COND_REPORT_HAB0 })
	public ResponseEntity<PropertyConfigurationDTO> findByDescPropertyAndLibrary(final HttpServletRequest request,
			@RequestParam(name = "desc") final DescriptionProperty property,
			@RequestParam(name = "library") final Library library) {
		// Vérification des droits d'accès par rapport à la bibliothèque demandée
		if (!libraryAccesssHelper.checkLibrary(request, library)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return createResponseEntity(propertyConfigurationService.findByDescPropertyAndLibrary(property, library));
	}

	@RequestMapping(method = RequestMethod.GET, params = { "internal", "library" },
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({ COND_REPORT_HAB0 })
	public ResponseEntity<PropertyConfigurationDTO> findByInternalPropertyAndLibrary(final HttpServletRequest request,
			@RequestParam(name = "internal") final PropertyConfiguration.InternalProperty property,
			@RequestParam(name = "library") final Library library) {
		// Vérification des droits d'accès par rapport à la bibliothèque demandée
		if (!libraryAccesssHelper.checkLibrary(request, library)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return createResponseEntity(propertyConfigurationService.findByInternalPropertyAndLibrary(property, library));
	}

	@RequestMapping(value = "/{identifier}", method = RequestMethod.POST)
	@Timed
	@RolesAllowed(COND_REPORT_HAB5)
	public ResponseEntity<PropertyConfigurationDTO> update(final HttpServletRequest request,
			@RequestBody final PropertyConfigurationDTO value) throws PgcnException {
		// Vérification des droits d'accès par rapport à la bibliothèque demandée
		if (!libraryAccesssHelper.checkLibrary(request, value.getLibrary().getIdentifier())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(propertyConfigurationService.save(value), HttpStatus.OK);
	}

}
