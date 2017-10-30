package bzb.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import bzb.model.SendMessage;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;

public class CollectionRecordThread implements Runnable {

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("collection record run");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		while (true) {
			try {
				ZkemSDK zkem = new ZkemSDK();
				boolean result = zkem.connect(PropertiesUtil.getProperty("ip"),
						Integer.parseInt(PropertiesUtil.getProperty("port")));
				System.out.println(result);
				int dwMachineNumber = 100;
				if (result) {
					// 查询打卡用户
					Map<Integer, Map<String, Object>> user_map = new HashMap<Integer, Map<String, Object>>();
					/*String usersql = "select name, nickname, enrollnumber, wechatno from user where enabled = 1";
					List<Map<String, Object>> user_list = DBUtil.executerQuery(usersql);
					for (Map<String, Object> map : user_list) {
						user_map.put(Integer.parseInt(map.get("enrollnumber").toString()), map);
					}*/
					Map<String, Object> usermap = Cache.user;
					for(String key : usermap.keySet()){
						user_map.put(Integer.parseInt(((Map<String, Object>)usermap.get(key)).get("enrollnumber").toString()), usermap);
					}

					Date today = new Date();
					zkem.readGeneralLogData(dwMachineNumber);
					List<Map<String, Object>> list = zkem.getGeneralLogData(dwMachineNumber);
					
					List<Map<String, Object>> record_users = zkem.getUserInfo(dwMachineNumber);
					
					Map<Integer, String> map_users = new HashMap<Integer, String>();
					if (!record_users.isEmpty()) {
						for (Map<String, Object> user : record_users){
							map_users.put(Integer.parseInt("" + user.get("EnrollNumber")), user.get("Name").toString().trim());
						}
					}

					String check_sql = "select mode from sign_record where 1=1 and time > '" + format.format(today)
							+ "' ";
					String check_sql_where = "";
					String sql = "insert into sign_record(enrollnumber, name, mode, time, md5 ) select ";

					String strValues = "";					
					for (Map<String, Object> map : list) {
						int mode = 0;// 默认签到
						// 查询最后一次记录
						int EnrollNumber = Integer.parseInt("" + map.get("EnrollNumber"));
						check_sql_where = " and enrollnumber = " + EnrollNumber + " ORDER BY time DESC";
						List<Map<String, Object>> check_list = DBUtil.executerQuery(check_sql + check_sql_where);
						if (!check_list.isEmpty()) {
							// 当天最后一次打卡，如果是签到，则本次是签退，否则是签到							
							mode = Integer.parseInt(check_list.get(0).get("mode").toString()) == 0 ? 1 : 0;
						}

						// 添加MD5，防止重复记录插入
						int md5 = ("" + map.get("EnrollNumber") + map.get("Time")).hashCode();

						strValues = "" + map.get("EnrollNumber") + ",'" + map_users.get(EnrollNumber) + "'," + mode+  ",'" + map.get("Time") + "'," + md5
								+ " WHERE NOT EXISTS (SELECT md5 FROM sign_record where md5 =" + md5 + ")";
						DBUtil.executer(sql + strValues);

						// 发送信息
						if (PropertiesUtil.getProperty("wechat_login").equals("0")){
							//微信没登录，不发送信息
							continue;
						}
						String nickname = user_map.get(Integer.parseInt("" + map.get("EnrollNumber"))).get("nickname")
								.toString();
						String name = user_map.get(Integer.parseInt("" + map.get("EnrollNumber"))).get("name")
								.toString();
						SendMessage message = new SendMessage();
						String userId=null;
						List<JSONObject> friends = WechatTools.getContactList();
						for (JSONObject fri : friends) {
							if (nickname.equals(fri.get("NickName").toString())) {
								userId = fri.getString("UserName");
								message.setWechatid(fri.getString("UserName"));
								message.setNickname(nickname);
								// message.setWechatno(wechatno);
								break;
							}
						}
						String content = "";
						if (mode == 0) {
							content = "[" + PropertiesUtil.getProperty("projectname") + "]:" + name + " 同学在"
									+ map.get("Time") + " <span style=\"text-decoration: underline; color: rgb(255, 0, 0);\"> 到达 </span>" + PropertiesUtil.getProperty("projectname");
						}else {
							content = "[" + PropertiesUtil.getProperty("projectname") + "]:" + name + " 同学在"
									+ map.get("Time") + " <span style=\"text-decoration: underline; color: rgb(255, 0, 0);\"> 离开 </span>" + PropertiesUtil.getProperty("projectname");
						}
						message.setContent(content);
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
						
						//将发送信息保存入数据库
						try {
							message.setCreatetime(new Date());
							String insertsql = CommonUtil.buildInsertSql(message);
							DBUtil.executer(insertsql);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					zkem.clearGLog(dwMachineNumber);

				}
				zkem.disConnect();
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}