package org.numahop.numahop.domain.document.conditionreport;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.common.base.MoreObjects;
import org.numahop.numahop.domain.AbstractDomainObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = DescriptionValue.TABLE_NAME)
@JsonSubTypes({ @JsonSubTypes.Type(name = "doc_condreport_desc_value", value = DescriptionValue.class) })
public class DescriptionValue extends AbstractDomainObject {

	public static final String TABLE_NAME = "doc_condreport_desc_value";

	@Column(name = "label", nullable = false)
	private String label;

	/**
	 * Constat d'état
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "property")
	private DescriptionProperty property;

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public DescriptionProperty getProperty() {
		return property;
	}

	public void setProperty(final DescriptionProperty property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("label", label).toString();
	}

}
