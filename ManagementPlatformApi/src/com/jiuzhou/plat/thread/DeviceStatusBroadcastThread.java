package com.jiuzhou.plat.thread;

import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.websocket.SystemWebsocketHandler;

/**
* @author xingmh
* @version 创建时间：2019年2月19日 下午1:45:45
* 广播设备状态信息
*/
public class DeviceStatusBroadcastThread implements Runnable {

	private SystemWebsocketHandler systemWebsocketHandler;
	
	public DeviceStatusBroadcastThread() {
		this.systemWebsocketHandler = SpringContextHolder.getBean(SystemWebsocketHandler.class);
	}
	@Override
	public void run() {
		
		while (true) {
			try {
				systemWebsocketHandler.sendMessageToAllUsers();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
