package util.transform;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

public class AmandmanXmlToPdf extends TransformersXMLToPDF {

	public static final String PDF_FILE_TEST = "E:/TEST_FILES/amandman.pdf";
	public static final String AMANDMAN_RESOURCE = "/resources/amandman_fo.xsl";
	public static final String TEST_AMANDMAN = "/resources/amandman_1.xml";
	
	public AmandmanXmlToPdf(String pathToConf) throws SAXException, IOException {
		// Initialize FOP factory object
		fopFactory = FopFactory.newInstance(new File(pathToConf));

		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
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

	@Override
	public InputStream loadXSL() throws Exception {
		InputStream is = getClass().getResourceAsStream(AMANDMAN_RESOURCE);
		if(is == null)
			throw new Exception("Nije pronasao amandman.xsl");
		return is;
	}

}
