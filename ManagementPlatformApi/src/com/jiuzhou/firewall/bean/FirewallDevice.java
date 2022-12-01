package com.jiuzhou.firewall.bean;

import java.util.Date;

/**
* @author xingmh
* @version 创建时间：2019年1月2日 下午1:55:37
* 类说明 防火墙设备实体类
*/
public class FirewallDevice {
	
	/**
	 * 设备模式：初始模式
	 */
	public static final int MODE_PASSIVE = 1;
	/**
	 * 设备模式：测试模式
	 */
	public static final int MODE_TEST = 2;
	/**
	 * 设备模式：运行模式
	 */
	public static final int MODE_OPERATIONAL = 3;
	/**
	 * 设备模式：学习模式
	 */
	public static final int MODE_STUDY = 4;
	
	private String 	device_name;		//设备名称（唯一标识）
	private String 	device_mark;		//设备备注名称
	private String	said;				//1路由模式2网桥模式
	private String  mip;				//主连接IP
	private String  uip;				//备连接IP
	private String  by_pass;			//by_pass
	private String	heartbeat;			//心跳间隔
	private String  ip_address;			//ip地址
	private String  subnetmask;			//子网掩码
	private String 	gateway;			//默认网关
	private String	introduction;		//简介
	private int  	mode;				//当前模式1出厂模式2初始模式3测试模式4运行模式
	private String  state;				//完好状态
	private String  modal;				//设备型号
	private Date	insert_time;		//插入时间
	private int		del_flag;			//删除标志位
	private String 	tempo;				//日志速率
	private String 	edition;			//版本
	private String  styles;				//状态0正常1非正常
	private String  syslog;				//logIP地址
	private String  logport;			//log端口
	private String 	linecomplete;		//用于会话管理标志链接完整性
	private String 	syslog_ip;			//syslog配置IP
	private String  syslog_protocol;	//syslog配置协议
	private String 	syslog_port;		//syslog配置端口
	private int     syslog_power;       //syslog开关
	private String  snmp_ip;            //SNMP配置ip
	private String  snmp_port;			//SNMP配置端口
	private String  snmp_version;       //SNMP版本
	private int     snmp_power;         //SNMP开关
	private String  ntp_ip;				//NTP配置ip
	private String  ntp_password;       //NTP配置密钥
	private int     ntp_power;          //NTP开关
	private int     anti_attack_dos;	//是否开启全局抗dos攻击，0：关闭，1：开启
	private int     anti_attack_scan;   //是否开启全局扫描，0：关闭，1：开启
	private int     default_rule_action;//默认规则行为，1：拦截，2：放行
	private int     default_rule_log;   //默认日志记录，1：开启，2：关闭
	private int     dynamic_route_power;//动态路由开关
	private String  dynamic_route_action;//动态路由RIP/OSPF
	private int     auto_ip_mac;         //ip/mac自动开关
	private int     study_mode;          //学习模式，0：关闭，1：开启
	
	public FirewallDevice() {
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getDevice_mark() {
		return device_mark;
	}

	public void setDevice_mark(String device_mark) {
		this.device_mark = device_mark;
	}

	public String getSaid() {
		return said;
	}

	public void setSaid(String said) {
		this.said = said;
	}

	public String getMip() {
		return mip;
	}

	public void setMip(String mip) {
		this.mip = mip;
	}

	public String getUip() {
		return uip;
	}

	public void setUip(String uip) {
		this.uip = uip;
	}

	public String getBy_pass() {
		return by_pass;
	}

	public void setBy_pass(String by_pass) {
		this.by_pass = by_pass;
	}

	public String getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(String heartbeat) {
		this.heartbeat = heartbeat;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
	}

	public String getSyslog() {
		return syslog;
	}

	public void setSyslog(String syslog) {
		this.syslog = syslog;
	}

	public String getLogport() {
		return logport;
	}

	public void setLogport(String logport) {
		this.logport = logport;
	}

	public String getLinecomplete() {
		return linecomplete;
	}

	public void setLinecomplete(String linecomplete) {
		this.linecomplete = linecomplete;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getSyslog_ip() {
		return syslog_ip;
	}

	public void setSyslog_ip(String syslog_ip) {
		this.syslog_ip = syslog_ip;
	}

	public String getSyslog_protocol() {
		return syslog_protocol;
	}

	public void setSyslog_protocol(String syslog_protocol) {
		this.syslog_protocol = syslog_protocol;
	}

	public String getSyslog_port() {
		return syslog_port;
	}

	public void setSyslog_port(String syslog_port) {
		this.syslog_port = syslog_port;
	}

	public String getNtp_ip() {
		return ntp_ip;
	}

	public void setNtp_ip(String ntp_ip) {
		this.ntp_ip = ntp_ip;
	}

	public String getModal() {
		return modal;
	}

	public void setModal(String modal) {
		this.modal = modal;
	}

	public int getAnti_attack_dos() {
		return anti_attack_dos;
	}

	public void setAnti_attack_dos(int anti_attack_dos) {
		this.anti_attack_dos = anti_attack_dos;
	}

	public int getAnti_attack_scan() {
		return anti_attack_scan;
	}

	public void setAnti_attack_scan(int anti_attack_scan) {
		this.anti_attack_scan = anti_attack_scan;
	}

	public int getDefault_rule_action() {
		return default_rule_action;
	}

	public void setDefault_rule_action(int default_rule_action) {
		this.default_rule_action = default_rule_action;
	}

	public int getDefault_rule_log() {
		return default_rule_log;
	}

	public void setDefault_rule_log(int default_rule_log) {
		this.default_rule_log = default_rule_log;
	}

	public String getSnmp_port() {
		return snmp_port;
	}

	public void setSnmp_port(String snmp_port) {
		this.snmp_port = snmp_port;
	}

	public String getNtp_password() {
		return ntp_password;
	}

	public void setNtp_password(String ntp_password) {
		this.ntp_password = ntp_password;
	}

	public int getDynamic_route_power() {
		return dynamic_route_power;
	}

	public void setDynamic_route_power(int dynamic_route_power) {
		this.dynamic_route_power = dynamic_route_power;
	}

	public String getDynamic_route_action() {
		return dynamic_route_action;
	}

	public void setDynamic_route_action(String dynamic_route_action) {
		this.dynamic_route_action = dynamic_route_action;
	}

	public String getSnmp_ip() {
		return snmp_ip;
	}

	public void setSnmp_ip(String snmp_ip) {
		this.snmp_ip = snmp_ip;
	}

	public String getSnmp_version() {
		return snmp_version;
	}

	public void setSnmp_version(String snmp_version) {
		this.snmp_version = snmp_version;
	}

	public int getAuto_ip_mac() {
		return auto_ip_mac;
	}

	public void setAuto_ip_mac(int auto_ip_mac) {
		this.auto_ip_mac = auto_ip_mac;
	}

	public int getNtp_power() {
		return ntp_power;
	}

	public void setNtp_power(int ntp_power) {
		this.ntp_power = ntp_power;
	}

	public int getSyslog_power() {
		return syslog_power;
	}

	public void setSyslog_power(int syslog_power) {
		this.syslog_power = syslog_power;
	}

	public int getSnmp_power() {
		return snmp_power;
	}

	public void setSnmp_power(int snmp_power) {
		this.snmp_power = snmp_power;
	}

	public int getStudy_mode() {
		return study_mode;
	}

	public void setStudy_mode(int study_mode) {
		this.study_mode = study_mode;
	}
	
	
}
