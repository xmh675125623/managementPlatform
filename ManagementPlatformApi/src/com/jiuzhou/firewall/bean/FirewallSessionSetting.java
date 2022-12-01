package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年1月21日 下午4:56:25
* 类说明
*/
public class FirewallSessionSetting {

	private String device_name;       //设备名
	private int    line_complete;     //连接完整性：0：关，1：开
	private int    tcp_time;          //tcp协议会话维持时间
	
	public FirewallSessionSetting() {
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public int getLine_complete() {
		return line_complete;
	}

	public void setLine_complete(int line_complete) {
		this.line_complete = line_complete;
	}

	public int getTcp_time() {
		return tcp_time;
	}

	public void setTcp_time(int tcp_time) {
		this.tcp_time = tcp_time;
	}
	
	
}
