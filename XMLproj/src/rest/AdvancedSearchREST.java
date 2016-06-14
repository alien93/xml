package rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import queries.MySparqlQuery;
import util.ConnPropertiesReader;

@Path("/advanced")
public class AdvancedSearchREST {
	
	@GET
	@Path("/search/{oznaka}/{naziv}/{vrsta}/{datumMin}/{datumMax}/{mesto}"
			+ "/{brPozitivnihGlasovaMin}/{brPozitivnihGlasovaMax}"
			+ "/{brUkupnihGlasovaMin}/{brUkupnihGlasovaMax}/{type:.+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("type") String type,
						   @PathParam("oznaka") String oznaka,
						   @PathParam("naziv") String naziv,
						   @PathParam("vrsta") String vrsta,
						   @PathParam("datumMin") String datumMin,
						   @PathParam("datumMax") String datumMax,
						   @PathParam("mesto") String mesto,
						   @PathParam("brPozitivnihGlasovaMin") String brPozitivnihGlasovaMin,
						   @PathParam("brPozitivnihGlasovaMax") String brPozitivnihGlasovaMax,
						   @PathParam("brUkupnihGlasovaMin") String brUkupnihGlasovaMin,
						   @PathParam("brUkupnihGlasovaMax") String brUkupnihGlasovaMax){
		int brPozGlasovaMin = -1;
		int brPozGlasovaMax = -1;
		int brUkGlasovaMin = -1;
		int brUkGlasovaMax = -1;
		try{ brPozGlasovaMin = Integer.parseInt(brPozitivnihGlasovaMin); }catch(Exception e){}
		try{ brPozGlasovaMax = Integer.parseInt(brPozitivnihGlasovaMax); }catch(Exception e){}
		try{ brUkGlasovaMin = Integer.parseInt(brUkupnihGlasovaMin); }catch(Exception e){}
		try{ brUkGlasovaMax = Integer.parseInt(brUkupnihGlasovaMax); }catch(Exception e){}
		
		MySparqlQuery query = new MySparqlQuery(type, getValue(oznaka), getValue(naziv), getValue(mesto), 
												getValue(datumMin), getValue(datumMax), getValue(vrsta));
		query.setBrPozitivnihGlasovaMin(brPozGlasovaMin);
		query.setBrPozitivnihGlasovaMax(brPozGlasovaMax);
		query.setBrUkupnihGlasovaMin(brUkGlasovaMin);
		query.setBrUkupnihGlasovaMax(brUkGlasovaMax);
		
		try {
			String result = query.execute(ConnPropertiesReader.loadProperties(), type + "/metadata", true);
			return Response.ok().entity(result).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	private String getValue(String val){
		return "_".equals(val.trim()) ? "" : val.trim();
	}
	
}
