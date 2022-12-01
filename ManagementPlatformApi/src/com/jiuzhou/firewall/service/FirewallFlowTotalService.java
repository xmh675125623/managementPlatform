package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年2月15日 下午2:35:49
* 类说明
*/
public interface FirewallFlowTotalService {

	/**
	 * 根据设备标识获取统计列表
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
	 * 更新统计信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateFlow(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 导出流量统计信息
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exportFlow(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
}
