package com.jiuzhou.plat.thread;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.jiuzhou.firewall.service.impl.FirewallMacServiceImpl;
import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.cache.FileDownloadInfo;
import com.jiuzhou.plat.cache.TimedClearBase;
import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.service.impl.ServiceBase;

/**
* @author xingmh
* @version 2018年9月7日 下午7:13:47
* 缓存信息定期删除线程，用于删除验证码缓存及用户登录信息
*/
public class CacheClearThread implements Runnable {
	
	private SystemSettingService systemSettingService;
	
	public CacheClearThread() {
		this.systemSettingService = SpringContextHolder.getBean(SystemSettingService.class);
	}

	
	@Override
	public void run() {
		
		System.out.println("+++++++++++++++++++++++启动清理缓存线程+++++++++++++++++++++++++++");
		
		Map<String, Object> cacheMap = ServiceBase.getCacheMap();
		
		
		
		while (true) {
			
			//获取用户session保留时间设置
			int userSessionTime = 0;
			try {
				SystemSetting userSessionSetting = systemSettingService.getByName(SystemSetting.LOGIN_SESSION_TIME);
				userSessionTime = userSessionSetting.getIntValue();
				if (userSessionTime <= 0) {
					userSessionTime = 30;
				}
			} catch (Exception e) {
				e.printStackTrace();
				userSessionTime = 30;
			}
			
			Iterator<Map.Entry<String, Object>> iterator = cacheMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> item = iterator.next();
				
				Object object = item.getValue();
				
				//处理用户session
				if (object instanceof AdminUserLoginInfo) {
					try {
						AdminUserLoginInfo adminUserLoginInfo = AdminUserLoginInfo.class.cast(object);
						long activeTime = adminUserLoginInfo.getActiveTime();
						if (System.currentTimeMillis() - activeTime >= userSessionTime*60*1000) {
							cacheMap.remove(item.getKey());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//处理继承自TimedClearBase的
				if (object instanceof TimedClearBase) {
					try {
						TimedClearBase timedClearBase = TimedClearBase.class.cast(object);
						if (timedClearBase.getCache_add_time() > 0 
								&& timedClearBase.getCache_save_time() > 0
								&& System.currentTimeMillis() - timedClearBase.getCache_add_time() >= timedClearBase.getCache_save_time()) {
							//清除文件下载缓存
							if (object instanceof FileDownloadInfo) {
								FileDownloadInfo info = FileDownloadInfo.class.cast(object);
								if (info.isDownloading()) {
									continue;
								}
								File file = new File(info.getFilePath());
								file.delete();
							}
							
							cacheMap.remove(item.getKey());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
			//清理自动扫描的ip/mac缓存信息
			try {
				long nowTime = new Date().getTime();
				Iterator<Map.Entry<String, Date>> it = FirewallMacServiceImpl.AUTO_MAC_CACHE.entrySet().iterator();
				while (it.hasNext()) {
					 Map.Entry<String, Date> entry = it.next();
					 if (nowTime - entry.getValue().getTime() > 1000*60*60) {//一小时后删除
						 it.remove();
					 }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
