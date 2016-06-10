//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.10 at 06:19:51 PM CEST 
//


package entities.amendment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for TSadrzajDela complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TSadrzajDela">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.parlament.gov.rs/generic_types}ref" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Glava" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Sadrzaj" type="{http://www.parlament.gov.rs/generic_types}TSadrzajGlave"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="oznaka" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;anyAttribute processContents='lax'/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Odeljak" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Sadrzaj" type="{http://www.parlament.gov.rs/generic_types}TSadrzajOdeljka"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="oznaka" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;anyAttribute processContents='lax'/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Clan" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Sadrzaj" type="{http://www.parlament.gov.rs/generic_types}TSadrzajClana"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="oznaka" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;anyAttribute processContents='lax'/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TSadrzajDela", propOrder = {
    "content"
})
public class TSadrzajDela {

    @XmlElementRefs({
        @XmlElementRef(name = "Odeljak", namespace = "http://www.parlament.gov.rs/generic_types", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Clan", namespace = "http://www.parlament.gov.rs/generic_types", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Glava", namespace = "http://www.parlament.gov.rs/generic_types", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ref", namespace = "http://www.parlament.gov.rs/generic_types", type = Ref.class, required = false)
    })
    @XmlMixed
    protected List<Object> content;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ref }
     * {@link String }
     * {@link JAXBElement }{@code <}{@link TSadrzajDela.Odeljak }{@code >}
     * {@link JAXBElement }{@code <}{@link TSadrzajDela.Clan }{@code >}
     * {@link JAXBElement }{@code <}{@link TSadrzajDela.Glava }{@code >}
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }


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
     *         &lt;element name="Sadrzaj" type="{http://www.parlament.gov.rs/generic_types}TSadrzajClana"/>
     *       &lt;/sequence>
     *       &lt;attribute name="oznaka" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "sadrzaj"
    })
    public static class Clan {

        @XmlElement(name = "Sadrzaj", required = true)
        protected TSadrzajClana sadrzaj;
        @XmlAttribute(name = "oznaka")
        protected String oznaka;
        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        /**
         * Gets the value of the sadrzaj property.
         * 
         * @return
         *     possible object is
         *     {@link TSadrzajClana }
         *     
         */
        public TSadrzajClana getSadrzaj() {
            return sadrzaj;
        }

        /**
         * Sets the value of the sadrzaj property.
         * 
         * @param value
         *     allowed object is
         *     {@link TSadrzajClana }
         *     
         */
        public void setSadrzaj(TSadrzajClana value) {
            this.sadrzaj = value;
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
     *         &lt;element name="Sadrzaj" type="{http://www.parlament.gov.rs/generic_types}TSadrzajGlave"/>
     *       &lt;/sequence>
     *       &lt;attribute name="oznaka" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
        "sadrzaj"
    })
    public static class Glava {

        @XmlElement(name = "Sadrzaj", required = true)
        protected TSadrzajGlave sadrzaj;
        @XmlAttribute(name = "oznaka")
        @XmlSchemaType(name = "anySimpleType")
        protected String oznaka;
        @XmlAttribute(name = "naziv")
        @XmlSchemaType(name = "anySimpleType")
        protected String naziv;
        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        /**
         * Gets the value of the sadrzaj property.
         * 
         * @return
         *     possible object is
         *     {@link TSadrzajGlave }
         *     
         */
        public TSadrzajGlave getSadrzaj() {
            return sadrzaj;
        }

        /**
         * Sets the value of the sadrzaj property.
         * 
         * @param value
         *     allowed object is
         *     {@link TSadrzajGlave }
         *     
         */
        public void setSadrzaj(TSadrzajGlave value) {
            this.sadrzaj = value;
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
     *         &lt;element name="Sadrzaj" type="{http://www.parlament.gov.rs/generic_types}TSadrzajOdeljka"/>
     *       &lt;/sequence>
     *       &lt;attribute name="oznaka" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
        "sadrzaj"
    })
    public static class Odeljak {

        @XmlElement(name = "Sadrzaj", required = true)
        protected TSadrzajOdeljka sadrzaj;
        @XmlAttribute(name = "oznaka")
        @XmlSchemaType(name = "anySimpleType")
        protected String oznaka;
        @XmlAttribute(name = "naziv")
        @XmlSchemaType(name = "anySimpleType")
        protected String naziv;
        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        /**
         * Gets the value of the sadrzaj property.
         * 
         * @return
         *     possible object is
         *     {@link TSadrzajOdeljka }
         *     
         */
        public TSadrzajOdeljka getSadrzaj() {
            return sadrzaj;
        }

        /**
         * Sets the value of the sadrzaj property.
         * 
         * @param value
         *     allowed object is
         *     {@link TSadrzajOdeljka }
         *     
         */
        public void setSadrzaj(TSadrzajOdeljka value) {
            this.sadrzaj = value;
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

}
