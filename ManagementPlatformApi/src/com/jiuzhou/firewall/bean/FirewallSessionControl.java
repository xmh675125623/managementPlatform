package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月22日 下午3:50:16
* 类说明
*/
public class FirewallSessionControl {

	private int id;
	private String device_name;        //设备名
	private String ip;                 //ip
	private String tcp_port;           //tcp端口
	private int num;                   //最大会话数
	private int delete_flag;
	private Date add_time;
	private String add_user;
	private Date update_time;
	private String update_user;
	
	public FirewallSessionControl() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public String getAdd_user() {
		return add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTcp_port() {
		return tcp_port;
	}

	public void setTcp_port(String tcp_port) {
		this.tcp_port = tcp_port;
	}

	
	
}
