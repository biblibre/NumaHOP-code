package org.numahop.numahop.service.util;

import org.numahop.numahop.domain.ObjectWithErrors;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.exception.message.PgcnList;
import java.util.List;

/**
 * Lancement des erreurs
 *
 * @author jbrunet Créé le 10 juil. 2017
 */
public final class ErrorThrowerService {

	private ErrorThrowerService() {
	}

	public static void addAndThrow(ObjectWithErrors object, List<PgcnErrorCode> errorCodes)
			throws PgcnValidationException {
		final PgcnError.Builder builder = new PgcnError.Builder();
		final PgcnList<PgcnError> errors = new PgcnList<>();
		errorCodes.forEach(errorCode -> {
			final PgcnError error = builder.reinit().setCode(errorCode).build();
			errors.add(error);
		});
		object.setErrors(errors);
		throw new PgcnValidationException(object, errors);
	}

}
