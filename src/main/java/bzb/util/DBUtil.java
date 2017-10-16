package bzb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class DBUtil {
	
	private static Logger logger = LogManager.getLogger(DBUtil.class);
	
	private static String JDBC = "org.sqlite.JDBC";
	private static String Driver = "jdbc:sqlite:sign_wechat_db";
	static Connection conn = null;
	
	static{
		try {
			Class.forName(JDBC);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void getConnection(){
		try {
			conn = DriverManager.getConnection(Driver);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/***
	 * 执行非查询SQL语句
	 * @param sql
	 */
	public static void executer(String sql){		
		try {
			getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * 执行查询SQL语句
	 * @param sql
	 * @return json 列表
	 */
	public static List<Map<String, Object>> executerQuery(String sql){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			
		try {
			getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ResultSet resultSet = ps.getResultSet();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();
			String[] column = new String[count];
			for (int i = 0; i < count; i++){
				column[i] = rsmd.getColumnName(i + 1); 
			}
			while(resultSet.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < count; i++){
					map.put(column[i], resultSet.getObject(i + 1));
				}
				result.add(map);
			}
			resultSet.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
