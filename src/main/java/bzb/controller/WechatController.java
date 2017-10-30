package bzb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
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
import bzb.util.PropertiesUtil;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;


@Controller
@RequestMapping(value="/wechat")
public class WechatController {
	
	@Autowired
	WechatService wechatService;

	private static Logger logger = LogManager.getLogger(WechatController.class);
	
	@RequestMapping(value="/sign", method = RequestMethod.GET)
	public String index(){
		
		return "sign";
	}
	/**
	 * 微信登陆
	 * @return
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)
	@ResponseBody
	public String wechatLogin(){
		wechatService.login();
		PropertiesUtil.setProperty("wechat_login", "1");
		return "{\"status\": 200}";
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public String listSignRecord(HttpServletRequest request){
		String msg = "";
		int status = 200;
		org.json.JSONObject result =  new org.json.JSONObject();
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		String name = request.getParameter("name");
		String mode = request.getParameter("mode");
		String strpage = request.getParameter("page");
		String strrows = request.getParameter("rows");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(begin != null && !begin.isEmpty() && end != null && !end.isEmpty()){
			try {
				if (!format.parse(begin).after(format.parse(end))) {
					status = 400;
					msg = "终止时间要晚于起始时间";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {
			String sql = "select name, time, mode from sign_record where 1=1 ";
			String sql_counter = "select count(*) as counter from sign_record where 1=1";
			String where = "";
			if (begin != null && !begin.isEmpty()) {
				where += " and time >= '" + begin +"'";
			}
			if (end != null && !end.isEmpty()) {
				where += " and time <'" + end + "'";
			}
			
			if (mode != null && !mode.isEmpty()){
				where += " and mode = " + mode;
			}
			if (name != null && !name.isEmpty()) {
				where += " and name like '%" + name + "%'";
			}
			
			List<Map<String, Object>> counter = DBUtil.executerQuery(sql_counter + where);
			if (!counter.isEmpty()) {
				result.put("total", counter.get(0).get("counter"));
			}
			where += " ORDER BY time DESC ";
			where += " limit " + strrows + "  offset " + (Integer.parseInt(strrows) * (Integer.parseInt(strpage)-1));
			
			List<Map<String, Object>> list = DBUtil.executerQuery(sql + where);
			result.put("rows", list);
			
		}
		result.put("status", status);
		result.put("msg", msg);
		return result.toString();
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
	
	
	@RequestMapping(value="/export", method = RequestMethod.GET)
	@ResponseBody
	public String export(){
		String result="ok";
		
		
		return result;
	}
	
}
