//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.07 at 01:07:57 AM CEST 
//


package entities.genericTypes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the entities.genericTypes package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _StatusIzmene_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "status_izmene");
    private final static QName _TSadrzajStavaTacka_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Tacka");
    private final static QName _TSadrzajClanaStav_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Stav");
    private final static QName _TSadrzajTackePodtacka_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Podtacka");
    private final static QName _TSadrzajOdeljkaClan_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Clan");
    private final static QName _TSadrzajGlaveOdeljak_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Odeljak");
    private final static QName _TSadrzajPodtackeAlineja_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Alineja");
    private final static QName _TSadrzajDelaGlava_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Glava");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: entities.genericTypes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TSadrzajGlave }
     * 
     */
    public TSadrzajGlave createTSadrzajGlave() {
        return new TSadrzajGlave();
    }

    /**
     * Create an instance of {@link TSadrzajOdeljka }
     * 
     */
    public TSadrzajOdeljka createTSadrzajOdeljka() {
        return new TSadrzajOdeljka();
    }

    /**
     * Create an instance of {@link TSadrzajDela }
     * 
     */
    public TSadrzajDela createTSadrzajDela() {
        return new TSadrzajDela();
    }

    /**
     * Create an instance of {@link Ref }
     * 
     */
    public Ref createRef() {
        return new Ref();
    }

    /**
     * Create an instance of {@link TSadrzajClana }
     * 
     */
    public TSadrzajClana createTSadrzajClana() {
        return new TSadrzajClana();
    }

    /**
     * Create an instance of {@link TSadrzajStava }
     * 
     */
    public TSadrzajStava createTSadrzajStava() {
        return new TSadrzajStava();
    }

    /**
     * Create an instance of {@link TAmandmanStatus }
     * 
     */
    public TAmandmanStatus createTAmandmanStatus() {
        return new TAmandmanStatus();
    }

    /**
     * Create an instance of {@link TSadrzajTacke }
     * 
     */
    public TSadrzajTacke createTSadrzajTacke() {
        return new TSadrzajTacke();
    }

    /**
     * Create an instance of {@link TSadrzajPodtacke }
     * 
     */
    public TSadrzajPodtacke createTSadrzajPodtacke() {
        return new TSadrzajPodtacke();
    }

    /**
     * Create an instance of {@link TSadrzajGlave.Odeljak }
     * 
     */
    public TSadrzajGlave.Odeljak createTSadrzajGlaveOdeljak() {
        return new TSadrzajGlave.Odeljak();
    }

    /**
     * Create an instance of {@link TSadrzajGlave.Clan }
     * 
     */
    public TSadrzajGlave.Clan createTSadrzajGlaveClan() {
        return new TSadrzajGlave.Clan();
    }

    /**
     * Create an instance of {@link TSadrzajOdeljka.Clan }
     * 
     */
    public TSadrzajOdeljka.Clan createTSadrzajOdeljkaClan() {
        return new TSadrzajOdeljka.Clan();
    }

    /**
     * Create an instance of {@link TSadrzajDela.Glava }
     * 
     */
    public TSadrzajDela.Glava createTSadrzajDelaGlava() {
        return new TSadrzajDela.Glava();
    }

    /**
     * Create an instance of {@link TSadrzajDela.Odeljak }
     * 
     */
    public TSadrzajDela.Odeljak createTSadrzajDelaOdeljak() {
        return new TSadrzajDela.Odeljak();
    }

    /**
     * Create an instance of {@link TSadrzajDela.Clan }
     * 
     */
    public TSadrzajDela.Clan createTSadrzajDelaClan() {
        return new TSadrzajDela.Clan();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TStatusAkta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "status_izmene")
    public JAXBElement<TStatusAkta> createStatusIzmene(TStatusAkta value) {
        return new JAXBElement<TStatusAkta>(_StatusIzmene_QNAME, TStatusAkta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Tacka", scope = TSadrzajStava.class)
    public JAXBElement<Object> createTSadrzajStavaTacka(Object value) {
        return new JAXBElement<Object>(_TSadrzajStavaTacka_QNAME, Object.class, TSadrzajStava.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Stav", scope = TSadrzajClana.class)
    public JAXBElement<Object> createTSadrzajClanaStav(Object value) {
        return new JAXBElement<Object>(_TSadrzajClanaStav_QNAME, Object.class, TSadrzajClana.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Tacka", scope = TSadrzajClana.class)
    public JAXBElement<Object> createTSadrzajClanaTacka(Object value) {
        return new JAXBElement<Object>(_TSadrzajStavaTacka_QNAME, Object.class, TSadrzajClana.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Podtacka", scope = TSadrzajTacke.class)
    public JAXBElement<Object> createTSadrzajTackePodtacka(Object value) {
        return new JAXBElement<Object>(_TSadrzajTackePodtacka_QNAME, Object.class, TSadrzajTacke.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajOdeljka.Clan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Clan", scope = TSadrzajOdeljka.class)
    public JAXBElement<TSadrzajOdeljka.Clan> createTSadrzajOdeljkaClan(TSadrzajOdeljka.Clan value) {
        return new JAXBElement<TSadrzajOdeljka.Clan>(_TSadrzajOdeljkaClan_QNAME, TSadrzajOdeljka.Clan.class, TSadrzajOdeljka.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajGlave.Clan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Clan", scope = TSadrzajGlave.class)
    public JAXBElement<TSadrzajGlave.Clan> createTSadrzajGlaveClan(TSadrzajGlave.Clan value) {
        return new JAXBElement<TSadrzajGlave.Clan>(_TSadrzajOdeljkaClan_QNAME, TSadrzajGlave.Clan.class, TSadrzajGlave.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajGlave.Odeljak }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Odeljak", scope = TSadrzajGlave.class)
    public JAXBElement<TSadrzajGlave.Odeljak> createTSadrzajGlaveOdeljak(TSadrzajGlave.Odeljak value) {
        return new JAXBElement<TSadrzajGlave.Odeljak>(_TSadrzajGlaveOdeljak_QNAME, TSadrzajGlave.Odeljak.class, TSadrzajGlave.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Alineja", scope = TSadrzajPodtacke.class)
    public JAXBElement<Object> createTSadrzajPodtackeAlineja(Object value) {
        return new JAXBElement<Object>(_TSadrzajPodtackeAlineja_QNAME, Object.class, TSadrzajPodtacke.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajDela.Clan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Clan", scope = TSadrzajDela.class)
    public JAXBElement<TSadrzajDela.Clan> createTSadrzajDelaClan(TSadrzajDela.Clan value) {
        return new JAXBElement<TSadrzajDela.Clan>(_TSadrzajOdeljkaClan_QNAME, TSadrzajDela.Clan.class, TSadrzajDela.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajDela.Odeljak }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Odeljak", scope = TSadrzajDela.class)
    public JAXBElement<TSadrzajDela.Odeljak> createTSadrzajDelaOdeljak(TSadrzajDela.Odeljak value) {
        return new JAXBElement<TSadrzajDela.Odeljak>(_TSadrzajGlaveOdeljak_QNAME, TSadrzajDela.Odeljak.class, TSadrzajDela.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajDela.Glava }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Glava", scope = TSadrzajDela.class)
    public JAXBElement<TSadrzajDela.Glava> createTSadrzajDelaGlava(TSadrzajDela.Glava value) {
        return new JAXBElement<TSadrzajDela.Glava>(_TSadrzajDelaGlava_QNAME, TSadrzajDela.Glava.class, TSadrzajDela.class, value);
    }

}
