package com.jiuzhou.plat.service;

import com.jiuzhou.plat.cache.AdminUserLoginInfo;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年10月9日 下午7:38:42
* 类说明
*/
public interface AuditWhitelistService {
	
	/**
	 * 获取白名单列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getAuditWhitelistAll(JSONObject paramJson)throws Exception;
	
	/**
	 * 变更百名单信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String updateAuditWhitelist(JSONObject paramJson, 
										AdminUserLoginInfo loginInfo, 
										int logId, 
										String logTableName)throws Exception;
	
	/**
	 * 删除白名单
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteAuditWhitelist(JSONObject paramJson,
										AdminUserLoginInfo loginInfo, 
										int logId, 
										String logTableName)throws Exception;
	
	/**
	 * 添加白名单
	 * @param paramJson
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	public String addAuditWhitelist(JSONObject paramJson,
										AdminUserLoginInfo loginInfo, 
										int logId, 
										String logTableName)throws Exception;
	
	/**
	 * 添加白名单规则
	 * @param paramJson
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	public String addAuditWhitelistRule(JSONObject paramJson,
										AdminUserLoginInfo loginInfo,
										int logId, 
										String logTableName)throws Exception;
	
	/**
	 * 修改白名单规则
	 * @param paramJson
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	public String updateAuditWhitelistRule(JSONObject paramJson,
			AdminUserLoginInfo loginInfo,
			int logId, 
			String logTableName)throws Exception;
	
	/**
	 * 删除白名单规则
	 * @param paramJson
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	public String deleteAuditWhitelistRule(JSONObject paramJson,
			AdminUserLoginInfo loginInfo,
			int logId, 
			String logTableName)throws Exception;
}
