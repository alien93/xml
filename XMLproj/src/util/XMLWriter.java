package util;
/**
 * @author nina
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;

import util.ConnPropertiesReader.ConnectionProperties;

public class XMLWriter {

	private static DatabaseClient client;
	
	
	/**
	 * Writes xml file to database
	 * 
	 * @param props - connection properties
	 * @param inputFile - xml file to be written
	 * @param docId - document id (example: /test/test.xml or /test/ in case you want the file name to be generated
	 * @param collId - collection id
	 * @param generateUri - true if you want uri to be automatically generated, otherwise false
	 * @throws FileNotFoundException
	 */
	public static void writeXML(ConnectionProperties props, String inputFile, String docId, String collId, boolean generateUri) throws FileNotFoundException{
	
		// Initialize the database client
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.pass, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.pass, props.authType);
		}
		
		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		DocumentUriTemplate template = null;			
		
		if(generateUri){
			template = xmlManager.newDocumentUriTemplate("xml");
			template.setDirectory(docId);
		}
			
		// Create an input stream handle to hold XML content.
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(inputFile));
			
		// A collection URI serves as an identifier.
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(collId);
		
		if(generateUri){
			xmlManager.create(template, metadata, handle);
		}
		else{
			// Write the document to the database
			xmlManager.write(docId, metadata, handle);
		}
		
		// Release the client
		client.release();
	}
	
	//test
	public static void main(String[] args) throws IOException {
		System.out.println("Starting...");
		// Define a URI value for a document.
		String inputFile = "./src/resources/amandman_prihvacen3.xml";
		//String inputFile = "data/rdfa/RS26-16-lat_primer_donetog_akta1.xml";
		String docId = "amandman_prihvacen3.xml";	//document id
		String collId = "/propisi/amandmani/prihvaceni";			//collection
		writeXML(ConnPropertiesReader.loadProperties(), inputFile, docId, collId, false);
	}
}
