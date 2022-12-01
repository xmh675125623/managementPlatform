package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallAntiAttackService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月23日 下午5:03:15
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallAntiAttackController {

	@Autowired
	private FirewallAntiAttackService firewallAntiAttackService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_anti.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_anti.search".equals(method)) {
				return firewallAntiAttackService.getInfo(paramJson, request);
				
			} else if ("firewall_anti.edit".equals(method)) {
				return firewallAntiAttackService.update(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
}
