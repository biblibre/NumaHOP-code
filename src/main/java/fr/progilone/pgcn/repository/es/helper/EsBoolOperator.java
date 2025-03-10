package fr.progilone.pgcn.repository.es.helper;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/**
 * Type de recherche
 */
public enum EsBoolOperator {

	SHOULD, MUST, MUST_NOT, FILTER;

	/**
	 * Vérifie si la chaine passée en paramètre est un opérateur valide
	 * @param operator
	 * @return
	 */
	public static boolean isValid(final String operator) {
		return !StringUtils.isEmpty(operator)
				&& Arrays.stream(values()).anyMatch(val -> StringUtils.equals(val.name(), operator));
	}

}
