package org.numahop.numahop.domain.ocrlangconfiguration;

import org.numahop.numahop.domain.AbstractDomainObject;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = ActivatedOcrLanguage.TABLE_NAME)
public class ActivatedOcrLanguage extends AbstractDomainObject {

	public static final String TABLE_NAME = "conf_activated_ocr_lang";

	/**
	 * Entités liés
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ocr_lang_configuration")
	private OcrLangConfiguration ocrLangConfiguration;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ocr_language")
	private OcrLanguage ocrLanguage;

	public OcrLangConfiguration getOcrLangConfiguration() {
		return ocrLangConfiguration;
	}

	public void setOcrLangConfiguration(final OcrLangConfiguration ocrLangConfiguration) {
		this.ocrLangConfiguration = ocrLangConfiguration;
	}

	public OcrLanguage getOcrLanguage() {
		return ocrLanguage;
	}

	public void setOcrLanguage(final OcrLanguage ocrLanguage) {
		this.ocrLanguage = ocrLanguage;
	}

}
