package org.numahop.numahop.domain.dto.document;

import org.numahop.numahop.domain.check.AutomaticCheckResult;
import org.numahop.numahop.domain.document.DigitalDocument;
import org.numahop.numahop.domain.document.DocPage;
import org.numahop.numahop.domain.dto.delivery.SimpleDeliveryDTO;
import org.numahop.numahop.domain.storage.StoredFile;
import java.time.LocalDate;
import java.util.Set;

public class DeliveredDigitalDocumentDTO {

	private String identifier;

	private String digitalId;

	private LocalDate deliveryDate;

	private Integer nbPages;

	private Long totalLength;

	private DigitalDocument.DigitalDocumentStatus status;

	private Set<AutomaticCheckResult> automaticCheckResults;

	private Set<DocPage> pages;

	private Set<StoredFile> files;

	private SimpleDeliveryDTO delivery;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDigitalId() {
		return digitalId;
	}

	public void setDigitalId(String digitalId) {
		this.digitalId = digitalId;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(final LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getNbPages() {
		return nbPages;
	}

	public void setNbPages(final Integer nbPages) {
		this.nbPages = nbPages;
	}

	public Long getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(final Long totalLength) {
		this.totalLength = totalLength;
	}

	public DigitalDocument.DigitalDocumentStatus getStatus() {
		return status;
	}

	public void setStatus(DigitalDocument.DigitalDocumentStatus status) {
		this.status = status;
	}

	public SimpleDeliveryDTO getDelivery() {
		return delivery;
	}

	public void setDelivery(SimpleDeliveryDTO delivery) {
		this.delivery = delivery;
	}

	public Set<AutomaticCheckResult> getAutomaticCheckResults() {
		return automaticCheckResults;
	}

	public void setAutomaticCheckResults(Set<AutomaticCheckResult> automaticCheckResults) {
		this.automaticCheckResults = automaticCheckResults;
	}

	public Set<DocPage> getPages() {
		return pages;
	}

	public void setPages(Set<DocPage> pages) {
		this.pages = pages;
	}

	public Set<StoredFile> getFiles() {
		return files;
	}

	public void setFiles(Set<StoredFile> files) {
		this.files = files;
	}

}
