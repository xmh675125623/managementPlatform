package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午1:50:51
* 类说明
*/
public interface FirewallAssetService {

	/**
	 * 获取所有资产列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getAll(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 添加资产信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String add(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 编辑资产信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String update(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 删除资产信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String delete(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
}
