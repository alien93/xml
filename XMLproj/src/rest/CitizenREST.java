package rest;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import entities.act.Akt;
import queries.MySparqlQuery;
import util.ActXmlToPdf;
import util.AmandmanXmlToPdf;
import util.ConnPropertiesReader;
import util.TransformersAutobot;
import util.XQueryInvoker;

@Path("/test")
public class CitizenREST {

	@GET
	@Path("/test")
	public String test() {
		String path = CitizenREST.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		System.out.println(path);
		String document = path + "resources/xquery/getNonActiveActs.xqy";
		try {
			XQueryInvoker.invokeQuery(ConnPropertiesReader.loadProperties(), document);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String confPath = path+"fop.xconf";
		try {
			TransformersAutobot act1 = new ActXmlToPdf(confPath);
			TransformersAutobot aman1 = new AmandmanXmlToPdf(confPath);
			
			act1.test();
			aman1.test();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "test";
	}
	
	@POST
	@Path("/test/xml")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addAkt(Akt akt){
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
		try {
			InputStream is = getClass().getResourceAsStream("/resources/akt.xsd");
			if(is == null){
				System.out.println("NULL");
				return Response.status(404).build();
			}
			StreamSource schemaTypes = new StreamSource(getClass().getResourceAsStream("/resources/generic_types.xsd"));
			StreamSource schemaAkt = new StreamSource(getClass().getResourceAsStream("/resources/akt.xsd"));
			
			Source[] schemas = {schemaTypes,schemaAkt};
			
			Schema schema = sf.newSchema(schemas);
			
			JAXBContext context = JAXBContext.newInstance(Akt.class);
			
			Marshaller marsh = context.createMarshaller();
			marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marsh.setSchema(schema);
	        marsh.marshal(akt,System.out);


	        
		} catch (SAXException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(404).entity("NESTO NIJE U REDU").build();
		}
        
		
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("/test/pdf/{id}")
	@Produces("application/pdf")
	public Response getPdf(@PathParam("id") String id) throws Exception{
		
		
		//input stream ce biti dobijeni dokument iz baze
		InputStream is = getClass().getResourceAsStream("/resources/akt_1.xml");
		
		
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		String path = CitizenREST.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length()); 
		TransformersAutobot t = new ActXmlToPdf(path+"fop.xconf");
		
		t.transform(is, os);
		
		
		
		ResponseBuilder builder = Response.ok(os.toByteArray());
		builder.header("Content-Disposition",
				"attachment; filename="+id+".pdf");
		
		return builder.build();
		
	}
	
	@GET
	@Path("/test/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(){
		
		MySparqlQuery q = new MySparqlQuery(MySparqlQuery.AKT_DONET);
		String metadataCollection = "/propisi/akti/doneti/metadata";
		ResponseBuilder response = Response.ok();
		try {
			return response.status(200).entity(q.execute(ConnPropertiesReader.loadProperties(), metadataCollection, false)).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return response.status(400).build();
		}
		
	}

}