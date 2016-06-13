package rest;

import java.io.IOException;

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
import util.ConnPropertiesReader;

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
		
		try {
			result = QueryExecutor.executeFromString(ConnPropertiesReader.loadProperties(), query);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return result;
	}
	
}
