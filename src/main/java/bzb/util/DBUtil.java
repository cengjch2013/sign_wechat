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

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db")
public class DBUtil {

	private static Logger logger = LogManager.getLogger(DBUtil.class);

	private static String JDBC = "org.sqlite.JDBC";
	private static String Driver = "jdbc:sqlite:sign_wechat_db";
	private String url = "";
	static Connection conn = null;

	private String user;

	private String passwd;

	/*
	 * @PostConstruct private synchronized void init() { try {
	 * Class.forName("com.mysql.jdbc.Driver"); // 指定连接类型 //
	 * Class.forName("com.mysql.jdbc.Driver"); url =
	 * "jdbc:mysql://127.0.0.1:3306/sign_wechat?useUnicode=true&" +
	 * "characterEncoding=UTF8&autoReconnect=true&failOverReadOnly=false"; //
	 * String url = "jdbc:mysql://10.5.0.171:3306/gdcenter"; conn =
	 * DriverManager.getConnection(url, user, passwd);// 获取连接 //
	 * conn.setAutoCommit(false);//取消自动提交 logger.info("create db connection"); }
	 * catch (Exception e) { logger.warn("connect to mysql db:" + url +
	 * " failed :" + e.getMessage(), e); } }
	 */

	static {
		try {
			Class.forName(JDBC);
			// init();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getConnection() {
		try {
			if (conn == null || conn.isClosed())
				conn = DriverManager.getConnection(Driver);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/***
	 * 执行非查询SQL语句
	 * 
	 * @param sql
	 */
	public static void executer(String sql) {
		try {
			logger.info("exec: " + sql);
			getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/***
	 * 执行查询SQL语句
	 * 
	 * @param sql
	 * @return json 列表
	 */
	public static List<Map<String, Object>> executerQuery(String sql) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		logger.info("exec: " + sql);
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			getConnection();
			ps = conn.prepareStatement(sql);
			ps.execute();
			resultSet = ps.getResultSet();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();
			String[] column = new String[count];
			for (int i = 0; i < count; i++) {
				column[i] = rsmd.getColumnName(i + 1);
			}
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < count; i++) {
					map.put(column[i], resultSet.getObject(i + 1));
				}
				result.add(map);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed())
					resultSet.close();
				if (ps != null && !ps.isClosed())
					ps.close();
				if (conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
