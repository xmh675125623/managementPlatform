package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.PlatIpWhitelistService;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午7:59:25
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class PlatIpWhitelistController {
	
	@Autowired
	private PlatIpWhitelistService platIpWhitelistService;
	
	@ResponseBody
	@RequestMapping(value="/ip_whitelist.do", produces="application/json; charset=utf-8")
	public String ipWhitelist (@RequestBody String jsonParam, HttpServletRequest request) {
		
//		int logId = (int) request.getAttribute("currentOperateId");
//		String logTableName = (String) request.getAttribute("currentOperateTableName");
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		
		AdminUserLoginInfo loginInfo = 
				ServiceBase.getCacheStatic(
						ServiceBase.CACHE_LOGIN_INFO+paramJson.getInt("aid"), 
						AdminUserLoginInfo.class);
		
		try {

			String method = paramJson.getString("method");
			if ("plat.whitelist.search".equals(method)) {
				return platIpWhitelistService.getIpWhitelists(paramJson);
				
			} else if ("plat.whitelist.add".equals(method)) {
				return platIpWhitelistService.addIpWhitelist(paramJson, loginInfo);
				
			} else if ("plat.whitelist.delete".equals(method)) {
				return platIpWhitelistService.deleteIpWhitelist(paramJson);
				
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
}
