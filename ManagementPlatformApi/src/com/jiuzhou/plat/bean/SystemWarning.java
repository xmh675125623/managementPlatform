package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年11月7日 下午7:57:20
* 系统告警枚举
*/
public class SystemWarning {
	
	
	public static final SystemWarning DISK_USAGE = new SystemWarning("磁盘告警", "磁盘使用率已达到告警阀值，请及时备份旧的防火墙日志和操作日志，以避免丢失重要信息");
	
	private SystemWarning() {
		// TODO Auto-generated constructor stub
	}
	
	private String title;
	private String description;
	
	private SystemWarning(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
