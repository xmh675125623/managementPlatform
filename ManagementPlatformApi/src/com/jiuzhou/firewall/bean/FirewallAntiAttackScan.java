package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年1月23日 上午11:16:07
* 类说明
*/
public class FirewallAntiAttackScan {

	private String device_name;
	private int tcp_scan;
	private int udp_scan;
	
	public FirewallAntiAttackScan() {
	}

	public int getTcp_scan() {
		return tcp_scan;
	}

	public void setTcp_scan(int tcp_scan) {
		this.tcp_scan = tcp_scan;
	}

	public int getUdp_scan() {
		return udp_scan;
	}

	public void setUdp_scan(int udp_scan) {
		this.udp_scan = udp_scan;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

}
