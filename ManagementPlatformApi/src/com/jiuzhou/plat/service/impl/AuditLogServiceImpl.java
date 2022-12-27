package com.jiuzhou.plat.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiuzhou.plat.bean.FirewallLog;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DatabaseTableInfo;
import com.jiuzhou.plat.mapper.AuditLogMapper;
import com.jiuzhou.plat.mapper.DatabaseTableMapper;
import com.jiuzhou.plat.service.AuditLogService;
import com.jiuzhou.plat.util.DateUtils;
import com.jiuzhou.plat.util.SearchCondition;

import net.sf.json.JSONObject;

/**
 * 
 * @author xingmh
 * @version 2018年10月12日
 */
@Service("AuditLogService")
public class AuditLogServiceImpl extends ServiceBase implements AuditLogService {

	@Autowired
	private DatabaseTableMapper databaseTableMapper; 
	@Autowired
	private AuditLogMapper auditLogMapper;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=false)
	public String search(JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取表信息
		List<DatabaseTableInfo> tableList = databaseTableMapper.getAuditLogTableInfoList();
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
		
		int page = 1;
		if (paramJson.has("page")) {
			page = paramJson.getInt("page");
		}
		
		int pageSize = 10;
		if (paramJson.has("pageSize")) {
			pageSize = paramJson.getInt("pageSize");
		}
		
		String tableName = null;
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
		
		List<SearchCondition>  conditions = new ArrayList<>();
		//检索条件 事件类型
		if (paramJson.has("event_type")) {
			String eventType = paramJson.getString("event_type");
			if (StringUtils.isNotBlank(eventType)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "event_type", eventType));
			}
		}
		//检索条件 事件等级
		if (paramJson.has("level")) {
			String level = paramJson.getString("level");
			if (StringUtils.isNotBlank(level)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "level", level));
			}
		}
		//检索条件 模块
		if (paramJson.has("module")) {
			String module = paramJson.getString("module");
			if (StringUtils.isNotBlank(module)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "module", module));
			}
		}
		//检索条件 描述
		if (paramJson.has("context")) {
			String context = paramJson.getString("context");
			if (StringUtils.isNotBlank(context)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_LIKE, "context", context));
			}
		}
		
		//检索条件 源IP
		if (paramJson.has("sip")) {
			String sip = paramJson.getString("sip");
			if (StringUtils.isNotBlank(sip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "source_ip", sip));
			}
		}
		
		//检索条件 目的IP
		if (paramJson.has("dip")) {
			String dip = paramJson.getString("dip");
			if (StringUtils.isNotBlank(dip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "target_ip", dip));
			}
		}
		
		String condition = SearchCondition.toConditions(conditions);
		
		//排序字段
		String sortField = null;
		String sortOrder  = null;
		if (paramJson.has("sortField") && paramJson.has("sortOrder")) {
			sortField = paramJson.getString("sortField");
			sortOrder = paramJson.getString("sortOrder");
			if (StringUtils.isNotBlank("sortField") && StringUtils.isNotBlank("sortOrder")) {
				sortOrder = "ascend".equals(sortOrder)?" ASC ":" DESC ";
			}
		}
		
		List<FirewallLog> list = auditLogMapper.search(tableName, 
				(page-1)*pageSize, 
				pageSize, 
				condition, 
				sortField,
				sortOrder);
		int count = auditLogMapper.searchCount(tableName, condition);
		if (list == null) {
			list = new ArrayList<>();
		}
		
		commonResult.setStatus(true);
		commonResult.put("list", list);
		commonResult.put("count", count);
		commonResult.put("tableName", tableName);
		commonResult.put("page", page);
		return commonResult.toString();
	}

	@Override
	public void insertBathBySql(String sql) throws Exception {

		String currentTableName = getCache(CACHE_AUDIT_LOG_TABLE, String.class);
		String tableName = "audit_log_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createAuditLogTable(tableName);
			}
			setCache(CACHE_AUDIT_LOG_TABLE, tableName);
		}
		
//		if (",".equals(sql.substring(sql.length()-1, sql.length()))) {
//			sql = sql.substring(0, sql.length()-1);
//		}
		
		auditLogMapper.insertBatchBySql(sql, tableName);
		
	}

	@Override
	public void insertBathBySqls(String deviceName, List<String> sql) throws Exception {
		String currentTableName = getCache(CACHE_AUDIT_LOG_TABLE, String.class);
		String tableName = "firewall_log_" + deviceName + "_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createAuditLogTable(tableName);
			}
			setCache(CACHE_AUDIT_LOG_TABLE + "_" + deviceName, tableName);
		}
		
		auditLogMapper.insertBatchBySqls(sql, tableName);
		
	}

	@Override
	public void insertDeviceRestartLog() {
		
		//从文件中读取当前设备名
		File conf = new File("/jiuzhou/site/dis_mgmt/conf/dis_mgmt_conf");
		if (!conf.exists()) {
			return;
		}
		Properties prop = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(conf);
			prop.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		
		//判断当前设备是否为1U设备
		String deviceName = prop.getProperty("FWA_ID");
		if (StringUtils.isBlank(deviceName)) {
			return;
		}
		
		String deviceModal = prop.getProperty("FWA_MODEL");
		if (StringUtils.isBlank(deviceName) || !"JZ-FWA-1U-6D".equals(deviceModal)) {
			return;
		}
		
		String currentTableName = getCache(CACHE_AUDIT_LOG_TABLE, String.class);
		String tableName = "firewall_log_" + deviceName + "_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createAuditLogTable(tableName);
			}
			setCache(CACHE_AUDIT_LOG_TABLE + "_" + deviceName, tableName);
		}
		
		//插入防火墙日志
		FirewallLog log = new FirewallLog();
		log.setAdd_time(new Date());
		log.setEvent_type(FirewallLog.EVENT_TYPE_NORMAL);
		log.setLevel(FirewallLog.LEVEL_WARING);
		log.setModule(FirewallLog.MODULE_SYSTEM);
		log.setContext("设备重启");
		
		List<FirewallLog> logs = new ArrayList<>();
		logs.add(log);
		auditLogMapper.insertBatch(logs, tableName);
	}

}
