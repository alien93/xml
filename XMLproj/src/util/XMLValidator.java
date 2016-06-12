package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.ws.rs.core.Response;
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
import entities.amendment.Amandman;
import rest.CitizenREST;


public class XMLValidator {
	
	private static XMLValidator instance = null;
	
	private XMLValidator(){
		
	}
	
	public static XMLValidator getInstance(){
		if(instance == null)
			instance = new XMLValidator();
	    return instance;
	}

	public Response validateAct(Akt akt, String path){
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try{
			InputStream is = getClass().getResourceAsStream("/resources/akt.xsd");
			if(is == null){
				System.out.println("Schema does not exist");
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
	        try {
				marsh.marshal(akt, new FileOutputStream(path));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}catch(JAXBException | SAXException e){
			e.printStackTrace();
			return Response.status(404).build();
		}
		return Response.status(200).entity("OK").build();
	}
	
	public Response validateAmendment(Amandman amandman){
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try{
			InputStream is = getClass().getResourceAsStream("/resources/amandman.xsd");
			if(is == null){
				System.out.println("Schema does not exist");
				return Response.status(404).build();
			}
			StreamSource schemaTypes = new StreamSource(getClass().getResourceAsStream("/resources/generic_types.xsd"));
			StreamSource schemaAkt = new StreamSource(getClass().getResourceAsStream("/resources/amandman.xsd"));
			
			Source[] schemas = {schemaTypes,schemaAkt};
			
			Schema schema = sf.newSchema(schemas);
			
			JAXBContext context = JAXBContext.newInstance(Akt.class);
			
			Marshaller marsh = context.createMarshaller();
			marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marsh.setSchema(schema);
	        marsh.marshal(amandman, System.out);
		}catch(JAXBException | SAXException e){
			e.printStackTrace();
			return Response.status(404).build();
		}
		return Response.status(200).entity("OK").build();
	}
}
