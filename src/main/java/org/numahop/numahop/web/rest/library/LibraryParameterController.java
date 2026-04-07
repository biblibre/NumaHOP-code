package org.numahop.numahop.web.rest.library;

import static org.numahop.numahop.web.rest.library.security.AuthorizationConstants.LIB_HAB1;
import static org.numahop.numahop.web.rest.library.security.AuthorizationConstants.LIB_HAB2;
import static org.numahop.numahop.web.rest.library.security.AuthorizationConstants.LIB_HAB3;
import static org.numahop.numahop.web.rest.library.security.AuthorizationConstants.LIB_HAB5;
import static org.numahop.numahop.web.rest.library.security.AuthorizationConstants.LIB_HAB6;
import static org.numahop.numahop.web.rest.library.security.AuthorizationConstants.LIB_HAB7;

import com.codahale.metrics.annotation.Timed;
import org.numahop.numahop.domain.administration.SftpConfiguration;
import org.numahop.numahop.domain.dto.library.LibraryParameterDTO;
import org.numahop.numahop.domain.dto.library.LibraryParameterValuedDTO;
import org.numahop.numahop.domain.library.LibraryParameter;
import org.numahop.numahop.exception.PgcnException;
import org.numahop.numahop.service.administration.SftpConfigurationService;
import org.numahop.numahop.service.library.LibraryParameterService;
import org.numahop.numahop.service.library.ui.UILibraryParameterService;
import org.numahop.numahop.web.rest.AbstractRestController;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rest/libraryparameter")
public class LibraryParameterController extends AbstractRestController {

	private final LibraryParameterService libraryParameterService;

	private final UILibraryParameterService uiLibraryParameterService;

	private final SftpConfigurationService sftpConfigurationService;

	@Autowired
	public LibraryParameterController(final LibraryParameterService libraryParameterService,
			final UILibraryParameterService uiLibraryParameterService,
			final SftpConfigurationService sftpConfigurationService) {
		super();
		this.libraryParameterService = libraryParameterService;
		this.uiLibraryParameterService = uiLibraryParameterService;
		this.sftpConfigurationService = sftpConfigurationService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@Timed
	@RolesAllowed(LIB_HAB1)
	public ResponseEntity<LibraryParameterValuedDTO> create(final HttpServletRequest request,
			@RequestBody final LibraryParameterValuedDTO param) throws PgcnException {
		final LibraryParameterValuedDTO savedLibraryParameter = uiLibraryParameterService.create(param);
		return new ResponseEntity<>(savedLibraryParameter, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Timed
	@RolesAllowed(LIB_HAB3)
	public ResponseEntity<LibraryParameter> delete(final HttpServletRequest request, @PathVariable final String id) {
		libraryParameterService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({ LIB_HAB5, LIB_HAB6, LIB_HAB7 })
	public ResponseEntity<LibraryParameterDTO> getById(final HttpServletRequest request,
			@PathVariable final String id) {
		final LibraryParameterDTO libraryParameter = uiLibraryParameterService.getOneDTO(id);
		return createResponseEntity(libraryParameter);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@Timed
	@RolesAllowed({ LIB_HAB2 })
	public ResponseEntity<LibraryParameterValuedDTO> update(final HttpServletRequest request,
			@RequestBody final LibraryParameterValuedDTO param) throws PgcnException {
		final LibraryParameterValuedDTO savedLibraryParam = uiLibraryParameterService.update(param);
		return new ResponseEntity<>(savedLibraryParam, HttpStatus.OK);
	}

	@RequestMapping(params = { "cinesdefaultvalues" }, method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({ LIB_HAB5, LIB_HAB6, LIB_HAB7 })
	public ResponseEntity<LibraryParameterValuedDTO> getCinesDefaultValuesByLibrary(
			@RequestParam(name = "sftpConfig", required = false) final String confId) {

		final SftpConfiguration conf = sftpConfigurationService.findOne(confId);
		final LibraryParameterValuedDTO libraryParameter = uiLibraryParameterService
			.getCinesDefaultValues(conf.getLibrary());
		return createResponseEntity(libraryParameter);
	}

}
