package bzb.sign_wechat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import bzb.model.SendMessage;
import bzb.service.IFaceService;
import bzb.util.CommonUtil;
import bzb.util.DBUtil;
import bzb.util.ZkemSDK;
import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
//        System.out.println(System.getProperty("user.dir"));
//        String qrPath = System.getProperty("user.dir") + "/qrpath/";
//        IMsgHandlerFace msg= new IMsgHandler();
//        Wechat wechat = new Wechat(msg, qrPath);
//        wechat.start();
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
        //String sql = "drop table sendmessage";
        DBUtil.executer(sql);
        SendMessage message = new SendMessage();
        message.setContent("nihao");
        message.setNickname("浮现");
        message.setWechatid("123");
        message.setWechatno("234");
        message.setCreatetime(new Date());
        String insertSQL = CommonUtil.buildInsertSql(message);
        System.out.println(insertSQL);
        
        DBUtil.executer(insertSQL);
        
        sql = "select * from sendmessage";
        List<Map<String, Object>> result = DBUtil.executerQuery(sql);
        System.out.println(result);
        
    }
    
    public void testIFaceService(){
    	ZkemSDK zkem = new ZkemSDK();
    	boolean result = zkem.connect("192.168.2.104", 4370);
    	System.out.println(result);
    	int ReadGeneralLogData=100;
    	if(result){
    		zkem.readGeneralLogData(ReadGeneralLogData);
    		List<Map<String, Object>> list = zkem.getGeneralLogData(ReadGeneralLogData);
    		for (Map<String, Object> map : list){
    			System.out.println(map);
    		}
    		List<Map<String, Object>> users = zkem.getUserInfo(ReadGeneralLogData);
    		for (Map<String, Object> map : users){
    			System.out.println(map);
    		}
    	}
    	
    }
    
}
