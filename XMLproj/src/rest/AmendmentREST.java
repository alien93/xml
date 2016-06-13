package rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import queries.MySparqlQuery;
import util.ActXmlToPdf;
import util.ConnPropertiesReader;
import util.XQueryInvoker;

@Path("/amendment")
public class AmendmentREST {

	@GET
	@Path("/amendmentsForAct/{actId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAmendmentsForAct(@PathParam("actId")String actId){
		MySparqlQuery q = new MySparqlQuery(MySparqlQuery.AKT_DONET);
		String metadataCollection = "/propisi/amandmani/u_proceduri/metadata";
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
	@Path("/amendmentId/{id}")
	@Produces("application/pdf")
	public Response getAmandmentById(@PathParam("id") String id){
		String query = "declare namespace p=\"http://www.parlament.gov.rs/propisi\";\n" + 
					   "declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";\n" +
					   "for $am in fn:collection(\"/propisi/amandmani/u_proceduri/metadata\")\n" +
					   "where $am/p:Amandman/p:Sporedni_deo/p:Meta_podaci/ns1:Oznaka = \"" + id + "\"" +
					   "\nreturn ($am)//p:Amandman;";
		return helpQuery(query, id);
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
