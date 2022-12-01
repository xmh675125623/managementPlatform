package com.jiuzhou.plat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DatabaseTableInfo;
import com.jiuzhou.plat.bean.OperateLog;
import com.jiuzhou.plat.mapper.DatabaseTableMapper;
import com.jiuzhou.plat.mapper.OperateLogMapper;
import com.jiuzhou.plat.service.OperateLogService;
import com.jiuzhou.plat.util.DateUtils;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月3日 下午10:00:20
* 类说明
*/
@Service("OperateLogService")
public class OperateLogServiceImpl extends ServiceBase implements OperateLogService {

	@Autowired
	OperateLogMapper operateLogMapper;
	
	@Autowired
	DatabaseTableMapper databaseTableMapper;
	
	@Override
	public String insert(OperateLog log) {
		
		@SuppressWarnings("unchecked")
		List<String> tables = getCache(CACHE_OPERATE_LOG_TABLES, ArrayList.class);
		String tableName = "plat_operate_log_" + DateUtils.toFormat(new Date(), "yyyyMM");
		
		//判断当月的表是否存在不存在则创建
		if (!tables.contains(tableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createOperateLogTable(tableName);
			}
			tables.add(tableName);
		}
		
		operateLogMapper.insert(log, tableName);
		return tableName;
	}

	
	@Override
	public String search(JSONObject paramJson) {
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取表信息
		List<DatabaseTableInfo> tableList = databaseTableMapper.getOperateLogTableInfoList();
		if (tableList == null) {
			tableList = new ArrayList<>();
		}
		List<DatabaseTableInfo> commonTableList = new ArrayList<>();
		List<DatabaseTableInfo> importTableList = new ArrayList<>();
		for (DatabaseTableInfo databaseTableInfo : tableList) {
			if (databaseTableInfo.getTable_name().indexOf("import") > 0) {
				importTableList.add(databaseTableInfo);
				continue;
			}
			commonTableList.add(databaseTableInfo);
		}
		commonTableList.addAll(importTableList);
		tableList = commonTableList;
		commonResult.put("tableList", tableList);

		String tableName = "";
		if (paramJson.has("table_name")) {
			tableName = paramJson.getString("table_name");
		}
		
		//如果表名为空并且数据库表列表不存在,直接返回空列表
		if (StringUtils.isBlank(tableName) && tableList.size() < 1) {
			commonResult.setStatus(true);
			commonResult.put("list", new ArrayList<>());
			commonResult.put("count", 0);
			return commonResult.toString();
		}
		
		//表名为空但数据库表列表长度大于0则取列表第0个对象的表名
		if (StringUtils.isBlank(tableName) && tableList.size() > 0) {
			tableName = tableList.get(0).getTable_name();
		}
		
		int page = 1;
		if (paramJson.has("page")) {
			page = paramJson.getInt("page");
		}
		
		int pageSize = 10;
		if (paramJson.has("pageSize")) {
			pageSize = paramJson.getInt("pageSize");
		}
		
		List<OperateLog> list = operateLogMapper.search(tableName, (page-1)*pageSize, pageSize);
		int count = operateLogMapper.searchCount(tableName);
		if (list == null) {
			list = new ArrayList<>();
		}
		
		commonResult.setStatus(true);
		commonResult.put("list", list);
		commonResult.put("count", count);
		commonResult.put("tableName", tableName);
		commonResult.put("page", page);
		commonResult.put("pageSize", pageSize);
		return commonResult.toString();
		
	}


	@Override
	public String update(OperateLog log, String tableName) {
		operateLogMapper.update(log, tableName);
		return null;
	}

}
