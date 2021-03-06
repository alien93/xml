package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;

import util.ConnPropertiesReader.ConnectionProperties;

public class XQueryInvoker {

	private static DatabaseClient client;	

	public static void invokeQuery(ConnectionProperties props, String queryDoc) throws IOException{
		// Initialize the database client
		
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.pass, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.pass, props.authType);
		}

		// Script file which is to be invoked
		String filePath = queryDoc;

		
		System.out.println("[INFO] Invoking: " + filePath);

		// Initialize XQuery invoker object
		ServerEvaluationCall invoker = client.newServerEval();

		// Read the file contents into a string object
		String query = readFile(filePath, StandardCharsets.UTF_8);

		// Invoke the query
		invoker.xquery(query);

		// Interpret the results
		EvalResultIterator response = invoker.eval();

		System.out.print("[INFO] Response: ");

		if (response.hasNext()) {

			for (EvalResult result : response) {
				System.out.println("\n" + result.getString());
			}
		} else { 		
			System.out.println("your query returned an empty sequence.");
		}

		client.release();
	}
	
	/**
	 * 
	 * @param props 
	 * @param query - kao String
	 * @return - XML dokument kao String
	 */
	public static synchronized String invoke(ConnectionProperties props, String query){
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.pass, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.pass, props.authType);
		}
		ServerEvaluationCall invoker = client.newServerEval();
		invoker.xquery(query);
		EvalResultIterator response = invoker.eval();
		String retVal = "";
		if (response.hasNext()) {
			for (EvalResult result : response) {
				retVal += result.getString() + "\n";
			}
		} else { 		
			System.out.println("your query returned an empty sequence.");
		}
		client.release();
		return retVal;
	}
	
	/**
	 * Reading file contents into a string.
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void main(String[] args) throws IOException{
		String document = "/getNonActiveActs.xqy";
		invokeQuery(ConnPropertiesReader.loadProperties(), document);
	}

}
