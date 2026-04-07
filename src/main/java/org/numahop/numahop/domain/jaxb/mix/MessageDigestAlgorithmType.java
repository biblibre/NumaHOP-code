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
 * Classe Java pour messageDigestAlgorithmType.
 * </p>
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * </p>
 *
 * <pre>
 * &lt;simpleType name="messageDigestAlgorithmType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="Adler-32"/&gt;
 * &lt;enumeration value="CRC32"/&gt;
 * &lt;enumeration value="HAVAL"/&gt;
 * &lt;enumeration value="MD5"/&gt;
 * &lt;enumeration value="MNP"/&gt;
 * &lt;enumeration value="SHA-1"/&gt;
 * &lt;enumeration value="SHA-256"/&gt;
 * &lt;enumeration value="SHA-384"/&gt;
 * &lt;enumeration value="SHA-512"/&gt;
 * &lt;enumeration value="TIGER"/&gt;
 * &lt;enumeration value="WHIRLPOOL"/&gt;
 * &lt;enumeration value="unknown"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "messageDigestAlgorithmType")
@XmlEnum
public enum MessageDigestAlgorithmType {

	@XmlEnumValue("Adler-32")
	ADLER_32("Adler-32"), @XmlEnumValue("CRC32")
	CRC_32("CRC32"), HAVAL("HAVAL"), @XmlEnumValue("MD5")
	MD_5("MD5"), MNP("MNP"), @XmlEnumValue("SHA-1")
	SHA_1("SHA-1"), @XmlEnumValue("SHA-256")
	SHA_256("SHA-256"), @XmlEnumValue("SHA-384")
	SHA_384("SHA-384"), @XmlEnumValue("SHA-512")
	SHA_512("SHA-512"), TIGER("TIGER"), WHIRLPOOL("WHIRLPOOL"), @XmlEnumValue("unknown")
	UNKNOWN("unknown");

	private final String value;

	MessageDigestAlgorithmType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static MessageDigestAlgorithmType fromValue(String v) {
		for (MessageDigestAlgorithmType c : MessageDigestAlgorithmType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
