package util;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;

import util.ConnPropertiesReader.ConnectionProperties;


/**
 * @author nina
 */
public class XMLUpdate {

	private enum UpdatePositions{
		BEFORE,
		AFTER,
		LAST_CHILD
	}

	private static DatabaseClient client;

	/**
	 * Insert xml patch to existing xml document
	 * 
	 * @param props - connection properties
	 * @param docId - document id
	 * @param namespaces - namespaces
	 * @param patch - data to be added
	 * @param contextXPath - context path
	 * @param position - add before, after or as a last child
	 * @throws FileNotFoundException
	 */
	public static void updateXMLInsert(ConnectionProperties props, String docId, EditableNamespaceContext namespaces, String patch, String contextXPath, UpdatePositions position) throws FileNotFoundException {

		// Initialize the database client
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.pass, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.pass, props.authType);
		}

		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();

		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		// Insert fragments
		switch(position){
		case BEFORE:{
			patchBuilder.insertFragment(contextXPath, Position.BEFORE, patch);
			break;
		}
		case AFTER:{
			patchBuilder.insertFragment(contextXPath, Position.AFTER, patch);
			break;
		}
		case LAST_CHILD:{
			patchBuilder.insertFragment(contextXPath, Position.LAST_CHILD, patch);
			break;
		}
		}

		DocumentPatchHandle patchHandle = patchBuilder.build();

		//inserting
		xmlManager.patch(docId, patchHandle);

		// Release the client
		client.release();
	}

	/**
	 * Replace existing xml part with another
	 * 
	 * @param props - connection properties
	 * @param docId - document id
	 * @param namespaces - namespaces
	 * @param patch - patch to be used as a replacemet
	 * @param contextXPath - context xPath
	 * @throws FileNotFoundException
	 */
	public static void updateXMLReplace(ConnectionProperties props, String docId, EditableNamespaceContext namespaces, String patch, String contextXPath) throws FileNotFoundException {

		// Initialize the database client
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.pass, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.pass, props.authType);
		}

		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();

		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		// Replace fragment
		patchBuilder.replaceFragment(contextXPath, patch);

		DocumentPatchHandle patchHandle = patchBuilder.build();

		xmlManager.patch(docId, patchHandle);

		// Release the client
		client.release();
	}
	
	/**
	 * Remove part/whole xml document
	 * 
	 * @param props - connection properties 
	 * @param docId - document id
	 * @param namespaces - namespaces
	 * @param contextXPath - context xPath pointing to the part of an xml document that will be removed
	 * @throws FileNotFoundException
	 */
	public static void updateXMLRemove(ConnectionProperties props, String docId, EditableNamespaceContext namespaces, String contextXPath) throws FileNotFoundException {
		// Initialize the database client
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.pass, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.pass, props.authType);
		}

		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();

		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		// Replace fragment
		patchBuilder.delete(contextXPath);

		DocumentPatchHandle patchHandle = patchBuilder.build();

		xmlManager.patch(docId, patchHandle);

		// Release the client
		client.release();
	}

	//test
	public static void main(String[] args) throws IOException {
		// Define a URI value for a document.
		String docId = "/test/sl-011.xml";
		// Defining namespace mappings
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		
		namespaces.put("p", "http://www.parlament.gov.rs/propisi");
		namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

		// Creating an XML patch
		String patch = "<p:Clan oznaka=\"cl3\">" + 
					"<p:Sadrzaj>"+
                    "Овим правилником ближе се уређују начин и поступак" +
                    "доделе средстава из буџета Града Новог Сада (у даљем" +
                    "тексту: буџет Града) за одобрење годишњих и посебних..." +
                    "</p:Sadrzaj>" +
					"</p:Clan>";

		// Defining XPath context
		String contextXPath = "/p:Akt/p:Glavni_deo/p:Glava";

		updateXMLInsert(ConnPropertiesReader.loadProperties(), docId, namespaces, patch, contextXPath, UpdatePositions.LAST_CHILD);
	}
}

