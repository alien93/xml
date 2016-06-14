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
		System.out.println("***********" + text + "*************");
		String oznaka = "Oznaka".equals(category.trim()) ? text.trim() : "";
		String naziv = "Naziv".equals(category.trim()) ? text.trim() : "";
		String mesto = "Mesto".equals(category.trim()) ? text.trim() : "";
		String datumMin = "Datum".equals(category.trim()) ? text.trim() : "";
		String datumMax = "Datum".equals(category.trim()) ? text.trim() : "";
		String vrsta = "Vrsta".equals(category.trim()) ? text.trim() : "";
		if("Sve kategorije".equals(category.trim())){
			oznaka = text.trim(); naziv = text.trim(); mesto = text.trim(); datumMin = text.trim(); datumMax = text.trim(); vrsta = text.trim();
		}
		int brPozitivnihGlasova = -1;
		if("Sve kategorije".equals(category.trim()) || "Broj pozitivnih glasova".equals(category.trim())){
			try{ 
				brPozitivnihGlasova = Integer.parseInt(text.trim()); 
			} catch(Exception e){ }
		}
		int brUkupnihGlasova = -1;
		if("Sve kategorije".equals(category.trim()) || "Broj ukupnih glasova".equals(category.trim())){
			try{
				brUkupnihGlasova = Integer.parseInt(text.trim());
			}catch(Exception e){}
		}
		
		String metadataCollection = metaData;
		MySparqlQuery q = new MySparqlQuery(type, 
				oznaka, naziv, mesto, datumMin, datumMax, vrsta);
		if("Sve kategorije".equals(category.trim())) q.setOperator(" || ");
		q.setBrPozitivnihGlasovaMin(brPozitivnihGlasova);
		q.setBrPozitivnihGlasovaMax(brPozitivnihGlasova);
		q.setBrUkupnihGlasovaMin(brUkupnihGlasova);
		q.setBrUkupnihGlasovaMax(brUkupnihGlasova);
		
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
