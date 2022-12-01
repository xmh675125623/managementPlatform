package com.jiuzhou.plat.bean;

import java.util.Date;

/**
 * 角色功能基础类
 * @author xingmh
 */
public class RoleFunction {
	
	private int		id;
	private int		role_id;		//角色id
	private int 	function_id;	//功能/菜单id
	private int 	half;			//子菜单/功能是否全部选中，0是，1不是
	private Date	add_time;
	private String 	add_user;
	
	public RoleFunction() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getFunction_id() {
		return function_id;
	}

	public void setFunction_id(int function_id) {
		this.function_id = function_id;
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

	public int getHalf() {
		return half;
	}

	public void setHalf(int half) {
		this.half = half;
	}

	
}
