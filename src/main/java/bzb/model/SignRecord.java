package bzb.model;

import java.util.Date;

public class SignRecord {

	private int id;
	private int enrollnumber;
	private Date time;
	private int mode; // 0:签到，1:签退
	
	private int md5;
	
	
	public SignRecord(int enrollnumber, Date time, int mode){
		this.enrollnumber = enrollnumber;
		this.time = time;
		this.mode = mode;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getEnrollnumber() {
		return enrollnumber;
	}


	public void setEnrollnumber(int enrollnumber) {
		this.enrollnumber = enrollnumber;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public int getMode() {
		return mode;
	}


	public void setMode(int mode) {
		this.mode = mode;
	}


	public int getMd5() {
		return md5;
	}


	public void setMd5(int md5) {
		this.md5 = md5;
	}
	
}
