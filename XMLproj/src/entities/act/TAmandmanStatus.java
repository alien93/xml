//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.10 at 01:35:19 PM CEST 
//


package entities.act;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TAmandmanStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TAmandmanStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://www.parlament.gov.rs/generic_types}ref"/>
 *         &lt;element ref="{http://www.parlament.gov.rs/generic_types}status_izmene"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAmandmanStatus", propOrder = {

})
public class TAmandmanStatus {

    @XmlElement(required = true)
    protected Ref ref;
    @XmlElement(name = "status_izmene", required = true)
    @XmlSchemaType(name = "string")
    protected TStatusAkta statusIzmene;

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link Ref }
     *     
     */
    public Ref getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ref }
     *     
     */
    public void setRef(Ref value) {
        this.ref = value;
    }

    /**
     * Gets the value of the statusIzmene property.
     * 
     * @return
     *     possible object is
     *     {@link TStatusAkta }
     *     
     */
    public TStatusAkta getStatusIzmene() {
        return statusIzmene;
    }

    /**
     * Sets the value of the statusIzmene property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStatusAkta }
     *     
     */
    public void setStatusIzmene(TStatusAkta value) {
        this.statusIzmene = value;
    }

}
