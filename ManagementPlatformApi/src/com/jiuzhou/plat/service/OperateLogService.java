package com.jiuzhou.plat.service;

import com.jiuzhou.plat.bean.OperateLog;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月3日 下午9:57:56
* 操作日志
*/
public interface OperateLogService {
	
	public String insert(OperateLog log);
	
	public String search(JSONObject paramJson);
	
	public String update(OperateLog log, String tableName);
	
}
