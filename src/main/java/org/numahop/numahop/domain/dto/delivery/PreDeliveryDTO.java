package org.numahop.numahop.domain.dto.delivery;

import org.numahop.numahop.domain.dto.AbstractDTO;
import org.numahop.numahop.domain.dto.document.DigitalDocumentDTO;
import org.numahop.numahop.domain.dto.document.PhysicalDocumentDTO;
import org.numahop.numahop.domain.dto.document.PreDeliveryDocumentDTO;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe containing the informations to display for the delivery validation view.
 */
public class PreDeliveryDTO extends AbstractDTO {

	// Set of document to be delivered.
	private Set<PreDeliveryDocumentDTO> documents = new HashSet<>();

	// Set of document that cannot be delivered.
	private Set<PhysicalDocumentDTO> undeliveredDocuments = new HashSet<>();

	private final Set<DigitalDocumentDTO> lockedDigitalDocuments = new HashSet<>();

	public PreDeliveryDTO(final Set<PreDeliveryDocumentDTO> documents) {
		this.documents = documents;
	}

	public PreDeliveryDTO() {
	}

	public Set<PreDeliveryDocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(final Set<PreDeliveryDocumentDTO> documents) {
		this.documents.clear();
		if (documents != null) {
			documents.forEach(this::addDocument);
		}
	}

	public void addDocument(final PreDeliveryDocumentDTO doc) {
		if (doc != null) {
			this.documents.add(doc);
		}
	}

	public Set<DigitalDocumentDTO> getLockedDigitalDocuments() {
		return lockedDigitalDocuments;
	}

	public void setLockedDigitalDocuments(final Set<DigitalDocumentDTO> digitalDocumentsLocked) {
		this.lockedDigitalDocuments.clear();
		if (digitalDocumentsLocked != null) {
			digitalDocumentsLocked.forEach(this::addLockedDigitalDocument);
		}
	}

	public void addLockedDigitalDocument(final DigitalDocumentDTO digitalDocument) {
		if (digitalDocument != null) {
			this.lockedDigitalDocuments.add(digitalDocument);
		}
	}

	public Set<PhysicalDocumentDTO> getUndeliveredDocuments() {
		return undeliveredDocuments;
	}

	public void setUndeliveredDocuments(final Set<PhysicalDocumentDTO> undeliveredDocuments) {
		this.undeliveredDocuments = undeliveredDocuments;
	}

	public void addUndeliveredDigitalDocument(final PhysicalDocumentDTO physicalDocument) {
		if (physicalDocument != null) {
			this.undeliveredDocuments.add(physicalDocument);
		}
	}

}
