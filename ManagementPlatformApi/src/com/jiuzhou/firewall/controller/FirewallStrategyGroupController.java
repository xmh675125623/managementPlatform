package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallStrategyGroupService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午6:34:28
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallStrategyGroupController {

	@Autowired
	private FirewallStrategyGroupService firewallStrategyGroupService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_strategy_group.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall.rule_strategy_group.search".equals(method)) {
				return firewallStrategyGroupService.listByDeviceName(paramJson, request);
				
			} else if ("firewall.rule_strategy_group.add".equals(method)) {
				return firewallStrategyGroupService.add(paramJson, request);
				
			} else if ("firewall.rule_strategy_group.edit".equals(method)) {
				return firewallStrategyGroupService.update(paramJson, request);
				
			} else if ("firewall.rule_strategy_group.delete".equals(method)) {
				return firewallStrategyGroupService.delete(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
}
