package com.jiuzhou.plat.service;

import net.sf.json.JSONObject;

public interface AdminLoginService {
	
	/**
	 * 管理员登录
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String Login(JSONObject paramJson) throws Exception;
	
}
