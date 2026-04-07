package org.numahop.numahop.web.rest.exchange;

import com.codahale.metrics.annotation.Timed;
import org.numahop.numahop.domain.exchange.cines.CinesLanguageCode;
import org.numahop.numahop.exception.PgcnException;
import org.numahop.numahop.service.exchange.cines.CinesLanguageCodeService;
import org.numahop.numahop.web.rest.AbstractRestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur basique pour la gestion des codes langue pour export cines.
 */
@RestController
@RequestMapping(value = "/api/rest/conf/cineslangcode")
public class CinesLanguageCodeController extends AbstractRestController {

	private final CinesLanguageCodeService cinesLangCodeService;

	@Autowired
	public CinesLanguageCodeController(final CinesLanguageCodeService cinesLangCodeService) {
		this.cinesLangCodeService = cinesLangCodeService;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<CinesLanguageCode>> getListByActive() {

		return createResponseEntity(cinesLangCodeService.findAllActive(true));
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<CinesLanguageCode>> update(@RequestBody final List<CinesLanguageCode> cinesCodes)
			throws PgcnException {

		final List<CinesLanguageCode> savedCodes = cinesLangCodeService.update(cinesCodes);
		return new ResponseEntity<>(savedCodes, HttpStatus.OK);
	}

}
