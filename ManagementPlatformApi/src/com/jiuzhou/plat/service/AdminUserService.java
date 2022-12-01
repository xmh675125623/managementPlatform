package com.jiuzhou.plat.service;

import com.jiuzhou.plat.cache.AdminUserLoginInfo;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月4日 下午2:42:13
* 类说明
*/
public interface AdminUserService {

	/**
	 * 分页获取用户列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getListByPage(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取所有用户列表
	 * @return
	 * @throws Exception
	 */
	public String getListNoPage() throws Exception;
	
	/**
	 * 变更用户信息
	 * @return
	 * @throws Exception
	 */
	public String update(JSONObject paramJson, int logId, String logTableName) throws Exception;
	
	/**
	 * 添加用户信息
	 * @return
	 * @throws Exception
	 */
	public String insert(JSONObject paramJson, int logId, String logTableName) throws Exception;
	
	/**
	 * 删除用户信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String delete(JSONObject paramJson, int logId, String logTableName) throws Exception;
	
	/**
	 * 获取自己的信息
	 * @return
	 * @throws Exception
	 */
	public String getSelfUserInfo(JSONObject paramJson) throws Exception;
	
	/**
	 * 修改密码
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String updatePassword(JSONObject paramJson, AdminUserLoginInfo loginInfo, int logId, String logTableName) throws Exception;
	
}
