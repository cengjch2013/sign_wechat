package bzb.model;

import java.util.Date;

public class SendMessage {
	
	private int id;
	private Date createtime;
	private int messagetype;//0:签到，1,签退
	private int sendflag;// 0,未发送；1,已发送，2：发送失败
	private String content;//发送内容
	private String wechatno;//微信号
	private String nickname;//微信号昵称
	private String wechatid;//微信ID
	
	private String error;//错误原因
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(int messagetype) {
		this.messagetype = messagetype;
	}
	public int getSendflag() {
		return sendflag;
	}
	public void setSendflag(int sendflag) {
		this.sendflag = sendflag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWechatno() {
		return wechatno;
	}
	public void setWechatno(String wechatno) {
		this.wechatno = wechatno;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getWechatid() {
		return wechatid;
	}
	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
