package rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import queries.MySparqlQuery;
import util.ConnPropertiesReader;

/**
 * Filtriranje akata
 * @author Milan
 *
 */
@Path("/filter")
public class ActFilterREST {
	
	@GET
	@Path("/activeActs/{text}/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response filterActive(@PathParam("text") String text, 
							@PathParam("category") String category){
		
		return helpQuery(MySparqlQuery.AKT_DONET, "/propisi/akti/doneti/metadata", category, text);
	}
	
	@GET
	@Path("/nonActiveActs/{text}/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response filterNonActive(@PathParam("text") String text, 
							@PathParam("category") String category){
		
		return helpQuery(MySparqlQuery.AKT_U_PROCEDURI, "/propisi/akti/u_proceduri/metadata", category, text);
	}
	
	private Response helpQuery(String type, String metaData, String category, String text){
		String oznaka = "Oznaka".equals(category.trim()) ? text : "";
		String naziv = "Naziv".equals(category.trim()) ? text : "";
		String mesto = "Mesto".equals(category.trim()) ? text : "";
		String datumMin = "Datum".equals(category.trim()) ? text : "";
		String datumMax = "Datum".equals(category.trim()) ? text : "";
		String vrsta = "Vrsta".equals(category.trim()) ? text : "";
		String metadataCollection = metaData;
		MySparqlQuery q = new MySparqlQuery(type, 
				oznaka, naziv, mesto, datumMin, datumMax, vrsta);
		ResponseBuilder response = Response.ok();
		try {
			boolean filter = "_".equals(text.trim()) ? false : true;
			return response.status(200).entity(
					q.execute(ConnPropertiesReader.loadProperties(), metadataCollection, filter)).build();
		} catch (IOException e) {
			e.printStackTrace();
			Response.status(400).build();
		}
		return null;
	}
}
