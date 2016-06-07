package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/citizen")
public class CitizenREST {

	@GET
	@Path("/test")
	public String test(){
		return "test";
	}
	
}
