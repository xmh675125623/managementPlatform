package com.jiuzhou.plat.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.service.GatewayLogService;

/**
* @author xingmh
* @version 创建时间：2023年1月5日 下午1:53:47
* 类说明
*/
public class InsertGatewayLogThread implements Runnable {
	
	private GatewayLogService gatewayLogService;  
	
	public InsertGatewayLogThread() {
		this.gatewayLogService = SpringContextHolder.getBean(GatewayLogService.class);
	}

	@Override
	public void run() {
		
		System.out.println("+++++++++++++++++++++++启动网关日志插入线程+++++++++++++++++++++++++++");

		while (true) {
			try {
				
				
				for (Map.Entry<String, List<String>> entry : GatewayLogReceiveThread.LOG_MAP.entrySet()) { 
					String deviceName = entry.getKey();
					List<String> logList = entry.getValue();
					if (logList.size() > 0) {
						GatewayLogReceiveThread.LOG_MAP.put(deviceName, new ArrayList<String>());
						gatewayLogService.insertBathBySqls(deviceName, logList);
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
