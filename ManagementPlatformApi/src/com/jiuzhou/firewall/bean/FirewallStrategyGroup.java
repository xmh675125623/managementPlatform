package com.jiuzhou.firewall.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午4:33:48
* 类说明
*/
public class FirewallStrategyGroup {
	
	private int id;
	private String device_name;    //设备名
	private String group_name;     //策略组名
	private int source_asset;      //源资产id
	private String source_asset_name;//源资产名
	private String source_asset_ip;//源资产IP
	private int target_asset;      //目的资产id
	private String target_asset_name;//目的资产名
	private String target_asset_ip;//目的资产IP
	private int delete_flag;       //删除标识
	private Date add_time;
	private String add_user;
	private Date update_time;
	private String update_user;
	
	private List<FirewallStrategy> strategies = new ArrayList<>();
	
	public FirewallStrategyGroup() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public int getSource_asset() {
		return source_asset;
	}

	public void setSource_asset(int source_asset) {
		this.source_asset = source_asset;
	}

	public int getTarget_asset() {
		return target_asset;
	}

	public void setTarget_asset(int target_asset) {
		this.target_asset = target_asset;
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

	public String getSource_asset_name() {
		return source_asset_name;
	}

	public void setSource_asset_name(String source_asset_name) {
		this.source_asset_name = source_asset_name;
	}

	public String getSource_asset_ip() {
		return source_asset_ip;
	}

	public void setSource_asset_ip(String source_asset_ip) {
		this.source_asset_ip = source_asset_ip;
	}

	public String getTarget_asset_name() {
		return target_asset_name;
	}

	public void setTarget_asset_name(String target_asset_name) {
		this.target_asset_name = target_asset_name;
	}

	public String getTarget_asset_ip() {
		return target_asset_ip;
	}

	public void setTarget_asset_ip(String target_asset_ip) {
		this.target_asset_ip = target_asset_ip;
	}

	public List<FirewallStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<FirewallStrategy> strategies) {
		this.strategies = strategies;
	}
}
