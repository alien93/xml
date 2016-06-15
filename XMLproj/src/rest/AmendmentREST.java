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
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import entities.act.Akt;
import entities.act.TStatusAkta;
import entities.amendment.Amandman;
import entities.amendment.TStatusAmandmana;
import queries.QueryExecutor;
import util.AmandmanXmlToPdf;
import util.ConnPropertiesReader;
import util.RDFtoTriples;
import util.XMLValidator;
import util.XMLWriter;
import util.XQueryInvoker;

@Path("/amendment")
public class AmendmentREST {

	@GET
	@Path("/amendmentsForAct/{actId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAmendmentsForAct(@PathParam("actId") String actId){
		String result = "";
		System.out.println("Act id: " + actId);
		String query = "SELECT * FROM </propisi/amandmani/u_proceduri/metadata>{"+
					   " ?propis <http://www.parlament.gov.rs/propisi/predicate/belongsTo> ?akt ."+
					    "{"+
					     "   SELECT * {"+
					      "      ?propis <http://www.parlament.gov.rs/propisi/predicate/oznaka> ?oznakaAmandman ."+
					       "     ?propis <http://www.parlament.gov.rs/propisi/predicate/naziv> ?nazivAmandman ."+
					        "}"+
					     "}"+
					    "FILTER ( str(?akt) = \"http://www.parlament.gov.rs/propisi/akti/u_proceduri/" + actId + "\")"+
					"}";
		System.out.println(query);
		try {
			result = QueryExecutor.executeFromString(ConnPropertiesReader.loadProperties(), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GET
	@Path("/amendmentId/{id}")
	@Produces("application/pdf")
	public Response getAmandmentById(@PathParam("id") String id){
		String query = "declare namespace p=\"http://www.parlament.gov.rs/propisi\";\n" + 
					   "declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";\n" +
					   "for $am in fn:collection(\"/propisi/amandmani/u_proceduri\")\n" +
					   "where $am/p:Amandman/p:Sporedni_deo/p:Meta_podaci/ns1:Oznaka = \"" + id + "\"" +
					   "\nreturn ($am)//p:Amandman;";
		return helpQuery(query, id);
	}
	
	@POST
	@Path("/addAmendment")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addAmendment(Amandman amandman){
		
		//create temp file
		String path = XMLValidator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		String xmlPath = path + "temp.xml";
		
		//check validity
		Response r  = XMLValidator.getInstance().validateAmendment(amandman);
		
		//write if valid		
		if(r.getStatus() == 200){
			try {
				XMLWriter.writeXML(ConnPropertiesReader.loadProperties(), xmlPath, "", "/propisi/amandmani/u_proceduri", true);
			
				//create metadata
				String grddlPath = path + "grddl.xsl";
				String sparqlNamedGraph = "/propisi/amandmani/u_proceduri/metadata";
				String rdfFilePath = path + "temp.rdf";
				RDFtoTriples.convert(ConnPropertiesReader.loadProperties(), xmlPath, rdfFilePath, sparqlNamedGraph, grddlPath);
				
			} catch (IOException | SAXException | TransformerException e) {
				e.printStackTrace();
			}
		}
		
		return r;
	}
	
	@GET
	@Path("/xmlById/{amId}")
	@Produces(MediaType.APPLICATION_XML)
	public String getXmlById(@PathParam("amId") String amId){
		System.out.println("XML for amendment: " + amId);
		String result = "";
		String query = 	"declare namespace p=\"http://www.parlament.gov.rs/propisi\";"+
						"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";"+
						"for $doc in fn:collection(\"/propisi/amandmani/u_proceduri\")"+
						"where $doc/p:Amandman/p:Sporedni_deo/p:Meta_podaci/ns1:Oznaka = \""+ amId +"\""+
						"return $doc";
		try {
			result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@POST
	@Path("/changeCollection")
	@Consumes(MediaType.APPLICATION_XML)
	public Response changeCollection(Amandman amandman){
		System.out.println("Changing collection");
		//create temp file
		String path = XMLValidator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		String xmlPath = path + "temp.xml";
		
		//check validity
		Response r  = XMLValidator.getInstance().validateAmendment(amandman);
		
		//write if valid		
		if(r.getStatus() == 200){
			try {
				XMLWriter.writeXML(ConnPropertiesReader.loadProperties(), xmlPath, "", "/propisi/amandmani/povuceni", true);
			
				//create metadata
				String grddlPath = path + "grddl.xsl";
				String sparqlNamedGraph = "/propisi/amandmani/povuceni/metadata";
				String rdfFilePath = path + "tmp.rdf";
				RDFtoTriples.convert(ConnPropertiesReader.loadProperties(), xmlPath, rdfFilePath, sparqlNamedGraph, grddlPath);
				
			} catch (IOException | SAXException | TransformerException e) {
				e.printStackTrace();
			}
		}
		
		return r;
	}
	
	@POST
	@Path("/removeAmendment/{amId}")
	public void removeAmendment(@PathParam("amId") String amId){
		String query = 	"declare namespace p=\"http://www.parlament.gov.rs/propisi\";"+
						"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";"+
						"for $doc in fn:collection(\"/propisi/amandmani/u_proceduri\")"+
						"where $doc/p:Amandman/p:Sporedni_deo/p:Meta_podaci/ns1:Oznaka = \""+ amId +"\""+
						"return base-uri($doc)";
		
		String result = "";
		try {
			result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
			result = result.replace("\n", "");
			String removeDocQuery = "xdmp:document-delete(\""+ result + "\")";
			XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), removeDocQuery);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String metadataDocQuery = 	"declare namespace sem=\"http://marklogic.com/semantics\";"+
									"for $doc in fn:collection(\"/propisi/amandmani/u_proceduri/metadata\")"+
									"where $doc/sem:triples/sem:triple[2]/sem:object = \""+ amId +"\""+
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
	public Amandman changeStatus(Amandman amandman, @PathParam("status")String status){
		switch(status){
		case "prihvacen":
			amandman.setStatus(TStatusAmandmana.PRIHVACEN);;
			break;
		case "u_proceduri":
			amandman.setStatus(TStatusAmandmana.U_PROCEDURI);
			break;
		case "odbijen":
			amandman.setStatus(TStatusAmandmana.ODBIJEN);
			break;
		}
		return amandman;
	}
	
	private Response helpQuery(String query, String id){
		try {
			String result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
			InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			path = path.substring(1, path.length()); 
			new AmandmanXmlToPdf(path+"fop.xconf").transform(is, os);
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
