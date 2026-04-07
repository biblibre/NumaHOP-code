package org.numahop.numahop.domain.dto.exportftpconfiguration;

import org.numahop.numahop.domain.dto.AbstractVersionedDTO;

public class ExportFTPConfigurationDeliveryFolderDTO extends AbstractVersionedDTO {

	private String identifier;

	private String name;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
