package bzb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import bzb.model.User;
import bzb.util.Cache;
import bzb.util.CommonUtil;
import bzb.util.DBUtil;
import bzb.util.ZkemSDK;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	private static  ZkemSDK zkem = new ZkemSDK();

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index() {
		return "users";
	}
	
	@RequestMapping(value = "/addform", method = RequestMethod.GET)
	public String form(HttpServletRequest request) {
		
		
		return "addform";
	}

	@RequestMapping(value = "/classes", method = RequestMethod.GET)
	@ResponseBody
	public String queryClass() {
		JSONObject result = new JSONObject();
		int status = 200;
		String msg = "";
		List<String> classnames = new ArrayList<String>();
		try {
			if (!Cache.classes.isEmpty()) {// 从缓存中读取班级列表
				classnames = Cache.classes;
			} else {
				String sql = "SELECT DISTINCT  classname as classname FROM user";
				
				List<Map<String, Object>> list = DBUtil.executerQuery(sql);
				if (!list.isEmpty()) {
					for (Map<String, Object> map : list) {
						classnames.add(map.get("classname").toString());
					}
					Cache.classes.addAll(classnames);					
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			msg = e.getMessage();
			status = 400;
		}
		result.put("classnames", classnames);
		result.put("status", status);
		result.put("msg", msg);

		return result.toString();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String msg = "";
		int status = 200;
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String classname = request.getParameter("classname");
		String wechatno = request.getParameter("wechatno");
		try {
			User user = new User();
			user.setClassname(classname);
			user.setName(name);
			user.setNickname(nickname);
			user.setWechatno(wechatno);
			user.setEnabled(true);
			user.setCreatetime(new Date());
			//加入数据库
			String sql = CommonUtil.buildInsertSql(user);
			DBUtil.executer(sql);
			
			if (!Cache.classes.contains(classname.trim())){
				Cache.classes.add(classname);
			}
			
			//加入到打卡机
			
			//zkem.setUserInfo(dwMachineNumber, number, name, password, isPrivilege, enabled) ;
			
			//加入到Cache
		} catch (Exception e) {
			// TODO: handle exception
			status = 400;
			msg = e.getMessage();
		}

		result.put("status", status);
		result.put("msg", msg);
		return result.toJSONString();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update() {
		JSONObject result = new JSONObject();

		return result.toJSONString();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String id = request.getParameter("id");
		String msg = "";
		int status = 200;
		if (id != null ) {
			String[] ids = id.split(",");
			for(String str: ids){
				String sql = "update user set enable = 0 where id = " + str;
				DBUtil.executer(sql);
			}
		}else {
			msg = "请选择删除的记录";
			status = 400;
		}

		result.put("status", status);
		result.put("msg", msg);
		return result.toString();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String msg = "";
		int status = 200;
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String classname = request.getParameter("classname");
		String strpage = request.getParameter("page");
		String strrows = request.getParameter("rows");

		try {
			//只查询未删除的
			String sql = "select * from user where enabled =1 ";
			String sql_counter = "select count(*) as counter from user where enabled =1 ";
			int page = 1;
			int size = 10;
			
			String where = "";
			if(name != null && !name.equals("")){
				where += " and name like '%" + name + "%'";
			}
			
			if(classname != null && !classname.equals("")){
				where += " and classname = '"+ classname + "'";
			}
			
			if (nickname != null && !nickname.equals("")){
				where += " and nickname = '" + nickname + "' ";
			}

			if (!(strpage == null || !strpage.isEmpty() || Integer.parseInt(strpage) < 1)) {
				page = Integer.parseInt(strpage);
			}
			if (!(strrows == null || !strrows.isEmpty() || Integer.parseInt(strrows) < 1)) {
				size = Integer.parseInt(strrows);
			}

			

			// 获取总数
			List<Map<String, Object>> counter = DBUtil.executerQuery(sql_counter + where);
			if (!counter.isEmpty()) {
				result.put("total", counter.get(0).get("counter"));
			}

			// 获取分页记录
			where += " limit " + size + "  offset " + (size * (page - 1));
			List<Map<String, Object>> list = DBUtil.executerQuery(sql + where);
			result.put("rows", list);
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
