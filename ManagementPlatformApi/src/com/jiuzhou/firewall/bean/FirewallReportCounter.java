package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2020年9月28日 下午3:47:38
* 类说明
*/
public class FirewallReportCounter {
	
	/**
	 * 计数类型 日志模块
	 */
	public static final int COUNT_TYPE_LOG_MODULE = 1;
	
	/**
	 * 计数类型 日志等级
	 */
	public static final int COUNT_TYPE_LOG_LEVEL = 2;

	private int    id;
	private String device_name;    //设备名
	private String count_date;     //计数日期
	private int    count_type;     //计数类型，1：日志模块，2：日志等级
	private String count_title;    //计数题目
	private long   count_num;      //数量
	
	public FirewallReportCounter() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount_type() {
		return count_type;
	}

	public void setCount_type(int count_type) {
		this.count_type = count_type;
	}

	public String getCount_title() {
		return count_title;
	}

	public void setCount_title(String count_title) {
		this.count_title = count_title;
	}

	public long getCount_num() {
		return count_num;
	}

	public void setCount_num(long count_num) {
		this.count_num = count_num;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getCount_date() {
		return count_date;
	}

	public void setCount_date(String count_date) {
		this.count_date = count_date;
	}
	
	
}
