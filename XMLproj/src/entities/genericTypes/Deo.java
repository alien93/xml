//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.17 at 12:36:40 AM CEST 
//


package entities.genericTypes;

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
 *         &lt;element name="Sadrzaj" type="{http://www.parlament.gov.rs/generic_types}TSadrzajDela"/>
 *         &lt;element ref="{http://www.parlament.gov.rs/generic_types}Status" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="oznaka" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "sadrzaj",
    "status"
})
@XmlRootElement(name = "Deo")
public class Deo {

    @XmlElement(name = "Sadrzaj", required = true)
    protected TSadrzajDela sadrzaj;
    @XmlElement(name = "Status")
    protected TAmandmanStatus status;
    @XmlAttribute(name = "oznaka")
    protected String oznaka;
    @XmlAttribute(name = "naziv")
    protected String naziv;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the sadrzaj property.
     * 
     * @return
     *     possible object is
     *     {@link TSadrzajDela }
     *     
     */
    public TSadrzajDela getSadrzaj() {
        return sadrzaj;
    }

    /**
     * Sets the value of the sadrzaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSadrzajDela }
     *     
     */
    public void setSadrzaj(TSadrzajDela value) {
        this.sadrzaj = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TAmandmanStatus }
     *     
     */
    public TAmandmanStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAmandmanStatus }
     *     
     */
    public void setStatus(TAmandmanStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the oznaka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOznaka() {
        return oznaka;
    }

    /**
     * Sets the value of the oznaka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOznaka(String value) {
        this.oznaka = value;
    }

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
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
