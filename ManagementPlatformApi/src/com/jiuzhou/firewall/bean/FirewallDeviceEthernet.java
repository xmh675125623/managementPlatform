package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年1月16日 下午1:59:34
* 防火墙设备网口
*/
public class FirewallDeviceEthernet {
	
	/**
	 * 模式：网桥
	 */
	public static final int MODE_BIRDGE = 1;
	/**
	 * 模式：路由
	 */
	public static final int MODE_ROUTE = 2;

	private int     id;				
	private String  device_name;	//设备名
	private int     number;			//网口标号
	private int     bypass_number;	//互为bypass网口标号
	private int     mode;			//网口模式1：网桥模式，2：路由模式
	private String  ip_address;		//网口IP
	private String  mask;			//网口子网掩码
	private String  gateway;		//网口网关
	private int     eth_status;		//状态：0：未连接，1：running
	
	public FirewallDeviceEthernet() {
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getBypass_number() {
		return bypass_number;
	}

	public void setBypass_number(int bypass_number) {
		this.bypass_number = bypass_number;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public int getEth_status() {
		return eth_status;
	}

	public void setEth_status(int eth_status) {
		this.eth_status = eth_status;
	}
	
	
	
	
}
