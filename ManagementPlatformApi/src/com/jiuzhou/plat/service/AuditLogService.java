package com.jiuzhou.plat.service;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * @author xingmh
 * @version 2018年10月12日
 */
public interface AuditLogService {
	
	/**
	  *  查询
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String search(JSONObject paramJson) throws Exception;
	
	/**
	 * 批量插入
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public void insertBathBySql(String sql) throws Exception;
	
	/**
	 * 批量插入
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public void insertBathBySqls(String deviceName, List<String> sql) throws Exception;
	
	/**
	 * 1U设备重启插入重启日志
	 * @throws Exception
	 */
	public void insertDeviceRestartLog();
	
	
}
