package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallDeviceEthernetService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月17日 下午3:59:33
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallDeviceEthernetController {

	@Autowired
	private FirewallDeviceEthernetService firewallDeviceEthernetService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_eth.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_eth.search".equals(method)) {
				return firewallDeviceEthernetService.listByDeviceName(paramJson, request);
				
			} else if ("firewall_eth.mode_edit".equals(method)) {
				return firewallDeviceEthernetService.updateMode(paramJson, request);
				
			} else if ("firewall_eth.info_edit".equals(method)) {
				return firewallDeviceEthernetService.updateNetworkInfo(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
