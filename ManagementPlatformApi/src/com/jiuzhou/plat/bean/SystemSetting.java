package com.jiuzhou.plat.bean;

import java.util.Date;

/**
 * 系统设置基础类
 * @author xingmh
 */
public class SystemSetting {
	
	/**
	 * 用户禁止登录后，可再次登录需等待的时长，单位：分钟
	 */
	public static final String LOGIN_WAIT_TIME = "login_wait_time";
	
	/**
	 * 允许连续输入错误的操作密码次数，如果超过则退出当前登录
	 */
	public static final String OPERATE_ALLOW_SUM = "operate_allow_sum";
	
	/**
	 * 允许连续登录失败的次数，如果超过则帐号被封
	 */
	public static final String LOGIN_ALLOW_SUM = "login_allow_sum";
	/**
	 * 用户session时间，单位：分钟
	 */
	public static final String LOGIN_SESSION_TIME = "login_session_time";
	
	/**
	 * 硬盘使用率告警阀值
	 */
	public static final String DISK_WARNING = "disk_warning";
	
	/**
	 * 管理口IP
	 */
	public static final String MANAGE_IP = "manage_ip";

	/**
	 * 用户登录密码是否必须包含小写字母
	 */
	public static final String LOGIN_PASS_LOWERCASE = "login_pass_lowercase";
	
	/**
	 * 用户登录密码是否必须包含大写字母
	 */
	public static final String LOGIN_PASS_UPPERCASE = "login_pass_uppercase";
	
	/**
	 * 用户登录密码是否必须包含特殊字符
	 */
	public static final String LOGIN_PASS_SPECIAL_CHAR = "login_pass_special_char";
	
	/**
	 * 用户登录密码是否必须包含数字
	 */
	public static final String LOGIN_PASS_NUMBER = "login_pass_number";
	
	/**
	 * 用户登录密码最小长度
	 */
	public static final String LOGIN_PASS_LENGTH = "login_pass_length";
	
	/**
	 * 用户登录密码更新周期
	 */
	public static final String LOGIN_PASS_CYCLE = "login_pass_cycle";
	
	/**
	 * 用户操作密码验证时间间隔
	 */
	public static final String OPERATE_PASS_TIME = "operate_pass_time";
	
	/**
	 * 当前平台标识
	 */
	public static final String PLAT_ID = "plat_id";
	
	private int		id;				//id
	private String 	name;			//设置名称
	private	String 	value;			//值
	private String 	default_value;	//默认值
	private String	description;	//描述
	private String  type;			//值类型
	private Date	add_time;		//添加时间	
	private String 	add_user;		//添加者
	private Date	update_time;	//更新时间
	private String 	update_user;	//更新者
	private int min;                //最小值
	private int max;                //最大值
	
	public SystemSetting() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public int getIntValue() {
		
		try {
			int intVal = Integer.parseInt(value);
			if (LOGIN_WAIT_TIME.equals(this.name)) {
				if (intVal < 1) {
					intVal = Integer.parseInt(default_value);
				}
				
			} else if (OPERATE_ALLOW_SUM.equals(this.name)) {
				if (intVal < 1) {
					intVal = Integer.parseInt(default_value);
				}
				
			} else if (DISK_WARNING.equals(this.name)) {
				if (intVal < 60 || intVal > 80) {
					intVal = Integer.parseInt(default_value);
				}
				
			} else if (LOGIN_ALLOW_SUM.equals(this.name)) {
				if (intVal < 1) {
					intVal = Integer.parseInt(default_value);
				}
				
			} else if (LOGIN_PASS_LOWERCASE.equals(this.name) 
					|| LOGIN_PASS_UPPERCASE.equals(this.name) 
					|| LOGIN_PASS_SPECIAL_CHAR.equals(this.name)
					|| LOGIN_PASS_NUMBER.equals(this.name)) {
				if (intVal !=0 && intVal != 1) {
					intVal = Integer.parseInt(default_value);
				}
				
			} else if (LOGIN_PASS_LENGTH.equals(this.name)) {
				if (intVal < 6 || intVal > 15) {
					intVal = Integer.parseInt(default_value);
				}
				
			} else if (LOGIN_PASS_CYCLE.equals(this.name)) {
				if (intVal < 1) {
					intVal = Integer.parseInt(default_value);
				}
			}
			return intVal;
		} catch (Exception e) {
			return Integer.parseInt(default_value);
		}
		
	}
	
	public int getDefaultIntValue() {
		return Integer.parseInt(default_value);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	
}
