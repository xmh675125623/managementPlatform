package com.jiuzhou.plat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.RoleService;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月9日 下午3:32:15
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@RequestMapping(value="/role.do")
	public String role(@RequestBody String jsonParam) {
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			AdminUserLoginInfo loginInfo = 
				new ServiceBase().getCache(
						ServiceBase.CACHE_LOGIN_INFO+paramJson.getInt("aid"), 
						AdminUserLoginInfo.class);
			if ("plat.role.search".equals(method)) {
				return roleService.getListNoPage();
				
			} else if ("plat.role.add".equals(method)) {
				return roleService.addRole(paramJson);
				
			} else if ("plat.role.edit".equals(method)) {
				return roleService.updateRole(paramJson, loginInfo);
				
			} else if ("plat.role.delete".equals(method)) {
				return roleService.deleteRole(paramJson, loginInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
