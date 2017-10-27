package bzb.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties prop = new Properties();
	static{
		try {
			InputStream in = new BufferedInputStream (ClassLoader.getSystemResource("application.properties").openStream());
			prop.load(in);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return prop.getProperty(key);
	}
	
	public static void setProperty(String key, String value){
		prop.setProperty(key, value);
	}
}
