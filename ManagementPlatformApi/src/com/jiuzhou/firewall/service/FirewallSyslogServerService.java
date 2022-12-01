package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import com.jiuzhou.firewall.bean.FirewallDevice;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2020年8月19日 下午4:20:41
* 类说明
*/
public interface FirewallSyslogServerService {
	
	/**
	 * 根据设备标识获取nat列表
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
	
	public String giveSyslogToDevice(FirewallDevice device) throws Exception;
	
}
