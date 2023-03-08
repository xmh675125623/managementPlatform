package com.jiuzhou.plat.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2023年1月9日 上午10:15:37
* 类说明
*/
public class IsolationLog implements DeviceLog {
	
	public static final int MODULE_COMMON = 1;
	public static final int MODULE_NET_MODULE = 2;
	
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
	
	public IsolationLog() {
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
