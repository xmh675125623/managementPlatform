package com.jiuzhou.plat.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DatabaseTableInfo;
import com.jiuzhou.plat.bean.IDSLog;
import com.jiuzhou.plat.cache.FileDownloadInfo;
import com.jiuzhou.plat.mapper.DatabaseTableMapper;
import com.jiuzhou.plat.mapper.IDSLogMapper;
import com.jiuzhou.plat.service.IDSLogService;
import com.jiuzhou.plat.util.DateUtils;
import com.jiuzhou.plat.util.SearchCondition;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2023年1月5日 上午10:40:44
* 类说明
*/
@Service("IDSLogService")
public class IDSLogServiceImpl extends ServiceBase implements IDSLogService {

	@Autowired
	private DatabaseTableMapper databaseTableMapper; 
	@Autowired
	private IDSLogMapper idsLogMapper;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=false)
	public String search(JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取表信息
		List<DatabaseTableInfo> tableList = databaseTableMapper.getIDSLogTableInfoList();
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
				sortOrder = "ascending".equals(sortOrder)?" ASC ":" DESC ";
			}
		}
		
		List<IDSLog> list = idsLogMapper.search(tableName, 
				(page-1)*pageSize, 
				pageSize, 
				condition, 
				sortField,
				sortOrder);
		int count = idsLogMapper.searchCount(tableName, condition);
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

		String currentTableName = getCache(CACHE_IDS_LOG_TABLE, String.class);
		String tableName = "ids_log_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createIDSLogTable(tableName);
			}
			setCache(CACHE_IDS_LOG_TABLE, tableName);
		}
		
		idsLogMapper.insertBatchBySql(sql, tableName);
		
	}

	@Override
	public void insertBathBySqls(String deviceName, List<String> sql) throws Exception {
		String currentTableName = getCache(CACHE_IDS_LOG_TABLE, String.class);
		String tableName = "ids_log_" + deviceName + "_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createIDSLogTable(tableName);
			}
			setCache(CACHE_IDS_LOG_TABLE + "_" + deviceName, tableName);
		}
		
		idsLogMapper.insertBatchBySqls(sql, tableName);
		
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
		
		String currentTableName = getCache(CACHE_IDS_LOG_TABLE, String.class);
		String tableName = "ids_log_" + deviceName + "_" + DateUtils.toFormat(new Date(), "yyyyMMdd");
		//判断当天的日志表是否存在
		if (!tableName.equals(currentTableName)) {
			String tableName_ = databaseTableMapper.getTableName(tableName);
			if (StringUtils.isBlank(tableName_) || !tableName.equals(tableName_)) {
				databaseTableMapper.createIDSLogTable(tableName);
			}
			setCache(CACHE_IDS_LOG_TABLE + "_" + deviceName, tableName);
		}
		
		//插入防火墙日志
		IDSLog log = new IDSLog();
		log.setAdd_time(new Date());
		log.setEvent_type(IDSLog.EVENT_TYPE_NORMAL);
		log.setLevel(IDSLog.LEVEL_WARING);
		log.setModule(IDSLog.MODULE_SYSTEM);
		log.setContext("设备重启");
		
		List<IDSLog> logs = new ArrayList<>();
		logs.add(log);
		idsLogMapper.insertBatch(logs, tableName);
	}
	
	@Override
	public String exportSelectedLog(JSONObject paramJson) throws Exception {
		
		Map<String, String> eventTypeMap = new HashMap<String, String>();
		eventTypeMap.put("1", "正常事件");
		eventTypeMap.put("0", "异常事件");
		
		Map<String, String> levelMap = new HashMap<String, String>();
		levelMap.put("1", "低");
		levelMap.put("2", "中");
		levelMap.put("3", "高");
		
		Map<String, String> moduleMap = new HashMap<String, String>();
		moduleMap.put("11", "NET_FILTER");
		moduleMap.put("12", "MODBUS_TCP");
		moduleMap.put("15", "HTTP");
		moduleMap.put("16", "FTP");
		moduleMap.put("17", "TELNET");
		
		String selectedRowsStr = paramJson.getString("selectedRows");
		
		JSONArray selectedRows = JSONArray.fromObject(selectedRowsStr);
		
		//创建文件
		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String destFileName = System.currentTimeMillis() + "_exportLog";
		File destFile = new File(fileSavePath + destFileName);
		int i = 1;
		while (destFile.exists()) {
			destFileName += i;
			destFile = new File(fileSavePath + destFileName);
			i ++;
		}
		
		
		
		//创建工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();//这里也可以设置sheet的Name
        //创建工作表对象
        HSSFSheet logSheet = workbook.createSheet();
        workbook.setSheetName(0,"日志列表");//设置sheet的Name
        
        
        //创建工作表的行
        HSSFRow logRow = logSheet.createRow(0);//设置第一行表头，从零开始
        logRow.createCell(0).setCellValue("ID");
        logRow.createCell(1).setCellValue("事件类型");
        logRow.createCell(2).setCellValue("源IP");
        logRow.createCell(3).setCellValue("目的IP");
        logRow.createCell(4).setCellValue("等级");
        logRow.createCell(5).setCellValue("模块");
        logRow.createCell(6).setCellValue("信息");
        logRow.createCell(7).setCellValue("添加时间");
        
        
        int logRowIndex = 1;
        for (int j = 0; j < selectedRows.size(); j++) {
        	JSONObject jsonObject = selectedRows.getJSONObject(j);
    		HSSFRow row = logSheet.createRow(logRowIndex);
    		
    		HSSFCellStyle cellStyle = workbook.createCellStyle();
    		HSSFFont font = workbook.createFont();
    		if (jsonObject.getInt("level") == 2) {
    			font.setColor(HSSFColor.YELLOW.index);
            	
            } else if (jsonObject.getInt("level") == 3) {
            	font.setColor(HSSFColor.RED.index);
            }
    		cellStyle.setFont(font);
    		
    		HSSFCell cell0 = row.createCell(0);
    		cell0.setCellValue(jsonObject.getString("id"));
    		cell0.setCellStyle(cellStyle);
    		
    		HSSFCell cell1 = row.createCell(1);
    		cell1.setCellValue(eventTypeMap.get(jsonObject.getString("event_type")));
    		cell1.setCellStyle(cellStyle);
    		
    		HSSFCell cell2 = row.createCell(2);
    		cell2.setCellValue(jsonObject.getString("source_ip"));
    		cell2.setCellStyle(cellStyle);
    		
    		HSSFCell cell3 = row.createCell(3);
    		cell3.setCellValue(jsonObject.getString("target_ip"));
    		cell3.setCellStyle(cellStyle);
    		
    		HSSFCell cell4 = row.createCell(4);
    		cell4.setCellValue(levelMap.get(jsonObject.getString("level")));
    		cell4.setCellStyle(cellStyle);
    		
    		HSSFCell cell5 = row.createCell(5);
    		cell5.setCellValue(moduleMap.get(jsonObject.getString("module")));
    		cell5.setCellStyle(cellStyle);
    		
    		HSSFCell cell6 = row.createCell(6);
    		cell6.setCellValue(jsonObject.getString("message"));
    		cell6.setCellStyle(cellStyle);
    		
    		HSSFCell cell7 = row.createCell(7);
    		cell7.setCellValue(jsonObject.getString("add_time"));
    		cell7.setCellStyle(cellStyle);
    		
            logRowIndex ++;
        	
		}
       
 
        //文档输出
        FileOutputStream out = new FileOutputStream(destFile);
        workbook.write(out);
        out.close();
        
        //缓存文件下载信息
        FileDownloadInfo downloadInfo = 
				new FileDownloadInfo(new Date().getTime(), 1*60*1000);
		downloadInfo.setToken(UUID.randomUUID().toString());
		downloadInfo.setJsonParam(paramJson);
		downloadInfo.setFilePath(destFile.getParent() + File.separator + destFileName);
		downloadInfo.setFileName("日志列表.xls");
		setCache(FIRE_DOWNLOAD_INFO+downloadInfo.getToken(), downloadInfo);
		
		CommonResult commonResult = new CommonResult(false, "");
		commonResult.setStatus(true);
		commonResult.put("fileMark", downloadInfo.getToken());
		return commonResult.toString();
		
	}

}
