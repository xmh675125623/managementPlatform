package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月7日 下午1:59:32
* 类说明
*/
public interface FirewallDeviceService {

	/**
	 * 防火墙设备探索
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String sniffDevice(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	/**
	 * 将探索到的设备添加到设备管理
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String addToManage(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	/**
	 * 获取设备列表用于页面select标签
	 * @return
	 * @throws Exception
	 */
	public JSONArray getForSelectTag() throws Exception;
	
	/**
	 * 根据设备名获取设备信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getDeviceInfo(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 更新设备信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String updateDevice(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
}
