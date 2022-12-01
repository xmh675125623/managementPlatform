package com.jiuzhou.plat.service;

import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;

import net.sf.json.JSONObject;

/**
 * 
 * @author xingmh
 *
 */
public interface SystemSettingService {

	public SystemSetting getByName(String name) throws Exception;
	
	public String getList(JSONObject paramJson) throws Exception;
	
	public String update(JSONObject paramJson, AdminUserLoginInfo loginInfo) throws Exception;
	
	public double getDiskUsage() throws Exception;
	
}
