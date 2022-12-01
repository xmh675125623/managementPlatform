package com.jiuzhou.plat.service;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年10月25日 下午2:58:09
* 类说明
*/
public interface AuditReportService {
	
	/**
	 * 生成报表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String createReport(JSONObject paramJson) throws Exception;
	
	/**
	 * 导出报表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String exportReport(JSONObject paramJson, HttpServletResponse response) throws Exception;
}
