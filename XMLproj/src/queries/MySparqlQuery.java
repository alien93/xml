package queries;
/**
 * @author Stanko Kuveljic
 */
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.semantics.SPARQLMimeTypes;
import com.marklogic.client.semantics.SPARQLQueryDefinition;
import com.marklogic.client.semantics.SPARQLQueryManager;

import util.ConnPropertiesReader;
import util.ConnPropertiesReader.ConnectionProperties;


public class MySparqlQuery {
	public static final String AMANDMAN = "amandmani";
	public static final String AKT_U_PROCEDURI = "/propisi/akti/u_proceduri";
	public static final String SVE_AAAAA = "";
	public static final String AKTI = "akti";
	public static final String AKT_DONET = "/propisi/akti/doneti";

	private static final String PROPERTY = "http://www.parlament.gov.rs/propisi/predicate/";

	private String type;
	private String oznaka;
	private String naziv;
	private String mesto;
	private String datum;
	private String vrsta;

	/**
	 * Executes query
	 * @param props - connection properties
	 * @param metadataCollection - metadata collection
	 * @param useFilter - true if you want to filter your results
	 * @return
	 */
	public String execute(ConnectionProperties props, String metadataCollection, boolean useFilter) {
		String query = makeQuery(metadataCollection, useFilter);

		//System.out.println(query);
		
		DatabaseClient client = DatabaseClientFactory.newClient(props.host,
				props.port, props.database, props.user, props.pass,
				props.authType);
		
		// Create a SPARQL query manager to query RDF datasets
		SPARQLQueryManager sparqlQueryManager = client.newSPARQLQueryManager();
		
		JacksonHandle resultsHandle = new JacksonHandle();
		resultsHandle.setMimetype(SPARQLMimeTypes.SPARQL_JSON);
		
		SPARQLQueryDefinition queryDef = sparqlQueryManager.newQueryDefinition(query);
		
		resultsHandle = sparqlQueryManager.executeSelect(queryDef, resultsHandle);

		client.release();
		
		return resultsHandle.get().toString();
	}

	/**
	 * Creates query
	 * @param metadataCollection - metadata collection
	 * @param useFilter - true if you want to filter your results
	 * @return query
	 */
	private String makeQuery(String metadataCollection, boolean useFilter) {
		StringBuilder query = new StringBuilder();

		query.append("SELECT * FROM <" + metadataCollection + ">\n");
		query.append("WHERE{\n");
		query.append(selectTemplate("oznaka"));
		query.append(selectTemplate("naziv"));
		query.append(selectTemplate("datum"));
		query.append(selectTemplate("vrsta"));
		query.append(selectTemplate("mesto"));

		if(useFilter){
			query.append("FILTER (" + regexTemplate("?akt", type) + " && "
					+ regexTemplate("?oznaka", oznaka) + " && "
					+ regexTemplate("?naziv", naziv) + " && "
					+ regexTemplate("?datum", datum) + " && "
					+ regexTemplate("?vrsta", vrsta) + " && "
					+ regexTemplate("?mesto", mesto) + ")\n}");
		}
		else
			query.append("\n}");
		return query.toString();
	}

	/**
	 * 
	 * @param what
	 * @return
	 */
	private String selectTemplate(String what) {
		return "?akt <" + PROPERTY + what + ">?" + what + " .\n";
	}

	/**
	 * 
	 * @param onWhat
	 * @param value
	 * @return
	 */
	private String regexTemplate(String onWhat, String value) {
		return "regex(" + onWhat + ",\".*" + value + ".*\",\"i\")";
	}

	/**
	 * 
	 * @param type
	 */
	public MySparqlQuery(String type) {
		super();
		this.type = type;
		this.oznaka = "";
		this.naziv = "";
		this.mesto = "";
		this.datum = "";
		this.vrsta = "";
	}

	/**
	 * 
	 * @param type
	 * @param oznaka
	 * @param naziv
	 * @param mesto
	 * @param datum
	 * @param vrsta
	 */
	public MySparqlQuery(String type, String oznaka, String naziv, String mesto,
			String datum, String vrsta) {
		super();
		this.type = type;
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.mesto = mesto;
		this.datum = datum;
		this.vrsta = vrsta;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getVrsta() {
		return vrsta;
	}

	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}

	public String getType() {
		return type;
	}

	public static void main(String[] args) {
		String metadataCollection = "/propisi/akti/doneti/metadata";
		boolean useFilter = false;

		
		MySparqlQuery msq = new MySparqlQuery(SVE_AAAAA, "", "", "", "",
				"");
		try {
			System.out.println(msq.execute(ConnPropertiesReader.loadProperties(), metadataCollection, useFilter));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
