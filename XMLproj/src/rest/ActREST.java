package rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.marklogic.client.util.EditableNamespaceContext;

import entities.act.Akt;
import entities.act.SporedniDeo;
import entities.act.SporedniDeo.DonetAkt;
import entities.act.SporedniDeo.DonetAkt.MetaPodaci;
import entities.act.TStatusAkta;
import entities.amendment.Alineja;
import entities.amendment.Amandman;
import entities.amendment.Clan;
import entities.amendment.Deo;
import entities.amendment.Glava;
import entities.amendment.Odeljak;
import entities.amendment.Podtacka;
import entities.amendment.Stav;
import entities.amendment.Tacka;
import queries.MySparqlQuery;
import util.ConnPropertiesReader;
import util.RDFtoTriples;
import util.SaveDocumentHtmlPdf;
import util.XMLUpdate;
import util.XMLValidator;
import util.XMLWriter;
import util.XQueryInvoker;
import util.XMLUpdate.UpdatePositions;
import util.transform.ActXmlToPdf;

@Path("/act")
public class ActREST {

	/**
	 * Dobavlja sve donete akte
	 * @return Status uspesnosti dobavljanja
	 */
	@GET
	@Path("/active")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActiveActs(){
		MySparqlQuery q = new MySparqlQuery(MySparqlQuery.AKT_DONET);
		String metadataCollection = "/propisi/akti/doneti/metadata";
		ResponseBuilder response = Response.ok();
		try {
			return response.status(200).entity(q.execute(ConnPropertiesReader.loadProperties(), metadataCollection, false)).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return response.status(400).build();
		}
	}

	/**
	 * Dobavlja sve akte u proceduri
	 * @return Status uspesnosti dobavljanja
	 */
	@GET
	@Path("/nonActive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNonActiveActs(){
		MySparqlQuery q = new MySparqlQuery(MySparqlQuery.AKT_DONET);
		String metadataCollection = "/propisi/akti/u_proceduri/metadata";
		ResponseBuilder response = Response.ok();
		try {
			return response.status(200).entity(q.execute(ConnPropertiesReader.loadProperties(), metadataCollection, false)).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return response.status(400).build();
		}
	}

	/**
	 * Dobavlja donet akt na osnovu oznake
	 * @param id - oznaka akta
	 * @return
	 */
	@GET
	@Path("/activeId/{id}")
	@Produces("application/pdf")
	public Response getActiveActById(@PathParam("id") String id){
		String query = "declare namespace p=\"http://www.parlament.gov.rs/propisi\";\n" + 
				"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";\n" +
				"for $doc in fn:collection(\"/propisi/akti/doneti\")\n" +
				"where $doc/p:Akt/p:Sporedni_deo/p:Donet_akt/p:Meta_podaci/ns1:Oznaka = \"" + id + "\"" +
				"\nreturn ($doc)//p:Akt;";
		return helpQuery(query, id);
	}

	/**
	 * Dobavlja akt u proceduri na osnovu oznake
	 * @param id - oznaka akta
	 * @return
	 */
	@GET
	@Path("/nonActiveId/{id}")
	@Produces("application/pdf")
	public Response getNonActiveActById(@PathParam("id") String id){
		String query = "declare namespace p=\"http://www.parlament.gov.rs/propisi\";\n" + 
				"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";\n" +
				"for $doc in fn:collection(\"/propisi/akti/u_proceduri\")\n" +
				"where $doc/p:Akt/p:Sporedni_deo/p:Akt_u_proceduri/p:Meta_podaci/ns1:Oznaka = \"" + id + "\"" +
				"\nreturn ($doc)//p:Akt;";
		return helpQuery(query, id);
	}

	/**
	 * Dodaje akt u kolekciju "u_proceduri"
	 * @param akt koji se dodaje 
	 * @return Status uspesnosti
	 */
	@POST
	@Path("/addAct")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addAct(Akt akt){

		//create temp file
		String path = XMLValidator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		String xmlPath = path + "temp.xml";

		//check validity
		Response r  = XMLValidator.getInstance().validateAct(akt, xmlPath);

		//write if valid		
		if(r.getStatus() == 200){
			try {
				XMLWriter.writeXML(ConnPropertiesReader.loadProperties(), xmlPath, "", "/propisi/akti/u_proceduri", true);
				String oznaka = akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getOznaka().getValue();
				SaveDocumentHtmlPdf sdhp = new SaveDocumentHtmlPdf("/propisi/akti/u_proceduri", oznaka);
				sdhp.save();

				//create metadata
				String grddlPath = path + "grddl.xsl";
				String sparqlNamedGraph = "/propisi/akti/u_proceduri/metadata";
				String rdfFilePath = path + "temp.rdf";
				RDFtoTriples.convert(ConnPropertiesReader.loadProperties(), xmlPath, rdfFilePath, sparqlNamedGraph, grddlPath);

			} catch (IOException | SAXException | TransformerException e) {
				e.printStackTrace();
			}
		}

		return r;
	}

