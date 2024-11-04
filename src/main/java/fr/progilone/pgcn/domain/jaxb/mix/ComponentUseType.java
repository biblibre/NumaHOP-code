//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802
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
 * Classe Java pour componentUseType.
 * </p>
 *
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * </p>
 *
 * <pre>
 * &lt;simpleType name="componentUseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="image data"/&gt;
 *     &lt;enumeration value="associated alpha data"/&gt;
 *     &lt;enumeration value="unassociated alpha data"/&gt;
 *     &lt;enumeration value="range data"/&gt;
 *     &lt;enumeration value="unspecified data"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */
@XmlType(name = "componentUseType")
@XmlEnum
public enum ComponentUseType {

    @XmlEnumValue("image data")
    IMAGE_DATA("image data"),
    @XmlEnumValue("associated alpha data")
    ASSOCIATED_ALPHA_DATA("associated alpha data"),
    @XmlEnumValue("unassociated alpha data")
    UNASSOCIATED_ALPHA_DATA("unassociated alpha data"),
    @XmlEnumValue("range data")
    RANGE_DATA("range data"),
    @XmlEnumValue("unspecified data")
    UNSPECIFIED_DATA("unspecified data");

    private final String value;

    ComponentUseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ComponentUseType fromValue(String v) {
        for (ComponentUseType c : ComponentUseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
