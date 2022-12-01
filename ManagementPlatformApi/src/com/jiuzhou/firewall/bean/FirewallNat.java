package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月15日 下午2:22:18
* 类说明
*/
public class FirewallNat {
	
	/**
	 * nat类型：snat
	 */
	public static final int NAT_TYPE_SNAT = 1;
	/**
	 * nat类型：dnat
	 */
	public static final int NAT_TYPE_DNAT = 2;
	/**
	 * nat类型：动态snat
	 */
	public static final int NAT_TYPE_DYNAMIC_SNAT = 3;
	/**
	 * nat类型：动态dnat
	 */
	public static final int NAT_TYPE_DYNAMIC_DNAT = 4;
	
	private int		id;
	private String	device_name;		//设备名
	private int		nat_type;			//nat类型1：snat，2：dnat，3：动态snat，4：动态dnat
	private String	protocol;			//协议类型
	private String	ip_address;			//ip地址
	private String	port;				//端口号
	private String	nat_ip_address;		//nat ip地址
	private String  nat_sip_address;	//nat ip起始地址（动态nat）
	private String	nat_dip_address;	//nat ip结束地址（动态nat）
	private String	nat_port;			//nat端口
	private Date	add_time;
	private String  add_user;
	private	Date	update_time;
	private String	update_user;
	private int		delete_flag;
	
	public FirewallNat() {
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

	public int getNat_type() {
		return nat_type;
	}

	public void setNat_type(int nat_type) {
		this.nat_type = nat_type;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getNat_ip_address() {
		return nat_ip_address;
	}

	public void setNat_ip_address(String nat_ip_address) {
		this.nat_ip_address = nat_ip_address;
	}

	public String getNat_sip_address() {
		return nat_sip_address;
	}

	public void setNat_sip_address(String nat_sip_address) {
		this.nat_sip_address = nat_sip_address;
	}

	public String getNat_dip_address() {
		return nat_dip_address;
	}

	public void setNat_dip_address(String nat_dip_address) {
		this.nat_dip_address = nat_dip_address;
	}

	public String getNat_port() {
		return nat_port;
	}

	public void setNat_port(String nat_port) {
		this.nat_port = nat_port;
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
	
	public static final String getNatName(int natType) {
		switch (natType) {
		case NAT_TYPE_DNAT:
			return "DNAT";
		case NAT_TYPE_SNAT:
			return "SNAT";
		case NAT_TYPE_DYNAMIC_SNAT:
			return "动态SNAT";
		case NAT_TYPE_DYNAMIC_DNAT:
			return "动态DNAT";
		default:
			return "未知";
		}
	}

	
}
