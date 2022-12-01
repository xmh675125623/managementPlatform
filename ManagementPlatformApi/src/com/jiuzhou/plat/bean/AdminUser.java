package com.jiuzhou.plat.bean;

import java.util.Date;

import com.jiuzhou.plat.util.DateUtils;

/**
 * 用户基础类
 * @author xingmh
 */
public class AdminUser {
	
	private int id;
	private String user_account;		//登录帐号
	private String password;			//登录密码
	private String option_password;		//操作密码
	private int role_id;				//角色id
	private String role_name;			//角色名
	private String user_name;			//名字
	private Date add_time;				//添加时间
	private String add_time_str;		//添加时间format
	private String add_user;			//添加者
	private Date update_time;			//变更时间
	private String update_time_str;		//变更时间format
	private String update_user;			//变更者
	private String remark;				//备注
	private int del_flag;				//删除标志 1：删除
	private int num;					//登录失败次数
	private int operate_num;			//操作密码输入失败次数
	private Date login_time;			//最后登录时间
	private String login_time_str;		//最后登录时间format
	private Date login_fail_time;		//登录密码输入错误时间	
	private Date login_password_update_time;//最后一次变更登录密码时间
	
	public AdminUser() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOption_password() {
		return option_password;
	}

	public void setOption_password(String option_password) {
		this.option_password = option_password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
		if (add_time != null) {
			this.setAdd_time_str(DateUtils.toSimpleDateTime(add_time));
		}
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
		if (update_time != null) {
			this.setUpdate_time_str(DateUtils.toSimpleDateTime(update_time));
		}
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
		if (login_time != null) {
			this.setLogin_time_str(DateUtils.toSimpleDateTime(login_time));
		}
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getAdd_time_str() {
		return add_time_str;
	}

	public void setAdd_time_str(String add_time_str) {
		this.add_time_str = add_time_str;
	}

	public String getUpdate_time_str() {
		return update_time_str;
	}

	public void setUpdate_time_str(String update_time_str) {
		this.update_time_str = update_time_str;
	}

	public String getLogin_time_str() {
		return login_time_str;
	}

	public void setLogin_time_str(String login_time_str) {
		this.login_time_str = login_time_str;
	}
	
	
	public String toLogDescription () {
		return "[账号：" + this.getUser_account() + "]"
			+ "[密码：" + this.getPassword() + "]"
			+ "[操作密码：" + this.getOption_password() + "]"
			+ "[角色：" + this.getRole_name() + "]"
			+ "[用户名：" + this.getUser_name() + "]"
			+ "[备注：" + this.getRemark() + "]";
	}

	public int getOperate_num() {
		return operate_num;
	}

	public void setOperate_num(int operate_num) {
		this.operate_num = operate_num;
	}

	public Date getLogin_fail_time() {
		return login_fail_time;
	}

	public void setLogin_fail_time(Date login_fail_time) {
		this.login_fail_time = login_fail_time;
	}

	public Date getLogin_password_update_time() {
		return login_password_update_time;
	}

	public void setLogin_password_update_time(Date login_password_update_time) {
		this.login_password_update_time = login_password_update_time;
	}

}
