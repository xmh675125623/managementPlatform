package com.jiuzhou.plat.bean;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* @author xingmh
* @version 2018年9月4日 下午3:04:21
* 通用ajax返回结果类
*/
public class CommonResult {

	private Map<String, Object>	resultMap = new HashMap<String, Object>();		
	private Boolean 	status;		//返回状态
	private String 		errorMsg;	//错误信息
	
	public CommonResult() {
	}

	public CommonResult(boolean status, String errorMsg) {
		super();
		this.status = status;
		this.errorMsg = errorMsg;
		this.resultMap.put("status", status?"ok":"error");
		this.resultMap.put("errorMsg", errorMsg);
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
		if (status) {
			this.resultMap.put("status", "ok");
		} else {
			this.resultMap.put("status", "error");
		}
		
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		this.resultMap.put("errorMsg", errorMsg);
	}
	
	public void put(String key, Object object) {
		this.resultMap.put(key, object);
	}
	
	public Object get(String key) {
		return this.resultMap.get("key");
	}
	
	@Override
	public String toString() {
//		JSONObject resultJson = JSONObject.fromObject(this.resultMap);
//		resultJson = new JSONObject();
//		resultJson.putAll(this.resultMap);
		Gson gson = new GsonBuilder()
		        .setDateFormat("yyyy-MM-dd HH:mm:ss")
		        .create();
		return gson.toJson(this.resultMap);
	}
	
	
}
