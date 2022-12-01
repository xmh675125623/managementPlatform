package com.jiuzhou.plat.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单/功能基础类
 * @author xingmh
 */
public class Function {

	private int id;
	private String function_name;		//功能名称
	private String menu;				//菜单名称
	private String description;			//描述
	private String method;				//功能标识
	private String url;					//链接
	private String icon;				//图标
	private int parent_id;				//上级id
	private int level;					//等级 1：一级菜单，2：二级菜单，3：功能
	private int del_flag;				//删除标志位 1：删除
	private int is_menu;				//是否为菜单 1：是
	private int option_password_auth;	//是否需要验证操作密码
	private String remark;				//备注
	private int sort;					//排序		
	private Date add_time;
	private String add_user;
	private Date update_time;
	private String update_user;
	
	private int half;					//组织功能菜单树用的，判断阶段是否为自身以及子菜单功能全部选中，0：是，1：不是
	
	private	List<Function> children = new ArrayList<>();	//下级菜单 
	
	//前段d2-admin框架菜单所需字段
	private String path;
	private String title;
	
	
	public Function() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFunction_name() {
		return function_name;
	}

	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
		this.setTitle(menu);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.setPath(url);
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}

	public int getIs_menu() {
		return is_menu;
	}

	public void setIs_menu(int is_menu) {
		this.is_menu = is_menu;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<Function> getChildren() {
		return children;
	}

	public void setChildren(List<Function> childrens) {
		this.children = childrens;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getHalf() {
		return half;
	}

	public void setHalf(int half) {
		this.half = half;
	}

	public int getOption_password_auth() {
		return option_password_auth;
	}

	public void setOption_password_auth(int option_password_auth) {
		this.option_password_auth = option_password_auth;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
