package org.numahop.numahop.web.rest.document.conditionreport;

import static org.numahop.numahop.web.rest.delivery.security.AuthorizationConstants.DEL_HAB0;
import static org.numahop.numahop.web.rest.delivery.security.AuthorizationConstants.DEL_HAB2;

import com.codahale.metrics.annotation.Timed;
import org.numahop.numahop.domain.document.conditionreport.ConditionReportSlipConfiguration;
import org.numahop.numahop.exception.PgcnException;
import org.numahop.numahop.service.document.conditionreport.ConditionReportSlipConfigurationService;
import org.numahop.numahop.web.rest.AbstractRestController;
import org.numahop.numahop.web.util.LibraryAccesssHelper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rest/condreportslip_configuration")
public class ConditionReportSlipConfigurationController extends AbstractRestController {

	private final ConditionReportSlipConfigurationService confService;

	private final LibraryAccesssHelper libraryAccesssHelper;

	@Autowired
	public ConditionReportSlipConfigurationController(final ConditionReportSlipConfigurationService confService,
			final LibraryAccesssHelper libraryAccesssHelper) {
		this.confService = confService;
		this.libraryAccesssHelper = libraryAccesssHelper;
	}

	@RolesAllowed(DEL_HAB0)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ConditionReportSlipConfiguration> getById(final HttpServletRequest request,
			@PathVariable final String id) {
		if (!libraryAccesssHelper.checkLibrary(request, id)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Optional<ConditionReportSlipConfiguration> condSlipConfiguration = confService.getOneByLibrary(id);
		return createResponseEntity(condSlipConfiguration);
	}

	@RolesAllowed({ DEL_HAB2 })
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@Timed
	public ResponseEntity<ConditionReportSlipConfiguration> update(final HttpServletRequest request,
			@RequestBody final ConditionReportSlipConfiguration condSlipConfiguration) throws PgcnException {

		if (!libraryAccesssHelper.checkLibrary(request, condSlipConfiguration.getLibrary())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		return createResponseEntity(confService.update(condSlipConfiguration));
	}

}
