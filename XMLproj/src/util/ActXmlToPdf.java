package util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.TransformerFactory;

import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

public class ActXmlToPdf extends TransformersXMLToPDF {

	public static final String PDF_FILE_TEST = "C:/Users/Nina/Desktop/akt.pdf";
	public static final String ACT_RESOURCE = "/resources/akt_fo.xsl";
	public static final String TEST_ACT = "/resources/akt_1.xml";

	public ActXmlToPdf(String pathToConf) throws SAXException, IOException {
		// Initialize FOP factory object
		fopFactory = FopFactory.newInstance(new File(pathToConf));

		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
	}

	@Override
	public void transform(InputStream input, OutputStream output)
			throws Exception {

		super.transform(input, output);

	}

	@Override
	public InputStream loadXSL() throws Exception {

		InputStream is = getClass().getResourceAsStream(ACT_RESOURCE);

		if (is == null)
			throw new Exception("Nije pronadjen XSL fajl");

		return is;

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

		// WRITE TO PDF

		File pdfFile = new File(PDF_FILE_TEST);

		try {
			OutputStream out = new BufferedOutputStream(
					new FileOutputStream(pdfFile));
			out.write(((ByteArrayOutputStream) os).toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
