package bzb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bzb.util.DBUtil;

@Controller
public class AdminController {

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		int status = 200;
		String msg = "";
		if (username == null || pwd == null){
			status = 400;
			msg = "用户名和密码不能为空";
		}else{
			String sql = "select username,id from user where username='"+username + "' and password='"+pwd+"'";
			List<Map<String, Object>> queryresult =DBUtil.executerQuery(sql);
			if(queryresult.isEmpty()){
				status = 400;
				msg = "用户名或密码错误";
			}else {
				request.getSession().setAttribute("username", username);
			}
		}
		result.put("status", status);
		result .put("msg", msg);
		
		return result;
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(){
		return "home";
	}
}
