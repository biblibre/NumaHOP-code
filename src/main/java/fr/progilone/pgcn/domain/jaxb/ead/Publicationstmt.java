//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB),
// v2.2.8-b130911.1802
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2017.05.16 à 10:56:55 AM CEST
//

package fr.progilone.pgcn.domain.jaxb.ead;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Classe Java pour publicationstmt complex type.
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 *
 * <pre>
 * &lt;complexType name="publicationstmt">
 * &lt;complexContent>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * &lt;choice maxOccurs="unbounded">
 * &lt;element name="publisher" type="{urn:isbn:1-931666-22-9}publisher"/>
 * &lt;element name="date" type="{urn:isbn:1-931666-22-9}date"/>
 * &lt;element name="address" type="{urn:isbn:1-931666-22-9}address"/>
 * &lt;element name="num" type="{urn:isbn:1-931666-22-9}num"/>
 * &lt;element name="p" type="{urn:isbn:1-931666-22-9}p"/>
 * &lt;/choice>
 * &lt;attGroup ref="{urn:isbn:1-931666-22-9}a.common"/>
 * &lt;attribute name="encodinganalog" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 * &lt;/restriction>
 * &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "publicationstmt", propOrder = { "publisherOrDateOrAddress" })
public class Publicationstmt {

	@XmlElements({ @XmlElement(name = "publisher", type = Publisher.class),
			@XmlElement(name = "date", type = Date.class), @XmlElement(name = "address", type = Address.class),
			@XmlElement(name = "num", type = Num.class), @XmlElement(name = "p", type = P.class) })
	protected List<Object> publisherOrDateOrAddress;

	@XmlAttribute(name = "encodinganalog")
	@XmlSchemaType(name = "anySimpleType")
	protected String encodinganalog;

	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	@XmlAttribute(name = "altrender")
	@XmlSchemaType(name = "anySimpleType")
	protected String altrender;

	@XmlAttribute(name = "audience")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String audience;

	/**
	 * Gets the value of the publisherOrDateOrAddress property.
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present inside the
	 * JAXB object. This is why there is not a <CODE>set</CODE> method for the
	 * publisherOrDateOrAddress property.
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getPublisherOrDateOrAddress ().add (newItem);
	 * </pre>
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Publisher }
	 * {@link Date } {@link Address } {@link Num } {@link P }
	 */
	public List<Object> getPublisherOrDateOrAddress() {
		if (publisherOrDateOrAddress == null) {
			publisherOrDateOrAddress = new ArrayList<>();
		}
		return this.publisherOrDateOrAddress;
	}

	/**
	 * Obtient la valeur de la propriété encodinganalog.
	 * @return possible object is {@link String }
	 */
	public String getEncodinganalog() {
		return encodinganalog;
	}

	/**
	 * Définit la valeur de la propriété encodinganalog.
	 * @param value allowed object is {@link String }
	 */
	public void setEncodinganalog(String value) {
		this.encodinganalog = value;
	}

	/**
	 * Obtient la valeur de la propriété id.
	 * @return possible object is {@link String }
	 */
	public String getId() {
		return id;
	}

	/**
	 * Définit la valeur de la propriété id.
	 * @param value allowed object is {@link String }
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * Obtient la valeur de la propriété altrender.
	 * @return possible object is {@link String }
	 */
	public String getAltrender() {
		return altrender;
	}

	/**
	 * Définit la valeur de la propriété altrender.
	 * @param value allowed object is {@link String }
	 */
	public void setAltrender(String value) {
		this.altrender = value;
	}

	/**
	 * Obtient la valeur de la propriété audience.
	 * @return possible object is {@link String }
	 */
	public String getAudience() {
		return audience;
	}

	/**
	 * Définit la valeur de la propriété audience.
	 * @param value allowed object is {@link String }
	 */
	public void setAudience(String value) {
		this.audience = value;
	}

}
