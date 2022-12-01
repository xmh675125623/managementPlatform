package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年1月23日 上午11:12:45
* 类说明
*/
public class FirewallAntiAttackDos {
	
	private String device_name;
	private int tcp_syn_flood;
	private int win_nuke;
	private int smurf;
	private int land;
	private int udp_flood;
	private int icmp_flood;
	private int teardrop;
	
	public FirewallAntiAttackDos() {
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public int getTcp_syn_flood() {
		return tcp_syn_flood;
	}

	public void setTcp_syn_flood(int tcp_syn_flood) {
		this.tcp_syn_flood = tcp_syn_flood;
	}

	public int getWin_nuke() {
		return win_nuke;
	}

	public void setWin_nuke(int win_nuke) {
		this.win_nuke = win_nuke;
	}

	public int getSmurf() {
		return smurf;
	}

	public void setSmurf(int smurf) {
		this.smurf = smurf;
	}

	public int getLand() {
		return land;
	}

	public void setLand(int land) {
		this.land = land;
	}

	public int getUdp_flood() {
		return udp_flood;
	}

	public void setUdp_flood(int udp_flood) {
		this.udp_flood = udp_flood;
	}

	public int getIcmp_flood() {
		return icmp_flood;
	}

	public void setIcmp_flood(int icmp_flood) {
		this.icmp_flood = icmp_flood;
	}

	public int getTeardrop() {
		return teardrop;
	}

	public void setTeardrop(int teardrop) {
		this.teardrop = teardrop;
	}

	
	
}
