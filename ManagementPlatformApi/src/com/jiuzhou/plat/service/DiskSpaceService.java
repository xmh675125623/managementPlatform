package com.jiuzhou.plat.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月28日 上午10:57:34
* 类说明
*/
public interface DiskSpaceService {
	
	/**
	 * 获取审计日志表信息列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getAuditLogTableInfos(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取最早的审计记录表
	 * @return
	 */
	public String getFirstAuditLogTableName() throws Exception;
	
	/**
	 * 删除审计日志表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteAuditLogTable(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取最早的操作日志表
	 * @return
	 */
	public String getFirstOperateLogTableName() throws Exception;
	
	/**
	 * 获取导出日志表token
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getExportAuditLogToken(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 加密导出审计日志表数据
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exportAuditLog(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 解密导入审计日志表数据
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String importAuditLog(HttpServletResponse response, MultipartFile  file) throws Exception;
	
	/**
	 * 获取操作日志表信息列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getOperateLogTableInfos(JSONObject paramJson) throws Exception;
	
	/**
	 * 删除操作日志表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteOperateLogTable(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取导出日志表token
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getExportOperateLogToken(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 加密导出操作日志表数据
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exportOperateLog(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 解密导入操作日志表数据
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String importOperateLog(HttpServletResponse response, MultipartFile  file) throws Exception;
	
	
	/**
	 * 获取防火墙日志表信息列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getFirewallLogTableInfos(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取最早的防火墙日志表
	 * @return
	 */
	public String getFirstFirewallLogTableName() throws Exception;
	
	/**
	 * 删除防火墙日志表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteFirewallLogTable(JSONObject paramJson) throws Exception;
	
	/**
	 * 加密导出防火墙日志表数据
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exportFirewallLog(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 解密导入防火墙日志表数据
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String importFirewallLog(HttpServletResponse response, MultipartFile  file) throws Exception;
	
	/**
	 * 获取隔离日志表信息列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getIsolationLogTableInfos(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取最早的隔离日志表
	 * @return
	 */
	public String getFirstIsolationLogTableName() throws Exception;
	
	/**
	 * 删除隔离日志表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteIsolationLogTable(JSONObject paramJson) throws Exception;
	
	/**
	 * 加密导出隔离日志表数据
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exportIsolationLog(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 解密导入隔离日志表数据
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String importIsolationLog(HttpServletResponse response, MultipartFile  file) throws Exception;
	
	
	/**
	 * 获取IDS日志表信息列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getIDSLogTableInfos(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取最早的IDS日志表
	 * @return
	 */
	public String getFirstIDSLogTableName() throws Exception;
	
	/**
	 * 删除IDS日志表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteIDSLogTable(JSONObject paramJson) throws Exception;
	
	/**
	 * 加密导出IDS日志表数据
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exportIDSLog(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 解密导入IDS日志表数据
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String importIDSLog(HttpServletResponse response, MultipartFile  file) throws Exception;
	
	/**
	 * 获取网关日志表信息列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String getGatewayLogTableInfos(JSONObject paramJson) throws Exception;
	
	/**
	 * 获取最早的IDS日志表
	 * @return
	 */
	public String getFirstGatewayLogTableName() throws Exception;
	
	/**
	 * 删除IDS日志表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public String deleteGatewayLogTable(JSONObject paramJson) throws Exception;
	
	/**
	 * 加密导出IDS日志表数据
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exportGatewayLog(JSONObject paramJson, HttpServletResponse response) throws Exception;
	
	/**
	 * 解密导入IDS日志表数据
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String importGatewayLog(HttpServletResponse response, MultipartFile  file) throws Exception;
	
}
