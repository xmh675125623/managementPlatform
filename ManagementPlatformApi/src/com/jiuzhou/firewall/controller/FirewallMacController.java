package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallMacService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月14日 下午4:54:02
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallMacController {

	@Autowired
	private FirewallMacService firewallMacService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_mac.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_mac.search".equals(method)) {
				return firewallMacService.getListByDeviceName(paramJson, request);
				
			} else if ("firewall_mac.auto_scan".equals(method)) {
				return firewallMacService.getAutoBindList(paramJson, request);
				
			} else if ("firewall_mac.add".equals(method)) {
				return firewallMacService.add(paramJson, request);
				
			} else if ("firewall_mac.edit".equals(method)) {
				return firewallMacService.update(paramJson, request);
				
			} else if ("firewall_mac.delete".equals(method)) {
				return firewallMacService.delete(paramJson, request);
				
			} else if ("firewall_mac.auto_scan_power".equals(method)) {
				return firewallMacService.updateAutoPower(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
