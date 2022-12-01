package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午4:13:38
* 事件分级
*/
public class AuditEventLevel {
	
	public static final int ID_FTP = 1;
	public static final int ID_TELNET = 2;
	public static final int ID_HTTP = 3;
	public static final int ID_SMTP = 4;
	public static final int ID_POP3 = 5;
	public static final int ID_MODBUS_TCP = 6;

	private	int		id;
	private String  event_name;		//事件名
	private int		event_level;	//事件等级 1：低，2：中，3：高
	
	public AuditEventLevel() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public int getEvent_level() {
		return event_level;
	}

	public void setEvent_level(int event_level) {
		this.event_level = event_level;
	}
	
	
	
}
