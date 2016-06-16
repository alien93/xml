package util.transform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AmandmanXmlToHtml extends TransformersXMLToHTML {

	public static final String HTML_FILE_TEST = "E:/TEST_FILES/amandman1.html";
	public static final String AMANDMAN_RESOURCE = "/resources/amandman_html.xsl";
	public static final String TEST_AMANDMAN = "/resources/amandman_u_proceduri1.xml";
	
	public AmandmanXmlToHtml() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void test() {
		InputStream is = getClass().getResourceAsStream(TEST_AMANDMAN);

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
		InputStream is = getClass().getResourceAsStream(AMANDMAN_RESOURCE);

		if (is == null)
			throw new Exception("Nije pronadjen XSL fajl");

		return is;
	}
	
	public static void main(String[] args){
		new AmandmanXmlToHtml().test();
	}

}
