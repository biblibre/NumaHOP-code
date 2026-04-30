package org.numahop.numahop.domain.dto.delivery;

import org.numahop.numahop.domain.delivery.Delivery;
import org.numahop.numahop.domain.dto.lot.SimpleLotForDeliveryDTO;

/**
 * DTO représentant les attributs d'une livraison à afficher parmi les résultats de
 * recherche
 */
public class SimpleDeliveryLotDTO {

	private String identifier;

	private String label;

	private Delivery.DeliveryStatus status;

	private SimpleLotForDeliveryDTO lot;

	public final String getLabel() {
		return label;
	}

	public final String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public Delivery.DeliveryStatus getStatus() {
		return status;
	}

	public void setStatus(final Delivery.DeliveryStatus status) {
		this.status = status;
	}

	public SimpleLotForDeliveryDTO getLot() {
		return lot;
	}

	public void setLot(final SimpleLotForDeliveryDTO lot) {
		this.lot = lot;
	}

}
