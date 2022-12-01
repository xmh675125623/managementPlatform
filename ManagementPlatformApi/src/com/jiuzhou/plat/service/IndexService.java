package com.jiuzhou.plat.service;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年11月6日 下午5:16:20
* 类说明
*/
public interface IndexService {
	
	/**
	 * 获取首页数据
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getIndexData(JSONObject paramJson) throws Exception;
	
	/**
	 * 同步设备时间
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String syncTime(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取系统告警
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String systemWarning(JSONObject paramJson) throws Exception;
	
}
