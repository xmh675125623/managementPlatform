package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallFlowTotalService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年2月15日 下午4:53:48
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallFlowTotalController {
	@Autowired
	private FirewallFlowTotalService firewallFlowTotalService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_flow_total.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, 
			HttpServletRequest request,  
			HttpServletResponse response){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_flow_total.search".equals(method)) {
				return firewallFlowTotalService.listByDeviceName(paramJson, request);
				
			} else if ("firewall_flow_total.add".equals(method)) {
				return firewallFlowTotalService.add(paramJson, request);
				
			} else if ("firewall_flow_total.edit".equals(method)) {
				return firewallFlowTotalService.update(paramJson, request);
				
			} else if ("firewall_flow_total.delete".equals(method)) {
				return firewallFlowTotalService.delete(paramJson, request);
				
			} else if ("firewall_flow_total.refresh".equals(method)) {
				return firewallFlowTotalService.updateFlow(paramJson, request);
				
			} else if ("firewall_flow_total.export".equals(method)) {
				return firewallFlowTotalService.exportFlow(paramJson, response);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
}
