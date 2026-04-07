package org.numahop.numahop.service.util;

import org.numahop.numahop.domain.check.AutomaticCheckResult;
import org.numahop.numahop.domain.check.AutomaticCheckResult.AutoCheckResult;
import org.numahop.numahop.domain.jaxb.facile.ValidatorType;

/**
 * Converti une réponse de contrôle automatique en une réponse au bon format
 */
public final class AutomaticCheckResultConvertUtil {

	private AutomaticCheckResultConvertUtil() {
	}

	public static AutomaticCheckResult convert(ValidatorType response) {
		if (response == null)
			return null;
		AutomaticCheckResult result = new AutomaticCheckResult();
		if (response.isValid()) {
			result.setResult(AutoCheckResult.OK);
		}
		else {
			result.setResult(AutoCheckResult.KO);
			result.setMessage(response.getMessage());
		}
		return result;
	}

}
