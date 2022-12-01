package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.AuditWhitelistService;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年10月10日 下午2:11:52
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class AuditWhitelistController {
	
	@Autowired
	private AuditWhitelistService auditWhitelistService;
	
	@ResponseBody
	@RequestMapping(value="/whitelist.do")
	public String whitelist(@RequestBody String jsonParam, HttpServletRequest request) {
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		
		int logId = (int) request.getAttribute("currentOperateId");
		String logTableName = (String) request.getAttribute("currentOperateTableName");
		
		try {

			String method = paramJson.getString("method");
			AdminUserLoginInfo loginInfo = 
				new ServiceBase().getCache(
						ServiceBase.CACHE_LOGIN_INFO+paramJson.getInt("aid"), 
						AdminUserLoginInfo.class);
			if ("whitelist.search".equals(method)) {
				return auditWhitelistService.getAuditWhitelistAll(paramJson);
				
			} else if ("whitelist.add".equals(method)) {
				return auditWhitelistService.addAuditWhitelist(paramJson, loginInfo, logId, logTableName);
				
			} else if ("whitelist.edit".equals(method)) {
				return auditWhitelistService.updateAuditWhitelist(paramJson, loginInfo, logId, logTableName);
				
			} else if ("whitelist.delete".equals(method)) {
				return auditWhitelistService.deleteAuditWhitelist(paramJson, loginInfo, logId, logTableName);
						
			} else if ("whitelist_rule.add".equals(method)) {
				return auditWhitelistService.addAuditWhitelistRule(paramJson, loginInfo, logId, logTableName);
				
			} else if ("whitelist_rule.edit".equals(method)) {
				return auditWhitelistService.updateAuditWhitelistRule(paramJson, loginInfo, logId, logTableName);
				
			} else if ("whitelist_rule.delete".equals(method)) {
				return auditWhitelistService.deleteAuditWhitelistRule(paramJson, loginInfo, logId, logTableName);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
