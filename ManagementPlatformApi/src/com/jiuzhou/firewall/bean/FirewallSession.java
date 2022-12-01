package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月21日 下午3:39:04
* 类说明
*/
public class FirewallSession {

	private String id;
	private String device_name;       //设备名
	private String source_ip;         //源ip
	private String source_port;       //源端口
	private String target_ip;         //目的端口
	private String target_port;       //目的端口
	private String tcp_name;          //协议
	private String name;              //应用名称
	private String status;            //连接状态
	private Date   add_time;
	private String outtime_time;      //超时时间
	
	public FirewallSession() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getSource_ip() {
		return source_ip;
	}

	public void setSource_ip(String source_ip) {
		this.source_ip = source_ip;
	}

	public String getSource_port() {
		return source_port;
	}

	public void setSource_port(String source_port) {
		this.source_port = source_port;
	}

	public String getTarget_ip() {
		return target_ip;
	}

	public void setTarget_ip(String target_ip) {
		this.target_ip = target_ip;
	}

	public String getTarget_port() {
		return target_port;
	}

	public void setTarget_port(String target_port) {
		this.target_port = target_port;
	}

	public String getTcp_name() {
		return tcp_name;
	}

	public void setTcp_name(String tcp_name) {
		this.tcp_name = tcp_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public String getOuttime_time() {
		return outtime_time;
	}

	public void setOuttime_time(String outtime_time) {
		this.outtime_time = outtime_time;
	}
	
	
	
}
