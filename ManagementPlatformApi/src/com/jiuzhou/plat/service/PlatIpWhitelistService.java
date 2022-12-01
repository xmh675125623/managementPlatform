package com.jiuzhou.plat.service;

import com.jiuzhou.plat.cache.AdminUserLoginInfo;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午7:33:43
* 类说明
*/
public interface PlatIpWhitelistService {
	
	/**
	 * 获取ip地址白名单列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getIpWhitelists(JSONObject paramJson) throws Exception;
	
	/**
	 * 添加ip地址白名单
	 * @param paramJson
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	public String addIpWhitelist(JSONObject paramJson, AdminUserLoginInfo loginInfo) throws Exception;
	
	/**
	 * 删除ip地址白名单
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteIpWhitelist(JSONObject paramJson) throws Exception;
	
}
