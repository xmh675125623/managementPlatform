package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import com.jiuzhou.firewall.bean.FirewallMac;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月14日 下午2:36:15
* 类说明
*/
public interface FirewallMacService {
	
	/**
	 * 获取设备的绑定列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getListByDeviceName(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 获取自动绑定设备列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getAutoBindList(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 添加绑定
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String add(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 修改绑定
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String update(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 删除绑定
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String delete(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 自动扫描开关
	 * @return
	 * @throws Exception
	 */
	public String updateAutoPower(JSONObject paramJson, HttpServletRequest request) throws Exception;
	
	/**
	 * 插入扫描到的mac信息
	 * @param mac
	 */
	public void insertScanMac(FirewallMac mac);
	
}
