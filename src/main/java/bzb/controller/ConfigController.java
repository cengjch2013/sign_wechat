package bzb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import bzb.util.PropertiesUtil;

@Controller
@RequestMapping(value="/config")
public class ConfigController {
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String index(){
		
		return "config";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String msg = "";
		int status = 200;
		Map<String, String> data = new HashMap<String, String>();
		data.put("projectname", PropertiesUtil.getProperty("projectname"));
		data.put("ip", PropertiesUtil.getProperty("ip"));		
		result.put("data", data);
		result.put("status", status);
		result.put("msg", msg);
		return result.toString();	
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveConfig(HttpServletRequest request){
		JSONObject result = new JSONObject();
		String msg = "";
		int status = 200;
		try {
			String ip = request.getParameter("ip");
			String projectname = request.getParameter("projectname");
			PropertiesUtil.saveProperty("ip", ip);
			PropertiesUtil.saveProperty("projectname", projectname);
		} catch (Exception e) {
			// TODO: handle exception
			status = 400;
			msg = e.getMessage();
		}
		result.put("status", status);
		result.put("msg", msg);
		return result.toString();
	}
}
