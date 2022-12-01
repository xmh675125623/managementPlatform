package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午2:21:40
* 规则库
*/
public class FirewallRuleTemp extends FirewallStrategy {
	
	/**
	 * 规则类型:普通规则
	 */
	public static final int TYPE_COMMON = 1;
	/**
	 * 规则类型:特殊规则
	 */
	public static final int TYPE_SPECIAL = 2;
	
	private int    id;
	private String protocol;    //协议
	private String sport;       //源端口
	private String dport;       //目的端口
	private int    type;        //规则类型1普通规则2特殊规则
	private Date   insert_time; //插入时间
	private int    add_flag;    //是否已经添加到服务端 0是1否 默认 1
	private String rule_name;   //规则名称
	
	public FirewallRuleTemp() {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAdd_flag() {
		return add_flag;
	}

	public void setAdd_flag(int add_flag) {
		this.add_flag = add_flag;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	@Override
	public String getLogDesc() {
		return super.getLogDescProtected()
				+ "[规则名称："+this.getRule_name()+"]\n"
				+ "[协议类型："+this.getProtocol()+"]\n"
				+ "[源端口："+this.getSport()+"]\n"
				+ "[目的端口："+this.getDport()+"]\n";
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
}
