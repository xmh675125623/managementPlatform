package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年10月24日 下午6:34:53
* 类说明
*/

public class AuditReportBean {
	
	private String	table_date;
	private int 	level_1;	//信息数量
	private int 	level_2;	//注意数量
	private int 	level_3;	//告警数量
	private int 	level_0;	//合计
	
	public AuditReportBean() {
	}

	public int getLevel_1() {
		return level_1;
	}

	public void setLevel_1(int level_1) {
		this.level_1 = level_1;
	}

	public int getLevel_2() {
		return level_2;
	}

	public void setLevel_2(int level_2) {
		this.level_2 = level_2;
	}

	public int getLevel_3() {
		return level_3;
	}

	public void setLevel_3(int level_3) {
		this.level_3 = level_3;
	}

	public int getLevel_0() {
		return level_0;
	}

	public void setLevel_0(int level_0) {
		this.level_0 = level_0;
	}

	public String getTable_date() {
		return table_date;
	}

	public void setTable_date(String table_date) {
		this.table_date = table_date;
	}
	
	
}
