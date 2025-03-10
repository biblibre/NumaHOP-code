package fr.progilone.pgcn.domain.multilotsdelivery;

import fr.progilone.pgcn.domain.AbstractDomainObject;
import fr.progilone.pgcn.domain.delivery.Delivery;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = MultiLotsDelivery.TABLE_NAME)
public class MultiLotsDelivery extends AbstractDomainObject {

	/**
	 * Nom des tables dans la base de données.
	 */
	public static final String TABLE_NAME = "del_multi_lots_delivery";

	@Column(name = "label", nullable = false)
	private String label;

	@Column(name = "description")
	private String description;

	@Column(name = "delivery_payment")
	private DeliveryPayment payment;

	@Column(name = "delivery_status")
	private Delivery.DeliveryStatus status;

	@Column(name = "delivery_method")
	private DeliveryMethod method;

	@Column(name = "reception_date", nullable = false)
	private LocalDate receptionDate;

	@Column(name = "folder_path", nullable = false)
	private String folderPath;

	@Column(name = "digitizing_notes")
	private String digitizingNotes;

	@Column(name = "control_notes")
	private String controlNotes;

	@Column(name = "selected_by_train")
	private boolean selectedByTrain;

	@Column(name = "train_id")
	private String trainId;

	/**
	 * Liste des livraisons constituant la livraison groupée.
	 */
	@OneToMany(mappedBy = "multiLotsDelivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Delivery> deliveries = new ArrayList<>();

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public DeliveryPayment getPayment() {
		return payment;
	}

	public void setPayment(final DeliveryPayment payment) {
		this.payment = payment;
	}

	public Delivery.DeliveryStatus getStatus() {
		return status;
	}

	public void setStatus(final Delivery.DeliveryStatus status) {
		this.status = status;
	}

	public DeliveryMethod getMethod() {
		return method;
	}

	public void setMethod(final DeliveryMethod method) {
		this.method = method;
	}

	public LocalDate getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(final LocalDate receptionDate) {
		this.receptionDate = receptionDate;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(final String folderPath) {
		this.folderPath = folderPath;
	}

	public String getDigitizingNotes() {
		return digitizingNotes;
	}

	public void setDigitizingNotes(final String digitizingNotes) {
		this.digitizingNotes = digitizingNotes;
	}

	public String getControlNotes() {
		return controlNotes;
	}

	public void setControlNotes(final String controlNotes) {
		this.controlNotes = controlNotes;
	}

	public boolean isSelectedByTrain() {
		return selectedByTrain;
	}

	public void setSelectedByTrain(final boolean selectedByTrain) {
		this.selectedByTrain = selectedByTrain;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(final String trainId) {
		this.trainId = trainId;
	}

	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(final List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public enum DeliveryPayment {

		PAID, UNPAID

	}

	public enum DeliveryMethod {

		FTP, DISK, OTHER

	}

}
