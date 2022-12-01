package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.AuditReportService;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年10月25日 下午4:01:06
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class AuditReportController {
	
	@Autowired
	private AuditReportService auditReportService;
	
	@ResponseBody
	@RequestMapping(value="/report.do")
	public String whitelist(@RequestBody String jsonParam, HttpServletResponse response) {
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			AdminUserLoginInfo loginInfo = 
				new ServiceBase().getCache(
						ServiceBase.CACHE_LOGIN_INFO+paramJson.getInt("aid"), 
						AdminUserLoginInfo.class);
			if ("audit_report.search".equals(method)) {
				return auditReportService.createReport(paramJson);
				
			} else if ("audit_report.export".equals(method)) {
				return auditReportService.exportReport(paramJson, response);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
	}
}
