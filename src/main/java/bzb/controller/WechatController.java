package bzb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import bzb.model.SendMessage;
import bzb.service.WechatService;
import bzb.util.CommonUtil;
import bzb.util.DBUtil;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;


@Controller
@RequestMapping(value="/wechat")
public class WechatController {
	
	@Autowired
	WechatService wechatService;

	private static Logger logger = LogManager.getLogger(WechatController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> login(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("hello", "hello world");
		logger.info("wechat login");
		wechatService.login();
		return result;
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(){
		List<JSONObject> friends = WechatTools.getContactList();
		for (JSONObject fri : friends) {
//			if (wechatno.equals(fri.get("").toString())){
//				userId = fri.getString("");
//				break;
//			}
			System.out.println(fri);
		}
		return "index";
	}
	
	@RequestMapping(value="/sendmsg", method = RequestMethod.POST, consumes="application/json")	
	@ResponseBody
	public String sendMSG(HttpServletRequest request, @RequestBody SendMessage message){
		String result="ok";
		String userId = "";
//		String wechatno = request.getParameter("wechatno");
//		String content = request.getParameter("content");
		String wechatno = message.getNickname();
		String content = message.getContent();
		System.out.println(wechatno+"##"+content);
		
		if (wechatno != null && !wechatno.equals(""))
		{
			List<JSONObject> friends = WechatTools.getContactList();
			for (JSONObject fri : friends) {
				if (wechatno.equals(fri.get("NickName").toString())){
					userId = fri.getString("UserName");
					message.setWechatid(userId);
					message.setNickname(wechatno);
					message.setWechatno(wechatno);					
					break;
				}
			}
			if (userId != null && !userId.equals("")){
				try{
					MessageTools.sendMsgById(content, userId);
					message.setSendflag(1);
				}
				catch (Exception e) {
					// TODO: handle exception
					message.setError(e.getMessage());
					message.setSendflag(2);
				}				
			}else {
				message.setError("不存在此微信用户，请合适微信与注册信息是否一直");
				message.setSendflag(0);
			}
		}else {
			message.setError("参数错误");
			message.setSendflag(0);
		}
		
		try {
			message.setCreatetime(new Date());
			String insertsql = CommonUtil.buildInsertSql(message);
			DBUtil.executer(insertsql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
}
