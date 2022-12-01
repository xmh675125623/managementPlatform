package com.jiuzhou.firewall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.firewall.service.StudyRuleService;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2020年9月4日 上午11:10:50
* 类说明
*/
@Controller
@RequestMapping(value="/function")
public class StudyRuleController {

	@Autowired
	private StudyRuleService studyRuleService;
	
	@ResponseBody
	@RequestMapping(value="/study_rule.do", produces="application/json; charset=utf-8")
	public String firewallDevice(@RequestBody String jsonParam, HttpServletRequest request){
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		try {

			String method = paramJson.getString("method");
			if ("study_rule.search".equals(method)) {
				return studyRuleService.listByDeviceName(paramJson, request);
				
			} else if ("study_rule.edit_mode".equals(method)) {
				return studyRuleService.openStudyMode(paramJson, request);
				
			} else if ("study_rule.add_to_strategy".equals(method)) {
				return studyRuleService.addStudyRuleToStrategy(paramJson, request);
				
			} else if ("study_rule.delete".equals(method)) {
				return studyRuleService.deleteByDevice(paramJson, request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CommonResult(false, "系统发生未知错误").toString();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/study_rule_export.do")
	public String export(
			@RequestBody String jsonParam,
			HttpServletRequest request,
            HttpServletResponse response) {
		
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		
		try {

			String method = paramJson.getString("method");
			if ("study_rule.export".equals(method)) {
				return studyRuleService.exportStudyRule(paramJson, response, request);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new CommonResult(false, "系统发生未知错误").toString();
	}
	
}
