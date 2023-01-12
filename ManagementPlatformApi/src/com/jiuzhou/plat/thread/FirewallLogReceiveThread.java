package com.jiuzhou.plat.thread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiuzhou.firewall.bean.FirewallReportCounter;
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
	
	
	public FirewallLogReceiveThread() {
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
					String sql = new String(dp_receive.getData(), 0, dp_receive.getLength(), "UTF-8");
//					System.out.println(sql);
					String message = sql;
					String origin = sql.substring(0, sql.indexOf(": "));
					String number = sql.substring(sql.indexOf("<") + 1, sql.indexOf(">"));
//					System.out.println("number:" + number);
					int PRI = Integer.parseInt(number);
					int facility = PRI/8;
					int level = PRI%8;
					
					String tag = null;
					
					//通过STYPE判断当前设备是否为学习模式
					String stype = message.substring(message.indexOf("STYPE=")+6, message.indexOf("STYPE=")+7);
					
					if (message.indexOf("MOD=") > 0) {
						String mod_str = message.substring(message.indexOf("MOD=")+4);
//						tag = message.substring(message.indexOf("MOD=")+4, message.indexOf("MOD=")+5);
						tag = mod_str.substring(0, mod_str.indexOf(" "));
//						if ("6".equals(tag)) {
//							HotStandbyStartThread startThread = new HotStandbyStartThread();
//							startThread.setDeviceName(origin);
//							new Thread(startThread).start();
//						}
						message = message.substring(message.indexOf("MOD=")+5);
					} else {
						continue;
					}
					
					int macsrcIndex = 0;
					if (message.indexOf("MACSRC=") > 0) {
						macsrcIndex = message.indexOf("MACSRC=") + 7;
					}
					
					int macdstIndex = 0;
					if (message.indexOf("MACDST=") > 0) {
						macdstIndex = message.indexOf("MACDST=") + 7;
					}
					
					int macprotoIndex = 0;
					if (message.indexOf("MACPROTO=") > 0) {
						macprotoIndex = message.indexOf("MACPROTO=") + 9;
					}
					
					String sourceIp = "";
					if (message.indexOf("SIP:") > 0) {
						String tempStr = message.substring(message.indexOf("SIP:"));
						sourceIp = tempStr.substring(tempStr.indexOf("SIP:")+4, tempStr.indexOf(" "));
					}
					
					String targetIp = "";
					if (message.indexOf("DIP:") > 0) {
						String tempStr = message.substring(message.indexOf("DIP:"));
						targetIp = tempStr.substring(tempStr.indexOf("DIP:")+4, tempStr.indexOf(" "));
					}
					
					String protocol = "";
					if (message.indexOf("PROTO=", macprotoIndex) > 0) {
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
					
					message = message.substring(message.indexOf("STYPE=")+7);
						
					Date currentDate = new Date();
					
					sql = 
							"('"+DateUtils.toSimpleDateTime(currentDate)+"',"+facility+", '"+level+"', '"+tag+"', '"+origin+"','"+sourceIp+"','"+targetIp+"', '"+origin+" "+message+"')";
					
					List<String> sql_list = LOG_MAP.get(origin);
					if (sql_list == null) {
						sql_list = new ArrayList<>();
						LOG_MAP.put(origin, sql_list);
					}
					if (sql_list.size() < 20000) {
						sql_list.add(sql);
					}
					
					//报表计数
					ThreadLoader.firewallReportCounterThread.countAdd(
							origin, 
							DateUtils.toSimpleDate(currentDate), 
							FirewallReportCounter.COUNT_TYPE_LOG_LEVEL, 
							LEVEL_MAP.get(level+""));
					ThreadLoader.firewallReportCounterThread.countAdd(
							origin, 
							DateUtils.toSimpleDate(currentDate), 
							FirewallReportCounter.COUNT_TYPE_LOG_MODULE, 
							MODULE_MAP.get(tag));
						
					
					
					
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
