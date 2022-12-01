package com.jiuzhou.plat.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
* @author xingmh
* @version 2018年10月9日 下午6:39:55
* 白名单
*/
public class AuditWhitelist {
	
	private int 	id;
	private String	source_ip;		//源ip地址
	private String 	source_mac;		//源mac地址
	private String	ip_type;		//ip类型
	private String 	target_ip;		//目的ip地址
	private String 	target_mac;		//目的mac地址
	private int 	sport;			//源端口
	private int 	dport;			//目的端口
	private String	protocol;		//协议类型
	private int 	direction;		//方向1：单向，2：双向
	private Date	add_time;	
	private String  add_user;
	private Date	update_time;
	private String  update_user;
	private List<AuditWhitelistRule> rules = new ArrayList<>();
	
	public AuditWhitelist() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource_ip() {
		return source_ip;
	}

	public void setSource_ip(String source_ip) {
		this.source_ip = source_ip;
	}

	public String getSource_mac() {
		return source_mac;
	}

	public void setSource_mac(String source_mac) {
		this.source_mac = source_mac;
	}

	public String getTarget_ip() {
		return target_ip;
	}

	public void setTarget_ip(String target_ip) {
		this.target_ip = target_ip;
	}

	public String getTarget_mac() {
		return target_mac;
	}

	public void setTarget_mac(String target_mac) {
		this.target_mac = target_mac;
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

	public List<AuditWhitelistRule> getRules() {
		return rules;
	}

	public void setRules(List<AuditWhitelistRule> rules) {
		this.rules = rules;
	}

	public int getSport() {
		return sport;
	}

	public void setSport(int sport) {
		this.sport = sport;
	}

	public int getDport() {
		return dport;
	}

	public void setDport(int dport) {
		this.dport = dport;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getIp_type() {
		return ip_type;
	}

	public void setIp_type(String ip_type) {
		this.ip_type = ip_type;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String toLogDescription() {
		return "[源MAC地址：" + (StringUtils.isBlank(this.getSource_mac())?"全部":this.getSource_mac()) 
			+ "][目的MAC地址：" + (StringUtils.isBlank(this.getTarget_mac())?"全部":this.getTarget_mac())
			+ "][三层协议类型：" + (StringUtils.isBlank(this.getIp_type())?"全部":this.getIp_type())
			+ "][源IP地址：" + (StringUtils.isBlank(this.getSource_ip())?"全部":this.getSource_ip())
			+ "][目的IP地址：" + (StringUtils.isBlank(this.getTarget_ip())?"全部":this.getTarget_ip())
			+ "][四层协议类型：" + (StringUtils.isBlank(this.getProtocol())?"全部":this.getProtocol())
			+ "][源端口：" + (this.getSport() == -1 ? "全部":this.getSport())
			+ "][目的端口：" + (this.getDport() == -1 ? "全部":this.getDport())
			+ "][方向：" + getDirectionName(this.getDirection()) + "]";
	}
	
	public String getDirectionName (int direction) {
		switch (direction) {
		case 1:
			return "请求";
		case 2:
			return "响应";
		case 3:
			return "请求响应";
		default:
			return "未知";
		}
	}
	
}
