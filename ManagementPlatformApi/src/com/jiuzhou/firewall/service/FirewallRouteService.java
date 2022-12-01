package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月16日 下午4:50:45
* 类说明
*/
public interface FirewallRouteService {
	
	/**
	 * 根据设备标识获取route列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String listByDeviceName(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 添加
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String add(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 编辑
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String update(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 删除
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String delete(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 设置动态路由
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateDynamicRouteSetting(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
}
