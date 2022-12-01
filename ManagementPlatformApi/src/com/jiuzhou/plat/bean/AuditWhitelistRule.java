package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年10月17日 下午5:43:55
* 类说明
*/

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

public class AuditWhitelistRule {
	
	public static final int TYPE_COMMON = 1;
	public static final int TYPE_MODBUS_TCP = 2;
	
	private int 	id;
	private int 	whitelist_id;
	private int 	type;			//规则类型，1：普通，2：modbus_tcp	
	private String	rule_json;
	private Date 	add_time;
	private String	add_user;
	private Date	update_time;
	private String  update_user;
	private AuditRuleBase rule;
	
	public AuditWhitelistRule() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWhitelist_id() {
		return whitelist_id;
	}

	public void setWhitelist_id(int whitelist_id) {
		this.whitelist_id = whitelist_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getRule_json() {
		return rule_json;
	}

	public void setRule_json(String rule_json) {
		this.rule_json = rule_json;
		if (StringUtils.isBlank(rule_json)) {
			return;
		}
		JSONObject jsonRule = JSONObject.fromObject(rule_json);
		if (this.getType() == TYPE_MODBUS_TCP) {
			rule = (AuditRuleModbusTcp)JSONObject.toBean(jsonRule, AuditRuleModbusTcp.class);
		}
		this.setRule(rule);
	}

	public AuditRuleBase getRule() {
		return rule;
	}

	public void setRule(AuditRuleBase rule) {
		this.rule = rule;
	}
	
	
	
}
