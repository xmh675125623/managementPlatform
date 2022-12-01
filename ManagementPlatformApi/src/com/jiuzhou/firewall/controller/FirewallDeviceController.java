package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月7日 下午1:29:09
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallDeviceController {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_device.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall.device.sniff".equals(method)) {
				return firewallDeviceService.sniffDevice(paramJson, request);
				
			} else if ("firewall.device.add".equals(method)) {
				return firewallDeviceService.addToManage(paramJson, request);
				
			} else if ("firewall.device.list".equals(method)) {
				return firewallDeviceService.getDeviceInfo(paramJson, request);
				
			} else if ("firewall.device.update".equals(method)) {
				return firewallDeviceService.updateDevice(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
