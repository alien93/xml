package rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.marklogic.client.util.EditableNamespaceContext;

import entities.act.Akt;
import entities.amendment.Odeljak;
import entities.amendment.Podtacka;
import entities.amendment.Stav;
import entities.amendment.Tacka;
import entities.act.TStatusAkta;
import entities.act.TStatusIzmene;
import entities.amendment.Alineja;
import entities.amendment.Amandman;
import entities.amendment.Clan;
import entities.amendment.Deo;
import entities.amendment.Glava;
import queries.MySparqlQuery;
import util.ActXmlToPdf;
import util.ConnPropertiesReader;
import util.RDFtoTriples;
import util.XMLUpdate;
import util.XMLValidator;
import util.XMLWriter;
import util.XQueryInvoker;

@Path("/act")
public class ActREST {

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

	@GET
	@Path("/xmlById/{actId}")
	@Produces(MediaType.APPLICATION_XML)
	public String getXmlById(@PathParam("actId") String actId){
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

	@POST
	@Path("/changeCollection/{collectionName}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response changeCollection(Akt akt, @PathParam("collectionName")String collectionName){
		System.out.println("Changing collection");
		//create temp file
		String path = XMLValidator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		String xmlPath = path + "temp.xml";

		//check validity
		Response r  = XMLValidator.getInstance().validateAct(akt, xmlPath);

		//write if valid		
		if(r.getStatus() == 200){
			try {
				XMLWriter.writeXML(ConnPropertiesReader.loadProperties(), xmlPath, "", "/propisi/akti/" + collectionName, true);

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

	@POST
	@Path("/removeAct/{actId}")
	public void removeAct(@PathParam("actId") String actId){
		String result = getActsUri(actId);
		//copy document to another collection
		//remove document
		try {
			String removeDocQuery = "xdmp:document-delete(\""+ result + "\")";
			XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), removeDocQuery);
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
			String removeMetadataQuery = "xdmp:document-delete(\""+ result1 + "\")";
			XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), removeMetadataQuery);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

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
		}
		return akt;
	}

	@POST
	@Path("/updateAct/{actId}")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateAct(@PathParam("actId")String actId, Amandman amandman){
		System.out.println(actId);
		try {
			JAXBContext jc = JAXBContext.newInstance(Akt.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			InputStream actStream = new ByteArrayInputStream(getXmlById(actId).getBytes(StandardCharsets.UTF_8));
			Akt akt = (Akt) unmarshaller.unmarshal(actStream);
			for(Deo deo : amandman.getGlavniDeo().getDeo()){
				if(deo.getStatus()!=null)
					update(actId, akt, deo);
			}
			for(Odeljak odeljak : amandman.getGlavniDeo().getOdeljak()){
				if(odeljak.getStatus()!=null)
					update(actId, akt, odeljak);
			}
			for(Glava glava : amandman.getGlavniDeo().getGlava()){
				if(glava.getStatus()!=null)
					update(actId, akt, glava);
			}
			for(Clan clan : amandman.getGlavniDeo().getClan()){
				if(clan.getStatus()!=null)
					update(actId, akt, clan);
			}
			for(Tacka tacka : amandman.getGlavniDeo().getTacka()){
				if(tacka.getStatus()!=null)
					update(actId, akt, tacka);
			}
			for(Podtacka podtacka : amandman.getGlavniDeo().getPodtacka()){
				if(podtacka.getStatus()!=null)
					update(actId, akt, podtacka);
			}
			for(Alineja alineja : amandman.getGlavniDeo().getAlineja()){
				if(alineja.getStatus()!=null)
					update(actId, akt, alineja);
			}
			for(Stav stav : amandman.getGlavniDeo().getStav()){
				if(stav.getStatus()!=null)
					update(actId, akt, stav);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}


	private void update(String aktId, Akt akt, Object obj) {
		System.out.println("updating...");
		EditableNamespaceContext namespaces = new EditableNamespaceContext();

		namespaces.put("ns1", "http://www.parlament.gov.rs/generic_types");
		namespaces.put("p", "http://www.parlament.gov.rs/propisi");


		if(obj instanceof Deo){
			String oznaka = ((Deo) obj).getStatus().getRef().getIdRef();
			switch(((Deo) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Deo[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
				break;
			default:
				break;
			}

		}
		else if(obj instanceof Odeljak){
			String oznaka = ((Odeljak) obj).getStatus().getRef().getIdRef();
			switch(((Odeljak) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Odeljak[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Clan){
			String oznaka = ((Clan) obj).getStatus().getRef().getIdRef();
			switch(((Clan) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Clan[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Tacka){
			String oznaka = ((Tacka) obj).getStatus().getRef().getIdRef();
			switch(((Tacka) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Tacka[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Podtacka){
			String oznaka = ((Podtacka) obj).getStatus().getRef().getIdRef();
			switch(((Podtacka) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Podtacka[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Stav){
			String oznaka = ((Stav) obj).getStatus().getRef().getIdRef();
			switch(((Stav) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Stav[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Alineja){
			String oznaka = ((Alineja) obj).getStatus().getRef().getIdRef();
			switch(((Alineja) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Alineja[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
				break;
			default:
				break;
			}
		}
		else if(obj instanceof Glava){
			String oznaka = ((Glava) obj).getStatus().getRef().getIdRef();
			switch(((Glava) obj).getStatus().getStatusIzmene().value()) {
			case "brisi":
				System.out.println("Brisi");
				String contextXPath = "//ns1:Glava[@oznaka=\"" + oznaka + "\"]";
				try {
					XMLUpdate.updateXMLRemove(ConnPropertiesReader.loadProperties(), getActsUri(aktId), namespaces, contextXPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "menjaj":
				break;
			case "dodaj":
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
