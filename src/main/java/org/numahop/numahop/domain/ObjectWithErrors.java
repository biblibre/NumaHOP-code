package org.numahop.numahop.domain;

import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnList;
import java.util.Collection;

public interface ObjectWithErrors {

	Collection<PgcnError> getErrorsAsList();

	PgcnList<PgcnError> getErrors();

	void addError(PgcnError error);

	void setErrors(PgcnList<PgcnError> errors);

}
