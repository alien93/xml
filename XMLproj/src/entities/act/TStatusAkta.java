//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.10 at 01:35:19 PM CEST 
//


package entities.act;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TStatusAkta.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TStatusAkta">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="donet"/>
 *     &lt;enumeration value="u_proceduri"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TStatusAkta")
@XmlEnum
public enum TStatusAkta {

    @XmlEnumValue("donet")
    DONET("donet"),
    @XmlEnumValue("u_proceduri")
    U_PROCEDURI("u_proceduri");
    private final String value;

    TStatusAkta(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TStatusAkta fromValue(String v) {
        for (TStatusAkta c: TStatusAkta.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
