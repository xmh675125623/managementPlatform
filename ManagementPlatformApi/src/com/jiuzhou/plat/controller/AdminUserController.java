package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.AdminUserService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月4日 下午10:04:29
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class AdminUserController {
	
	@Autowired
	AdminUserService adminUserService;
	
	@ResponseBody
	@RequestMapping(value="/plat_admin_user.do", produces="application/json; charset=utf-8")
	public String adminUser (@RequestBody String jsonParam, HttpServletRequest request) {
		
		int logId = (int) request.getAttribute("currentOperateId");
		String logTableName = (String) request.getAttribute("currentOperateTableName");
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("plat.user.search".equals(method)) {
				return adminUserService.getListByPage(paramJson);
				
			} else if ("plat.user.add".equals(method)) {
				return adminUserService.insert(paramJson, logId, logTableName);
				
			} else if ("plat.user.edit".equals(method)) {
				return adminUserService.update(paramJson, logId, logTableName);
				
			} else if ("plat.user.delete".equals(method)) {
				return adminUserService.delete(paramJson, logId, logTableName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
}
