package bzb.util;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties prop = new Properties();
	static{
		try {
			prop.load(new FileReader(ClassLoader.getSystemResource("application.properties").getPath()));
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
	/*
	 * 将配置数据写入文件
	 */
	public static void saveProperty(String key, String value){
		try {
			FileOutputStream out = new FileOutputStream(ClassLoader.getSystemResource("application.properties").getPath(), false);
			Writer writer = new OutputStreamWriter(out, "utf-8");
			prop.setProperty(key, value);
			prop.store(writer, "");
			writer.flush();
			out.close();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static Map<String, Object> list(){
		Map<String, Object> map = new HashMap<String, Object>();
		for(Object key : prop.keySet()){
			map.put(key.toString(), prop.getProperty(key.toString()));
		}		
		return map;
	}
}
