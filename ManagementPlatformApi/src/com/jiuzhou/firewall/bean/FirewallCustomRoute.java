package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年3月6日 下午3:26:05
* 类说明
*/
public class FirewallCustomRoute {

	private int id;
	private String device_name;   //设备名
	private int in_eth;          //入口设备
	private int out_eth;         //出口设备
	private String sip;           //源ip
	private String dip;           //目的ip
	private String protocol;      //协议类型
	private String sport;         //源端口
	private String dport;         //目的端口
	private String gateway;       //网关
	private Date add_time;
	private String add_user;
	private Date update_time;
	private String update_user;
	
	public FirewallCustomRoute() {
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

	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	public String getDip() {
		return dip;
	}

	public void setDip(String dip) {
		this.dip = dip;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getDport() {
		return dport;
	}

	public void setDport(String dport) {
		this.dport = dport;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
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

	public int getIn_eth() {
		return in_eth;
	}

	public void setIn_eth(int in_eth) {
		this.in_eth = in_eth;
	}

	public int getOut_eth() {
		return out_eth;
	}

	public void setOut_eth(int out_eth) {
		this.out_eth = out_eth;
	}
	
	
}
