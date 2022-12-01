package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallNatService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月15日 下午4:13:51
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallNatController {

	@Autowired
	private FirewallNatService firewallNatService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_nat.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_nat.search".equals(method)) {
				return firewallNatService.listByDeviceName(paramJson, request);
				
			} else if ("firewall_nat.add".equals(method)) {
				return firewallNatService.add(paramJson, request);
				
			} else if ("firewall_nat.edit".equals(method)) {
				return firewallNatService.update(paramJson, request);
				
			} else if ("firewall_nat.delete".equals(method)) {
				return firewallNatService.delete(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
