package rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import queries.MySparqlQuery;
import util.ConnPropertiesReader;
import util.XQueryInvoker;

@Path("/searchText")
public class SearchTextContentREST {
	
	@GET
	@Path("/active/{text}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchActiveActs(@PathParam("text") String text){
		String collection = "/propisi/akti/doneti";
		System.out.println("*****" + text + "******");
		String result = helpQuery(text, collection, MySparqlQuery.AKT_DONET);
		System.out.println(result);
		return Response.ok().entity(result).build();
	}
	
	@GET
	@Path("/nonActive/{text}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchNonActiveActs(@PathParam("text") String text){
		String collection = "/propisi/akti/u_proceduri";
		String result = helpQuery(text, collection, MySparqlQuery.AKT_U_PROCEDURI);
		return Response.ok().entity(result).build();
	}
	
	private String helpQuery(String text, String collection, String vrsta){
		String retStr = vrsta.equals(MySparqlQuery.AKT_U_PROCEDURI) ? 
						"$doc/p:Akt/p:Sporedni_deo/p:Akt_u_proceduri/p:Meta_podaci/ns1:Oznaka" :
						"$doc/p:Akt/p:Sporedni_deo/p:Donet_akt/p:Meta_podaci/ns1:Oznaka";
		
		String query = "declare namespace p=\"http://www.parlament.gov.rs/propisi\";" + 
					   "declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";" + 
					   "for $doc in fn:collection(\"" + collection + "\")" + 
					   "where  $doc//*[text()[contains(.,'" + text + "')]]" + 
					   "return " + retStr;
		try {
			String result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
			List<String> oznake = new ArrayList<String>();
			for(String oznaka : result.split("\n")){
				String[] splitOznaka = oznaka.split(">");
				oznake.add(splitOznaka[splitOznaka.length - 1].split("<")[0]); //Oznake dokumenata koji zadovoljavaju kriterijum pretrage
			}
			
			String acts = new MySparqlQuery(vrsta).execute(ConnPropertiesReader.loadProperties(),
					collection + "/metadata", false);
			JsonObject object = new JsonParser().parse(acts).getAsJsonObject();
			JsonArray array = object.getAsJsonObject("results").getAsJsonArray("bindings");
			List<String> actsResult = new ArrayList<String>();
			for(String o : oznake){
				for(int i = 0; i < array.size(); i++){
					String oznaka = array.get(i).getAsJsonObject().get("oznaka").getAsJsonObject().get("value").getAsString();
					if(o.equals(oznaka)){
						System.out.println(array.get(i));
						actsResult.add(array.get(i).getAsJsonObject().toString());
					}
				}
			}
			String newBindins = "[";
			for(String a : actsResult){
				newBindins += a + ",";
			}
			if(newBindins.length() > 1) newBindins = newBindins.substring(0,  newBindins.length() - 1);
			newBindins += "]";
			if(!"_".equals(text.trim())){
				object.getAsJsonObject("results").remove("bindings");
				object.getAsJsonObject("results").add("bindings", new JsonParser().parse(newBindins).getAsJsonArray());
			}
			//System.out.println(object.toString());
			return object.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
