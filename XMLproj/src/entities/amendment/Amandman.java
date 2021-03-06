//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.17 at 12:37:02 AM CEST 
//


package entities.amendment;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.parlament.gov.rs/propisi}Sporedni_deo"/>
 *         &lt;element ref="{http://www.parlament.gov.rs/propisi}Glavni_deo"/>
 *       &lt;/sequence>
 *       &lt;attribute name="status" use="required" type="{http://www.parlament.gov.rs/generic_types}TStatusAmandmana" />
 *       &lt;anyAttribute processContents='lax'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sporedniDeo",
    "glavniDeo"
})
@XmlRootElement(name = "Amandman", namespace = "http://www.parlament.gov.rs/propisi")
public class Amandman {

    @XmlElement(name = "Sporedni_deo", namespace = "http://www.parlament.gov.rs/propisi", required = true)
    protected SporedniDeo sporedniDeo;
    @XmlElement(name = "Glavni_deo", namespace = "http://www.parlament.gov.rs/propisi", required = true)
    protected GlavniDeo glavniDeo;
    @XmlAttribute(name = "status", required = true)
    protected TStatusAmandmana status;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the sporedniDeo property.
     * 
     * @return
     *     possible object is
     *     {@link SporedniDeo }
     *     
     */
    public SporedniDeo getSporedniDeo() {
        return sporedniDeo;
    }

    /**
     * Sets the value of the sporedniDeo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SporedniDeo }
     *     
     */
    public void setSporedniDeo(SporedniDeo value) {
        this.sporedniDeo = value;
    }

    /**
     * Gets the value of the glavniDeo property.
     * 
     * @return
     *     possible object is
     *     {@link GlavniDeo }
     *     
     */
    public GlavniDeo getGlavniDeo() {
        return glavniDeo;
    }

    /**
     * Sets the value of the glavniDeo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlavniDeo }
     *     
     */
    public void setGlavniDeo(GlavniDeo value) {
        this.glavniDeo = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TStatusAmandmana }
     *     
     */
    public TStatusAmandmana getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStatusAmandmana }
     *     
     */
    public void setStatus(TStatusAmandmana value) {
        this.status = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
