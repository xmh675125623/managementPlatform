package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月14日 下午2:03:46
* IP/MAC绑定实体类
*/
public class FirewallMac {
	
	private int		id;
	private String	device_name;		//设备名称
	private String 	device_mark;		//设备别名
	private String	ip_address;			//ip地址
	private String  mac_address;		//mac地址
	private int		source;				//来源 0：手动添加，1：扫描添加
	private int 	delete_flag;		//删除标志位
	private Date	add_time;			//添加时间
	private String  add_user;			//添加者
	private Date 	update_time;		//更新时间
	private String 	update_user;		//变更者
	
	public FirewallMac() {
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

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
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

	public String getDevice_mark() {
		return device_mark;
	}

	public void setDevice_mark(String device_mark) {
		this.device_mark = device_mark;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	
	
}
