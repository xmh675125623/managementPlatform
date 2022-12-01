package com.jiuzhou.plat.thread;

import org.apache.commons.lang.StringUtils;

import com.jiuzhou.plat.bean.OperateLog;
import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.service.DiskSpaceService;
import com.jiuzhou.plat.service.OperateLogService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年11月7日 下午8:17:04
* 删除日志用于清理磁盘空间线程
*/
public class DiskProcessThread implements Runnable {
	
	public static boolean isRunning = false;
	
	private DiskSpaceService diskSpaceService;
	
	
	private OperateLogService operateLogService;
	
	public DiskProcessThread() {
		this.diskSpaceService = SpringContextHolder.getBean(DiskSpaceService.class);
		this.operateLogService = SpringContextHolder.getBean(OperateLogService.class);
	}

	@Override
	public void run() {
		isRunning = true;
		try {
			//获取最早的审计日志表表名
			String firstAuditLogTable = diskSpaceService.getFirstAuditLogTableName();
			
			//获取最早的操作日志表表名
			String firstOperateLogTable = diskSpaceService.getFirstOperateLogTableName();
			
			JSONObject jsonObject = new JSONObject();
			
			if (StringUtils.isNotBlank(firstAuditLogTable)) {
				//删除最早的审计日志表
				jsonObject.put("table_name", firstAuditLogTable);
				diskSpaceService.deleteAuditLogTable(jsonObject);
				
				//插入审计日志
//				String value = "('"+DateUtils.toSimpleDate(new Date())+"',2,3,'-1','磁盘使用率超过90%，系统自动删除数据库表"+firstAuditLogTable+"')";
//				auditLogService.insertBathBySql(value);
				//插入操作日志
				OperateLog log = new OperateLog();
				log.setType("系统告警");
				log.setFunction_name("系统告警");
				log.setDescription("磁盘使用率超过90%，系统自动删除数据库表"+firstAuditLogTable);
				log.setUser_name("系统");
				operateLogService.insert(log);
			}
			
			if (StringUtils.isNotBlank(firstOperateLogTable)) {
				//删除最早的操作日志表
				jsonObject.put("table_name", firstOperateLogTable);
				diskSpaceService.deleteOperateLogTable(jsonObject);
				
				//插入审计日志
//				String value = "('"+DateUtils.toSimpleDate(new Date())+"',2,3,'-1','磁盘使用率超过90%，系统自动删除数据库表"+firstOperateLogTable+"')";
//				auditLogService.insertBathBySql(value);
				//插入操作日志
				OperateLog log = new OperateLog();
				log.setType("系统告警");
				log.setFunction_name("系统告警");
				log.setDescription("磁盘使用率超过90%，系统自动删除数据库表"+firstOperateLogTable);
				log.setUser_name("系统");
				operateLogService.insert(log);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		isRunning = false;
	}

}
