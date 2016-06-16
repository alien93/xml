package util.transform;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ActXmlToHtml extends TransformersXMLToHTML {

	public static final String ACT_RESOURCE = "/resources/akt_html.xsl";
	public static final String TEST_ACT = "/resources/akt_1.xml";
	public static final String HTML_FILE_TEST = "E:/TEST_FILES/akt1.html";
	
	public ActXmlToHtml() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void test() {
		InputStream is = getClass().getResourceAsStream(TEST_ACT);

		if (is == null) {
			System.out.println("NULLCINA");
			return;
		}
		
		OutputStream os = new ByteArrayOutputStream();
		
		try {
			transform(is, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		File htmlFile = new File(HTML_FILE_TEST);

		try {
			FileOutputStream fos = new FileOutputStream(htmlFile);
			fos.write(((ByteArrayOutputStream) os).toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public InputStream loadXSL() throws Exception {
		InputStream is = getClass().getResourceAsStream(ACT_RESOURCE);

		if (is == null)
			throw new Exception("Nije pronadjen XSL fajl");

		return is;
	}
	
	public static void main(String[] args){
		new ActXmlToHtml().test();
	}

}
