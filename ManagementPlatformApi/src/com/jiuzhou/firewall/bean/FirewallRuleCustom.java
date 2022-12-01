package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午2:32:05
* 类说明
*/
public class FirewallRuleCustom extends FirewallStrategy {

	private int id;
	private String protocol;    //协议
	private String sport;       //源端口
	private String dport;       //目的端口

	public FirewallRuleCustom() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public String getLogDesc() {
		return super.getLogDescProtected()
				+ "[协议类型："+this.getProtocol()+"]\n"
				+ "[源端口："+this.getSport()+"]\n"
				+ "[目的端口："+this.getDport()+"]\n"
				+ "[进口网口："+(super.getInput_eth()-1)+"]\n"
				+ "[出口网口："+(super.getOutput_eth()-1)+"]";
	}

	
}
