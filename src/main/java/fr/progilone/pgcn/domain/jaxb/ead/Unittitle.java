//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB),
// v2.2.8-b130911.1802
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2017.05.16 à 10:56:55 AM CEST
//

package fr.progilone.pgcn.domain.jaxb.ead;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Classe Java pour unittitle complex type.
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 *
 * <pre>
 * &lt;complexType name="unittitle">
 * &lt;complexContent>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * &lt;choice maxOccurs="unbounded" minOccurs="0">
 * &lt;group ref="{urn:isbn:1-931666-22-9}m.phrase.basic"/>
 * &lt;group ref="{urn:isbn:1-931666-22-9}m.access"/>
 * &lt;element name="unitdate" type="{urn:isbn:1-931666-22-9}unitdate"/>
 * &lt;element name="num" type="{urn:isbn:1-931666-22-9}num"/>
 * &lt;element name="date" type="{urn:isbn:1-931666-22-9}date"/>
 * &lt;element name="bibseries" type="{urn:isbn:1-931666-22-9}bibseries"/>
 * &lt;element name="edition" type="{urn:isbn:1-931666-22-9}edition"/>
 * &lt;element name="imprint" type="{urn:isbn:1-931666-22-9}imprint"/>
 * &lt;/choice>
 * &lt;attGroup ref="{urn:isbn:1-931666-22-9}a.common"/>
 * &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 * &lt;attribute name="encodinganalog" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 * &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 * &lt;/restriction>
 * &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unittitle", propOrder = { "content" })
public class Unittitle {

	@XmlElementRefs({
			@XmlElementRef(name = "bibseries", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "ref", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "extptr", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "persname", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "function", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "extref", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "subject", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "unitdate", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "famname", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "expan", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "emph", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "linkgrp", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "genreform", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "bibref", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "geogname", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "abbr", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "name", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "date", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "archref", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "title", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "lb", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "ptr", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "occupation", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "num", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "imprint", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "corpname", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false),
			@XmlElementRef(name = "edition", namespace = "urn:isbn:1-931666-22-9", type = JAXBElement.class,
					required = false) })
	@XmlMixed
	protected List<Serializable> content;

	@XmlAttribute(name = "label")
	@XmlSchemaType(name = "anySimpleType")
	protected String label;

	@XmlAttribute(name = "encodinganalog")
	@XmlSchemaType(name = "anySimpleType")
	protected String encodinganalog;

	@XmlAttribute(name = "type")
	@XmlSchemaType(name = "anySimpleType")
	protected String type;

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
	 * Gets the value of the content property.
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present inside the
	 * JAXB object. This is why there is not a <CODE>set</CODE> method for the content
	 * property.
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getContent ().add (newItem);
	 * </pre>
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link JAXBElement
	 * }{@code <}{@link Bibseries }{@code >} {@link JAXBElement }{@code <}{@link Ref
	 * }{@code >} {@link JAXBElement }{@code <}{@link Extptr }{@code >} {@link JAXBElement
	 * }{@code <}{@link Persname }{@code >} {@link JAXBElement }{@code <}{@link Function
	 * }{@code >} {@link JAXBElement }{@code <}{@link Extref }{@code >} {@link JAXBElement
	 * }{@code <}{@link Subject }{@code >} {@link JAXBElement }{@code <}{@link Unitdate
	 * }{@code >} {@link JAXBElement }{@code <}{@link Famname }{@code >}
	 * {@link JAXBElement }{@code <}{@link Expan }{@code >} {@link String }
	 * {@link JAXBElement }{@code <}{@link Emph }{@code >} {@link JAXBElement
	 * }{@code <}{@link Linkgrp }{@code >} {@link JAXBElement }{@code <}{@link Genreform
	 * }{@code >} {@link JAXBElement }{@code <}{@link Bibref }{@code >} {@link JAXBElement
	 * }{@code <}{@link Geogname }{@code >} {@link JAXBElement }{@code <}{@link Abbr
	 * }{@code >} {@link JAXBElement }{@code <}{@link Name }{@code >} {@link JAXBElement
	 * }{@code <}{@link Date }{@code >} {@link JAXBElement }{@code <}{@link Archref
	 * }{@code >} {@link JAXBElement }{@code <}{@link Title }{@code >} {@link JAXBElement
	 * }{@code <}{@link Lb }{@code >} {@link JAXBElement }{@code <}{@link Ptr }{@code >}
	 * {@link JAXBElement }{@code <}{@link Occupation }{@code >} {@link JAXBElement
	 * }{@code <}{@link Num }{@code >} {@link JAXBElement }{@code <}{@link Imprint
	 * }{@code >} {@link JAXBElement }{@code <}{@link Corpname }{@code >}
	 * {@link JAXBElement }{@code <}{@link Edition }{@code >}
	 */
	public List<Serializable> getContent() {
		if (content == null) {
			content = new ArrayList<>();
		}
		return this.content;
	}

	/**
	 * Obtient la valeur de la propriété label.
	 * @return possible object is {@link String }
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Définit la valeur de la propriété label.
	 * @param value allowed object is {@link String }
	 */
	public void setLabel(String value) {
		this.label = value;
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
	 * Obtient la valeur de la propriété type.
	 * @return possible object is {@link String }
	 */
	public String getType() {
		return type;
	}

	/**
	 * Définit la valeur de la propriété type.
	 * @param value allowed object is {@link String }
	 */
	public void setType(String value) {
		this.type = value;
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
