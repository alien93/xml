package util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

public class XMLToPDF {
	
	public static final String PDF_FILE_TEST = "C:/Users/Nina/Desktop/akt.pdf";

	private FopFactory fopFactory;

	private TransformerFactory transformerFactory;

	public XMLToPDF(String path) throws SAXException, IOException {
		// Initialize FOP factory object
		fopFactory = FopFactory.newInstance(new File(path));
		

		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
	}

	public void transformAkt(InputStream actStream, OutputStream pdfStream) throws Exception {
		InputStream xslStream = getClass().getResourceAsStream("/resources/akt_fo.xsl");

		if (xslStream == null) {
			System.out.println("NIJE UCITAN akt_fo.xsl");
			return;
		}

		StreamSource xslSource = new StreamSource(xslStream);
		StreamSource actSource = new StreamSource(actStream);

		FOUserAgent userAgent = fopFactory.newFOUserAgent();

		ByteArrayOutputStream result = new ByteArrayOutputStream();

		Transformer xslFoTransformer = transformerFactory.newTransformer(xslSource);
		xslFoTransformer.setParameter("encoding", "UTF-8");
		xslFoTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		// Construct FOP instance with desired output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, result);

		// Resulting SAX events
		Result res = new SAXResult(fop.getDefaultHandler());

		// Start XSLT transformation and FOP processing
		xslFoTransformer.transform(actSource, res);
		
		pdfStream.write(result.toByteArray());
	}
	
	public void test(){
		
		InputStream is = getClass().getResourceAsStream("/resources/akt_1.xml");
		
		if(is == null)
		{
			System.out.println("NULLCINA");
			return;
		}
		
		OutputStream os = new ByteArrayOutputStream();
		
		try{
			transformAkt(is, os);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// WRITE TO PDF
		
		File pdfFile = new File(PDF_FILE_TEST);
		
		try {
			OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
			out.write(((ByteArrayOutputStream) os).toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args){
		try {
			XMLToPDF xmlToPDF = new XMLToPDF("src/fop.xconf");
			xmlToPDF.test();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
