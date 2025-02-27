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
 * Classe Java pour backLightType.
 * </p>
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * </p>
 *
 * <pre>
 * &lt;simpleType name="backLightType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="Front light"/&gt;
 * &lt;enumeration value="Backlight 1"/&gt;
 * &lt;enumeration value="Backlight 2"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "backLightType")
@XmlEnum
public enum BackLightType {

	@XmlEnumValue("Front light")
	FRONT_LIGHT("Front light"), @XmlEnumValue("Backlight 1")
	BACKLIGHT_1("Backlight 1"), @XmlEnumValue("Backlight 2")
	BACKLIGHT_2("Backlight 2");

	private final String value;

	BackLightType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static BackLightType fromValue(String v) {
		for (BackLightType c : BackLightType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
