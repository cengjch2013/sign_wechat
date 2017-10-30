package bzb;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bzb.util.ApplicationStartup;
import bzb.util.DBUtil;

@SpringBootApplication
public class Application{
	private static Logger logger = LogManager.getLogger(Application.class);
	
    public static void main( String[] args )
    {
        logger.info("init database table.......");
        /**初始化数据库表**/
        
        //初始化发送消息数据库
        String sql = "create table IF NOT EXISTS  sendmessage ("
        		+ "id INTEGER PRIMARY KEY autoincrement,"
        		+ "createtime TIMESTAMP default (datetime('now', 'localtime')),"
        		+ "messagetype integer(1),"
        		+ "sendflag integer(1),"
        		+ "wechatno varchar(32),"
        		+ "nickname varchar(32),"
        		+ "wechatid varchar(128),"
        		+ "content varchar(256),"
        		+ "error varchar(256))";
        DBUtil.executer(sql);
        
        //初始化系统登陆用户
        sql = "create table IF NOT EXISTS  sys_user ("
        		+ "id INTEGER PRIMARY KEY autoincrement,"
        		+ "createtime TIMESTAMP default (datetime('now', 'localtime')),"
        		+ "username varchar(32),"
        		+ "password varchar(64))";
        DBUtil.executer(sql);
        
        sql = "select * from sys_user where username='admin'";
        List<Map<String, Object>> result = DBUtil.executerQuery(sql);
        if(result.isEmpty()){
        	sql = "insert into sys_user(username,password) values('admin','admin')";
        	DBUtil.executer(sql);
        }
        
        //初始化签到用户数据库
        sql = "create table IF NOT EXISTS  user ("
        		+ "id INTEGER PRIMARY KEY autoincrement,"
        		+ "createtime TIMESTAMP default (datetime('now', 'localtime')),"
        		+ "name varchar(32),"
        		+ "enabled INTEGER,"
        		+ "enrollnumber INTEGER,"
        		+ "wechatno varchar(32),"
        		+ "nickname varchar(32),"
        		+ "wechatid varchar(128),"
        		+ "classname varchar(128),"
        		+ "password varchar(64))";
        DBUtil.executer(sql);
        
        //初始化签到记录数据库
        sql = "create table IF NOT EXISTS  sign_record ("
        		+ "id INTEGER PRIMARY KEY autoincrement,"
        		+ "time TIMESTAMP default (datetime('now', 'localtime')),"        		
        		+ "enrollnumber INTEGER," 
        		+ "name varchar(32),"
        		+ "mode INTEGER,"
        		+ "md5 INTEGER,"
        		+ "constraint uk_sign_record_unique_md5 unique(md5))";
        DBUtil.executer(sql);
        
      //初始化签到记录数据库
        sql = "create table IF NOT EXISTS  devices ("
        		+ "id INTEGER PRIMARY KEY autoincrement,"
        		+ "createtime TIMESTAMP default (datetime('now', 'localtime'))," 
        		+ "name varchar(32),"
        		+ "deviceid INTEGER,"
        		+ "enabled INTEGER,"
        		+ "ip varchar(32),"
        		+ "port INTEGER)";
        DBUtil.executer(sql);
        
        logger.info("init database table successfully");
        
        SpringApplication app = new SpringApplication(Application.class);
        //添加启动后事件
        app.addListeners(new ApplicationStartup());
        
        app.run(args);
        
        
    }
}
