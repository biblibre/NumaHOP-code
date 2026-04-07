//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB),
// v2.2.8-b130911.1802
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2017.05.16 à 10:56:55 AM CEST
//

package fr.progilone.pgcn.domain.jaxb.ead;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour av.level.
 * </p>
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * </p>
 *
 * <pre>
 * &lt;simpleType name="av.level"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 * &lt;enumeration value="class"/&gt;
 * &lt;enumeration value="collection"/&gt;
 * &lt;enumeration value="file"/&gt;
 * &lt;enumeration value="fonds"/&gt;
 * &lt;enumeration value="item"/&gt;
 * &lt;enumeration value="otherlevel"/&gt;
 * &lt;enumeration value="recordgrp"/&gt;
 * &lt;enumeration value="series"/&gt;
 * &lt;enumeration value="subfonds"/&gt;
 * &lt;enumeration value="subgrp"/&gt;
 * &lt;enumeration value="subseries"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "av.level")
@XmlEnum
public enum AvLevel {

	@XmlEnumValue("class")
	CLASS("class"), @XmlEnumValue("collection")
	COLLECTION("collection"), @XmlEnumValue("file")
	FILE("file"), @XmlEnumValue("fonds")
	FONDS("fonds"), @XmlEnumValue("item")
	ITEM("item"), @XmlEnumValue("otherlevel")
	OTHERLEVEL("otherlevel"), @XmlEnumValue("recordgrp")
	RECORDGRP("recordgrp"), @XmlEnumValue("series")
	SERIES("series"), @XmlEnumValue("subfonds")
	SUBFONDS("subfonds"), @XmlEnumValue("subgrp")
	SUBGRP("subgrp"), @XmlEnumValue("subseries")
	SUBSERIES("subseries");

	private final String value;

	AvLevel(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static AvLevel fromValue(String v) {
		for (AvLevel c : AvLevel.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
