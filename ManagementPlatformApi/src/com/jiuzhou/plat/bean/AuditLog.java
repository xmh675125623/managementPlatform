package com.jiuzhou.plat.bean;

import java.util.Date;

/**
 * 审计日志bean类
 * @author xingmh
 * @version 2022年12月27日
 */
public class AuditLog {
	
	/**
	  *  事件类型：正常事件
	 */
	public static final int EVENT_TYPE_NORMAL = 1;
	/**
	  *  事件类型：异常事件
	 */
	public static final int EVENT_TYPE_EXCEPTION = 2;
	
	/**
	  *  事件等级：信息
	 */
	public static final int LEVEL_INFO = 1;
	/**
	  *  事件等级：注意
	 */
	public static final int LEVEL_NOTE = 2;
	/**
	  *  事件等级：告警
	 */
	public static final int LEVEL_WARING = 3;
	
	public static final int MODULE_SYSTEM = -1;
	public static final int MODULE_USB = 1;
	public static final int MODULE_NIC = 2;
	public static final int MODULE_NET_MODULE = 3;
	public static final int MODULE_MODBUS_TCP = 4;
	public static final int MODULE_FTP = 5;
	public static final int MODULE_TELNET = 6;
	public static final int MODULE_HTTP = 7;
	public static final int MODULE_SMTP = 8;
	public static final int MODULE_POP3 = 9;
	
	private long 	id;
	private int     facility;
	private int     severity;      //事件等级
	private String  tag;           //模块
	private String  origin;        //设备名
	private String  message;
	
	
	
	private String 	source_ip;		//源IP
	private String 	target_ip;		//目的IP
	private int 	event_type;		//事件类型
	private int 	level;			//事件等级
	private int 	module;			//事件模块
	private String 	context;		//描述
	private Date 	add_time;
	
	public AuditLog() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public int getEvent_type() {
		return event_type;
	}

	public void setEvent_type(int event_type) {
		this.event_type = event_type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSource_ip() {
		return source_ip;
	}

	public void setSource_ip(String source_ip) {
		this.source_ip = source_ip;
	}

	public String getTarget_ip() {
		return target_ip;
	}

	public void setTarget_ip(String target_ip) {
		this.target_ip = target_ip;
	}

	public int getFacility() {
		return facility;
	}

	public void setFacility(int facility) {
		this.facility = facility;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
