package com.jiuzhou.firewall.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jiuzhou.plat.bean.SystemWarning;

/**
* @author xingmh
* @version 创建时间：2019年1月11日 下午6:29:35
* 设备状态信息
*/
public class FirewallDeviceStatus {
	
	private LinkedList<String> cpuUsages = new LinkedList<>(); 	//CPU使用量
	private String core0 = "";									//CPU温度
	private String core1 = "";
	private String core2 = "";
	private String core3 = "";
	private String memoryUsage = "";							//内存使用率
	private String memoryTotal = "";                            //内存总量
	private String diskWarning = "";                            //磁盘使用率告警阀值
	private String diskUsage = "";								//硬盘使用率
	private String diskTotal = "";                              //硬盘容量
	private String upTime = "";									//运行时间
	private ArrayList<LinkedList<String>> inputTotal = new ArrayList<>(); //流量进
	private ArrayList<LinkedList<String>> outputTotal = new ArrayList<>(); //流量出
	private List<SystemWarning> systemWarnings = new ArrayList<>();//系统告警
	
	public FirewallDeviceStatus() {
	}

	public LinkedList<String> getCpuUsages() {
		return cpuUsages;
	}

	public void setCpuUsages(LinkedList<String> cpuUsages) {
		this.cpuUsages = cpuUsages;
	}

	public String getCore0() {
		return core0;
	}

	public void setCore0(String core0) {
		this.core0 = core0;
	}

	public String getCore1() {
		return core1;
	}

	public void setCore1(String core1) {
		this.core1 = core1;
	}

	public String getCore2() {
		return core2;
	}

	public void setCore2(String core2) {
		this.core2 = core2;
	}

	public String getCore3() {
		return core3;
	}

	public void setCore3(String core3) {
		this.core3 = core3;
	}

	public String getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public String getDiskUsage() {
		return diskUsage;
	}

	public void setDiskUsage(String diskUsage) {
		this.diskUsage = diskUsage;
	}

	public String getDiskWarning() {
		return diskWarning;
	}

	public void setDiskWarning(String diskWarning) {
		this.diskWarning = diskWarning;
	}

	public List<SystemWarning> getSystemWarnings() {
		return systemWarnings;
	}

	public void setSystemWarnings(List<SystemWarning> systemWarnings) {
		this.systemWarnings = systemWarnings;
	}

	public ArrayList<LinkedList<String>> getInputTotal() {
		return inputTotal;
	}

	public void setInputTotal(ArrayList<LinkedList<String>> inputTotal) {
		this.inputTotal = inputTotal;
	}

	public ArrayList<LinkedList<String>> getOutputTotal() {
		return outputTotal;
	}

	public void setOutputTotal(ArrayList<LinkedList<String>> outputTotal) {
		this.outputTotal = outputTotal;
	}

	public String getMemoryTotal() {
		return memoryTotal;
	}

	public void setMemoryTotal(String memoryTotal) {
		this.memoryTotal = memoryTotal;
	}

	public String getDiskTotal() {
		return diskTotal;
	}

	public void setDiskTotal(String diskTotal) {
		this.diskTotal = diskTotal;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}
	
	
}
