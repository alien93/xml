package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.BinaryDocumentManager;
import com.marklogic.client.io.BytesHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;

import queries.MySparqlQuery;
import util.ConnPropertiesReader.ConnectionProperties;
import util.transform.ActXmlToHtml;
import util.transform.ActXmlToPdf;
import util.transform.AmandmanXmlToHtml;
import util.transform.AmandmanXmlToPdf;

public class ZipHelper {
	
	public static final String AKTI_DONETI = "/propisi/akti/doneti";
	public static final String AKTI_U_PROCEDURI = "/propisi/akti/u_proceduri";
	public static final String AMANDMANI_U_PROCEDURI = "/propisi/amandmani/u_proceduri";
	
	private static final String AKTI_DONETI_PDF = "/propisi/akti/doneti_pdf";
	private static final String AKTI_DONETI_HTML = "/propisi/akti/doneti_html";
	private static final String AKTI_U_PROCEDURI_PDF = "/propisi/akti/u_proceduri_pdf";
	private static final String AKTI_U_PROCEDURI_HTML = "/propisi/akti/u_proceduri_html";
	private static final String AMANDMANI_U_PRODECURI_PDF = "/propisi/akti/amandmani_u_proceduri_pdf";
	private static final String AMANDMANI_U_PRODECURI_HTML = "/propisi/akti/amandmani_u_proceduri_html";
	
	private String collection;
	private String docId;
	//private DatabaseClient client;
	private String docIdPDF;
	private String docIdHTML;
	
	public ZipHelper(){}
	
