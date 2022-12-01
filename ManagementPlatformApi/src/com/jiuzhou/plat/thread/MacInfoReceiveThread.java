package com.jiuzhou.plat.thread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.jiuzhou.firewall.bean.FirewallMac;
import com.jiuzhou.firewall.service.FirewallMacService;
import com.jiuzhou.plat.init.SpringContextHolder;

/**
* @author xingmh
* @version 创建时间：2019年3月11日 下午3:38:18
* 扫面mac信息接收线程
*/
public class MacInfoReceiveThread implements Runnable {
	
	private FirewallMacService firewallMacService;
	
	
	public MacInfoReceiveThread() {
		this.firewallMacService = SpringContextHolder.getBean(FirewallMacService.class);
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		System.out.println("+++++++++++++++++++++++启动获取mac/ip绑定信息线程+++++++++++++++++++++++++++");
		
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(6863);
		} catch (SocketException e1) {
			e1.printStackTrace();
			System.out.println("!!!!!!!!!!!!!!!!!!!!!获取设备状态监听启动失败!!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		}
		
		
		while (true) {
			
			try {
				
				byte[] buf = new byte[1024];
				DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
				// 接收从服务端发送回来的数据
				ds.receive(dp_receive);
				String dataStr = new String(dp_receive.getData());
//				System.out.println(dataStr);
				String[] deviceData = dataStr.replaceAll("\0", "").split("@");
				if (deviceData.length < 2) {
					continue;
				}
				
				String deviceName = deviceData[0];
				String[] macData = deviceData[1].split(";");
				
				FirewallMac mac1 = new FirewallMac();
				mac1.setDevice_name(deviceName);
				mac1.setMac_address(macData[0].split(" ")[1]);
				mac1.setIp_address(macData[0].split(" ")[0]);
				
				FirewallMac mac2 = new FirewallMac();
				mac2.setDevice_name(deviceName);
				mac2.setMac_address(macData[1].split(" ")[1]);
				mac2.setIp_address(macData[1].split(" ")[0]);
				
				firewallMacService.insertScanMac(mac1);
				firewallMacService.insertScanMac(mac2);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
