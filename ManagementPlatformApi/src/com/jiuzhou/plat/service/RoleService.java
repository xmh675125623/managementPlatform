package com.jiuzhou.plat.service;

import com.jiuzhou.plat.cache.AdminUserLoginInfo;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月5日 下午6:25:20
* 类说明
*/
public interface RoleService {
	
	/**
	 * 获取所有的角色列表
	 * @return
	 * @throws Exception
	 */
	public String getListNoPage() throws Exception;
	
	/**
	 * 添加角色
	 * @return
	 * @throws Exception
	 */
	public String addRole(JSONObject paramJson) throws Exception;
	
	/**
	 * 变更角色信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String updateRole(JSONObject paramJson, AdminUserLoginInfo loginInfo) throws Exception;
	
	/**
	 * 删除角色信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteRole(JSONObject paramJson, AdminUserLoginInfo loginInfo) throws Exception;
	
}
