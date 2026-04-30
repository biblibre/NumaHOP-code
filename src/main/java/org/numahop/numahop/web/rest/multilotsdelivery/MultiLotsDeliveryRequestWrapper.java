package org.numahop.numahop.web.rest.multilotsdelivery;

import org.numahop.numahop.domain.dto.document.PreDeliveryDocumentDTO;
import org.numahop.numahop.domain.dto.document.PreDeliveryLockedDocsDTO;
import java.util.ArrayList;
import java.util.List;

public class MultiLotsDeliveryRequestWrapper {

	private List<PreDeliveryLockedDocsDTO> lockedDocs = new ArrayList<>();

	private List<PreDeliveryDocumentDTO> metadatas = new ArrayList<>();

	public List<PreDeliveryLockedDocsDTO> getLockedDocs() {
		return lockedDocs;
	}

	public void setLockedDocs(final List<PreDeliveryLockedDocsDTO> lockedDocs) {
		this.lockedDocs = lockedDocs;
	}

	public List<PreDeliveryDocumentDTO> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(final List<PreDeliveryDocumentDTO> metadatas) {
		this.metadatas = metadatas;
	}

}
