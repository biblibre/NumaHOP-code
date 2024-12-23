//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB),
// v2.2.8-b130911.1802
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2017.08.25 à 03:15:17 PM CEST
//

package fr.progilone.pgcn.domain.jaxb.mix;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour componentPhotometricInterpretationType.
 * </p>
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * </p>
 *
 * <pre>
 * &lt;simpleType name="componentPhotometricInterpretationType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="R"/&gt;
 * &lt;enumeration value="G"/&gt;
 * &lt;enumeration value="B"/&gt;
 * &lt;enumeration value="Y"/&gt;
 * &lt;enumeration value="Cb"/&gt;
 * &lt;enumeration value="Cr"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "componentPhotometricInterpretationType")
@XmlEnum
public enum ComponentPhotometricInterpretationType {

	R("R"), G("G"), B("B"), Y("Y"), @XmlEnumValue("Cb")
	CB("Cb"), @XmlEnumValue("Cr")
	CR("Cr");

	private final String value;

	ComponentPhotometricInterpretationType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static ComponentPhotometricInterpretationType fromValue(String v) {
		for (ComponentPhotometricInterpretationType c : ComponentPhotometricInterpretationType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
