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
 * Classe Java pour cameraSensorType.
 * </p>
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * </p>
 *
 * <pre>
 * &lt;simpleType name="cameraSensorType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="undefined"/&gt;
 * &lt;enumeration value="MonochromeArea"/&gt;
 * &lt;enumeration value="OneChipColorArea"/&gt;
 * &lt;enumeration value="TwoChipColorArea"/&gt;
 * &lt;enumeration value="ThreeChipColorArea"/&gt;
 * &lt;enumeration value="MonochromeLinear"/&gt;
 * &lt;enumeration value="ColorTriLinear"/&gt;
 * &lt;enumeration value="ColorSequentialLinear"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "cameraSensorType")
@XmlEnum
public enum CameraSensorType {

	@XmlEnumValue("undefined")
	UNDEFINED("undefined"), @XmlEnumValue("MonochromeArea")
	MONOCHROME_AREA("MonochromeArea"), @XmlEnumValue("OneChipColorArea")
	ONE_CHIP_COLOR_AREA("OneChipColorArea"), @XmlEnumValue("TwoChipColorArea")
	TWO_CHIP_COLOR_AREA("TwoChipColorArea"), @XmlEnumValue("ThreeChipColorArea")
	THREE_CHIP_COLOR_AREA("ThreeChipColorArea"), @XmlEnumValue("MonochromeLinear")
	MONOCHROME_LINEAR("MonochromeLinear"), @XmlEnumValue("ColorTriLinear")
	COLOR_TRI_LINEAR("ColorTriLinear"), @XmlEnumValue("ColorSequentialLinear")
	COLOR_SEQUENTIAL_LINEAR("ColorSequentialLinear");

	private final String value;

	CameraSensorType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static CameraSensorType fromValue(String v) {
		for (CameraSensorType c : CameraSensorType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
