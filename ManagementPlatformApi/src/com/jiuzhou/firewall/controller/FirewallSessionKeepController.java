package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.FirewallSessionKeepServie;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年3月17日 下午10:39:53
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class FirewallSessionKeepController {

	@Autowired
	private FirewallSessionKeepServie firewallSessionKeepServie;
	
	@ResponseBody
	@RequestMapping(value="/firewall_session_keep.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("firewall_session_keep.search".equals(method)) {
				return firewallSessionKeepServie.listByDeviceName(paramJson, request);
				
			} else if ("firewall_session_keep.add".equals(method)) {
				return firewallSessionKeepServie.add(paramJson, request);
				
			} else if ("firewall_session_keep.delete".equals(method)) {
				return firewallSessionKeepServie.delete(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
}
