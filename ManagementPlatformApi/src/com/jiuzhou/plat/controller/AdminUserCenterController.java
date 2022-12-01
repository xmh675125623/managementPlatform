package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.AdminUserService;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月21日 下午3:01:02
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class AdminUserCenterController {
	
	@Autowired
	AdminUserService adminUserService;
	
	@ResponseBody
	@RequestMapping(value="/admin_user_center.do")
	public String userCenter(@RequestBody String jsonParam, HttpServletRequest request) {
		
		int logId = (int) request.getAttribute("currentOperateId");
		String logTableName = (String) request.getAttribute("currentOperateTableName");
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			AdminUserLoginInfo loginInfo = 
				ServiceBase.getCacheStatic(
						ServiceBase.CACHE_LOGIN_INFO+paramJson.getInt("aid"), 
						AdminUserLoginInfo.class);
			if ("plat.user_center.search".equals(method)) {
				return adminUserService.getSelfUserInfo(paramJson);
				
			} else if ("plat.user_center.edit".equals(method)) {
				return adminUserService.updatePassword(paramJson, loginInfo, logId, logTableName);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
