package util.transform;

import java.io.ByteArrayOutputStream;
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

public abstract class TransformersXMLToPDF implements TransformersAutobot {

	protected FopFactory fopFactory;

	protected TransformerFactory transformerFactory;
	
	public abstract InputStream loadXSL() throws Exception;

	@Override
	public void transform(InputStream xmlStream, OutputStream pdfStream) throws Exception {

		StreamSource xslSource = new StreamSource(loadXSL());
		StreamSource actSource = new StreamSource(xmlStream);

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
}
