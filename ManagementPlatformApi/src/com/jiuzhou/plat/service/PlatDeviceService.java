package com.jiuzhou.plat.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2021年11月22日 下午4:23:11
* 类说明
*/
public interface PlatDeviceService {
	
	/**
	 * 添加设备
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String addDevice(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	/**
	 * 移除设备
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String removeDevice(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	/**
	 * 编辑设备信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateDevice(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	/**
	 * 分页获取设备列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getListByPage(JSONObject paramJson) throws Exception;
	
	/**
	 * 根据ip获取设备名
	 * @param ipAddress
	 * @return
	 */
	public String getDeviceName(String ipAddress);

}