	public ZipHelper(String collection, String docId){
		this.collection = collection;
		this.docId = docId;
		transformDocId();
		try {
			initClient(ConnPropertiesReader.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private DatabaseClient initClient(ConnectionProperties props){
		if (props.database.equals("")) {
			DatabaseClient client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.pass, props.authType);
			return client;
		} else {
			DatabaseClient client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.pass, props.authType);
			return client;
		}
	}	
	private void transformDocId(){
		docIdPDF = docId + "_pdf";
		docIdHTML = docId + "_html";
	}
	
	public void transform(){
		byte[] byteDoc = getDoc(); 
		if(byteDoc == null) return;
		InputStream is = new ByteArrayInputStream(byteDoc);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length()) + "fop.xconf"; 
		//path = "D:/4. godina/8_Xml i web servisi/Xml/Projekat/xml/XMLproj/src/fop.xconf";
		try{
			if(AMANDMANI_U_PROCEDURI.equals(collection)){
				new AmandmanXmlToPdf(path).transform(is, os);
				writeDoc(os.toByteArray(), AMANDMANI_U_PRODECURI_PDF, true);
				is = new ByteArrayInputStream(getDoc());
				os = new ByteArrayOutputStream();
				new AmandmanXmlToHtml().transform(is, os);
				writeDoc(os.toByteArray(), AMANDMANI_U_PRODECURI_HTML, false);
			}else{
				if(collection.equals(AKTI_DONETI)){
					new ActXmlToPdf(path).transform(is, os);
					writeDoc(os.toByteArray(), AKTI_DONETI_PDF, true);
					is = new ByteArrayInputStream(getDoc());
					os = new ByteArrayOutputStream();
					new ActXmlToHtml().transform(is, os);
					writeDoc(os.toByteArray(), AKTI_DONETI_HTML, false);
				}
				else{
					new ActXmlToPdf(path).transform(is, os);
					writeDoc(os.toByteArray(), AKTI_U_PROCEDURI_PDF, true);
					is = new ByteArrayInputStream(getDoc());
					os = new ByteArrayOutputStream();
					new ActXmlToHtml().transform(is, os);
					writeDoc(os.toByteArray(), AKTI_U_PROCEDURI_HTML, false);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void delete(){
		deleteDoc(collection + "_pdf", docIdPDF);
		deleteDoc(collection + "_html", docIdHTML);
	}
	
	private byte[] getDoc(){
		try {
			String result = XQueryInvoker.invoke(ConnPropertiesReader.loadProperties(), getQuery());
			if("".equals(result)) return null;
			return result.getBytes(StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getQuery(){
		String forPart = "for $doc in fn:collection(\"" + collection + "\")\n";
		String wherePart = "where $doc/p:Akt/p:Sporedni_deo/p:Donet_akt/p:Meta_podaci/ns1:Oznaka = \"";
		if(collection.equals(AKTI_U_PROCEDURI)) 
			wherePart = "where $doc/p:Akt/p:Sporedni_deo/p:Akt_u_proceduri/p:Meta_podaci/ns1:Oznaka = \"";
		if(collection.equals(AMANDMANI_U_PROCEDURI)) 
			wherePart = "where $doc/p:Amandman/p:Sporedni_deo/p:Meta_podaci/ns1:Oznaka = \"";
		
		String query = 	"declare namespace p=\"http://www.parlament.gov.rs/propisi\";\n" +
				"declare namespace ns1=\"http://www.parlament.gov.rs/generic_types\";\n"+ forPart + wherePart + docId +"\""+
				"\nreturn $doc";
		System.out.println(query);
		return query;
	}
	
	private void writeDoc(byte[] docToWrite, String collId, boolean pdf){
		DatabaseClient client = null;
		try {
			client = initClient(ConnPropertiesReader.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String id = pdf ? docIdPDF : docIdHTML;
		if(docExists(collId, id)) deleteDoc(collId, id);
		
		BinaryDocumentManager docMgr = client.newBinaryDocumentManager();
		InputStreamHandle handle = new InputStreamHandle(new ByteArrayInputStream(docToWrite));
		
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(collId);
		docMgr.write(id, metadata, handle);
		
		client.release();
	}
	
	public void deleteDoc(String collection, String docId){
		DatabaseClient client = null;
		try {
			client = initClient(ConnPropertiesReader.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BinaryDocumentManager docMgr = client.newBinaryDocumentManager();
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(collection);
		docMgr.delete(docId);
		client.release();
	}
	
	/**
	 * Provjera da li dokument vec postoji
	 * @return
	 */
	private boolean docExists(String collection, String docId){
		DatabaseClient client = null;
		try {
			client = initClient(ConnPropertiesReader.loadProperties());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BinaryDocumentManager docMgr = client.newBinaryDocumentManager();
		BytesHandle content = new BytesHandle();
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(collection);
		try{
			docMgr.read(docId, metadata, content);
			client.release();
			return true;
		}catch(Exception e){
			client.release();
			return false;
		}
	}
	
	
	public byte[] getAllZipFiles(){
		String[] collections = { AKTI_DONETI, AKTI_U_PROCEDURI, AMANDMANI_U_PROCEDURI };
		String[] types = { "pdf", "html" };
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ZipOutputStream root = new ZipOutputStream(out);
		byte[] buffer = new byte[1024];
		
		try{
			for(String coll : collections){
				List<String> id = getId(coll);
				for(String oznaka : id){
					for(String t : types){
						if(!docExists(coll + "_" + t, oznaka + "_" + t)) continue;
						root.putNextEntry(new ZipEntry(getFolderName(coll) + "/" + t + "/" + oznaka + "_" + t + "." + t));
						InputStream in = new ByteArrayInputStream(getByteDoc(coll + "_" + t, oznaka + "_" + t));
						int len;
			        	while ((len = in.read(buffer)) > 0) {
			        		root.write(buffer, 0, len);
			        	}
			        	root.closeEntry();
					}
				}
			}
			root.close();
			return out.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private String getFolderName(String collection){
		switch (collection) {
			case AKTI_DONETI:
				return "AKTI_DONETI";
			case AKTI_U_PROCEDURI:
				return "AKTI_U_PROCEDURI";
			case AMANDMANI_U_PROCEDURI:
				return "AMANDMANI_U_PROCEDURI";
			default:
				return "_";
		}
	}
	
	/**
	 * 
	 * @param collection
	 * @return Vraca sve oznake dokumenata iz neke kolekcije 
	 */
	private List<String> getId(String collection){
		List<String> ret = new ArrayList<String>();
		try {
			String result = new MySparqlQuery(collection).
					execute(ConnPropertiesReader.loadProperties(), collection + "/metadata", false);
			JsonObject object = new JsonParser().parse(result).getAsJsonObject();
			JsonArray array = object.getAsJsonObject("results").getAsJsonArray("bindings");
			for(int i = 0; i < array.size(); i++){
				String oznaka = array.get(i).getAsJsonObject().get("oznaka").getAsJsonObject().get("value").getAsString();
				ret.add(oznaka);
			}
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return ret;
		}
	}
	
	private byte[] getByteDoc(String coll, String id){
		DatabaseClient client = null;
		try {
			client = initClient(ConnPropertiesReader.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BinaryDocumentManager docMgr = client.newBinaryDocumentManager();
		BytesHandle content = new BytesHandle();
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(coll);
		docMgr.read(id, metadata, content);
		byte[] contentBytes = content.get();
		client.release();
		return contentBytes;
	}
	
	public void test(){
		DatabaseClient client = null;
		try {
			client = initClient(ConnPropertiesReader.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BinaryDocumentManager docMgr = client.newBinaryDocumentManager();
		BytesHandle content = new BytesHandle();
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(AKTI_U_PROCEDURI_PDF);
		docMgr.read("am1_pdf", metadata, content);

		byte[] contentBytes = content.get();
		
		System.out.println(contentBytes.length);
	}
	
	public void init(){
		String in = ">>>>>>>>>>>>>>>>>>>\n";
		for(String oznaka : getId(collection)){
			this.setDocId(oznaka);
			this.transform();
			in += oznaka + "  ";
		}
		System.out.println(in + "\n<<<<<<<<<<<<<<<<<<<<");;
	}
	
	public void setDocId(String newId){
		this.docId = newId;
		transformDocId();
	}
	
	public static void main(String[] args) {
		
		
		for(String col : new String[] { AKTI_DONETI, AKTI_U_PROCEDURI, AMANDMANI_U_PROCEDURI }){
			new ZipHelper(col, "").init();
		}
		String[] akti_doneti = {"111111", "doc1", "doc2", "doc3" };
		String[] akti_procedura = {"1111", "2222", "3333", "4444", "6666"};
		String[] amandmani = {"am1", "am2", "am3", "am4", "am5"};
		
		//new ZipHelper(AMANDMANI_U_PROCEDURI, "am1").transform();
		/*new ZipHelper(AMANDMANI_U_PROCEDURI, "am2").transform();
		new ZipHelper(AMANDMANI_U_PROCEDURI, "am3").transform();
		new ZipHelper(AMANDMANI_U_PROCEDURI, "am4").transform();*/
		//new ZipHelper(AKTI_DONETI, "doc2").transform();
		//new ZipHelper(AKTI_DONETI, "doc1").transform();
		//new ZipHelper(AKTI_U_PROCEDURI, "2222").transform();
		//new ZipHelper(AKTI_U_PROCEDURI, "1111").transform();
		
		/*new ZipHelper("", "").deleteDoc("","am2_pdf");
		new ZipHelper("", "").deleteDoc("","am4_pdf");
		new ZipHelper("", "").deleteDoc("","1111_pdf");
		new ZipHelper("", "").deleteDoc("","2222_pdf");
		new ZipHelper("", "").deleteDoc("","doc1_pdf");
		new ZipHelper("", "").deleteDoc("","doc2_pdf");*/
		//new ZipHelper("", "").test();
		new ZipHelper("", "").getAllZipFiles();
		byte[] res = new ZipHelper("", "").getAllZipFiles();
		File f = new File("D:/4. godina/8_Agentske tehnologije/test.zip");
		
		try {
			FileOutputStream fw = new FileOutputStream(f);
			fw.write(res);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(res.length);
	}
}
