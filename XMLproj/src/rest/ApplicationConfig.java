package rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application{
	 public Set<Class<?>> getClasses() {
	        return new HashSet<Class<?>>(Arrays.asList(
	        											CitizenREST.class, 
	        											UserLoginREST.class, 
	        											ActREST.class,
	        											ActFilterREST.class,
	        											SearchTextContentREST.class,
	        											PdfReferenceREST.class));
	    }
}
