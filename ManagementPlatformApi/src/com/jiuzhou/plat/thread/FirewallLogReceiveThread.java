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
* @version 创建时间：2023年1月5日 下午1:49:34
* 类说明
*/
public class FirewallLogReceiveThread implements Runnable {
	
	public static final Map<String, String> MODULE_MAP = new HashMap<>();
	public static final Map<String, String> LEVEL_MAP = new HashMap<>();
	
	static{
		MODULE_MAP.put("1", "USB");
		MODULE_MAP.put("2", "NIC");
		MODULE_MAP.put("3", "FW_FILTER");
		MODULE_MAP.put("4", "MODBUS_TCP");
		MODULE_MAP.put("5", "OPC_Classic_TCP");
		MODULE_MAP.put("6", "IEC104");
		MODULE_MAP.put("7", "S7");
		MODULE_MAP.put("8", "S7_PLUS");
		
		MODULE_MAP.put("10", "IPMAC");
		MODULE_MAP.put("11", "NET_MODULE");
		MODULE_MAP.put("12", "MODBUS_TCP");
		MODULE_MAP.put("13", "OPC_TCP");
		MODULE_MAP.put("14", "IEC104_TCP");
		MODULE_MAP.put("15", "HTTP");
		MODULE_MAP.put("16", "FTP");
		MODULE_MAP.put("17", "TELNET");
		
		LEVEL_MAP.put("0", "Emerg");
		LEVEL_MAP.put("1", "Alert");
		LEVEL_MAP.put("2", "Critical");
		LEVEL_MAP.put("3", "Error");
		LEVEL_MAP.put("4", "Warning");
		LEVEL_MAP.put("5", "Notice");
		LEVEL_MAP.put("6", "Info");
		LEVEL_MAP.put("7", "Debug");
	}
	
	public static Map<String, List<String>> LOG_MAP = new HashMap<>();
	
	private PlatDeviceService platDeviceService;
	
	
	public FirewallLogReceiveThread() {
		this.platDeviceService = SpringContextHolder.getBean(PlatDeviceService.class);
	}

	@Override
	public void run() {
		try {
			System.out.println("+++++++++++++++++++++++启动防火墙日志接受线程+++++++++++++++++++++++++++");
			
			@SuppressWarnings("resource")
			DatagramSocket ds = new DatagramSocket(8007);
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			
			while(true){
				DatagramPacket dp_receive = new DatagramPacket(new byte[1024], 1024, addr, 8007);
				try {
					ds.receive(dp_receive);
					String ipAddress = dp_receive.getAddress().getHostAddress();
					String sql = new String(dp_receive.getData(), 0, dp_receive.getLength(), "UTF-8");
//					System.out.println(sql);
					String message = sql;
					String number = sql.substring(sql.indexOf("<") + 1, sql.indexOf(">"));
					int PRI = Integer.parseInt(number);
					int facility = PRI/8;
					int level = PRI%8;
					
					PlatDevice device = platDeviceService.getByIp(ipAddress + "_" + 1);
					if (device == null) {
						continue;
					}
					String origin = device.getDevice_name();
					if (StringUtils.isBlank(origin)) {
						continue;
					}
					
					Date currentDate = new Date();
					message = message.replaceAll("'", Matcher.quoteReplacement("\\'"));
					sql = 
							"('"+DateUtils.toSimpleDateTime(currentDate)+"',"+facility+", '"+level+"', NULL, '"+origin+"','','', '"+message+"')";
					
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
						warnLog.setLevel(LEVEL_MAP.get(level + ""));
						warnLog.setMessage(message);
						warnLog.setDevice(device);
						warnLog.setTime(DateUtils.toSimpleDateTime(currentDate));
						IndexWarnLog.addLog(warnLog);
						
						//告警日志统计
						ThreadLoader.firewallReportCounterThread.countAdd(
								"防火墙", 
								DateUtils.toSimpleDate(currentDate), 
								FirewallReportCounter.COUNT_WARN_LOG, 
								"防火墙");
					}
					
					//平台总日志数计数
					ThreadLoader.firewallReportCounterThread.countAdd(
							"plat", 
							DateUtils.toSimpleDate(currentDate), 
							FirewallReportCounter.COUNT_PLAT_LOG_SUM, 
							"plat");
					
					//防火墙总日志数计数
					ThreadLoader.firewallReportCounterThread.countAdd(
							"防火墙", 
							DateUtils.toSimpleDate(currentDate), 
							FirewallReportCounter.COUNT_FIREWALL_LOG_SUM, 
							"防火墙");
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
                
            }
			
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++防火墙日志接收线程启动失败+++++++++++++++++++++++++++");
			e.printStackTrace();
		}
		
	}
	
}
