package fr.progilone.pgcn.domain.dto.document;

import fr.progilone.pgcn.domain.document.Check;
import fr.progilone.pgcn.domain.dto.AbstractDTO;
import java.util.Set;

/**
 * Structure pour renseigner les erreurs sur une page &amp; les notes. Fait également
 * office de 'passe-plat' pour données de table des matieres.
 */
public class DocPageErrorsDTO extends AbstractDTO {

	public DocPageErrorsDTO() {
	}

	private Set<Check.ErrorLabel> failedChecks;

	private String checkNotes;

	/* Donnees pour TOC */
	private String typeToc;

	private String orderToc;

	private String titleToc;

	public Set<Check.ErrorLabel> getFailedChecks() {
		return failedChecks;
	}

	public String getCheckNotes() {
		return checkNotes;
	}

	public void setCheckNotes(final String checkNotes) {
		this.checkNotes = checkNotes;
	}

	public void setFailedChecks(final Set<Check.ErrorLabel> failedChecks) {
		this.failedChecks = failedChecks;
	}

	public String getTypeToc() {
		return typeToc;
	}

	public void setTypeToc(final String typeToc) {
		this.typeToc = typeToc;
	}

	public String getOrderToc() {
		return orderToc;
	}

	public void setOrderToc(final String orderToc) {
		this.orderToc = orderToc;
	}

	public String getTitleToc() {
		return titleToc;
	}

	public void setTitleToc(final String titleToc) {
		this.titleToc = titleToc;
	}

}
