package org.numahop.numahop.exception.dto;

import org.numahop.numahop.exception.PgcnExceptionLevel;
import org.numahop.numahop.exception.message.PgcnError;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Progilone_2 on 05/01/2016.
 */
public class PgcnBusinessExceptionDTO {

	private PgcnExceptionLevel level;

	private final List<PgcnError> errors = new ArrayList<>();

	public PgcnBusinessExceptionDTO(final PgcnExceptionLevel level, List<PgcnError> errors) {
		this.level = level;
		this.errors.clear();
		if (errors != null) {
			this.errors.addAll(errors);
		}
	}

	public PgcnExceptionLevel getLevel() {
		return level;
	}

	public void setLevel(final PgcnExceptionLevel level) {
		this.level = level;
	}

	public List<PgcnError> getErrors() {
		return errors;
	}

}
