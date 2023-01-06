package com.jiuzhou.plat.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.service.AuditLogService;

/**
* @author xingmh
* @version 创建时间：2018年11月19日 下午5:11:32
* 审计日志插入线程
*/
public class InsertAuditLogThread implements Runnable {
	
private AuditLogService auditLogService;  
	
	public InsertAuditLogThread() {
		this.auditLogService = SpringContextHolder.getBean(AuditLogService.class);
	}

	@Override
	public void run() {
		
		System.out.println("+++++++++++++++++++++++启动审计日志插入线程+++++++++++++++++++++++++++");

		while (true) {
			try {
				
				
				for (Map.Entry<String, List<String>> entry : AuditLogReceiveThread.LOG_MAP.entrySet()) { 
					String deviceName = entry.getKey();
					List<String> logList = entry.getValue();
					if (logList.size() > 0) {
						AuditLogReceiveThread.LOG_MAP.put(deviceName, new ArrayList<String>());
						auditLogService.insertBathBySqls(deviceName, logList);
					}
				}
				
				Thread.sleep(1000);
				
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		}

	}
	
}
