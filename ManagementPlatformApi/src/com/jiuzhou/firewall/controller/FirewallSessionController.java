package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallSessionControlService;
import com.jiuzhou.firewall.service.FirewallSessionService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月21日 下午6:41:39
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallSessionController {
	
	@Autowired
	private FirewallSessionService firewallSessionService;
	
	@Autowired
	private FirewallSessionControlService firewallSessionControlService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_session.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_session.search".equals(method)) {
				return firewallSessionService.search(paramJson, request);
				
			} else if ("firewall_session.update".equals(method)) {
				return firewallSessionService.updateSession(paramJson, request);
				
			} else if ("firewall_session.clear".equals(method)) {
				return firewallSessionService.clearSession(paramJson, request);
				
			} else if ("firewall_session.setting".equals(method)) {
				return firewallSessionService.updateSessionSetting(paramJson, request);
				
			} else if ("firewall_session_control.search".equals(method)) {
				return firewallSessionControlService.listByDeviceName(paramJson, request);
				
			} else if ("firewall_session_control.add".equals(method)) {
				return firewallSessionControlService.add(paramJson, request);
				
			} else if ("firewall_session_control.edit".equals(method)) {
				return firewallSessionControlService.update(paramJson, request);
				
			} else if ("firewall_session_control.delete".equals(method)) {
				return firewallSessionControlService.delete(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}

}
