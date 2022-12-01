package com.jiuzhou.plat.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2021年11月17日 上午10:48:52
* 类说明
*/
public class PlatDevice {
	
	/**
	 * 设备类型：防火墙
	 */
	public static final int TYPE_FIREWALL = 1;
	/**
	 * 设备类型：审计
	 */
	public static final int TYPE_AUDIT = 1;
	
	private int		id;
	private int 	type; //类型 1:防火墙 2:审计
	private String	device_name; //设备名
	private String	device_mark; //设备别名
	private String	ip_address;  //ip地址
	private String	access_url; //访问url
	private String	device_mac; //mac地址
	private String	subnetmask; //子网掩码
	private String	gateway;//默认网关
	private String	remark;//备注
	private Date	insert_time;//添加时间
	
	private String getTypeName () {
		switch (this.type) {
		case 1:
			return "防火墙";
		case 2:
			return "审计墙";
		default:
			return "";
		}
	}
	
	public String toLogDescription () {
		
		return "[类型：" + this.getTypeName() + "]"
				+ "[设备名：" + this.getDevice_name() + "]"
				+ "[设备别名：" + this.getDevice_mark() + "]"
				+ "[ip地址：" + this.getIp_address() + "]"
				+ "[访问url：" + this.getAccess_url() + "]"
				+ "[mac地址：" + this.getDevice_mac() + "]"
				+ "[子网掩码：" + this.getSubnetmask() + "]"
				+ "[默认网关：" + this.getGateway() + "]"
				+ "[备注：" + this.getRemark() + "]";
	}
	
	public PlatDevice() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getDevice_mark() {
		return device_mark;
	}

	public void setDevice_mark(String device_mark) {
		this.device_mark = device_mark;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getAccess_url() {
		return access_url;
	}

	public void setAccess_url(String access_url) {
		this.access_url = access_url;
	}

	public String getDevice_mac() {
		return device_mac;
	}

	public void setDevice_mac(String device_mac) {
		this.device_mac = device_mac;
	}

	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
