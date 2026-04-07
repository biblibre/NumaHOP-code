package org.numahop.numahop.domain.dto.administration.digitallibrary;

import org.numahop.numahop.domain.library.Library;

public class DigitalLibraryConfigurationDTO {

	private String identifier;

	private String label;

	private Library library;

	private boolean active;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(final Library library) {
		this.library = library;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

}
