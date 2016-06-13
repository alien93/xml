package rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import queries.MySparqlQuery;
import queries.QueryExecutor;
import util.ActXmlToPdf;
import util.ConnPropertiesReader;
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
