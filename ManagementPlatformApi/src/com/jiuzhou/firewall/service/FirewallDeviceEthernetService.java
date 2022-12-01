package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月17日 上午11:00:52
* 类说明
*/
public interface FirewallDeviceEthernetService {
	
	/**
	 * 根据设备标识获取网口列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String listByDeviceName(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 获取网口状态列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String listForStatus(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 变更网口mode
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateMode(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 变更网口网络信息，ip，网关等
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateNetworkInfo(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
}
