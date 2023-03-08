package com.jiuzhou.plat.bean;

import java.util.LinkedList;

/**
* @author xingmh
* @version 创建时间：2023年3月7日 下午3:42:35
* 首页实时告警日志
*/
public class IndexWarnLog implements DeviceLog{
	
	public static final LinkedList<IndexWarnLog> WARN_LOGS = new LinkedList<>();
	
	private PlatDevice device;
	private String level;
	private String message;
	private String time;
	
	public IndexWarnLog() {
	}

	public PlatDevice getDevice() {
		return device;
	}

	public void setDevice(PlatDevice device) {
		this.device = device;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void addLog(IndexWarnLog log) {
		if (WARN_LOGS.size() >= 100) {
			WARN_LOGS.pop();
		}
		WARN_LOGS.add(log);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	

}
