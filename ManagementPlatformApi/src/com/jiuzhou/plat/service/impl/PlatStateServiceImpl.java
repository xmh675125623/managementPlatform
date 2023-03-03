package com.jiuzhou.plat.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.VariableBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.firewall.bean.FirewallReportCounter;
import com.jiuzhou.firewall.utils.SnmpUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DeviceCount;
import com.jiuzhou.plat.mapper.PlatDeviceMapper;
import com.jiuzhou.plat.service.PlatStateService;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.util.DateUtils;

import net.sf.json.JSONObject;

/**
 * @author xingmh
 * @version 创建时间：2023年1月17日 上午10:25:28 类说明
 */
@Service("PlatStateService")
public class PlatStateServiceImpl extends ServiceBase implements PlatStateService {
	private static Snmp snmp  = null;
	private static Target snmpTarget = null;

	@Autowired
	PlatDeviceMapper platDeviceMapper;
	@Autowired
	SystemSettingService systemSettingService;

	@Override
	public String getPlatState(JSONObject paramJson) throws Exception {

		CommonResult commonResult = new CommonResult(false, "");

		// 获取设备数量信息
		List<DeviceCount> deviceCountInfo = platDeviceMapper.getDeviceCounts();
		
		// 获取设备总数
		int deviceNum = platDeviceMapper.getCountForPage();
		
		
		String cpuUsage = "";
		String memUsage = "";
		String diskUsage = "";
		if (SystemUtils.IS_OS_LINUX) {
			if (snmp == null) {
				snmp  = SnmpUtil.initSnmp();
			}
			if (snmpTarget == null) {
				snmpTarget = SnmpUtil.createTarget("127.0.0.1", 
						SnmpConstants.version2c, 
						SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
			}
			// 获取cpu使用率
			cpuUsage = (100 - Integer.parseInt(snmpGet(snmp, snmpTarget, "1.3.6.1.4.1.2021.11.11.0"))) + "";
			// 获取内存使用率
			int memoryTotal = Integer.parseInt(snmpGet(snmp, snmpTarget, "1.3.6.1.4.1.2021.4.5.0"));
			int memoryUse = Integer.parseInt(snmpGet(snmp, snmpTarget, "1.3.6.1.4.1.2021.4.6.0"));
			memUsage = (memoryTotal-memoryUse)*100/memoryTotal + "";
			// 获取磁盘使用率
			diskUsage = (int)systemSettingService.getDiskUsage() + "";
		}
		
		// 平台总日志数折线图数据局
		List<FirewallReportCounter> platLogCountList = platDeviceMapper.getPlatLogCountList();
		
		// 各设备类型日志数
		int[] countTypes = {FirewallReportCounter.COUNT_AUDIT_LOG_SUM, FirewallReportCounter.COUNT_FIREWALL_LOG_SUM,
				FirewallReportCounter.COUNT_ISOLATION_LOG_SUM, FirewallReportCounter.COUNT_IDS_LOG_SUM,
				FirewallReportCounter.COUNT_GATEWAY_LOG_SUM};
		List<FirewallReportCounter> typeLogCountList = 
				platDeviceMapper.getLastLogCountByType(countTypes, DateUtils.toSimpleDate(new Date()));
		
		commonResult.setStatus(true);
		commonResult.put("deviceCountInfo", deviceCountInfo);
		commonResult.put("cpuUsage", cpuUsage);
		commonResult.put("memUsage", memUsage);
		commonResult.put("diskUsage", diskUsage);
		commonResult.put("deviceNum", deviceNum);
		commonResult.put("platLogCountList", platLogCountList);
		commonResult.put("typeLogCountList", typeLogCountList);
		return commonResult.toString();

	}
	
	private String snmpGet(Snmp snmp, Target target, String oid) throws Exception {
		PDU pdu = SnmpUtil.createPDU(SnmpConstants.version2c, PDU.GET, oid);
		ResponseEvent responseEvent = snmp.send(pdu, target);
        PDU response = responseEvent.getResponse();
        VariableBinding variableBinding = response.get(0);
        return variableBinding.getVariable().toString();
	}
	


}
