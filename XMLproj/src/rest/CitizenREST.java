package rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import util.ActXmlToPdf;
import util.AmandmanXmlToPdf;
import util.ConnPropertiesReader;
import util.TransformersAutobot;
import util.XMLToPDF;
import util.XQueryInvoker;

@Path("/test")
public class CitizenREST {

	@GET
	@Path("/test")
	public String test() {
		String path = CitizenREST.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		System.out.println(path);
		String document = path + "resources/xquery/getNonActiveActs.xqy";
		try {
			XQueryInvoker.invokeQuery(ConnPropertiesReader.loadProperties(), document);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String confPath = path+"fop.xconf";
		try {
			TransformersAutobot act1 = new ActXmlToPdf(confPath);
			TransformersAutobot aman1 = new AmandmanXmlToPdf(confPath);
			
			act1.test();
			aman1.test();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "test";
	}

}