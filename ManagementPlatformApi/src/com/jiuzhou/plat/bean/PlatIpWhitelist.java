package com.jiuzhou.plat.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午7:11:12
* 系统登录IP地址白名单
*/
public class PlatIpWhitelist {
	
	private int 	id;
	private String 	ip_address;
	private String  remark;
	private String  add_user;
	private Date	add_time;
	
	public PlatIpWhitelist() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdd_user() {
		return add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

}
