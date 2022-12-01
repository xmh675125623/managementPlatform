package com.jiuzhou.plat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月10日 下午5:51:16
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class SystemSettingController {
	
	@Autowired
	private SystemSettingService systemSettingService;

	@ResponseBody
	@RequestMapping(value="/syssetting.do")
	public String systemSetting(@RequestBody String jsonParam) {
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			AdminUserLoginInfo loginInfo = 
					new ServiceBase().getCache(
							ServiceBase.CACHE_LOGIN_INFO+paramJson.getInt("aid"), 
							AdminUserLoginInfo.class);
			if ("plat.setting.search".equals(method)) {
				return systemSettingService.getList(paramJson);
				
			} else if ("plat.setting.edit".equals(method)) {
				return systemSettingService.update(paramJson, loginInfo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
