package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/advanced")
public class AdvancedSearchREST {
	
	@GET
	@Path("/search/{oznaka}/{naziv}/{vrsta}/{datumMin}/{datumMax}/{mesto}"
			+ "/{brPozitivnihGlasovaMin}/{brPozitivnihGlasovaMax}/{brUkupnihGlasovaMin}/{brUkupnihGlasovaMax}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("oznaka") String oznaka,
						   @PathParam("naziv") String naziv,
						   @PathParam("vrsta") String vrsta,
						   @PathParam("datumMin") String datumMin,
						   @PathParam("datumMax") String datumMax,
						   @PathParam("mesto") String mesto,
						   @PathParam("brPozitivnihGlasovaMin") int brPozitivnihGlasovaMin,
						   @PathParam("brPozitivnihGlasovaMax") int brPozitivnihGlasovaMax,
						   @PathParam("brUkupnihGlasovaMin") int brUkupnihGlasovaMin,
						   @PathParam("brUkupnihGlasovaMax") int brUkupnihGlasovaMax){
		return null;
	}
}
