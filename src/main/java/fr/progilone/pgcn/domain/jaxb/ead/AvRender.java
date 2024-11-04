//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802
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
 * Classe Java pour av.render.
 *
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 *
 * <pre>
 * &lt;simpleType name="av.render"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="altrender"/&gt;
 *     &lt;enumeration value="bold"/&gt;
 *     &lt;enumeration value="bolddoublequote"/&gt;
 *     &lt;enumeration value="bolditalic"/&gt;
 *     &lt;enumeration value="boldsinglequote"/&gt;
 *     &lt;enumeration value="boldsmcaps"/&gt;
 *     &lt;enumeration value="boldunderline"/&gt;
 *     &lt;enumeration value="doublequote"/&gt;
 *     &lt;enumeration value="italic"/&gt;
 *     &lt;enumeration value="nonproport"/&gt;
 *     &lt;enumeration value="singlequote"/&gt;
 *     &lt;enumeration value="smcaps"/&gt;
 *     &lt;enumeration value="sub"/&gt;
 *     &lt;enumeration value="super"/&gt;
 *     &lt;enumeration value="underline"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */
@XmlType(name = "av.render")
@XmlEnum
public enum AvRender {

    @XmlEnumValue("altrender")
    ALTRENDER("altrender"),
    @XmlEnumValue("bold")
    BOLD("bold"),
    @XmlEnumValue("bolddoublequote")
    BOLDDOUBLEQUOTE("bolddoublequote"),
    @XmlEnumValue("bolditalic")
    BOLDITALIC("bolditalic"),
    @XmlEnumValue("boldsinglequote")
    BOLDSINGLEQUOTE("boldsinglequote"),
    @XmlEnumValue("boldsmcaps")
    BOLDSMCAPS("boldsmcaps"),
    @XmlEnumValue("boldunderline")
    BOLDUNDERLINE("boldunderline"),
    @XmlEnumValue("doublequote")
    DOUBLEQUOTE("doublequote"),
    @XmlEnumValue("italic")
    ITALIC("italic"),
    @XmlEnumValue("nonproport")
    NONPROPORT("nonproport"),
    @XmlEnumValue("singlequote")
    SINGLEQUOTE("singlequote"),
    @XmlEnumValue("smcaps")
    SMCAPS("smcaps"),
    @XmlEnumValue("sub")
    SUB("sub"),
    @XmlEnumValue("super")
    SUPER("super"),
    @XmlEnumValue("underline")
    UNDERLINE("underline");

    private final String value;

    AvRender(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AvRender fromValue(String v) {
        for (AvRender c : AvRender.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
