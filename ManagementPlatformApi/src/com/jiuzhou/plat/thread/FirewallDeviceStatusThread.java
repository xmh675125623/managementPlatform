package com.jiuzhou.plat.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallDeviceStatus;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.utils.SnmpUtil;
import com.jiuzhou.plat.bean.OperateLog;
import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.bean.SystemWarning;
import com.jiuzhou.plat.init.SpringContextHolder;
import com.jiuzhou.plat.service.OperateLogService;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.service.impl.ServiceBase;

/**
* @author xingmh
* @version 创建时间：2018年11月6日 下午7:07:12
* 获取设备状态信息线程
*/
public class FirewallDeviceStatusThread implements Runnable {
	
	public static Map<String, FirewallDeviceStatus> deviceStatusMap = new HashMap<>();
	public static List<FirewallDevice> deviceList = new ArrayList<>();
	
	private SystemSettingService systemSettingService;
	
	private OperateLogService operateLogService;
	
	private FirewallDeviceMapper firewallDeviceMapper;
	
	public FirewallDeviceStatusThread() {
		this.systemSettingService = SpringContextHolder.getBean(SystemSettingService.class);
		this.operateLogService = SpringContextHolder.getBean(OperateLogService.class);
		this.firewallDeviceMapper = SpringContextHolder.getBean(FirewallDeviceMapper.class);
	}
	@Override
	public void run() {
		System.out.println("+++++++++++++++++++++++启动获取设备状态信息线程+++++++++++++++++++++++++++");
		
		deviceList = firewallDeviceMapper.getAll();
		
		Snmp snmp;
		try {
			snmp = SnmpUtil.initSnmp();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("!!!!!!!!!!!!!!!!!!!!!获取设备状态监听启动失败!!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		}
		
		while (true) {
			
			try {
				
				for (FirewallDevice firewallDevice : deviceList) {
					Target target = SnmpUtil.createTarget(firewallDevice.getIp_address(), 
							SnmpConstants.version2c, 
							SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
					this.analysisData(firewallDevice.getDevice_name(), snmp, target);
				}
				
			} catch (Exception e) {
//				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000*5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private String snmpGet(Snmp snmp, Target target, String oid) throws Exception {
		PDU pdu = SnmpUtil.createPDU(SnmpConstants.version2c, PDU.GET, oid);
		ResponseEvent responseEvent = snmp.send(pdu, target);
        PDU response = responseEvent.getResponse();
        VariableBinding variableBinding = response.get(0);
        return variableBinding.getVariable().toString();
	}
	
	
	private void analysisData(String deviceName, Snmp snmp, Target target) throws Exception {
		
		//设备名
		FirewallDeviceStatus deviceStatus = deviceStatusMap.get(deviceName);
		if (deviceStatus == null) {
			deviceStatus = new FirewallDeviceStatus();
			deviceStatusMap.put(deviceName, deviceStatus);
		}
		
		//运行时间
		String upTime = snmpGet(snmp, target, "1.3.6.1.2.1.1.3.0");
		deviceStatus.setUpTime(upTime);
		
		String core0 = snmpGet(snmp, target, "1.3.6.1.4.1.2021.13.16.2.1.3.1").substring(0, 2);
		String core1 = snmpGet(snmp, target, "1.3.6.1.4.1.2021.13.16.2.1.3.2").substring(0, 2);
		String core2 = snmpGet(snmp, target, "1.3.6.1.4.1.2021.13.16.2.1.3.3").substring(0, 2);
		String core3 = snmpGet(snmp, target, "1.3.6.1.4.1.2021.13.16.2.1.3.4").substring(0, 2);
		
		//CPU 温度
		deviceStatus.setCore0(core0);
		deviceStatus.setCore1(core1);
		deviceStatus.setCore2(core2);
		deviceStatus.setCore3(core3);
		
		//CPU占用率
		if (deviceStatus.getCpuUsages().size() >= 60) {
			deviceStatus.getCpuUsages().pop();
		}
		deviceStatus.getCpuUsages().add((100 - Integer.parseInt(snmpGet(snmp, target, "1.3.6.1.4.1.2021.11.11.0"))) + "");
		
		//内存使用
		int memoryTotal = Integer.parseInt(snmpGet(snmp, target, "1.3.6.1.4.1.2021.4.5.0"));
		int memoryUse = Integer.parseInt(snmpGet(snmp, target, "1.3.6.1.4.1.2021.4.6.0"));
		
		deviceStatus.setMemoryUsage(memoryUse*100/memoryTotal + "");
		
		//内存总量
		float memoryTotalf = Float.parseFloat(snmpGet(snmp, target, "1.3.6.1.4.1.2021.4.5.0"))/1024/1024;
		deviceStatus.setMemoryTotal(String.format("%.2f", memoryTotalf) + "GB");
		
		//硬盘使用率
//		String diskUsage = results[4].replace("%", "");
//		deviceStatus.setDiskUsage(diskUsage);
		double logDiskUsage = systemSettingService.getDiskUsage();
//		deviceStatus.setDiskUsage(diskUsage+"");
		
		float diskTotalf = Float.parseFloat(snmpGet(snmp, target, "1.3.6.1.4.1.2021.9.1.6.1"));
		deviceStatus.setDiskTotal(String.format("%.2f", diskTotalf/1024/1024) + "GB");
		
		float diskUsedf = Float.parseFloat(snmpGet(snmp, target, "1.3.6.1.4.1.2021.9.1.8.1"));
		
		double diskUsage = (diskUsedf/diskTotalf)*100;
		deviceStatus.setDiskUsage(String.format("%.2f", diskUsage));
		
		
		
		//流量进
//		if (deviceStatus.getInputTotal().size() >= 60) {
//			deviceStatus.getInputTotal().pop();
//		}
//		deviceStatus.getInputTotal().add(results[5]);
		
		//流量出
//		if (deviceStatus.getOutputTotal().size() >= 60) {
//			deviceStatus.getOutputTotal().pop();
//		}
//		deviceStatus.getOutputTotal().add(results[6]);
		
		//流量
		this.analysisFlow(snmp, target, deviceStatus);
		
		//磁盘使用率阀值
		SystemSetting diskWarning = systemSettingService.getByName(SystemSetting.DISK_WARNING);
		int diskWarningVal =  diskWarning.getIntValue();
		deviceStatus.setDiskWarning(diskWarningVal + "");
		
		//判断磁盘使用率并生成告警信息
		this.diskWarningProcess(logDiskUsage, diskWarningVal);
		
		//告警信息
		@SuppressWarnings("unchecked")
		List<SystemWarning> systemWarnings = ServiceBase.getCacheStatic(ServiceBase.CACHE_SYSTEM_WARNING, ArrayList.class);
		if (systemWarnings == null) {
			systemWarnings = new ArrayList<>();
		}
		deviceStatus.setSystemWarnings(systemWarnings);
		
	}
	
	private void analysisFlow (Snmp snmp, Target target, FirewallDeviceStatus deviceStatus) throws Exception {
		PDU pdu = SnmpUtil.createPDU(SnmpConstants.version2c, PDU.GETNEXT, "1.3.6.1.2.1.2.2.1.2");
        //4、发送报文，并获取返回结果
        boolean matched = true;
        int ethIndex = 0;
        while (matched) {
            ResponseEvent responseEvent = snmp.send(pdu, target);
            if (responseEvent == null || responseEvent.getResponse() == null) {
                break;
            }
            PDU response = responseEvent.getResponse();
            String nextOid = null;
            Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
            
            for (int i = 0; i < variableBindings.size(); i++) {
                VariableBinding variableBinding = variableBindings.elementAt(i);
                Variable variable = variableBinding.getVariable();
                nextOid = variableBinding.getOid().toDottedString();
                if (!nextOid.startsWith("1.3.6.1.2.1.2.2.1.2")) {
                    matched = false;
                    break;
                }
                
                LinkedList<String> inputList = null;
                LinkedList<String> outputList = null;
                if (variable.toString().indexOf("Intel") > -1) {
                	if (deviceStatus.getInputTotal().size() < ethIndex + 1) {
                		inputList = new LinkedList<>();
                		deviceStatus.getInputTotal().add(inputList);
                		outputList = new LinkedList<>();
                		deviceStatus.getOutputTotal().add(outputList);
                	} else {
                		inputList = deviceStatus.getInputTotal().get(ethIndex);
                		outputList = deviceStatus.getOutputTotal().get(ethIndex);
                	}
                }
                
                if (inputList != null) {
                	if (inputList.size() >= 60) {
                    	inputList.pop();
                    	outputList.pop();
            		}
                    
                    inputList.add(snmpGet(snmp, target, "1.3.6.1.2.1.2.2.1.10"+variableBinding.getOid().toString().replace("1.3.6.1.2.1.2.2.1.2", "")));
                    outputList.add(snmpGet(snmp, target, "1.3.6.1.2.1.2.2.1.16"+variableBinding.getOid().toString().replace("1.3.6.1.2.1.2.2.1.2", "")));
                    ethIndex++;
                }
                
//                System.out.println(variableBinding.getOid().toString());
//                System.out.println(variable);
                
                
            }
            if (!matched) {
                break;
            }
            pdu.clear();
            pdu.add(new VariableBinding(new OID(nextOid)));
        }
	}
	
	void diskWarningProcess(double diskUsage, int diskWarning) throws Exception {
		
		//如果超过告警阀值则生成告警信息
		if (diskUsage >= diskWarning) {
			@SuppressWarnings("unchecked")
			List<SystemWarning> systemWarnings = ServiceBase.getCacheStatic(ServiceBase.CACHE_SYSTEM_WARNING, ArrayList.class);
			if (systemWarnings == null) {
				systemWarnings = new ArrayList<>();
				ServiceBase.setCacheStatic(ServiceBase.CACHE_SYSTEM_WARNING, systemWarnings);
			}
			if (!systemWarnings.contains(SystemWarning.DISK_USAGE)) {
				systemWarnings.add(SystemWarning.DISK_USAGE);
				//插入审计日志
//				String value = "('"+DateUtils.toSimpleDate(new Date())+"',2,3,'-1','磁盘使用率超过阀值')";
//				auditLogService.insertBathBySql(value);
				//插入操作日志
				OperateLog log = new OperateLog();
				log.setType("系统告警");
				log.setFunction_name("系统告警");
				log.setDescription("磁盘使用率已达到告警阀值，请及时备份旧的审计日志和操作日志，以避免丢失重要信息。当前："+diskUsage+"%, 告警阀值："+diskWarning+"%");
				log.setUser_name("系统");
				operateLogService.insert(log);
			}
			
		} else {
			//如果小于告警阀值，判断当前是否存在告警信息，如果存在则删除
			@SuppressWarnings("unchecked")
			List<SystemWarning> systemWarnings = ServiceBase.getCacheStatic(ServiceBase.CACHE_SYSTEM_WARNING, ArrayList.class);
			if (systemWarnings == null) {
				systemWarnings = new ArrayList<>();
				ServiceBase.setCacheStatic(ServiceBase.CACHE_SYSTEM_WARNING, systemWarnings);
			}
			if (systemWarnings.contains(SystemWarning.DISK_USAGE)) {
				systemWarnings.remove(SystemWarning.DISK_USAGE);
			}
			
		}
		
		//如果磁盘使用率超过90%，则进行磁盘清理线程
		if (diskUsage > 90 && !DiskProcessThread.isRunning) {
			new Thread(new DiskProcessThread(), "DiskProcessThread").start();
		}
		
	}

}
