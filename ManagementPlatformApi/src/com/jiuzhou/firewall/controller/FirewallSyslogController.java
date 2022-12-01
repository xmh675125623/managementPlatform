package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallSyslogServerService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2020年8月19日 下午4:38:51
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallSyslogController {
	
	@Autowired
	private FirewallSyslogServerService firewallSyslogServerService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_syslog.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_syslog.search".equals(method)) {
				return firewallSyslogServerService.listByDeviceName(paramJson, request);
				
			} else if ("firewall_syslog.add".equals(method)) {
				return firewallSyslogServerService.add(paramJson, request);
				
			} else if ("firewall_syslog.edit".equals(method)) {
				return firewallSyslogServerService.update(paramJson, request);
				
			} else if ("firewall_syslog.delete".equals(method)) {
				return firewallSyslogServerService.delete(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
