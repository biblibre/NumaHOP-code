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
 * Classe Java pour scannerSensorType.
 * </p>
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * </p>
 *
 * <pre>
 * &lt;simpleType name="scannerSensorType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="undefined"/&gt;
 * &lt;enumeration value="MonochromeLinear"/&gt;
 * &lt;enumeration value="ColorTriLinear"/&gt;
 * &lt;enumeration value="ColorSequentialLinear"/&gt;
 * &lt;enumeration value="MonochromeArea"/&gt;
 * &lt;enumeration value="OneChipColourArea"/&gt;
 * &lt;enumeration value="TwoChipColorArea"/&gt;
 * &lt;enumeration value="ThreeChipColorArea"/&gt;
 * &lt;enumeration value="ColorSequentialArea"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "scannerSensorType")
@XmlEnum
public enum ScannerSensorType {

	@XmlEnumValue("undefined")
	UNDEFINED("undefined"), @XmlEnumValue("MonochromeLinear")
	MONOCHROME_LINEAR("MonochromeLinear"), @XmlEnumValue("ColorTriLinear")
	COLOR_TRI_LINEAR("ColorTriLinear"), @XmlEnumValue("ColorSequentialLinear")
	COLOR_SEQUENTIAL_LINEAR("ColorSequentialLinear"), @XmlEnumValue("MonochromeArea")
	MONOCHROME_AREA("MonochromeArea"), @XmlEnumValue("OneChipColourArea")
	ONE_CHIP_COLOUR_AREA("OneChipColourArea"), @XmlEnumValue("TwoChipColorArea")
	TWO_CHIP_COLOR_AREA("TwoChipColorArea"), @XmlEnumValue("ThreeChipColorArea")
	THREE_CHIP_COLOR_AREA("ThreeChipColorArea"), @XmlEnumValue("ColorSequentialArea")
	COLOR_SEQUENTIAL_AREA("ColorSequentialArea");

	private final String value;

	ScannerSensorType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static ScannerSensorType fromValue(String v) {
		for (ScannerSensorType c : ScannerSensorType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
