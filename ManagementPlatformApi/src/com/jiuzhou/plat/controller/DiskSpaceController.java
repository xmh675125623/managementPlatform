package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.DiskSpaceService;
import com.jiuzhou.plat.util.WebDataAESUtils;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月28日 上午10:54:45
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class DiskSpaceController {
	
	@Autowired
	private DiskSpaceService diskSpaceService;
	
	@ResponseBody
	@RequestMapping(value="/disk_space.do")
	public String diskSpace(@RequestBody String jsonParam, HttpServletResponse response) {
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("plat.disk_audit.search".equals(method)) {
				return diskSpaceService.getAuditLogTableInfos(paramJson);
				
			} else if ("plat.disk_audit.delete".equals(method)) {
				return diskSpaceService.deleteAuditLogTable(paramJson);
				
			} else if ("plat.disk_operate.search".equals(method)) {
				return diskSpaceService.getOperateLogTableInfos(paramJson);
				
			} else if ("plat.disk_operate.delete".equals(method)) {
				return diskSpaceService.deleteOperateLogTable(paramJson);
				
			} else if ("plat.disk_firewall.search".equals(method)) {
				return diskSpaceService.getFirewallLogTableInfos(paramJson);
				
			} else if ("plat.disk_firewall.delete".equals(method)) {
				return diskSpaceService.deleteFirewallLogTable(paramJson);
				
			} else if ("plat.disk_isolation.search".equals(method)) {
				return diskSpaceService.getIsolationLogTableInfos(paramJson);
				
			} else if ("plat.disk_isolation.delete".equals(method)) {
				return diskSpaceService.deleteIsolationLogTable(paramJson);
				
			} else if ("plat.disk_ids.search".equals(method)) {
				return diskSpaceService.getIDSLogTableInfos(paramJson);
				
			} else if ("plat.disk_ids.delete".equals(method)) {
				return diskSpaceService.deleteIDSLogTable(paramJson);
				
			} else if ("plat.disk_gateway.search".equals(method)) {
				return diskSpaceService.getGatewayLogTableInfos(paramJson);
				
			} else if ("plat.disk_gateway.delete".equals(method)) {
				return diskSpaceService.deleteGatewayLogTable(paramJson);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/disk_export.do")
	public String export(
			@RequestBody String jsonParam,
			HttpServletRequest request,
            HttpServletResponse response) {
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		
		try {

			String method = paramJson.getString("method");
			if ("plat.disk_operate.export".equals(method)) {
				return diskSpaceService.exportOperateLog(paramJson, response);
//				return diskSpaceService.getExportOperateLogToken(paramJson, response);
				
			} else if ("plat.disk_audit.export".equals(method)) {
				return diskSpaceService.exportAuditLog(paramJson, response);
//				return diskSpaceService.getExportAuditLogToken(paramJson, response);
				
			} else if ("plat.disk_firewall.export".equals(method)) {
				return diskSpaceService.exportFirewallLog(paramJson, response);
				
			} else if ("plat.disk_isolation.export".equals(method)) {
				return diskSpaceService.exportIsolationLog(paramJson, response);
				
			} else if ("plat.disk_ids.export".equals(method)) {
				return diskSpaceService.exportIDSLog(paramJson, response);
				
			} else if ("plat.disk_gateway.export".equals(method)) {
				return diskSpaceService.exportGatewayLog(paramJson, response);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/disk_import.do", produces="text/html;charset=UTF-8;")
	public String import_(
			HttpServletRequest request,
			HttpServletResponse response, 
			MultipartFile file) {
		try {
			
			// web数据解密
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			JSONObject paramJson = new JSONObject();
			paramJson.put("key", key);
			paramJson.put("value", value);
			String body = WebDataAESUtils.webDataDecrypt(paramJson.toString());
			paramJson = JSONObject.fromObject(body);
			
			String method = paramJson.getString("method");
			if ("plat.disk_operate.import".equals(method)) {
				return diskSpaceService.importOperateLog(response, file);
				
			} else if ("plat.disk_audit.import".equals(method)) {
				return diskSpaceService.importAuditLog(response, file);
				
			} else if ("plat.disk_firewall.import".equals(method)) {
				return diskSpaceService.importFirewallLog(response, file);
				
			} else if ("plat.disk_isolation.import".equals(method)) {
				return diskSpaceService.importIsolationLog(response, file);
				
			} else if ("plat.disk_ids.import".equals(method)) {
				return diskSpaceService.importIDSLog(response, file);
				
			} else if ("plat.disk_gateway.import".equals(method)) {
				return diskSpaceService.importGatewayLog(response, file);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new CommonResult(false, "系统发生未知错误").toString();
	}
		
	
}
