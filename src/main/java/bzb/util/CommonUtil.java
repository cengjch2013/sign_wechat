package bzb.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bzb.Application;

public class CommonUtil {
	
	private static Logger logger = LogManager.getLogger(Application.class);
	
	public static String genUUID(){
		String str = new String(System.currentTimeMillis() + "");
		return str;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> resultToModel(ResultSet resultSet, Class<?> clazz) {
		List<T> returnList = new ArrayList<T>();
		if (resultSet != null) {
			//根据class对象，获取所有属性
			Field[] fileds = clazz.getDeclaredFields();
			Map<String, Field> map = new HashMap<String, Field>();
			for (Field field : fileds) {
				map.put(field.getName(), field);
			}
			
			try {
				while (resultSet.next()) {
					Object o = clazz.newInstance();
					ResultSetMetaData metaData = resultSet.getMetaData();
					int cols=metaData.getColumnCount();
					for(int i = 1;i<= cols;i++){
						String colName = metaData.getColumnLabel(i);
						String fieldName=toFirstLetterUpperCase(colName);
						Field f = map.get(fieldName);
						if(f != null){
							f.setAccessible(true);
							if("int".equals(f.getType().toString())){
								if(resultSet.getObject(i) == null){
									f.set(o, 0);
								}else{
									f.set(o, resultSet.getInt(i));
								}
							}else{
								f.set(o, resultSet.getObject(i));
							}
						}
					}
					returnList.add((T) o);
				}
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
		return returnList;
	}

	/**
	 * 根据属性名及表名，自动编写返回insert语句
	 * 
	 * @param clazz
	 * @param tableName
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static <T> String buildInsertSql(T t) {
		Class<?> clazz = t.getClass();
		StringBuffer sql = new StringBuffer();
		try {
			String tableName = clazz.getName();
			Field[] fileds = clazz.getDeclaredFields();
			sql.append("insert into ").append(tableName.substring(tableName.lastIndexOf(".") + 1).toLowerCase());
			sql.append("(");
			for (int i = 1; i < fileds.length; i++) {
				Field filed = fileds[i];
				String name = filed.getName();
				if (i > 1) {
					sql.append(",");
				}
				sql.append(name);
			}
			sql.append(") values (");
			for (int i = 1; i < fileds.length; i++) {
				Field filed = fileds[i];
				String name = filed.getName();
				if (i > 1) {
					sql.append(",");
				}
				 Method method = clazz.getDeclaredMethod("get"+ toFirstLetterUpperCase(name));
				 Object value = method.invoke(t);
				if (!"int".equals(filed.getType().toString())) {
					sql.append("'").append(value).append("'");
				}/* else if (value == null) {
					continue;
				}*/else {
					sql.append(value);
				}
			}
			sql.append(")");
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return sql.toString();
	}


	/**
	 * 将字符串首字母大写
	 * @param str
	 * @return
	 */
	public static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		String firstLetter = str.substring(0, 1).toUpperCase();
		return firstLetter + str.substring(1, str.length());
	}
}
