package com.jiuzhou.plat.bean;

import java.util.Date;

/**
* @author xingmh
* @version 2018年9月28日 上午9:49:10
* 审计记录表信息
*/
public class DatabaseTableInfo {
	
	private String 	table_name;		//表名
	private String 	table_rows;		//数据条数
	private float 	data_size;		//数据大小
	private float 	index_size;		//索引大小
	private float 	total_size;		//合计占用空间
	private Date 	create_time;	//创建时间
	
	public DatabaseTableInfo() {
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getTable_rows() {
		return table_rows;
	}

	public void setTable_rows(String table_rows) {
		this.table_rows = table_rows;
	}

	public float getData_size() {
		return data_size;
	}

	public void setData_size(float data_size) {
		this.data_size = data_size;
	}

	public float getIndex_size() {
		return index_size;
	}

	public void setIndex_size(float index_size) {
		this.index_size = index_size;
	}

	public float getTotal_size() {
		return total_size;
	}

	public void setTotal_size(float total_size) {
		this.total_size = total_size;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
}
