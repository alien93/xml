package util;
/**
 * Ucitava parametre za konektovanje na bazu iz connection.properties
 * @author nina
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import com.marklogic.client.DatabaseClientFactory.Authentication;

public class ConnPropertiesReader {

	public static synchronized ConnectionProperties loadProperties() throws IOException{
		ConnectionProperties cp = null;

		String propsName = "connection.properties";
		
		InputStream propsStream = openStream(propsName);
		if(propsStream == null)
			throw new IOException("Could not read properties " + propsName);

		Properties props = new Properties();
		props.load(propsStream);
		cp = new ConnectionProperties(props);
		return cp;
	}
	
	public static synchronized InputStream openStream(String filename) throws IOException{
		return ConnPropertiesReader.class.getClassLoader().getResourceAsStream(filename);
	}
		
	static public class ConnectionProperties {
		public String host;
		public int port = -1;
		public String user;
		public String pass;
		public String database;
		public Authentication authType;
	
		public ConnectionProperties(Properties props) {
			super();
			host = props.getProperty("conn.host").trim();
			port = Integer.parseInt(props.getProperty("conn.port"));
			user = props.getProperty("conn.user").trim();
			pass = props.getProperty("conn.pass").trim();
			database = props.getProperty("conn.database").trim();
			authType = Authentication.valueOf(props.getProperty("conn.authentication_type").toUpperCase().trim());
		}
	}

	
	
}
