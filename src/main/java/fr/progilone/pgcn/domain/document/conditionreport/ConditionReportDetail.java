package fr.progilone.pgcn.domain.document.conditionreport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import fr.progilone.pgcn.domain.AbstractDomainObject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Constat d'état: données d'un constat particulier
 */
@Entity
@Table(name = ConditionReportDetail.TABLE_NAME)
@JsonSubTypes({ @JsonSubTypes.Type(name = "doc_condreport_detail", value = ConditionReportDetail.class) })
public class ConditionReportDetail extends AbstractDomainObject {

	public static final String TABLE_NAME = "doc_condreport_detail";

	public enum Type {

		/**
		 * Constat avant le départ de la bibliothèque
		 */
		LIBRARY_LEAVING,
		/**
		 * Confirmation constat initial par le prestataire
		 */
		PROVIDER_RECEPTION,
		/**
		 * Constat avant numérisation prestataire
		 */
		DIGITALIZATION,
		/**
		 * Constat apres numérisation prestataire
		 */
		LIBRARY_BACK,
		/**
		 * Constat de retour à la bibliothèque
		 */
		LIBRARY_RETURN,
		/**
		 * Constat pour une reprise de numérisation
		 */
		LIBRARY_NEW_DIGIT

	}

	/**
	 * Constat d'état
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "report")
	private ConditionReport report;

	/**
	 * Type du constat d'état
	 */
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;

	/**
	 * Nom du rédacteur du constat - bibliothèque
	 */
	@Column(name = "lib_writer_name")
	private String libWriterName;

	/**
	 * Qualité du rédacteur du constat - bibliothèque
	 */
	@Column(name = "lib_writer_function")
	private String libWriterFunction;

	/**
	 * Nom du rédacteur du constat - prestataire
	 */
	@Column(name = "prov_writer_name")
	private String provWriterName;

	/**
	 * Qualité du rédacteur du constat - prestataire
	 */
	@Column(name = "prov_writer_function")
	private String provWriterFunction;

	/**
	 * Date du constat
	 */
	@Column(name = "report_date")
	private LocalDate date;

	/**
	 * Commentaire de l'entête
	 */
	@Column(name = "comment", columnDefinition = "text")
	private String comment;

	/**
	 * Position de l'étape
	 */
	@Column(name = "position")
	private int position;

	/**
	 * Propriétés du constat d'état
	 */
	@OneToMany(mappedBy = "detail", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private final Set<Description> descriptions = new LinkedHashSet<>();

	/**
	 * Estimation du nombre de vues de la reliure
	 */
	@Column(name = "nb_view_binding")
	private int nbViewBinding = 0;

	/**
	 * Estimation du nombre de vues du corps d'ouvrage
	 */
	@Column(name = "nb_view_body")
	private int nbViewBody = 0;

	/**
	 * Estimation du nombre de vues suplémentaires
	 */
	@Column(name = "nb_view_additionnal")
	private int nbViewAdditionnal = 0;

	/**
	 * Dimension du document
	 */
	@Column(name = "dim1")
	private Integer dim1;

	/**
	 * Dimension du document
	 */
	@Column(name = "dim2")
	private Integer dim2;

	/**
	 * Dimension du document
	 */
	@Column(name = "dim3")
	private Integer dim3;

	/**
	 * État de la reliure: autres infos
	 */
	@Column(name = "binding_desc", columnDefinition = "text")
	private String bindingDesc;

	/**
	 * État du corps d'ouvrage: autres infos
	 */
	@Column(name = "body_desc", columnDefinition = "text")
	private String bodyDesc;

	/**
	 * Description: autres infos
	 */
	@Column(name = "additionnal_desc", columnDefinition = "text")
	private String additionnalDesc;

	@Column(name = "insurance")
	private Double insurance;

	public ConditionReport getReport() {
		return report;
	}

	public void setReport(final ConditionReport report) {
		this.report = report;
	}

	public Type getType() {
		return type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	ConditionReportDetail withSortedType(final int sortedType) {
		return this;
	}

	public String getLibWriterName() {
		return libWriterName;
	}

	public void setLibWriterName(final String libWriterName) {
		this.libWriterName = libWriterName;
	}

	public String getLibWriterFunction() {
		return libWriterFunction;
	}

	public void setLibWriterFunction(final String libWriterFunction) {
		this.libWriterFunction = libWriterFunction;
	}

	public String getProvWriterName() {
		return provWriterName;
	}

	public void setProvWriterName(final String provWriterName) {
		this.provWriterName = provWriterName;
	}

	public String getProvWriterFunction() {
		return provWriterFunction;
	}

	public void setProvWriterFunction(final String provWriterFunction) {
		this.provWriterFunction = provWriterFunction;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public void setDescriptions(final Set<Description> descriptions) {
		this.descriptions.clear();
		if (descriptions != null) {
			descriptions.forEach(this::addDescription);
		}
	}

	public void addDescription(final Description description) {
		if (description != null) {
			this.descriptions.add(description);
			description.setDetail(this);
		}
	}

	public Set<Description> getDescriptions() {
		return descriptions;
	}

	public int getNbViewBinding() {
		return nbViewBinding;
	}

	public void setNbViewBinding(final int nbViewBinding) {
		this.nbViewBinding = nbViewBinding;
	}

	public int getNbViewBody() {
		return nbViewBody;
	}

	public void setNbViewBody(final int nbViewBody) {
		this.nbViewBody = nbViewBody;
	}

	public int getNbViewAdditionnal() {
		return nbViewAdditionnal;
	}

	public void setNbViewAdditionnal(final int nbViewAdditionnal) {
		this.nbViewAdditionnal = nbViewAdditionnal;
	}

	public int getNbViewTotal() {
		return nbViewBinding + nbViewBody + nbViewAdditionnal;
	}

	public Integer getDim1() {
		return dim1;
	}

	public void setDim1(final Integer dim1) {
		this.dim1 = dim1;
	}

	public Integer getDim2() {
		return dim2;
	}

	public void setDim2(final Integer dim2) {
		this.dim2 = dim2;
	}

	public Integer getDim3() {
		return dim3;
	}

	public void setDim3(final Integer dim3) {
		this.dim3 = dim3;
	}

	public String getBindingDesc() {
		return bindingDesc;
	}

	public void setBindingDesc(final String bindingDesc) {
		this.bindingDesc = bindingDesc;
	}

	public String getBodyDesc() {
		return bodyDesc;
	}

	public void setBodyDesc(final String bodyDesc) {
		this.bodyDesc = bodyDesc;
	}

	public String getAdditionnalDesc() {
		return additionnalDesc;
	}

	public void setAdditionnalDesc(final String additionnalDesc) {
		this.additionnalDesc = additionnalDesc;
	}

	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(final Double insurance) {
		this.insurance = insurance;
	}

	/**
	 * On force le chargement du createur en json..
	 */
	@Override
	@JsonIgnore(false)
	@JsonProperty
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

}
