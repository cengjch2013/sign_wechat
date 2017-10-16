package bzb.service;

import org.springframework.stereotype.Service;

import bzb.sign_wechat.IMsgHandler;
import cn.zhouyafeng.itchat4j.Wechat;

@Service
public class WechatService {

	static String qrPath = System.getProperty("user.dir") + "/qrpath/";
	
	public void login(){
		IMsgHandler msg = new IMsgHandler();
		Wechat wechat = new Wechat(msg, qrPath);
		wechat.start();
	}
}
