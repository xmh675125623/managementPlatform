package com.jiuzhou.plat.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2023年1月9日 上午10:20:55
* 类说明
*/
public interface IsolationLogService {
	
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
	 * 删除log
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteLog(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	/**
	 * 清空log表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String emptyLog(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	
}
