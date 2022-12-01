package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午2:04:43
* 防火墙策略
*/
public abstract class FirewallStrategy {
	
	/**
	 * 规则类型：自定义
	 */
	public static final int RULE_TYPE_CUSTOM = 0;
	/**
	 * 规则类型：普通规则
	 */
	public static final int RULE_TYPE_COMMON = 1;
	/**
	 * 规则类型：特殊规则
	 */
	public static final int RULE_TYPE_SPECIAL = 2;
	/**
	 * 规则类型：opc classic tcp
	 */
	public static final int RULE_TYPE_OPC_CLASSIC_TCP = 3;
	/**
	 * 规则类型：modbus tcp
	 */
	public static final int RULE_TYPE_MODBUS_TCP = 4;
	
	/**
	 * 规则类型：iec104
	 */
	public static final int RULE_TYPE_IEC104 = 5;
	
	/**
	 * 规则类型：s7
	 */
	public static final int RULE_TYPE_S7 = 6;
	
	
	
	/**
	 * 时间类型：绝对时间
	 */
	public static final int TIME_TYPE_ABSOLUTE = 1;
	/**
	 * 时间类型：相对时间
	 */
	public static final int TIME_TYPE_RELATIVE = 2; 
	
	
	
	/**
	 * 规则行为：放行
	 */
	public static final int RULE_ACTION_PASS = 1;
	/**
	 * 规则行为：拦截
	 */
	public static final int RULE_ACTION_INTERCEPT = 2;
	
	
	
	/**
	 * 开启日志
	 */
	public static final int LOG_POWER_ON = 1;
	/**
	 * 不开启日志
	 */
	public static final int LOG_POWER_OFF = 2;
	
	
	
	private int    strategy_id;
	private String device_name;    //设备名
	private int    group_id;       //策略组id
	private String strategy_name;  //策略名
	private int rule_id;           //规则id
	private int    rule_type;      //规则类型0：自定义规则，1：普通规则，2：特殊规则，3：opc，4：modbus
	private String start_time;     //开始时间
	private String end_time;       //结束时间
	private int    time_type;      //1:绝对时间，2:相对时间
	private String relative_time;  //相对时间，一周中的哪几天
	private int    rule_action;    //规则1：放行，2：拦截
	private int    log_power;      //是否开启日志：1：开启，2：关闭
	private int    input_eth;      //进口网口
	private int    output_eth;     //出口网口
	private int    delete_flag;    //删除标志位
	private int    sync_flag;      //同步标志位
	private Date   add_time;
	private String add_user;
	private Date   update_time;
	private String update_user;
	
	private FirewallDeviceEthernet inputEthInfo;   //入口网口详细信息
	private FirewallDeviceEthernet outputEthInfo;  //出口网口详细信息
	
	public FirewallStrategy() {
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getStrategy_name() {
		return strategy_name;
	}

	public void setStrategy_name(String strategy_name) {
		this.strategy_name = strategy_name;
	}

	public int getRule_type() {
		return rule_type;
	}

	public void setRule_type(int rule_type) {
		this.rule_type = rule_type;
	}

	public int getTime_type() {
		return time_type;
	}

	public void setTime_type(int time_type) {
		this.time_type = time_type;
	}

	public String getRelative_time() {
		return relative_time;
	}

	public void setRelative_time(String relative_time) {
		this.relative_time = relative_time;
	}

	public int getRule_action() {
		return rule_action;
	}

	public void setRule_action(int rule_action) {
		this.rule_action = rule_action;
	}

	public int getLog_power() {
		return log_power;
	}

	public void setLog_power(int log_power) {
		this.log_power = log_power;
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

	public int getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(int strategy_id) {
		this.strategy_id = strategy_id;
	}

	public int getSync_flag() {
		return sync_flag;
	}

	public void setSync_flag(int sync_flag) {
		this.sync_flag = sync_flag;
	}
	
	protected String getLogDescProtected() {
		return "[设备名："+this.getDevice_name()+"]\n"
				+ "[策略组id："+this.getGroup_id()+"]\n"
				+ "[策略名："+this.getStrategy_name()+"]\n"
				+ "[规则类型："+this.getRuleTypeName(this.getRule_type())+"]\n"
				+ "[时间类型："+this.getTimeTypeName(this.getTime_type())+"]\n"
				+ "[相对日期："+this.getRelative_time()+"]\n"
				+ "[开始时间："+this.getStart_time()+"]\n"
				+ "[结束时间："+this.getEnd_time()+"]\n"
				+ "[规则行为："+this.getRuleActionName(this.getRule_action())+"]\n"
				+ "[日志："+this.getLogPowerName(this.getLog_power())+"]\n";
	}
	
	private String getRuleTypeName(int ruleType){
		switch (ruleType) {
			case RULE_TYPE_CUSTOM:
				return "自定义规则";
			case RULE_TYPE_COMMON:
				return "普通规则（库引用）";
			case RULE_TYPE_SPECIAL:
				return "特殊规则（库引用）";
			case RULE_TYPE_OPC_CLASSIC_TCP:
				return "OPC Classic TCP";
			case RULE_TYPE_MODBUS_TCP:
				return "MODBUS TCP";
			case RULE_TYPE_IEC104:
				return "IEC104";
			default:
				return "未知";
		}
	}
	
	private String getTimeTypeName(int timeType){
		switch (timeType) {
			case TIME_TYPE_ABSOLUTE:
				return "绝对时间";
			case TIME_TYPE_RELATIVE:
				return "相对时间";
			default:
				return "未知";
		}
	}
	
	private String getRuleActionName(int ruleAction) {
		switch (ruleAction) {
			case RULE_ACTION_PASS:
				return "放行";
			case RULE_ACTION_INTERCEPT:
				return "拦截";
			default:
				return "未知";
		}
	}
	
	private String getLogPowerName(int logPower) {
		switch (logPower) {
			case LOG_POWER_ON:
				return "开启";
			case LOG_POWER_OFF:
				return "关闭";
			default:
				return "未知";
		}
	}
	
	public abstract String getLogDesc();

	public int getRule_id() {
		return rule_id;
	}

	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getInput_eth() {
		return input_eth;
	}

	public void setInput_eth(int input_eth) {
		this.input_eth = input_eth;
	}

	public int getOutput_eth() {
		return output_eth;
	}

	public void setOutput_eth(int output_eth) {
		this.output_eth = output_eth;
	}

	public FirewallDeviceEthernet getInputEthInfo() {
		return inputEthInfo;
	}

	public void setInputEthInfo(FirewallDeviceEthernet inputEthInfo) {
		this.inputEthInfo = inputEthInfo;
	}

	public FirewallDeviceEthernet getOutputEthInfo() {
		return outputEthInfo;
	}

	public void setOutputEthInfo(FirewallDeviceEthernet outputEthInfo) {
		this.outputEthInfo = outputEthInfo;
	}

}
