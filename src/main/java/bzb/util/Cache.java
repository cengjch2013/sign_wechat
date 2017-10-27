package bzb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {
	
	/**
	 * 用户缓存
	 */
	public static Map<String, Object> user = null;
	
	/**
	 * 班级名称缓存
	 */
	public static List<String> classes = null;
	
	static{
		user = new HashMap<String, Object>();
		
		classes = new ArrayList<String>();
		
	}
	
	

}
