//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2017.01.03 à 11:16:35 AM CET
//

package fr.progilone.pgcn.domain.jaxb.aip;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Classe Java pour stringNotNULLtext complex type.
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 *
 * <pre>
 * &lt;complexType name="stringNotNULLtext"&gt;
 * &lt;simpleContent&gt;
 * &lt;extension base="&lt;http://www.cines.fr/pac/test/aip&gt;stringNotNULL"&gt;
 * &lt;attribute name="language"
 * type="{urn:un:unece:uncefact:codelist:draft:DAF:languageCode:2011-10-07}LanguageCodeType" /&gt;
 * &lt;/extension&gt;
 * &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stringNotNULLtext", propOrder = { "value" })
public class StringNotNULLtext {

	@XmlValue
	protected String value;

	@XmlAttribute(name = "language")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String language;

	/**
	 * Chaine de caractères composée d'au moins 1 caractère imprimable ou non
	 * exclusivement composé d'espace et/ou de tabulations et/ou de nouvelle ligne et/ou
	 * de retour chariot
	 * @return possible object is {@link String }
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Définit la valeur de la propriété value.
	 * @param value allowed object is {@link String }
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Obtient la valeur de la propriété language.
	 * @return possible object is {@link String }
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Définit la valeur de la propriété language.
	 * @param value allowed object is {@link String }
	 */
	public void setLanguage(String value) {
		this.language = value;
	}

}
