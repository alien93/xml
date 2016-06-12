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

import entities.act.Akt;
import queries.MySparqlQuery;
import util.ActXmlToPdf;
import util.ConnPropertiesReader;
import util.XMLValidator;
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
	public void addAct(Akt akt){
		//check validity
		XMLValidator.getInstance().validateAct(akt);
		//write if valid
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