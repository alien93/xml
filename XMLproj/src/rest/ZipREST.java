package rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import util.ZipHelper;

@Path("/zip")
public class ZipREST {
	
	@GET
	@Path("/getFiles")
	@Produces("application/zip")
	public Response getFiles(){
		/*
		File folderToZip = new File(getClass().getResource("/resources/zip").getPath());
		ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(zipOut);
		byte[] buffer = new byte[1024];
		
		try{
			for(File f : folderToZip.listFiles()){
				out.putNextEntry(new ZipEntry(f.getName()));
				FileInputStream in = new FileInputStream(f);
				int len;
	        	while ((len = in.read(buffer)) > 0) {
	        		out.write(buffer, 0, len);
	        	}
	        	in.close();
			}
			out.closeEntry();
			out.close();	
					
			return Response.ok(zipOut.toByteArray()).
					header("Content-Disposition", 
					"attachment; filename="+System.currentTimeMillis()+".zip").build();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}*/
		
		return Response.ok(new ZipHelper().getAllZipFiles()).
				header("Content-Disposition", 
				"attachment; filename="+System.currentTimeMillis()+".zip").build();
	}
}
