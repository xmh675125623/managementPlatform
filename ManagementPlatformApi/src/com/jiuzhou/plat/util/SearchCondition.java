package com.jiuzhou.plat.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
* @author xingmh
* @version 创建时间：2018年10月15日 下午4:30:17
*  拼sql where语句用的工具类
*/
public class SearchCondition {

	public static final int TYPE_EQUAL = 0;
	public static final int TYPE_IN = 1;
	public static final int TYPE_LIKE = 2;

	private int type;
	private String condition;
	private String value;
	
	public SearchCondition() {
	}
	
	public SearchCondition(int type, String condition, String value) {
		super();
		this.setType(type);
		this.condition = condition;
		this.value = value;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		
		String condition = this.getCondition();
		int type = this.getType();
		String value = this.getValue();
		
		if (StringUtils.isBlank(condition)) {
			throw new RuntimeException("condition字段名不可为空");
		}
		
		if (type == TYPE_EQUAL) {
			if (StringUtils.isNotBlank(value)) {
				return " AND " + condition + " = '" + value + "'"; 
			}
		
		} else if (type == TYPE_IN) {
			value = value.replaceAll("\\[", "").replaceAll("\\]", "");
			if (StringUtils.isNotBlank(value)) {
				String[] values = value.split(",");
				String result = " AND " + condition + " IN (";
				for (int i = 0; i < values.length; i++) {
					if (i != 0 ) {
						result += ",";
					}
					result += values[i];
				}
				result += ")";
				return result;
			}

		} else if (type == TYPE_LIKE) {
			if (StringUtils.isNotBlank(value)) {
				return " AND " + condition + " LIKE '%" + value + "%'"; 
			}
			
		}
		
		return "";
		
	}
	
	public static final String toConditions(List<SearchCondition> conditions) {
		if (conditions == null || conditions.size() < 1) {
			return "";
		}
		
		String result = "";
		for (int i = 0; i < conditions.size(); i++) {
			result += conditions.get(i).toString();
		}
		return result;
	}

}
