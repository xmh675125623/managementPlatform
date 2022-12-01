package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.IndexService;
import com.jiuzhou.plat.service.NetworkSettingService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年11月6日 下午5:15:10
* 首页
*/
@Controller
@RequestMapping(value="/function")
public class IndexController {
	@Autowired
	IndexService indexService;
	@Autowired
	NetworkSettingService networkSettingService;
	
	@ResponseBody
	@RequestMapping(value="/index.do")
	public String logs(@RequestBody String jsonParam, HttpServletResponse response) {
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("index.total_data".equals(method)) {
				return indexService.getIndexData(paramJson);
				
			} else if ("index.sync_time".equals(method)) {
				return indexService.syncTime(paramJson);
				
			} else if ("network.search".equals(method)) {
				return networkSettingService.getNetworkSettingInfo(paramJson);
				
			} else if ("network.eidt".equals(method)) {
				return networkSettingService.updateNetworkSetting(paramJson);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
	}
}
