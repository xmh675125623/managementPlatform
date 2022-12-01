package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月23日 下午2:34:29
* 类说明
*/
public interface FirewallAntiAttackService {
	
	/**
	 * 获取抗攻击设置信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getInfo(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 修改抗攻击设置信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String update(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
}
