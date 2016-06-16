package util.transform;

import java.io.InputStream;
import java.io.OutputStream;

public interface TransformersAutobot {
	
	public void transform(InputStream input, OutputStream output) throws Exception;
	
	public void test();
}
