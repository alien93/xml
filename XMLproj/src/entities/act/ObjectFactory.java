//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.16 at 03:47:35 PM CEST 
//


package entities.act;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the entities.act package. 
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

    private final static QName _Status_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Status");
    private final static QName _StatusIzmene_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "status_izmene");
    private final static QName _TSadrzajPodtackeAlineja_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Alineja");
    private final static QName _TSadrzajStavaTacka_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Tacka");
    private final static QName _TSadrzajGlaveClan_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Clan");
    private final static QName _TSadrzajGlaveOdeljak_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Odeljak");
    private final static QName _TSadrzajClanaStav_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Stav");
    private final static QName _TSadrzajDelaGlava_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Glava");
    private final static QName _TSadrzajTackePodtacka_QNAME = new QName("http://www.parlament.gov.rs/generic_types", "Podtacka");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: entities.act
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SporedniDeo }
     * 
     */
    public SporedniDeo createSporedniDeo() {
        return new SporedniDeo();
    }

    /**
     * Create an instance of {@link SporedniDeo.DonetAkt }
     * 
     */
    public SporedniDeo.DonetAkt createSporedniDeoDonetAkt() {
        return new SporedniDeo.DonetAkt();
    }

    /**
     * Create an instance of {@link SporedniDeo.AktUProceduri }
     * 
     */
    public SporedniDeo.AktUProceduri createSporedniDeoAktUProceduri() {
        return new SporedniDeo.AktUProceduri();
    }

    /**
     * Create an instance of {@link TSadrzajOdeljka }
     * 
     */
    public TSadrzajOdeljka createTSadrzajOdeljka() {
        return new TSadrzajOdeljka();
    }

    /**
     * Create an instance of {@link TSadrzajGlave }
     * 
     */
    public TSadrzajGlave createTSadrzajGlave() {
        return new TSadrzajGlave();
    }

    /**
     * Create an instance of {@link TSadrzajDela }
     * 
     */
    public TSadrzajDela createTSadrzajDela() {
        return new TSadrzajDela();
    }

    /**
     * Create an instance of {@link GlavniDeo }
     * 
     */
    public GlavniDeo createGlavniDeo() {
        return new GlavniDeo();
    }

    /**
     * Create an instance of {@link Deo }
     * 
     */
    public Deo createDeo() {
        return new Deo();
    }

    /**
     * Create an instance of {@link TAmandmanStatus }
     * 
     */
    public TAmandmanStatus createTAmandmanStatus() {
        return new TAmandmanStatus();
    }

    /**
     * Create an instance of {@link entities.act.Glava }
     * 
     */
    public entities.act.Glava createGlava() {
        return new entities.act.Glava();
    }

    /**
     * Create an instance of {@link entities.act.Odeljak }
     * 
     */
    public entities.act.Odeljak createOdeljak() {
        return new entities.act.Odeljak();
    }

    /**
     * Create an instance of {@link entities.act.Clan }
     * 
     */
    public entities.act.Clan createClan() {
        return new entities.act.Clan();
    }

    /**
     * Create an instance of {@link TSadrzajClana }
     * 
     */
    public TSadrzajClana createTSadrzajClana() {
        return new TSadrzajClana();
    }

    /**
     * Create an instance of {@link Stav }
     * 
     */
    public Stav createStav() {
        return new Stav();
    }

    /**
     * Create an instance of {@link TSadrzajStava }
     * 
     */
    public TSadrzajStava createTSadrzajStava() {
        return new TSadrzajStava();
    }

    /**
     * Create an instance of {@link Tacka }
     * 
     */
    public Tacka createTacka() {
        return new Tacka();
    }

    /**
     * Create an instance of {@link TSadrzajTacke }
     * 
     */
    public TSadrzajTacke createTSadrzajTacke() {
        return new TSadrzajTacke();
    }

    /**
     * Create an instance of {@link Podtacka }
     * 
     */
    public Podtacka createPodtacka() {
        return new Podtacka();
    }

    /**
     * Create an instance of {@link TSadrzajPodtacke }
     * 
     */
    public TSadrzajPodtacke createTSadrzajPodtacke() {
        return new TSadrzajPodtacke();
    }

    /**
     * Create an instance of {@link Alineja }
     * 
     */
    public Alineja createAlineja() {
        return new Alineja();
    }

    /**
     * Create an instance of {@link Akt }
     * 
     */
    public Akt createAkt() {
        return new Akt();
    }

    /**
     * Create an instance of {@link Datum }
     * 
     */
    public Datum createDatum() {
        return new Datum();
    }

    /**
     * Create an instance of {@link Oznaka }
     * 
     */
    public Oznaka createOznaka() {
        return new Oznaka();
    }

    /**
     * Create an instance of {@link BrPozitivnihGlasova }
     * 
     */
    public BrPozitivnihGlasova createBrPozitivnihGlasova() {
        return new BrPozitivnihGlasova();
    }

    /**
     * Create an instance of {@link Naziv }
     * 
     */
    public Naziv createNaziv() {
        return new Naziv();
    }

    /**
     * Create an instance of {@link Ref }
     * 
     */
    public Ref createRef() {
        return new Ref();
    }

    /**
     * Create an instance of {@link BrUkupnihGlasova }
     * 
     */
    public BrUkupnihGlasova createBrUkupnihGlasova() {
        return new BrUkupnihGlasova();
    }

    /**
     * Create an instance of {@link Vrsta }
     * 
     */
    public Vrsta createVrsta() {
        return new Vrsta();
    }

    /**
     * Create an instance of {@link Mesto }
     * 
     */
    public Mesto createMesto() {
        return new Mesto();
    }

    /**
     * Create an instance of {@link SporedniDeo.DonetAkt.MetaPodaci }
     * 
     */
    public SporedniDeo.DonetAkt.MetaPodaci createSporedniDeoDonetAktMetaPodaci() {
        return new SporedniDeo.DonetAkt.MetaPodaci();
    }

    /**
     * Create an instance of {@link SporedniDeo.AktUProceduri.MetaPodaci }
     * 
     */
    public SporedniDeo.AktUProceduri.MetaPodaci createSporedniDeoAktUProceduriMetaPodaci() {
        return new SporedniDeo.AktUProceduri.MetaPodaci();
    }

    /**
     * Create an instance of {@link TSadrzajOdeljka.Clan }
     * 
     */
    public TSadrzajOdeljka.Clan createTSadrzajOdeljkaClan() {
        return new TSadrzajOdeljka.Clan();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link TAmandmanStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Status")
    public JAXBElement<TAmandmanStatus> createStatus(TAmandmanStatus value) {
        return new JAXBElement<TAmandmanStatus>(_Status_QNAME, TAmandmanStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TStatusIzmene }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "status_izmene")
    public JAXBElement<TStatusIzmene> createStatusIzmene(TStatusIzmene value) {
        return new JAXBElement<TStatusIzmene>(_StatusIzmene_QNAME, TStatusIzmene.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Tacka", scope = TSadrzajStava.class)
    public JAXBElement<Object> createTSadrzajStavaTacka(Object value) {
        return new JAXBElement<Object>(_TSadrzajStavaTacka_QNAME, Object.class, TSadrzajStava.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajGlave.Clan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Clan", scope = TSadrzajGlave.class)
    public JAXBElement<TSadrzajGlave.Clan> createTSadrzajGlaveClan(TSadrzajGlave.Clan value) {
        return new JAXBElement<TSadrzajGlave.Clan>(_TSadrzajGlaveClan_QNAME, TSadrzajGlave.Clan.class, TSadrzajGlave.class, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajDela.Clan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Clan", scope = TSadrzajDela.class)
    public JAXBElement<TSadrzajDela.Clan> createTSadrzajDelaClan(TSadrzajDela.Clan value) {
        return new JAXBElement<TSadrzajDela.Clan>(_TSadrzajGlaveClan_QNAME, TSadrzajDela.Clan.class, TSadrzajDela.class, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSadrzajOdeljka.Clan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Clan", scope = TSadrzajOdeljka.class)
    public JAXBElement<TSadrzajOdeljka.Clan> createTSadrzajOdeljkaClan(TSadrzajOdeljka.Clan value) {
        return new JAXBElement<TSadrzajOdeljka.Clan>(_TSadrzajGlaveClan_QNAME, TSadrzajOdeljka.Clan.class, TSadrzajOdeljka.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/generic_types", name = "Podtacka", scope = TSadrzajTacke.class)
    public JAXBElement<Object> createTSadrzajTackePodtacka(Object value) {
        return new JAXBElement<Object>(_TSadrzajTackePodtacka_QNAME, Object.class, TSadrzajTacke.class, value);
    }

}