	/**
	 * Dobavlja sadrzaj xml dokumenta na osnovu oznake akta iz kolekcije "u_proceduri".
	 * Izmeni sadrzaj sporednog dela akta tako da odgovara donetom aktu
	 * @param actId - oznaka akta
	 * @param data - podaci kojima ce sporedni deo biti dopunjen
	 * @return Izmenjen akt - (Akt_u_proceduri -> Donet_akt)
	 */
	@POST
	@Path("/xmlById/{actId}")
	@Produces(MediaType.APPLICATION_XML)
	public String getXmlById(@PathParam("actId") String actId, String data){
		String odStrane = data.split("\\$\\$\\$\\$")[0];
		String poPostupku = data.split("\\$\\$\\$\\$")[1];
		String result = "";
		String query = 	"declare namespace p=\"http://www.parlament.gov.rs/propisi\";"+
				"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";"+
				"for $doc in fn:collection(\"/propisi/akti/u_proceduri\")"+
				"where $doc/p:Akt/p:Sporedni_deo/p:Akt_u_proceduri/p:Meta_podaci/ns1:Oznaka = \""+ actId +"\""+
				"return $doc";
		try {
			result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
			InputStream actStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
			Akt akt = actXmlToAct(actStream);
			akt = changeElement(akt, odStrane, poPostupku);
			result = actToXml(akt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Dobavlja sadrzaj xml dokumenta akta na osnovu oznake akta iz kolekcije "u_proceduri"
	 * @param actId - oznaka akta
	 * @return Sadrzaj xml dokumenta
	 */
	@GET
	@Path("/xmlById/{actId}")
	@Produces(MediaType.APPLICATION_XML)
	public String getXmlById(@PathParam("actId")String actId){
		String result = "";
		String query = 	"declare namespace p=\"http://www.parlament.gov.rs/propisi\";"+
				"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";"+
				"for $doc in fn:collection(\"/propisi/akti/u_proceduri\")"+
				"where $doc/p:Akt/p:Sporedni_deo/p:Akt_u_proceduri/p:Meta_podaci/ns1:Oznaka = \""+ actId +"\""+
				"return $doc";
		try {
			result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Kopira akt iz kolekcije kojoj trenutno pripada u novu kolekciju
	 * @param akt - akt koji se kopira
	 * @param collectionName - kolekcija u koju se kopira
	 * @return Status uspesnosti
	 */
	@POST
	@Path("/changeCollection/{collectionName}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response changeCollection(Akt akt, @PathParam("collectionName")String collectionName){		
		//create temp file
		String path = XMLValidator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		String xmlPath = path + "temp.xml";

		//check validity
		Response r  = XMLValidator.getInstance().validateAct(akt, xmlPath);

		//write if valid		
		if(r.getStatus() == 200){
			try {
				String id = "";
				if(collectionName.equals("povuceni"))
					id = akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getOznaka().getValue();
				else
					id = akt.getSporedniDeo().getDonetAkt().getMetaPodaci().getOznaka().getValue();
				System.out.println("Id: " + id);
				SaveDocumentHtmlPdf sdhp = new SaveDocumentHtmlPdf("/propisi/akti/u_proceduri", id);
				sdhp.delete();
				XMLWriter.writeXML(ConnPropertiesReader.loadProperties(), xmlPath, "", "/propisi/akti/" + collectionName, true);
				sdhp = new SaveDocumentHtmlPdf("/propisi/akti/" + collectionName, id);
				sdhp.save();

				//create metadata
				String grddlPath = path + "grddl.xsl";
				String sparqlNamedGraph = "/propisi/akti/"+collectionName+"/metadata";
				String rdfFilePath = path + "temp.rdf";
				RDFtoTriples.convert(ConnPropertiesReader.loadProperties(), xmlPath, rdfFilePath, sparqlNamedGraph, grddlPath);

			} catch (IOException | SAXException | TransformerException e) {
				e.printStackTrace();
			}
		}

		return r;
	}

	/**
	 * Dobavlja uri akta na osnovu oznake akta iz kolekcije "u_proceduri"
	 * @param actId - oznaka akta
	 * @return uri akta
	 */
	private String getActsUri(String actId){
		String result = "";
		//get document's name
		String query = 	"declare namespace p=\"http://www.parlament.gov.rs/propisi\";"+
				"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";"+
				"for $doc in fn:collection(\"/propisi/akti/u_proceduri\")"+
				"where $doc/p:Akt/p:Sporedni_deo/p:Akt_u_proceduri/p:Meta_podaci/ns1:Oznaka = \""+ actId +"\""+
				"return base-uri($doc)";
		try {
			result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		result = result.replace("\n", "");

		return result;
	}
	
	/**
	 * Dobavlja uri akta na osnovu oznake iz kolekcije "doneti"
	 * @param actId - oznaka akta
	 * @return uri akta
	 */
	private String getActiveActsUri(String actId){
		String result = "";
		//get document's name
		String query = 	"declare namespace p=\"http://www.parlament.gov.rs/propisi\";"+
				"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";"+
				"for $doc in fn:collection(\"/propisi/akti/doneti\")"+
				"where $doc/p:Akt/p:Sporedni_deo/p:Donet_akt/p:Meta_podaci/ns1:Oznaka = \""+ actId +"\""+
				"return base-uri($doc)";
		try {
			result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		result = result.replace("\n", "");

		return result;
	}

	/**
	 * Uklanja akt iz baze i metapodatke (kolekcija "u_proceduri/metadata")
	 * @param actId - oznaka akta
	 */
	@POST
	@Path("/removeAct/{actId}")
	public void removeAct(@PathParam("actId") String actId){
		String result = getActsUri(actId);
		//copy document to another collection
		//remove document
		try {
			if(!result.equals("")){
				String removeDocQuery = "xdmp:document-delete(\""+ result + "\")";
				XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), removeDocQuery);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//remove metadata
		//get the name of the document that contains metadata
		String metadataDocQuery = 	"declare namespace sem=\"http://marklogic.com/semantics\";"+
									"for $doc in fn:collection(\"/propisi/akti/u_proceduri/metadata\")"+
									"where $doc/sem:triples/sem:triple[1]/sem:object = \""+ actId +"\""+
									"return base-uri($doc)";
		try {
			String result1 = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), metadataDocQuery);
			result1 = result1.replace("\n", "");
			if(!result1.equals("")){
				String removeMetadataQuery = "xdmp:document-delete(\""+ result1 + "\")";
				XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), removeMetadataQuery);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Menja status akta
	 * @param akt - akt kome se menja status
	 * @param status - status izmene (moze biti "donet", "u_proceduri" ili "odbijen")
	 * @return Akt sa izmenjenim statusom
	 */
	@POST
	@Path("/changeStatus/{status}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Akt changeStatus(Akt akt, @PathParam("status")String status){
		switch(status){
		case "donet":
			akt.setStatus(TStatusAkta.DONET);
			break;
		case "u_proceduri":
			akt.setStatus(TStatusAkta.U_PROCEDURI);
			break;
		case "odbijen":
			akt.setStatus(TStatusAkta.ODBIJEN);
			break;
		}
		return akt;
	}

	/**
	 * Menja sporedni element iz Akt_u_proceduri u Donet_akt
	 * @param akt - akt nad kojim se vrsi izmena
	 * @param odStrane - akt je donet od strane ovog lica
	 * @param poPostupku - akt je donet po ovom postupku
	 * @return Izmenjeni akt
	 */
	private Akt changeElement(Akt akt, String odStrane, String poPostupku) {
		SporedniDeo sd = new SporedniDeo();
		String oznaka = akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getOznaka().getValue();
		sd.setAbout("http://www.parlament.gov.rs/propisi/akti/doneti/" + oznaka);
		DonetAkt da = new DonetAkt();
		da.setDonetOdStrane(odStrane);
		da.setPravniOsnovDonosenja(poPostupku);
		MetaPodaci mp = new MetaPodaci();
		mp.setOznaka(akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getOznaka());
		mp.setVrsta(akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getVrsta());
		mp.setNaziv(akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getNaziv());
		mp.setMesto(akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getMesto());
		mp.setDatum(akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getDatum());
		mp.setBrPozitivnihGlasova(akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getBrPozitivnihGlasova());
		mp.setBrUkupnihGlasova(akt.getSporedniDeo().getAktUProceduri().getMetaPodaci().getBrUkupnihGlasova());
		da.setMetaPodaci(mp);
		sd.setDonetAkt(da);
		akt.setSporedniDeo(sd);
		return akt;
	}
	
	/**
	 * Konvertuje xml koji sadrzi akt u objekat Akt.
	 * @param actStream xml sadrzaj akta
	 * @return objekat Akt
	 */
	private Akt actXmlToAct(InputStream actStream){
		Akt retVal = null;
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(Akt.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			retVal = (Akt) unmarshaller.unmarshal(actStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Konvertuje akt u xml
	 * @param akt - akt koji se konvertuje
	 * @return sadrzaj xml-a
	 */
	private String actToXml(Akt akt){
		String retVal = "";
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(Akt.class);
			Marshaller marshaller = jc.createMarshaller();
			OutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(akt, os);
			retVal = os.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Primenjuje amandman na akt
	 * @param actId - oznaka akta
	 * @param amandman - amandman koji se primenjuje na akt
	 */
	@POST
	@Path("/updateAct/{actId}")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateAct(@PathParam("actId")String actId, Amandman amandman){
		SaveDocumentHtmlPdf sdhp = new SaveDocumentHtmlPdf("/propisi/akti/doneti", actId);
		sdhp.delete();
		for(Deo deo : amandman.getGlavniDeo().getDeo()){
			if(deo.getStatus()!=null)
				update(actId,   deo);
		}
		for(Odeljak odeljak : amandman.getGlavniDeo().getOdeljak()){
			if(odeljak.getStatus()!=null)
				update(actId,   odeljak);
		}
		for(Glava glava : amandman.getGlavniDeo().getGlava()){
			if(glava.getStatus()!=null)
				 update(actId,   glava);
		}
		for(Clan clan : amandman.getGlavniDeo().getClan()){
			if(clan.getStatus()!=null)
				 update(actId,   clan);
		}
		for(Tacka tacka : amandman.getGlavniDeo().getTacka()){
			if(tacka.getStatus()!=null)
				 update(actId,   tacka);
		}
		for(Podtacka podtacka : amandman.getGlavniDeo().getPodtacka()){
			if(podtacka.getStatus()!=null)
				 update(actId,   podtacka);
		}
		for(Alineja alineja : amandman.getGlavniDeo().getAlineja()){
			if(alineja.getStatus()!=null)
				update(actId,   alineja);
		}
		for(Stav stav : amandman.getGlavniDeo().getStav()){
			if(stav.getStatus()!=null)
				update(actId,  stav);
		}
		sdhp = new SaveDocumentHtmlPdf("/propisi/akti/doneti", actId);
		sdhp.save();
	}


	/**
	 * Vrsi azuriranje akta
	 * @param aktId - oznaka akta
	 * @param obj - objekat koji se azurira (Deo, Clan...)
	 */
	private void update(String aktId, Object obj) {
		EditableNamespaceContext namespaces = new EditableNamespaceContext();

		namespaces.put("ns1", "http://www.parlament.gov.rs/generic_types");
		namespaces.put("p", "http://www.parlament.gov.rs/propisi");
		String docId = getActiveActsUri(aktId);
		if(docId == "")
			docId = getActsUri(aktId);
		System.out.println("DocId: " + docId);


		if(obj instanceof Deo){
			String oznaka = ((Deo) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Deo[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Deo.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Deo)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}
			
			switch(((Deo) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}

		}
		else if(obj instanceof Odeljak){
			String oznaka = ((Odeljak) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Odeljak[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Odeljak.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Odeljak)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}

			switch(((Odeljak) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Clan){
			String oznaka = ((Clan) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Clan[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Clan.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Clan)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}
			
			
			switch(((Clan) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Tacka){
			String oznaka = ((Tacka) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Tacka[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Tacka.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Tacka)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}
			
			
			switch(((Tacka) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Podtacka){
			String oznaka = ((Podtacka) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Podtacka[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Podtacka.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Podtacka)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}
			
			
			switch(((Podtacka) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Stav){
			String oznaka = ((Stav) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Stav[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Stav.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Stav)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}
			
			
			
			switch(((Stav) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Alineja){
			String oznaka = ((Alineja) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Alineja[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Alineja.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Alineja)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}
			
			
			
			switch(((Alineja) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Glava){
			String oznaka = ((Glava) obj).getStatus().getRef().getIdRef();
			String contextXPath = "//ns1:Glava[@oznaka=\"" + oznaka + "\"]";
			JAXBContext jc;
			OutputStream os = null;
			try {
				jc = JAXBContext.newInstance(Glava.class);
				Marshaller marshaller = jc.createMarshaller();
				os = new ByteArrayOutputStream();
				marshaller.marshal((Glava)obj, os);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			String patch = os.toString();
			if(patch.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")){
				patch = patch.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			}
			
			
			switch(((Glava) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), docId, namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				try {
					XMLUpdate.updateXMLReplace(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dodaj":
				try {
					XMLUpdate.updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.AFTER);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}

	}

	private Response helpQuery(String query, String id){
		try {
			String result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
			InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			path = path.substring(1, path.length()); 
			new ActXmlToPdf(path+"fop.xconf").transform(is, os);
			ResponseBuilder builder = Response.ok(os.toByteArray());
			builder.header("Content-Disposition",
					"attachment; filename="+id+".pdf");

			return builder.build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
	}
}
