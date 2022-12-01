package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年10月17日 下午6:17:35
* 类说明
*/
public class AuditWhitelistRuleTemplate {
	
	public static final String TYPE_MODBUS_TCP = "modbus_tcp";
	
	private int 	id;
	private String 	type;
	private String 	template;
	
	public AuditWhitelistRuleTemplate() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	
}
