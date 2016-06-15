package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import entities.act.Akt;

@Path("/ha")
public class HistoricalArchiveREST {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_XML)
	public void addToHistoricalArchive(Akt akt){
		//TODO
	}
	
}
