package bzb.sign_wechat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;

public class IMsgHandler implements IMsgHandlerFace {

	private static Logger logger = LogManager.getLogger(IMsgHandler.class);
	private static final Core core = Core.getInstance();
	private static final MyHttpClient myHttpClient = core.getMyHttpClient();
	private static final String path = System.getProperty("user.dir") + "/head"; // 这里需要设置保存头像的路径
	
	public String textMsgHandle(BaseMsg msg, String wechatno) {
		// TODO Auto-generated method stub
//		if(!msg.isGroupMsg()){
//			String userId = "";
//			String txt = msg.getText();
//			if (txt == null || "".equals(txt.trim()))
//				return null;
//			List<JSONObject> friends = WechatTools.getContactList();
//			for (JSONObject fri : friends) {
//				if (wechatno.equals(fri.get("").toString())){
//					userId = fri.getString("");
//							break;
//				}
//			}
//			
//			logger.info(txt);
//			if (userId != null && !userId.equals("")){
//				MessageTools.sendMsgById(txt, userId);
//			}			
//		}
		return null;
	}

	public String picMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String voiceMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String viedoMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String nameCardMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sysMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		
	}

	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String mediaMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String textMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
