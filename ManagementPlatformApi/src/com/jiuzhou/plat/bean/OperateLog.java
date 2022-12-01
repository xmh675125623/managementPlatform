package com.jiuzhou.plat.bean;

import java.util.Date;

/**
 * 用户界面操作日志基础类
 * @author xingmh
 */
public class OperateLog {
	
	private int 	id;
	private String 	type;			//操作类型
	private int 	user_id;		//用户id
	private int 	function_id;	//功能id
	private String 	function_name;	//功能名称
	private String 	description;	//描述
	private String 	user_name;		//操作者
	private String 	auth_result;	//是否通过验证
	private String 	ip_address;		//ip地址
	private Date	add_time;		//添加时间
	
	public OperateLog() {
	}
	
	public OperateLog(String type, int user_id, String function_name, String description, String user_name,
			String auth_result, String ip_address, Date add_time) {
		super();
		this.type = type;
		this.setUser_id(user_id);
		this.function_name = function_name;
		this.description = description;
		this.user_name = user_name;
		this.auth_result = auth_result;
		this.ip_address = ip_address;
		this.add_time = add_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFunction_name() {
		return function_name;
	}

	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getAuth_result() {
		return auth_result;
	}

	public void setAuth_result(String auth_result) {
		this.auth_result = auth_result;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getFunction_id() {
		return function_id;
	}

	public void setFunction_id(int function_id) {
		this.function_id = function_id;
	}
	
	
}
