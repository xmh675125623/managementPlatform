package com.jiuzhou.plat.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.service.IsolationLogService;

/**
* @author xingmh
* @version 创建时间：2023年1月9日 下午5:24:40
* 类说明
*/
public class InsertIsolationLogThread implements Runnable {
	
	private IsolationLogService isolationLogService;  
	
	public InsertIsolationLogThread() {
		this.isolationLogService = SpringContextHolder.getBean(IsolationLogService.class);
	}

	@Override
	public void run() {
		
		System.out.println("+++++++++++++++++++++++启动隔离日志插入线程+++++++++++++++++++++++++++");

		while (true) {
			try {
				
				
				for (Map.Entry<String, List<String>> entry : IsolationLogReceiveThread.LOG_MAP.entrySet()) { 
					String deviceName = entry.getKey();
					List<String> logList = entry.getValue();
					if (logList.size() > 0) {
						IsolationLogReceiveThread.LOG_MAP.put(deviceName, new ArrayList<String>());
						isolationLogService.insertBathBySqls(deviceName, logList);
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
