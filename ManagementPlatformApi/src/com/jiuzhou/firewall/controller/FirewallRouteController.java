package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallRouteService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月16日 下午5:34:19
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallRouteController {

	@Autowired
	private FirewallRouteService firewallRouteService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_route.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_route.search".equals(method)) {
				return firewallRouteService.listByDeviceName(paramJson, request);
				
			} else if ("firewall_route.add".equals(method)) {
				return firewallRouteService.add(paramJson, request);
				
			} else if ("firewall_route.edit".equals(method)) {
				return firewallRouteService.update(paramJson, request);
				
			} else if ("firewall_route.delete".equals(method)) {
				return firewallRouteService.delete(paramJson, request);
				
			} else if ("firewall_route.dynamic_route_set".equals(method)) {
				return firewallRouteService.updateDynamicRouteSetting(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
