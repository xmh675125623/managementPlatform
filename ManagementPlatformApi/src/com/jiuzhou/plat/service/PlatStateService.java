package com.jiuzhou.plat.service;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2023年1月17日 上午10:23:33
* 类说明
*/
public interface PlatStateService {
	
	/**
	 * 获取当前平台状态信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getPlatState(JSONObject paramJson) throws Exception;

}
