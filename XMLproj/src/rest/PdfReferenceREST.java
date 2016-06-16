package rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import util.ConnPropertiesReader;
import util.XQueryInvoker;
import util.transform.ActXmlToPdf;
import util.transform.AmandmanXmlToPdf;

@Path("/pdfReference")
public class PdfReferenceREST {
	
	@GET
	@Path("/ref/{id:.+}")
	@Produces("application/pdf")
	public Response getDocumentPdf(@PathParam("id") String id){
		String oznaka = id;
		if(id.contains("/")){
			oznaka = id.split("/")[0];
		}
		return getXmlDoc(oznaka);
	}
	
	private Response getXmlDoc(String id){
		String nameSpacePart = "declare namespace p=\"http://www.parlament.gov.rs/propisi\";\n" + 
				   "declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";\n";
		
		HashMap<String, String> collActMap = new HashMap<String, String>();
		collActMap.put("/propisi/akti/doneti", "where $doc/p:Akt/p:Sporedni_deo/p:Donet_akt/p:Meta_podaci/ns1:Oznaka = \"");
		collActMap.put("/propisi/akti/u_proceduri", "where $doc/p:Akt/p:Sporedni_deo/p:Akt_u_proceduri/p:Meta_podaci/ns1:Oznaka = \"");
		
		HashMap<String, String> collAmMap = new HashMap<String, String>();
		collAmMap.put("/propisi/amandmani/odbijeni", "where $doc/p:Amandman/p:Sporedni_deo/p:Meta_podaci/ns1:Oznaka = \"");
		collAmMap.put("/propisi/amandmani/prihvaceni", "where $doc/p:Amandman/p:Sporedni_deo/p:Meta_podaci/ns1:Oznaka = \"");
		
		try{
			for(Entry<String, String> act : collActMap.entrySet()){
				String query = nameSpacePart + makeForPart(act.getKey(), act.getValue() + id + "\"\nreturn ($doc)//p:Akt;");
				System.out.println(query);
				String result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
				if(!"".equals(result)) return makeResponse(result, true);
			}
			for(Entry<String, String> am : collActMap.entrySet()){
				String query = nameSpacePart + makeForPart(am.getKey(), am.getValue() + id + "\"\nreturn ($doc)//p:Amandman;");
				System.out.println(query);
				String result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), query);
				if(!"".equals(result)) return makeResponse(result, false);
			}
		}catch(Exception e){ 
			e.printStackTrace();
			return Response.status(400).build();
		};
		return Response.status(400).build();
	}
	
	private String makeForPart(String collection, String wherePart){
		return "for $doc in fn:collection(\"" + collection + "\")\n" + wherePart;
	}
	
	private Response makeResponse(String xmlDoc, boolean isAct){
		
		InputStream is = new ByteArrayInputStream(xmlDoc.getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length()); 
		
		try{
			if(isAct){
				new ActXmlToPdf(path+"fop.xconf").transform(is, os);
			}else{
				new AmandmanXmlToPdf(path + "fop.xconfig").transform(is, os);
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}
		
		ResponseBuilder builder = Response.ok(os.toByteArray());
		//builder.header("Content-Disposition",
			//	"attachment; filename="+System.currentTimeMillis()+".pdf");
		
		return builder.build();
	}
}
