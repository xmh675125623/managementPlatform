package com.jiuzhou.plat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.AuditEventlevelService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午5:03:10
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class AuditEventLevelController {
	
	@Autowired
	private AuditEventlevelService auditEventlevelService;

	@ResponseBody
	@RequestMapping(value="/event_level.do", produces="application/json; charset=utf-8")
	public String eventLevel (@RequestBody String jsonParam, HttpServletRequest request) {
		
//		int logId = (int) request.getAttribute("currentOperateId");
//		String logTableName = (String) request.getAttribute("currentOperateTableName");
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("event_level.search".equals(method)) {
				return auditEventlevelService.getEventLevelList(paramJson);
				
			} else if ("event_level.edit".equals(method)) {
				return auditEventlevelService.updateEventLevel(paramJson);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
}
