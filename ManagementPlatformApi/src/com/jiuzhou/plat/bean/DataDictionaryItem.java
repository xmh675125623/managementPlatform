package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年10月17日 下午5:15:44
*  数据字典类
*/

import java.util.Date;

public class DataDictionaryItem {
	
	public static final String CODE_RULE_PROTOCOL = "rule_protocol";
	
	private int 	dic_id;		//类型id
	private String 	dic_code;	//类型code
	private String 	dic_name;	//类型名
	private int		item_id;	//
	private String 	item_name;
	private String 	item_value;
	private int		item_sort;
	private int 	delete_flag;
	private Date	add_time;
	private String 	add_user;
	private Date	update_time;
	private String 	update_user;
	
	public DataDictionaryItem() {
	}

	public int getDic_id() {
		return dic_id;
	}

	public void setDic_id(int dic_id) {
		this.dic_id = dic_id;
	}

	public String getDic_code() {
		return dic_code;
	}

	public void setDic_code(String dic_code) {
		this.dic_code = dic_code;
	}

	public String getDic_name() {
		return dic_name;
	}

	public void setDic_name(String dic_name) {
		this.dic_name = dic_name;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_value() {
		return item_value;
	}

	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}

	public int getItem_sort() {
		return item_sort;
	}

	public void setItem_sort(int item_sort) {
		this.item_sort = item_sort;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
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
	
	
	
}
