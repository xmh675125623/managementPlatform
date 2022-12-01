package com.jiuzhou.plat.service;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午5:10:35
* 类说明
*/
public interface AuditEventlevelService {
	
	/**
	 * 获取事件分级列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getEventLevelList(JSONObject paramJson) throws Exception;
	
	/**
	 * 更新事件分级
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String updateEventLevel(JSONObject paramJson) throws Exception;
	
}
