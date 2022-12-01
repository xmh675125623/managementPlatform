package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.PlatDeviceService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月7日 下午1:29:09
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class PlatDeviceController {
	
	@Autowired
	private PlatDeviceService platDeviceService;
	
	@ResponseBody
	@RequestMapping(value="/plat_device.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("plat.device.add".equals(method)) {
				return platDeviceService.addDevice(paramJson, request);
				
			} else if ("plat.device.remove".equals(method)) {
				return platDeviceService.removeDevice(paramJson, request);
				
			} else if ("plat.device.edit".equals(method)) {
				return platDeviceService.updateDevice(paramJson, request);
				
			} else if ("plat.device.list".equals(method)) {
				return platDeviceService.getListByPage(paramJson);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
}
