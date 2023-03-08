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
	
	/**
	 * 计数类型 事件分类
	 */
	public static final int COUNT_TYPE_LOG_EVENT = 3;
	
	/**
	 * 计数类型 平台总日志数
	 */
	public static final int COUNT_PLAT_LOG_SUM = 4;
	
	/**
	 * 计数类型 审计总日志数
	 */
	public static final int COUNT_AUDIT_LOG_SUM = 5;
	
	/**
	 * 计数类型 防火墙总日志数
	 */
	public static final int COUNT_FIREWALL_LOG_SUM = 6;
	
	/**
	 * 计数类型 隔离总日志数
	 */
	public static final int COUNT_ISOLATION_LOG_SUM = 7;
	
	/**
	 * 计数类型 IDS总日志数
	 */
	public static final int COUNT_IDS_LOG_SUM = 8;
	
	/**
	 * 计数类型 网关总日志数
	 */
	public static final int COUNT_GATEWAY_LOG_SUM = 9;
	
	/**
	 * 计数类型 网关总日志数
	 */
	public static final int COUNT_WARN_LOG = 10;

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
