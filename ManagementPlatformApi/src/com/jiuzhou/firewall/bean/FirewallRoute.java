package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月16日 下午4:16:27
* 类说明
*/
public class FirewallRoute {
	
	/**
	 * 路由模式：默认路由
	 */
	public static final int TYPE_DEFAULT = 0;
	/**
	 * 路由模式：主机路由
	 */
	public static final int TYPE_HOST = 1;
	/**
	 * 路由模式：网络路由
	 */
	public static final int TYPE_NETWORK = 2;
	
	private int     id;
	private String  device_name;
	private String  ip;
	private String  gateway;
	private int     type;
	private int     out_eth;
	private int     delete_flag;
	private Date    add_time;
	private String  add_user;
	private Date    update_time;
	private String  update_user;
	
	public FirewallRoute() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOut_eth() {
		return out_eth;
	}

	public void setOut_eth(int out_eth) {
		this.out_eth = out_eth;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
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

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	
	
}
