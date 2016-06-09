package rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


import util.ConnPropertiesReader;
import util.XQueryInvoker;

@Path("/test")
public class CitizenREST {

	@GET
	@Path("/test")
	public String test(){
		String path = CitizenREST.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		System.out.println(path);
		String document = path + "resources/xquery/getNonActiveActs.xqy";
		try {
			XQueryInvoker.invokeQuery(ConnPropertiesReader.loadProperties(), document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "test";
	}
	
}
//C:\Users\Nina\Documents\nina\XML i Web servisi\XML i Web servisi\apache-tomcat-8.0.35\wtpwebapps\XMLproj\WEB-INF\classes\resources
//C:\Users\Nina\Documents\nina\XML%20i%20Web%20servisi\XML%20i%20Web%20servisi\apache-tomcat-8.0.35\wtpwebapps\XMLproj\WEB-INF\classes\resources\xquery\getNonActiveActs.xqy
