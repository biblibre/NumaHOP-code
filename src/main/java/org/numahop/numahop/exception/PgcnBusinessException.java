package org.numahop.numahop.exception;

import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnList;

/**
 * Classe exception métier.
 */
public class PgcnBusinessException extends PgcnException {

	public PgcnBusinessException(final PgcnList<PgcnError> errors) {
		super(errors);
	}

	public PgcnBusinessException(final PgcnList<PgcnError> errors, PgcnExceptionLevel level) {
		super(errors, level);
	}

	public PgcnBusinessException(final PgcnError error) {
		super(error);
	}

	public PgcnBusinessException(final PgcnError error, PgcnExceptionLevel level) {
		super(error, level);
	}

}
