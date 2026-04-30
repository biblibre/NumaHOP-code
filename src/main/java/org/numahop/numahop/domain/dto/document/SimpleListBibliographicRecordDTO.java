package org.numahop.numahop.domain.dto.document;

import org.numahop.numahop.domain.dto.AbstractDTO;
import org.numahop.numahop.domain.dto.lot.SimpleLotDTO;
import org.numahop.numahop.domain.dto.project.SimpleProjectDTO;
import org.numahop.numahop.domain.dto.train.SimpleTrainDTO;

/**
 * DTO représentant une notice pour les listes
 */
public class SimpleListBibliographicRecordDTO extends AbstractDTO {

	private String identifier;

	private String title;

	private SimpleDocUnitDTO docUnit;

	private SimpleProjectDTO project;

	private SimpleLotDTO lot;

	private SimpleTrainDTO train;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public SimpleDocUnitDTO getDocUnit() {
		return docUnit;
	}

	public void setDocUnit(final SimpleDocUnitDTO docUnit) {
		this.docUnit = docUnit;
	}

	public SimpleProjectDTO getProject() {
		return project;
	}

	public void setProject(final SimpleProjectDTO project) {
		this.project = project;
	}

	public SimpleLotDTO getLot() {
		return lot;
	}

	public void setLot(final SimpleLotDTO lot) {
		this.lot = lot;
	}

	public SimpleTrainDTO getTrain() {
		return train;
	}

	public void setTrain(final SimpleTrainDTO train) {
		this.train = train;
	}

}
