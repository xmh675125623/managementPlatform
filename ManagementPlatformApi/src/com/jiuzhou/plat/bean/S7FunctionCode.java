package com.jiuzhou.plat.bean;

import java.util.ArrayList;
import java.util.List;

/**
* @author xingmh
* @version 创建时间：2020年8月25日 上午10:43:06
* 类说明
*/
public class S7FunctionCode {
	
	public static final String PDU_TYPE_JOB = "JOB";
	public static final String PDU_TYPE_USERDATA = "USERDATA";
	
	private int    id;
	private int    parent_id;
	private String pdu_type;
	private String code;
	private String func;
	private String description;
	
	private List<S7FunctionCode> subFunctions = new ArrayList<>();
	
	public S7FunctionCode() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<S7FunctionCode> getSubFunctions() {
		return subFunctions;
	}

	public void setSubFunctions(List<S7FunctionCode> subFunctions) {
		this.subFunctions = subFunctions;
	}

	public String getPdu_type() {
		return pdu_type;
	}

	public void setPdu_type(String pdu_type) {
		this.pdu_type = pdu_type;
	}
	
}
