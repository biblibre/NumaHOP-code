package org.numahop.numahop.domain.ocrlangconfiguration;

import org.numahop.numahop.domain.AbstractDomainObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Langage utilisé pour l'OCRisation.
 *
 * @author ert
 */

@Entity
@Table(name = OcrLanguage.TABLE_NAME)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OcrLanguage extends AbstractDomainObject {

	public static final String TABLE_NAME = "conf_language_ocr";

	@Column(name = "code")
	private String code;

	@Column(name = "label")
	private String label;

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

}
