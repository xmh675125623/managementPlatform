package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallAssetService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午2:25:25
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallAssetController {
	
	@Autowired
	private FirewallAssetService firewallAssetService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_asset.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall.rule_asset.search".equals(method)) {
				return firewallAssetService.getAll(paramJson, request);
				
			} else if ("firewall.rule_asset.add".equals(method)) {
				return firewallAssetService.add(paramJson, request);
				
			} else if ("firewall.rule_asset.edit".equals(method)) {
				return firewallAssetService.update(paramJson, request);
				
			} else if ("firewall.rule_asset.delete".equals(method)) {
				return firewallAssetService.delete(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}

}
