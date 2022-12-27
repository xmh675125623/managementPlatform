package com.jiuzhou.plat.thread;

import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jiuzhou.plat.service.FirewallLogService; 

/**
 * 用于spring容器加载完成后启动系统中的线程
 * @author xingmh
 *
 */
@Component
public class ThreadLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	public static FirewallReportCounterThread firewallReportCounterThread;
	
	@Autowired
	private FirewallLogService auditLogService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		ApplicationContext applicationContext = event.getApplicationContext();
		
		//当最顶部容器加载完成后执行
		if (applicationContext.getParent() == null) {
			
			//判断是否为1U设备，是则插入设备重启日志
			if (SystemUtils.IS_OS_LINUX) {
				try {
//					auditLogService.insertDeviceRestartLog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//启动缓存清理线程
			new Thread(new CacheClearThread(), "CacheClearThread").start();
			
			//启动审计日志接受线程
//			if (ServiceBase.isLinux()) {
//				new Thread(new AuditLogReceiveThread(), "AuditLogReceiveThread").start();
//			}
//			new Thread(new AuditLogReceiveThread(), "AuditLogReceiveThread").start();
			
			//启动审计日志插入线程
//			new Thread(new InsertAuditLogThread(), "InsertAuditLogThread").start();
			
			//启动获取设备状态信息线程
//			new Thread(new FirewallDeviceStatusThread(), "FirewallDeviceStatusThread").start();
//			if (ServiceBase.isLinux()) {
//				new Thread(new DeviceStatusThread(), "DeviceStatusThread").start();
//			}
			
			//启动设备状态信息广播线程
//			new Thread(new DeviceStatusBroadcastThread(), "DeviceStatusBroadcastThread").start();
			
			//启动mac/ip信息接收线程
//			new Thread(new MacInfoReceiveThread(), "MacInfoReceiveThread").start();
			
			//启动报表计数器线程
//			firewallReportCounterThread = new FirewallReportCounterThread();
//			new Thread(firewallReportCounterThread, "FirewallReportCounterThread").start();
			
			
		}
		
	}

}
