package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.AuditLogService;
import com.jiuzhou.plat.service.FirewallLogService;
import com.jiuzhou.plat.service.OperateLogService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年10月15日 上午9:01:50
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class LogController {
	
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private FirewallLogService firewallLogService;
	
	@ResponseBody
	@RequestMapping(value="/logs.do")
	public String logs(@RequestBody String jsonParam, HttpServletResponse response) {
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("log.audit.search".equals(method)) {
				return auditLogService.search(paramJson);
				
			} else if ("log.audit.search_export".equals(method)) {
				return auditLogService.exportSelectedLog(paramJson);
				
			} else if ("plat.operate_log.search".equals(method)) {
				return operateLogService.search(paramJson);
				
			} else if ("log.firewall.search".equals(method)) {
				return firewallLogService.search(paramJson);
				
			} else if ("log.firewall.search_export".equals(method)) {
				return firewallLogService.exportSelectedLog(paramJson);
				
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
}
