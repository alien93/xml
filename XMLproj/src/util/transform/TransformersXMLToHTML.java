package util.transform;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.hibernate.dialect.ResultColumnReferenceStrategy;

public abstract class TransformersXMLToHTML implements TransformersAutobot {

	public TransformersXMLToHTML() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract InputStream loadXSL() throws Exception;

	@Override
	public void transform(InputStream input, OutputStream output)
			throws Exception {
		
		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(loadXSL());
        Transformer transformer = factory.newTransformer(xslt);

        transformer.setParameter("encoding", "UTF-8");
        
        Source text = new StreamSource(input);
        StreamResult result = new StreamResult(output);
        transformer.transform(text, result);

	}


}
