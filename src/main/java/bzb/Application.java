package bzb;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bzb.util.DBUtil;

@SpringBootApplication
public class Application{
	private static Logger logger = LogManager.getLogger(Application.class);
	
    public static void main( String[] args )
    {
        logger.info("init database table.......");
        //初始化数据库表
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
        sql = "create table IF NOT EXISTS  user ("
        		+ "id INTEGER PRIMARY KEY autoincrement,"
        		+ "createtime TIMESTAMP default (datetime('now', 'localtime')),"
        		+ "username varchar(32),"
        		+ "password varchar(64))";
        DBUtil.executer(sql);
        
        sql = "select * from user where username='admin'";
        List<Map<String, Object>> result = DBUtil.executerQuery(sql);
        if(result.isEmpty()){
        	sql = "insert into user(username,password) values('admin','admin')";
        	DBUtil.executer(sql);
        }
        
        
        SpringApplication.run(Application.class, args);
        
        
    }
}
