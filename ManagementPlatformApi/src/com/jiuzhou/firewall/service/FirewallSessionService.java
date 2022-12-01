package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月21日 下午5:09:29
* 类说明
*/
public interface FirewallSessionService {

	/**
	 * 根据设备标识及源ip查询
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String search(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 更新会话列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateSession(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 更新会话管理相关设置
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateSessionSetting(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 清空会话
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String clearSession(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
}
