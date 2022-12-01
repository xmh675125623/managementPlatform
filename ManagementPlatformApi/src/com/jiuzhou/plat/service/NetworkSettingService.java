package com.jiuzhou.plat.service;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年11月8日 下午4:01:32
* 网络设置service
*/
public interface NetworkSettingService {
	
	/**
	 * 获取当前网络设置信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getNetworkSettingInfo(JSONObject paramJson) throws Exception;
	
	/**
	 * 变更网络设置信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String updateNetworkSetting(JSONObject paramJson) throws Exception;
	
}
