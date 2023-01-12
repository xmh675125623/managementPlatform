package com.jiuzhou.plat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DatabaseTableInfo;
import com.jiuzhou.plat.bean.IsolationLog;
import com.jiuzhou.plat.mapper.DatabaseTableMapper;
import com.jiuzhou.plat.mapper.IsolationLogMapper;
import com.jiuzhou.plat.service.IsolationLogService;
import com.jiuzhou.plat.util.DateUtils;
import com.jiuzhou.plat.util.SearchCondition;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2023年1月9日 上午10:22:00
* 类说明
*/
@Service("IsolationLogService")
public class IsolationLogServiceImpl extends ServiceBase implements IsolationLogService {

	@Autowired
	private DatabaseTableMapper databaseTableMapper; 
	@Autowired
	private IsolationLogMapper isolationLogMapper;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=false)
	public String search(JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取表信息
		List<DatabaseTableInfo> tableList = databaseTableMapper.getIsolationLogTableInfoList();
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
		
		String tableName = null;
		if (paramJson.has("table_name")) {
			tableName = paramJson.getString("table_name");
		}
		//判断表是否存在
		tableName = databaseTableMapper.getTableName(tableName);
		if (StringUtils.isBlank(tableName)  && tableList.size() < 1) {
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
		
		List<SearchCondition>  conditions = new ArrayList<>();
		//检索条件 事件类型
//		if (paramJson.has("event_type")) {
//			String eventType = paramJson.getString("event_type");
//			if (StringUtils.isNotBlank(eventType)) {
//				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "event_type", eventType));
//			}
//		}
		//检索条件 事件等级
		if (paramJson.has("level")) {
			String level = paramJson.getString("level");
			if (StringUtils.isNotBlank(level)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "severity", level));
			}
		}
		//检索条件 模块
		if (paramJson.has("module")) {
			String module = paramJson.getString("module");
			if (StringUtils.isNotBlank(module)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "tag", module));
			}
		}
		//检索条件 描述
//		if (paramJson.has("context")) {
//			String context = paramJson.getString("context");
//			if (StringUtils.isNotBlank(context)) {
//				conditions.add(new SearchCondition(SearchCondition.TYPE_LIKE, "context", context));
//			}
//		}
		
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
				sortOrder = "ascending".equals(sortOrder)?" ASC ":" DESC ";
			}
		}
		
		List<IsolationLog> list = isolationLogMapper.search(tableName, 
				(page-1)*pageSize, 
				pageSize, 
				condition, 
				sortField,
				sortOrder);
		int count = isolationLogMapper.searchCount(tableName, condition);
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
		
		//磁盘空间
//		String canInert = ServiceBase.getCacheStatic(ServiceBase.CAN_INSERT_LOG, String.class);
//		if ("false".equals(canInert)) {
//			return;
//		}

		String currentTableName = getCache(CACHE_ISOLATION_LOG_TABLE, String.class);
		String tableName = "isolation_log_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createIsolationLogTable(tableName);
			}
			setCache(CACHE_ISOLATION_LOG_TABLE, tableName);
		}
		
//		if (",".equals(sql.substring(sql.length()-1, sql.length()))) {
//			sql = sql.substring(0, sql.length()-1);
//		}
		
		isolationLogMapper.insertBatchBySql(sql, tableName);
		
	}

	@Override
	public void insertBathBySqls(String deviceName, List<String> sql) throws Exception {
		
		//磁盘空间
//		String canInert = ServiceBase.getCacheStatic(ServiceBase.CAN_INSERT_LOG, String.class);
//		if ("false".equals(canInert)) {
//			return;
//		}
		
		String currentTableName = getCache(CACHE_ISOLATION_LOG_TABLE, String.class);
		String tableName = "isolation_log_" + deviceName + "_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createIsolationLogTable(tableName);
			}
			setCache(CACHE_ISOLATION_LOG_TABLE + "_" + deviceName, tableName);
		}
		
		isolationLogMapper.insertBatchBySqls(sql, tableName);
		
	}

	@Override
	public String deleteLog(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		String tableName = null;
		if (paramJson.has("table_name")) {
			tableName = paramJson.getString("table_name");
		}
		//判断数据库表名是否合法
		if (StringUtils.isBlank(tableName) || tableName.indexOf("separation_log") < 0) {
			commonResult.setErrorMsg("table_name参数错误");
			return commonResult.toString();
		}
		
		if (!paramJson.has("ids")) {
			commonResult.setErrorMsg("缺少参数ids");
			return commonResult.toString();
		}
		String ids = paramJson.getString("ids");
		
		String[] idArray = ids.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		
		if (idArray == null || idArray.length < 1) {
			commonResult.setErrorMsg("请勾选要删除的记录");
			return commonResult.toString();
		}
		
		isolationLogMapper.deleteByIds(tableName, idArray);
		
		// 记录操作日志
		String logDescription = "[表名："+tableName+"]\n"
				+ "[id:"+ids+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDescription);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	@Override
	public String emptyLog(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		String tableName = null;
		if (paramJson.has("table_name")) {
			tableName = paramJson.getString("table_name");
		}
		//判断数据库表名是否合法
		if (StringUtils.isBlank(tableName) || tableName.indexOf("separation_log") < 0) {
			commonResult.setErrorMsg("table_name参数错误");
			return commonResult.toString();
		}
		
		isolationLogMapper.emptyLog(tableName);
		
		// 记录操作日志
		String logDescription = "[表名："+tableName+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDescription);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

}
