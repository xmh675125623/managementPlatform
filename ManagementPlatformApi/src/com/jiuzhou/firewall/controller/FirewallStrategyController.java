package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jiuzhou.firewall.service.FirewallStrategyService;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.util.WebDataAESUtils;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月31日 上午10:00:19
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallStrategyController {

	@Autowired
	private FirewallStrategyService firewallStrategyService;
	
	@ResponseBody
	@RequestMapping(value="/firewall_strategy.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_strategy.search".equals(method)) {
				return firewallStrategyService.listByDeviceName(paramJson, request);
				
			} else if ("firewall_strategy.add".equals(method)) {
				return firewallStrategyService.add(paramJson, request);
				
			} else if ("firewall_strategy.edit".equals(method)) {
				return firewallStrategyService.update(paramJson, request);
				
			} else if ("firewall_strategy.delete".equals(method)) {
				return firewallStrategyService.delete(paramJson, request);
				
			} else if ("firewall_strategy.sync".equals(method)) {
				
				return firewallStrategyService.sync(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/firewall_strategy_import.do", produces="text/html;charset=UTF-8;")
	public String import_(
			HttpServletRequest request,
			HttpServletResponse response, 
			MultipartFile file) {
		try {
			
			// web数据解密
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			JSONObject paramJson = new JSONObject();
			paramJson.put("key", key);
			paramJson.put("value", value);
			String body = WebDataAESUtils.webDataDecrypt(paramJson.toString());
			paramJson = JSONObject.fromObject(body);
			
			String method = paramJson.getString("method");
			if ("firewall_strategy.import".equals(method)) {
				return firewallStrategyService.importRule(response, file, paramJson);
				
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
}
