package com.jiuzhou.plat.thread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;

import com.jiuzhou.firewall.bean.FirewallReportCounter;
import com.jiuzhou.plat.bean.IndexWarnLog;
import com.jiuzhou.plat.bean.PlatDevice;
import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.service.PlatDeviceService;
import com.jiuzhou.plat.util.DateUtils;

/**
* @author xingmh
* @version 创建时间：2023年1月9日 下午5:20:28
* 类说明
*/
public class IsolationLogReceiveThread implements Runnable {
	
	public static Map<String, List<String>> LOG_MAP = new HashMap<>();
	
	private PlatDeviceService platDeviceService;
	public IsolationLogReceiveThread() {
		this.platDeviceService = SpringContextHolder.getBean(PlatDeviceService.class);
	}

	@Override
	public void run() {
		try {
			System.out.println("+++++++++++++++++++++++启动隔离日志接受线程+++++++++++++++++++++++++++");
			
			@SuppressWarnings("resource")
			DatagramSocket ds = new DatagramSocket(8008);
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			
			while(true){
				DatagramPacket dp_receive = new DatagramPacket(new byte[1024], 1024, addr, 8008);
				try {
					ds.receive(dp_receive);
					String ipAddress = dp_receive.getAddress().getHostAddress();
					String sql = new String(dp_receive.getData(), 0, dp_receive.getLength(), "UTF-8");
//					System.out.println(sql);
					String message = sql.substring(sql.indexOf(": ") + 1);
					message = message.replaceAll("'", "\"");
					String logInfo = sql.substring(0, sql.indexOf(": "));
					String number = sql.substring(1, sql.indexOf(">"));
					int PRI = Integer.parseInt(number);
					int facility = PRI/8;
					int level = PRI%8;
					
					PlatDevice device = platDeviceService.getByIp(ipAddress + "_" + 5);
					if (device == null) {
						continue;
					}
					String origin = device.getDevice_name();
					if (StringUtils.isBlank(origin)) {
						continue;
					}
					
					String tag = null;
					
					logInfo = logInfo.replace("  ", " ");
					if (logInfo.length() > 1 && StringUtils.isNotBlank(logInfo)) {
						String msgContents[] = logInfo.split(" ");
						if (msgContents.length == 5) {
							tag = msgContents[4];
						}
					}
					
					if (message.indexOf("MOD=") > 0) {
						tag = message.substring(message.indexOf("MOD=")+4, message.indexOf("MOD=")+5);
						message = message.substring(message.indexOf("MOD=")+5);
					}
					
					String sourceIp = "";
					if (message.indexOf("SRC=") > 0) {
						String tempStr = message.substring(message.indexOf("SRC="));
						sourceIp = tempStr.substring(tempStr.indexOf("SRC=")+4, tempStr.indexOf(" "));
					}
					
					String targetIp = "";
					if (message.indexOf("DST=") > 0) {
						String tempStr = message.substring(message.indexOf("DST="));
						targetIp = tempStr.substring(tempStr.indexOf("DST=")+4, tempStr.indexOf(" "));
					}
					
					String protocol = "";
					if (message.indexOf("PROTO=") > 0) {
						String tempStr = message.substring(message.indexOf("PROTO="));
						protocol = tempStr.substring(tempStr.indexOf("PROTO=")+6, tempStr.indexOf(" "));
					}
					if ("2".equals(protocol)) {
						protocol = "IGMP";
					}
					
					String sport = "";
					if (message.indexOf("SPT=") > 0) {
						String tempStr = message.substring(message.indexOf("SPT="));
						sport = tempStr.substring(tempStr.indexOf("SPT=")+4, tempStr.indexOf(" "));
					}
					
					String dport = "";
					if (message.indexOf("DPT=") > 0) {
						String tempStr = message.substring(message.indexOf("DPT="));
						dport = tempStr.substring(tempStr.indexOf("DPT=")+4, tempStr.indexOf(" "));
					}
					Date currentDate = new Date();
					message = message.replaceAll("'", Matcher.quoteReplacement("\\'"));
					sql = 
							"('"+DateUtils.toSimpleDateTime(new Date())+"',"+facility+", '"+level+"', '"+tag+"', '"+origin+"','"+sourceIp+"','"+targetIp+"', '"+message+"')";
					
					List<String> sql_list = LOG_MAP.get(origin);
					if (sql_list == null) {
						sql_list = new ArrayList<>();
						LOG_MAP.put(origin, sql_list);
					}
					if (sql_list.size() < 20000) {
						sql_list.add(sql);
					}
					
					//事件等级是warning及以上的
					if (level <= 4) {
						//添加首页实时告警
						IndexWarnLog warnLog = new IndexWarnLog();
						warnLog.setLevel(FirewallLogReceiveThread.LEVEL_MAP.get(level + ""));
						warnLog.setMessage(message);
						warnLog.setDevice(device);
						warnLog.setTime(DateUtils.toSimpleDateTime(currentDate));
						IndexWarnLog.addLog(warnLog);
						
						//告警日志统计
						ThreadLoader.firewallReportCounterThread.countAdd(
								"隔离", 
								DateUtils.toSimpleDate(currentDate), 
								FirewallReportCounter.COUNT_WARN_LOG, 
								"隔离");
					}
					
					//平台总日志数计数
					ThreadLoader.firewallReportCounterThread.countAdd(
							"plat", 
							DateUtils.toSimpleDate(currentDate), 
							FirewallReportCounter.COUNT_PLAT_LOG_SUM, 
							"plat");
					
					//隔离总日志数计数
					ThreadLoader.firewallReportCounterThread.countAdd(
							"隔离", 
							DateUtils.toSimpleDate(currentDate), 
							FirewallReportCounter.COUNT_ISOLATION_LOG_SUM, 
							"隔离");
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
                
            }
			
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++隔离日志接收线程启动失败+++++++++++++++++++++++++++");
			e.printStackTrace();
		}
		
	}
	
}
