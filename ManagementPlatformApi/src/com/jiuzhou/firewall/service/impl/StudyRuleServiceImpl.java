package com.jiuzhou.firewall.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.firewall.bean.FirewallAsset;
import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallRuleCustom;
import com.jiuzhou.firewall.bean.FirewallRuleIEC104;
import com.jiuzhou.firewall.bean.FirewallRuleModbusTcp;
import com.jiuzhou.firewall.bean.FirewallRuleOpcClassicTcp;
import com.jiuzhou.firewall.bean.FirewallRuleS7;
import com.jiuzhou.firewall.bean.FirewallStrategy;
import com.jiuzhou.firewall.bean.FirewallStrategyGroup;
import com.jiuzhou.firewall.bean.StudyRuleItem;
import com.jiuzhou.firewall.mapper.FirewallAssetMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleCustomMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleIEC104Mapper;
import com.jiuzhou.firewall.mapper.FirewallRuleModbusTcpMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleOpcClassicTcpMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleS7Mapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyGroupMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyMapper;
import com.jiuzhou.firewall.mapper.StudyRuleItemMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallStrategyService;
import com.jiuzhou.firewall.service.StudyRuleService;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.FileDownloadInfo;
import com.jiuzhou.plat.service.impl.DiskSpaceServiceImpl;
import com.jiuzhou.plat.service.impl.ServiceBase;
import com.jiuzhou.plat.util.SearchCondition;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2020年9月1日 下午5:00:48
* 类说明
*/
@Service("StudyRuleService")
public class StudyRuleServiceImpl extends ServiceBase implements StudyRuleService {

	public static final Map<String, Object> RULE_CACHE_MAP = new ConcurrentHashMap<>();
	public static final Map<String, FirewallDevice> DEVICE_CACHE_MAP = new ConcurrentHashMap<>();
	
	@Autowired
	private StudyRuleItemMapper studyRuleItemMapper;
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	@Autowired
	private FirewallAssetMapper firewallAssetMapper; 
	@Autowired
	private FirewallStrategyGroupMapper firewallStrategyGroupMapper;
	@Autowired
	private FirewallStrategyMapper firewallStrategyMapper;
	@Autowired
	private FirewallRuleCustomMapper firewallRuleCustomMapper;
	@Autowired
	private FirewallRuleModbusTcpMapper firewallRuleModbusTcpMapper;
	@Autowired
	private FirewallRuleIEC104Mapper firewallRuleIEC104Mapper;
	@Autowired
	private FirewallRuleS7Mapper firewallRuleS7Mapper;
	@Autowired
	private FirewallRuleOpcClassicTcpMapper firewallRuleOpcClassicTcpMapper;
	@Autowired
	private FirewallStrategyService firewallStrategyService;
	
	@Override
	public String addStudyRuleToStrategy(JSONObject paramJson, HttpServletRequest request) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			return commonResult.toString();
		}
		
		List<SearchCondition>  conditions = new ArrayList<>();
		//检索条件 源IP
		if (paramJson.has("sip")) {
			String sip = paramJson.getString("sip");
			if (StringUtils.isNotBlank(sip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "sip", sip.trim()));
			}
		}
		
		//检索条件 目的IP
		if (paramJson.has("dip")) {
			String dip = paramJson.getString("dip");
			if (StringUtils.isNotBlank(dip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "dip", dip.trim()));
			}
		}
		
		//检索条件 源端口
		if (paramJson.has("sport")) {
			String sport = paramJson.getString("sport");
			if (StringUtils.isNotBlank(sport)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "sport", sport.trim()));
			}
		}
		
		//检索条件 目的端口
		if (paramJson.has("dport")) {
			String dport = paramJson.getString("dport");
			if (StringUtils.isNotBlank(dport)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "dport", dport.trim()));
			}
		}
		
		//检索条件 协议类型
		if (paramJson.has("protocol")) {
			String protocol = paramJson.getString("protocol");
			if (StringUtils.isNotBlank(protocol)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "protocol", protocol));
			}
		}
		
		//检索条件 工业协议
		if (paramJson.has("industryProrocol")) {
			String industryProrocol = paramJson.getString("industryProrocol");
			if (StringUtils.isNotBlank(industryProrocol)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "rule_type", industryProrocol));
			}
		}
			
		String condition = SearchCondition.toConditions(conditions);
		
		List<StudyRuleItem> studyRuleItems = 
				studyRuleItemMapper.search(deviceName, condition);
		
		
		//提取资产并插入
		List<FirewallAsset> assets = firewallAssetMapper.getAll();
		Map<String, Integer> exitAssetsMap = new HashMap<>();
		for (FirewallAsset firewallAsset : assets) {
			exitAssetsMap.put(firewallAsset.getIp_address(), firewallAsset.getId());
		}
		
		List<FirewallAsset> newAssets = new ArrayList<>();
		for (StudyRuleItem studyRuleItem : studyRuleItems) {
			if (exitAssetsMap.get(studyRuleItem.getSip()) == null) {
				FirewallAsset newAsset = new FirewallAsset();
				newAsset.setIp_address(studyRuleItem.getSip());
				newAsset.setAdd_time(new Date());
				newAsset.setAsset_name(studyRuleItem.getSip());
				newAssets.add(newAsset);
				exitAssetsMap.put(newAsset.getIp_address(), -1);
			}
			if (exitAssetsMap.get(studyRuleItem.getDip()) == null) {
				FirewallAsset newAsset = new FirewallAsset();
				newAsset.setIp_address(studyRuleItem.getDip());
				newAsset.setAdd_time(new Date());
				newAsset.setAsset_name(studyRuleItem.getDip());
				newAssets.add(newAsset);
				exitAssetsMap.put(newAsset.getIp_address(), -1);
			}
		}
		
		if (newAssets.size() > 0) {
			firewallAssetMapper.insertBatch(newAssets);
		}
		
		for (FirewallAsset firewallAsset : newAssets) {
			exitAssetsMap.put(firewallAsset.getIp_address(), firewallAsset.getId());
		}
		
		//提取规则组并插入
		List<FirewallStrategyGroup> groups = 
				firewallStrategyGroupMapper.getByDeviceName(device.getDevice_name());
		
		Map<String, Integer> exitGroupMap = new HashMap<>();
		Map<String, String> exitGroupIdMap = new HashMap<>();
		for (FirewallStrategyGroup group : groups) {
			exitGroupMap.put(group.getSource_asset_ip()+"-"+group.getTarget_asset_ip(), group.getId());
			exitGroupIdMap.put(group.getId()+"", 
					(StringUtils.isBlank(group.getSource_asset_ip())?"-":group.getSource_asset_ip())+"-"
					+(StringUtils.isBlank(group.getTarget_asset_ip())?"-":group.getTarget_asset_ip())+"-");
		}
		
		List<FirewallStrategyGroup> newGroups = new ArrayList<>();
		for (StudyRuleItem item : studyRuleItems) {
			if (exitGroupMap.get(item.getSip()+"-"+item.getDip()) == null) {
				FirewallStrategyGroup newGroup = new FirewallStrategyGroup();
				newGroup.setDevice_name(device.getDevice_name());
				newGroup.setGroup_name(item.getSip()+"-"+item.getDip());
				newGroup.setSource_asset(exitAssetsMap.get(item.getSip()));
				newGroup.setSource_asset_ip(item.getSip());
				newGroup.setTarget_asset(exitAssetsMap.get(item.getDip()));
				newGroup.setTarget_asset_ip(item.getDip());
				newGroup.setAdd_time(new Date());
				newGroups.add(newGroup);
				exitGroupMap.put(newGroup.getSource_asset_ip()+"-"+newGroup.getTarget_asset_ip(), -1);
			}
		}
		
		if (newGroups.size() > 0) {
			firewallStrategyGroupMapper.insertBatch(newGroups);
		}
		
		for (FirewallStrategyGroup group : newGroups) {
			exitGroupMap.put(group.getSource_asset_ip()+"-"+group.getTarget_asset_ip(), group.getId());
			exitGroupIdMap.put(group.getId()+"-", 
					(StringUtils.isBlank(group.getSource_asset_ip())?"":group.getSource_asset_ip())+"-"
					+(StringUtils.isBlank(group.getTarget_asset_ip())?"":group.getTarget_asset_ip())+"-");
		}
		
		
		//已存在策略
		List<FirewallRuleCustom> ruleCustoms = firewallStrategyMapper.getCustomByDeviceName(deviceName);
		Map<String, Integer> exitRuleMap = new HashMap<>();
		for (FirewallRuleCustom firewallRuleCustom : ruleCustoms) {
			String ipString = exitGroupIdMap.get(firewallRuleCustom.getGroup_id()+"");
			if (StringUtils.isBlank(ipString)) {
				continue;
			}
			String key = deviceName + "-" + ipString;
			key += (StringUtils.isBlank(firewallRuleCustom.getProtocol())?"":firewallRuleCustom.getProtocol().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleCustom.getSport())?"":firewallRuleCustom.getSport().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleCustom.getDport())?"":firewallRuleCustom.getDport().trim())+"-";
			key += firewallRuleCustom.getRule_type()+"-";
			key += "-------------------";
			exitRuleMap.put(key, firewallRuleCustom.getStrategy_id());
		}
		
		List<FirewallRuleModbusTcp> ruleModbusTcps = firewallStrategyMapper.getModbusTcpByDeviceName(deviceName);
		for (FirewallRuleModbusTcp firewallRuleModbusTcp : ruleModbusTcps) {
			String ipString = exitGroupIdMap.get(firewallRuleModbusTcp.getGroup_id()+"");
			if (StringUtils.isBlank(ipString)) {
				continue;
			}
			String key = deviceName + "-" + ipString;
			key += "---";
			key += firewallRuleModbusTcp.getRule_type()+"-";
			key += (StringUtils.isBlank(firewallRuleModbusTcp.getUnit_id())?"":firewallRuleModbusTcp.getUnit_id().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleModbusTcp.getFunction_code())?"":firewallRuleModbusTcp.getFunction_code().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleModbusTcp.getRead_start())?"":firewallRuleModbusTcp.getRead_start().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleModbusTcp.getRead_length())?"":firewallRuleModbusTcp.getRead_length().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleModbusTcp.getWrite_start())?"":firewallRuleModbusTcp.getWrite_start().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleModbusTcp.getWrite_length())?"":firewallRuleModbusTcp.getWrite_length().trim())+"-";
			key += (StringUtils.isBlank(firewallRuleModbusTcp.getWrite_values())?"":firewallRuleModbusTcp.getWrite_values().trim())+"-";
			key += "------------";
			exitRuleMap.put(key, firewallRuleModbusTcp.getStrategy_id());
		}
		
		List<FirewallRuleIEC104> ruleIEC104s = firewallStrategyMapper.getIEC104ByDeviceName(deviceName);
		for (FirewallRuleIEC104 ruleIEC104 : ruleIEC104s) {
			String ipString = exitGroupIdMap.get(ruleIEC104.getGroup_id()+"");
			if (StringUtils.isBlank(ipString)) {
				continue;
			}
			String key = deviceName + "-" + ipString;
			key += "---";
			key += ruleIEC104.getRule_type()+"-";
			key += (StringUtils.isBlank(ruleIEC104.getType())?"":ruleIEC104.getType().trim())+"-";
			key += (StringUtils.isBlank(ruleIEC104.getCot())?"":ruleIEC104.getCot().trim())+"-";
			key += (StringUtils.isBlank(ruleIEC104.getCoa())?"":ruleIEC104.getCoa().trim())+"-";
			key += (StringUtils.isBlank(ruleIEC104.getIoa_start())?"":ruleIEC104.getIoa_start().trim())+"-";
			key += (StringUtils.isBlank(ruleIEC104.getIoa_length())?"":ruleIEC104.getIoa_length().trim())+"-";
			key += "--------------";
			exitRuleMap.put(key, ruleIEC104.getStrategy_id());
		}
		
		List<FirewallRuleS7> ruleS7s = firewallStrategyMapper.getS7ByDeviceName(deviceName);
		for (FirewallRuleS7 ruleS7 : ruleS7s) {
			String ipString = exitGroupIdMap.get(ruleS7.getGroup_id()+"");
			if (StringUtils.isBlank(ipString)) {
				continue;
			}
			String key = deviceName + "-" + ipString;
			key += "---";
			key += ruleS7.getRule_type()+"-";
			key += (StringUtils.isBlank(ruleS7.getPdu_type())?"":ruleS7.getPdu_type().trim())+"-";
			key += (StringUtils.isBlank(ruleS7.getFunction_code())?"":ruleS7.getFunction_code().trim())+"-";
			key += (StringUtils.isBlank(ruleS7.getUser_data_type())?"":ruleS7.getUser_data_type().trim())+"-";
			key += (StringUtils.isBlank(ruleS7.getFunction_group_code())?"":ruleS7.getFunction_group_code().trim())+"-";
			key += (StringUtils.isBlank(ruleS7.getSub_function_code())?"":ruleS7.getSub_function_code().trim())+"-";
			key += "--------------";
			exitRuleMap.put(key, ruleS7.getStrategy_id());
		}
		
		List<FirewallRuleOpcClassicTcp> opcClassicTcps = firewallStrategyMapper.getOpcClassicTcpByDeviceName(deviceName);
		for (FirewallRuleOpcClassicTcp ruleOpc : opcClassicTcps) {
			String ipString = exitGroupIdMap.get(ruleOpc.getGroup_id()+"");
			if (StringUtils.isBlank(ipString)) {
				continue;
			}
			String key = deviceName + "-" + ipString;
			key += "---";
			key += ruleOpc.getRule_type()+"-";
			key += (StringUtils.isBlank(ruleOpc.getUuid())?"":ruleOpc.getUuid().trim())+"-";
			key += (StringUtils.isBlank(ruleOpc.getOpnum())?"":ruleOpc.getOpnum().trim())+"-";
			key += "-----------------";
			exitRuleMap.put(key, ruleOpc.getStrategy_id());
		}
		
		
		//插入策略
		List<FirewallRuleCustom> firewallRuleCustoms = new ArrayList<>();
		List<FirewallRuleModbusTcp> firewallRuleModbusTcps = new ArrayList<>();
		List<FirewallRuleIEC104> firewallRuleIEC104s = new ArrayList<>();
		List<FirewallRuleS7> firewallRuleS7s = new ArrayList<>();
		List<FirewallRuleOpcClassicTcp> firewallRuleOpcs = new ArrayList<>();
		for (StudyRuleItem item : studyRuleItems) {
			
			if (exitRuleMap.get(item.toCompareString()) != null) {
				continue;
			}
			
			if (item.getRule_type() == FirewallStrategy.RULE_TYPE_CUSTOM) {
				FirewallRuleCustom ruleCustom = new FirewallRuleCustom();
				if (StringUtils.isNotBlank(item.getProtocol())) {
					ruleCustom.setProtocol("IP");
				}
				ruleCustom.setSport(item.getSport());
				ruleCustom.setDport(item.getDport());
				ruleCustom.setDevice_name(device.getDevice_name());
				ruleCustom.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleCustom.setStrategy_name("学习规则");
				ruleCustom.setRule_type(FirewallStrategy.RULE_TYPE_CUSTOM);
				ruleCustom.setTime_type(1);
				ruleCustom.setLog_power(2);
				ruleCustom.setRule_action(1);
				firewallRuleCustoms.add(ruleCustom);
				
			} else if (item.getRule_type() == FirewallStrategy.RULE_TYPE_MODBUS_TCP) {
				FirewallRuleModbusTcp ruleModbusTcp = new FirewallRuleModbusTcp();
				ruleModbusTcp.setDevice_name(device.getDevice_name());
				ruleModbusTcp.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleModbusTcp.setStrategy_name("MODBUS学习规则");
				ruleModbusTcp.setRule_type(FirewallStrategy.RULE_TYPE_MODBUS_TCP);
				ruleModbusTcp.setTime_type(1);
				ruleModbusTcp.setLog_power(2);
				ruleModbusTcp.setRule_action(1);
				ruleModbusTcp.setUnit_id(item.getExtension1());
				ruleModbusTcp.setFunction_code(item.getExtension2());
				ruleModbusTcp.setRead_start(item.getExtension3());
				ruleModbusTcp.setRead_length(item.getExtension4());
				ruleModbusTcp.setWrite_start(item.getExtension5());
				ruleModbusTcp.setWrite_length(item.getExtension6());
				ruleModbusTcp.setWrite_values(item.getExtension7());
				firewallRuleModbusTcps.add(ruleModbusTcp);
				
			} else if (item.getRule_type() == FirewallStrategy.RULE_TYPE_IEC104) {
				FirewallRuleIEC104 ruleIEC104 = new FirewallRuleIEC104();
				ruleIEC104.setDevice_name(device.getDevice_name());
				ruleIEC104.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleIEC104.setStrategy_name("IEC104学习规则");
				ruleIEC104.setRule_type(FirewallStrategy.RULE_TYPE_IEC104);
				ruleIEC104.setTime_type(1);
				ruleIEC104.setLog_power(2);
				ruleIEC104.setRule_action(1);
				ruleIEC104.setType(item.getExtension1());
				ruleIEC104.setCot(item.getExtension2());
				ruleIEC104.setCoa(item.getExtension3());
				ruleIEC104.setIoa_start(item.getExtension4());
				ruleIEC104.setIoa_length(item.getExtension5());
				firewallRuleIEC104s.add(ruleIEC104);
				
			} else if (item.getRule_type() == FirewallStrategy.RULE_TYPE_S7) {
				FirewallRuleS7 ruleS7 = new FirewallRuleS7();
				ruleS7.setDevice_name(device.getDevice_name());
				ruleS7.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleS7.setStrategy_name("S7学习规则");
				ruleS7.setRule_type(FirewallStrategy.RULE_TYPE_S7);
				ruleS7.setTime_type(1);
				ruleS7.setLog_power(2);
				ruleS7.setRule_action(1);
				ruleS7.setPdu_type(item.getExtension1());
				ruleS7.setFunction_code(item.getExtension2());
				ruleS7.setUser_data_type(item.getExtension3());
				ruleS7.setFunction_group_code(item.getExtension4());
				ruleS7.setSub_function_code(item.getExtension5());
				firewallRuleS7s.add(ruleS7);
				
			} else if (item.getRule_type() == FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP) {
				FirewallRuleOpcClassicTcp opcClassicTcp = new FirewallRuleOpcClassicTcp();
				opcClassicTcp.setDevice_name(device.getDevice_name());
				opcClassicTcp.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				opcClassicTcp.setStrategy_name("opc学习规则");
				opcClassicTcp.setRule_type(FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP);
				opcClassicTcp.setTime_type(1);
				opcClassicTcp.setLog_power(2);
				opcClassicTcp.setRule_action(1);
				opcClassicTcp.setUuid(item.getExtension1());
				opcClassicTcp.setOpnum(item.getExtension2());
				firewallRuleOpcs.add(opcClassicTcp);
				
			}
			
			
		}
		
		//过滤普通规则
		Map<String, FirewallRuleCustom> firewallRuleCustomMap = new HashMap<>();
		for (FirewallRuleCustom firewallRuleCustom : firewallRuleCustoms) {
			String key = "";
			key += firewallRuleCustom.getDevice_name() + "--";
			key += firewallRuleCustom.getGroup_id() + "--";
			if (StringUtils.isNotBlank(firewallRuleCustom.getSport())) {
				key += firewallRuleCustom.getSport() + "--";
			} else {
				key += "--";
			}
			if (StringUtils.isNotBlank(firewallRuleCustom.getDport())) {
				key += firewallRuleCustom.getDport();
			}
			firewallRuleCustomMap.put(key, firewallRuleCustom);
		}
		
		for (FirewallRuleModbusTcp firewallRuleModbusTcp : firewallRuleModbusTcps) {
			String key = "";
			key += firewallRuleModbusTcp.getDevice_name() + "--";
			key += firewallRuleModbusTcp.getGroup_id() + "--";
			key += "--";
			key += "502";
			FirewallRuleCustom ruleCustom = firewallRuleCustomMap.get(key);
			if (ruleCustom != null) {
				firewallRuleCustomMap.remove(key);
				firewallRuleCustoms.remove(ruleCustom);
			}
		}
		
		for (FirewallRuleIEC104 firewallRuleIEC104 : firewallRuleIEC104s) {
			String key = "";
			key += firewallRuleIEC104.getDevice_name() + "--";
			key += firewallRuleIEC104.getGroup_id() + "--";
			key += "--";
			key += "2404";
			FirewallRuleCustom ruleCustom = firewallRuleCustomMap.get(key);
			if (ruleCustom != null) {
				firewallRuleCustomMap.remove(key);
				firewallRuleCustoms.remove(ruleCustom);
			}
		}
		
		for (FirewallRuleS7 firewallRuleS7 : firewallRuleS7s) {
			String key = "";
			key += firewallRuleS7.getDevice_name() + "--";
			key += firewallRuleS7.getGroup_id() + "--";
			key += "--";
			key += "102";
			FirewallRuleCustom ruleCustom = firewallRuleCustomMap.get(key);
			if (ruleCustom != null) {
				firewallRuleCustomMap.remove(key);
				firewallRuleCustoms.remove(ruleCustom);
			}
		}
		
		//opc没有端口号，单独处理
		for (FirewallRuleOpcClassicTcp firewallRuleOpcClassicTcp : firewallRuleOpcs) {
			
			for (FirewallRuleCustom firewallRuleCustom : firewallRuleCustoms) {
				if (firewallRuleOpcClassicTcp.getDevice_name().equals(firewallRuleCustom.getDevice_name()) &&
						firewallRuleOpcClassicTcp.getGroup_id() == firewallRuleCustom.getGroup_id()) {
					firewallRuleCustoms.remove(firewallRuleCustom);
				}
			}
			
		}
		
		
		if (firewallRuleCustoms.size() > 0) {
			firewallRuleCustomMapper.insertBatch(firewallRuleCustoms);
		}
		
		if (firewallRuleModbusTcps.size() > 0) {
			firewallRuleModbusTcpMapper.insertBatch(firewallRuleModbusTcps);
		}
		
		if (firewallRuleIEC104s.size() > 0) {
			firewallRuleIEC104Mapper.insertBatch(firewallRuleIEC104s);
		}
		
		if (firewallRuleS7s.size() > 0) {
			firewallRuleS7Mapper.insertBatch(firewallRuleS7s);
		}
		
		if (firewallRuleOpcs.size() > 0) {
			firewallRuleOpcClassicTcpMapper.insertBatch(firewallRuleOpcs);
		}
		
		List<FirewallStrategy> strategies = new ArrayList<>();
		for (FirewallRuleCustom firewallRuleCustom : firewallRuleCustoms) {
			firewallRuleCustom.setRule_id(firewallRuleCustom.getId());
			strategies.add(firewallRuleCustom);
		}
		for (FirewallRuleModbusTcp firewallRuleModbusTcp : firewallRuleModbusTcps) {
			firewallRuleModbusTcp.setRule_id(firewallRuleModbusTcp.getId());
			strategies.add(firewallRuleModbusTcp);
		}
		for (FirewallRuleIEC104 firewallRuleIEC104 : firewallRuleIEC104s) {
			firewallRuleIEC104.setRule_id(firewallRuleIEC104.getId());
			strategies.add(firewallRuleIEC104);
		}
		for (FirewallRuleS7 firewallRuleS7 : firewallRuleS7s) {
			firewallRuleS7.setRule_id(firewallRuleS7.getId());
			strategies.add(firewallRuleS7);
		}
		for (FirewallRuleOpcClassicTcp firewallRuleOpcClassicTcp : firewallRuleOpcs) {
			firewallRuleOpcClassicTcp.setRule_id(firewallRuleOpcClassicTcp.getId());
			strategies.add(firewallRuleOpcClassicTcp);
		}
		
		if (strategies.size() > 0) {
			firewallStrategyMapper.insertBatch(strategies);
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String openStudyMode(JSONObject paramJson, HttpServletRequest request) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//模式开关
		if (!paramJson.has("mode")) {
			commonResult.setErrorMsg("缺少参数mode");
			return commonResult.toString();
		}
		int mode = paramJson.getInt("mode");
		
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			return commonResult.toString();
		}
		
		if (mode == 1) {
			
			StringBuilder instruction = new StringBuilder();
			instruction.append("0x20 0x201 flush;0x30 opc flush;");
			instruction.append("0x20 0x201 last;0x30 opc last;");
			Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
			socketConfig.put("mip", device.getIp_address());
			//发送指令
			String result = TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
			
			if  (StringUtils.isNotBlank(result)) {
				commonResult.setErrorMsg(result);
				return commonResult.toString();
			}
		} else {
			device.setStudy_mode(mode);
			firewallDeviceMapper.update(device);
			DEVICE_CACHE_MAP.put(deviceName, device);
			return firewallStrategyService.sync(paramJson, request);
		}
		
		device.setStudy_mode(mode);
		firewallDeviceMapper.update(device);
		DEVICE_CACHE_MAP.put(deviceName, device);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String listByDeviceName(JSONObject paramJson, HttpServletRequest request) throws Exception {

		CommonResult commonResult = new CommonResult(false, "");
		
		//获取设备列表用于页面下拉选择
		JSONArray deviceArray = firewallDeviceService.getForSelectTag();
		commonResult.put("deviceArray", deviceArray);
		
		FirewallDevice device = null;
		if (paramJson.has("deviceName")) {
			device = firewallDeviceMapper.getByName(paramJson.getString("deviceName"));
		}else if (deviceArray.size() > 0) {
			device = firewallDeviceMapper.getByName(deviceArray.getJSONObject(0).getString("deviceName"));
		}
		
		if (device == null) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		
		int page = 1;
		if (paramJson.has("page")) {
			page = paramJson.getInt("page");
		}
		
		int pageSize = 10;
		if (paramJson.has("pageSize")) {
			pageSize = paramJson.getInt("pageSize");
		}
		
		List<SearchCondition>  conditions = new ArrayList<>();
		//检索条件 源IP
		if (paramJson.has("sip")) {
			String sip = paramJson.getString("sip");
			if (StringUtils.isNotBlank(sip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "sip", sip.trim()));
			}
		}
		
		//检索条件 目的IP
		if (paramJson.has("dip")) {
			String dip = paramJson.getString("dip");
			if (StringUtils.isNotBlank(dip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "dip", dip.trim()));
			}
		}
		
		//检索条件 源端口
		if (paramJson.has("sport")) {
			String sport = paramJson.getString("sport");
			if (StringUtils.isNotBlank(sport)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "sport", sport.trim()));
			}
		}
		
		//检索条件 目的端口
		if (paramJson.has("dport")) {
			String dport = paramJson.getString("dport");
			if (StringUtils.isNotBlank(dport)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "dport", dport.trim()));
			}
		}
		
		//检索条件 协议类型
		if (paramJson.has("protocol")) {
			String protocol = paramJson.getString("protocol");
			if (StringUtils.isNotBlank(protocol)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "protocol", protocol));
			}
		}
		
		//检索条件 工业协议
		if (paramJson.has("industryProrocol")) {
			String industryProrocol = paramJson.getString("industryProrocol");
			if (StringUtils.isNotBlank(industryProrocol)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "rule_type", industryProrocol));
			}
		}
			
		String condition = SearchCondition.toConditions(conditions);
		
//		List<StudyRuleItem> ruleItems = 
//				studyRuleItemMapper.getByDeviceName(device.getDevice_name());
		List<StudyRuleItem> ruleItems = 
				studyRuleItemMapper.searchForPage(
						device.getDevice_name(),
						(page-1)*pageSize, 
						pageSize,
						condition);
		
		int count = studyRuleItemMapper.searchCount(device.getDevice_name(), condition);
			
		commonResult.put("ruleList", ruleItems);
		commonResult.put("device", device);
		commonResult.put("count", count);
		commonResult.put("page", page);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(StudyRuleItem studyRuleItem) throws Exception {
		
		if (StringUtils.isBlank(studyRuleItem.getDevice_name())) {
			return;
		}
		
		Object ruleMap = RULE_CACHE_MAP.get(studyRuleItem.getDevice_name());
		
		Map<String, StudyRuleItem> deviceRules;
		
		
		if (ruleMap == null) {
			
			List<StudyRuleItem> ruleItems = 
					studyRuleItemMapper.getByDeviceName(studyRuleItem.getDevice_name());
			
			//从数据库获取并添加到缓存
			deviceRules = new HashMap<>();
			if (ruleItems != null && ruleItems.size() > 0) {
				for (StudyRuleItem ruleItem : ruleItems) {
					deviceRules.put(ruleItem.toCompareString(), ruleItem);
				}
			}
			
			RULE_CACHE_MAP.put(studyRuleItem.getDevice_name(), deviceRules);
		} else {
			deviceRules = (Map<String, StudyRuleItem>)ruleMap;
		}
		
		if (deviceRules.get(studyRuleItem.toCompareString()) == null) {
			deviceRules.put(studyRuleItem.toCompareString(), studyRuleItem);
			studyRuleItemMapper.insert(studyRuleItem);
		}
		
		//反向
//		if (StringUtils.isNotBlank(studyRuleItem.getSport()) || 
//				StringUtils.isNotBlank(studyRuleItem.getDport())) {
//			StudyRuleItem studyRuleItem2 = new StudyRuleItem();
//			studyRuleItem2.setDevice_name(studyRuleItem.getDevice_name());
//			studyRuleItem2.setSip(studyRuleItem.getDip());
//			studyRuleItem2.setDip(studyRuleItem.getSip());
//			studyRuleItem2.setSport(studyRuleItem.getDport());
//			studyRuleItem2.setDport(studyRuleItem.getSport());
//			studyRuleItem2.setProtocol(studyRuleItem.getProtocol());
//			if (deviceRules.get(studyRuleItem2.toCompareString()) == null) {
//				deviceRules.put(studyRuleItem2.toCompareString(), studyRuleItem2);
//				studyRuleItemMapper.insert(studyRuleItem2);
//			}
//		}
		
		
	}

	@Override
	public boolean isStudyMode(String deviceName) throws Exception {
		
		FirewallDevice device = DEVICE_CACHE_MAP.get(deviceName);
		
		if (device == null) {
			device = firewallDeviceMapper.getByName(deviceName);
		}
		
		if (device == null || device.getMode() != FirewallDevice.MODE_STUDY) {
			return false;
		}
		
		return true;
	}

	@Override
	public String deleteByDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		//设备名
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			return commonResult.toString();
		}
		
		studyRuleItemMapper.deleteByDeviceName(deviceName);
		RULE_CACHE_MAP.remove(deviceName);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String exportStudyRule(JSONObject paramJson, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		List<SearchCondition>  conditions = new ArrayList<>();
		//检索条件 源IP
		if (paramJson.has("sip")) {
			String sip = paramJson.getString("sip");
			if (StringUtils.isNotBlank(sip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "sip", sip.trim()));
			}
		}
		
		//检索条件 目的IP
		if (paramJson.has("dip")) {
			String dip = paramJson.getString("dip");
			if (StringUtils.isNotBlank(dip)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "dip", dip.trim()));
			}
		}
		
		//检索条件 源端口
		if (paramJson.has("sport")) {
			String sport = paramJson.getString("sport");
			if (StringUtils.isNotBlank(sport)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "sport", sport.trim()));
			}
		}
		
		//检索条件 目的端口
		if (paramJson.has("dport")) {
			String dport = paramJson.getString("dport");
			if (StringUtils.isNotBlank(dport)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "dport", dport.trim()));
			}
		}
		
		//检索条件 协议类型
		if (paramJson.has("protocol")) {
			String protocol = paramJson.getString("protocol");
			if (StringUtils.isNotBlank(protocol)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "protocol", protocol));
			}
		}
		
		//检索条件 工业协议
		if (paramJson.has("industryProrocol")) {
			String industryProrocol = paramJson.getString("industryProrocol");
			if (StringUtils.isNotBlank(industryProrocol)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "rule_type", industryProrocol));
			}
		}
			
		String condition = SearchCondition.toConditions(conditions);
		
		List<StudyRuleItem> ruleItems = 
				studyRuleItemMapper.search(deviceName, condition);
		
		//创建文件
		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String destFileName = System.currentTimeMillis() + "_exportStudyRule";
		File destFile = new File(fileSavePath + destFileName);
		int i = 1;
		while (destFile.exists()) {
			destFileName += i;
			destFile = new File(fileSavePath + destFileName);
			i ++;
		}
		
		
		
		//创建工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();//这里也可以设置sheet的Name
        //创建工作表对象
        HSSFSheet CustomeSheet = workbook.createSheet();
        HSSFSheet ModbusTcpSheet = workbook.createSheet();
        HSSFSheet IEC104Sheet = workbook.createSheet();
        HSSFSheet S7Sheet = workbook.createSheet();
        HSSFSheet opcSheet = workbook.createSheet();
        workbook.setSheetName(0,"普通规则");//设置sheet的Name
        workbook.setSheetName(1,"MODBUS_TCP规则");
        workbook.setSheetName(2,"IEC104规则");
        workbook.setSheetName(3,"S7规则");
        workbook.setSheetName(4,"OPC规则");
        
        
        //创建工作表的行
        HSSFRow customeRow = CustomeSheet.createRow(0);//设置第一行表头，从零开始
        customeRow.createCell(0).setCellValue("源IP");
        customeRow.createCell(1).setCellValue("目的IP");
        customeRow.createCell(2).setCellValue("协议类型");
        customeRow.createCell(3).setCellValue("源端口");
        customeRow.createCell(4).setCellValue("目的端口");
        
        HSSFRow modbustcpRow = ModbusTcpSheet.createRow(0);
        modbustcpRow.createCell(0).setCellValue("源IP");
        modbustcpRow.createCell(1).setCellValue("目的IP");
        modbustcpRow.createCell(2).setCellValue("设备ID");
        modbustcpRow.createCell(3).setCellValue("功能码");
        modbustcpRow.createCell(4).setCellValue("读起始地址");
        modbustcpRow.createCell(5).setCellValue("读地址长度");
        modbustcpRow.createCell(6).setCellValue("写起始地址");
        modbustcpRow.createCell(7).setCellValue("写地址长度");
        modbustcpRow.createCell(8).setCellValue("写值");
        
        HSSFRow iec14Row = IEC104Sheet.createRow(0);
        iec14Row.createCell(0).setCellValue("源IP");
        iec14Row.createCell(1).setCellValue("目的IP");
        iec14Row.createCell(2).setCellValue("类型标识");
        iec14Row.createCell(3).setCellValue("传输原因");
        iec14Row.createCell(4).setCellValue("公共地址");
        iec14Row.createCell(5).setCellValue("信息对象起始地址");
        iec14Row.createCell(6).setCellValue("信息对象地址长度");
        
        HSSFRow s7Row = S7Sheet.createRow(0);
        s7Row.createCell(0).setCellValue("源IP");
        s7Row.createCell(1).setCellValue("目的IP");
        s7Row.createCell(2).setCellValue("PDU类型");
        s7Row.createCell(3).setCellValue("JOB功能码");
        s7Row.createCell(4).setCellValue("USERDATA参数类型");
        s7Row.createCell(5).setCellValue("USERDATA功能组");
        s7Row.createCell(6).setCellValue("USERDATA子功能码");
        
        HSSFRow opcRow = opcSheet.createRow(0);
        opcRow.createCell(0).setCellValue("源IP");
        opcRow.createCell(1).setCellValue("目的IP");
        opcRow.createCell(2).setCellValue("UUID");
        opcRow.createCell(3).setCellValue("Opcnum");
        
        int customRowIndex = 1;
        int modbusRowIndex = 1;
        int iec104RowIndex = 1;
        int s7RowIndex = 1;
        int opcRowIndex = 1;
        for (int j = 0; j < ruleItems.size(); j++) {
        	StudyRuleItem ruleItem = ruleItems.get(j);
        	if (ruleItem.getRule_type() == FirewallStrategy.RULE_TYPE_CUSTOM) {
        		HSSFRow row = CustomeSheet.createRow(customRowIndex);
            	row.createCell(0).setCellValue(ruleItem.getSip());
                row.createCell(1).setCellValue(ruleItem.getDip());
                row.createCell(2).setCellValue(StringUtils.isBlank(ruleItem.getProtocol())?"ip":ruleItem.getProtocol());
                row.createCell(3).setCellValue(ruleItem.getSport());
                row.createCell(4).setCellValue(ruleItem.getDport());
                customRowIndex ++;
        	} else if (ruleItem.getRule_type() == FirewallStrategy.RULE_TYPE_MODBUS_TCP) {
        		HSSFRow row = ModbusTcpSheet.createRow(modbusRowIndex);
            	row.createCell(0).setCellValue(ruleItem.getSip());
                row.createCell(1).setCellValue(ruleItem.getDip());
                row.createCell(2).setCellValue(ruleItem.getExtension1());
                row.createCell(3).setCellValue(ruleItem.getExtension2());
                row.createCell(4).setCellValue(ruleItem.getExtension3());
                row.createCell(5).setCellValue(ruleItem.getExtension4());
                row.createCell(6).setCellValue(ruleItem.getExtension5());
                row.createCell(7).setCellValue(ruleItem.getExtension6());
                
                if ("5".equals(ruleItem.getExtension2())) {
                	if ("65280".equals(ruleItem.getExtension7())) {
                    	row.createCell(8).setCellValue("ON");
                    } else if ("0".equals(ruleItem.getExtension7())) {
                    	row.createCell(8).setCellValue("OFF");
                    }
                	
                } else if ("15".equals(ruleItem.getExtension2())) {
                	if (ruleItem.getExtension7() != null) {
                		String extension7Str = ruleItem.getExtension7().replaceAll("1", "ON");
                		extension7Str = extension7Str.replaceAll("0", "OFF");
                    	row.createCell(8).setCellValue(extension7Str);
                	}
                	
                } else {
                	row.createCell(8).setCellValue(ruleItem.getExtension7());
                }
                
                modbusRowIndex ++;
        	} else if (ruleItem.getRule_type() == FirewallStrategy.RULE_TYPE_IEC104) {
        		HSSFRow row = IEC104Sheet.createRow(iec104RowIndex);
            	row.createCell(0).setCellValue(ruleItem.getSip());
                row.createCell(1).setCellValue(ruleItem.getDip());
                row.createCell(2).setCellValue(ruleItem.getExtension1());
                row.createCell(3).setCellValue(ruleItem.getExtension2());
                row.createCell(4).setCellValue(ruleItem.getExtension3());
                row.createCell(5).setCellValue(ruleItem.getExtension4());
                row.createCell(6).setCellValue(ruleItem.getExtension5());
                iec104RowIndex ++;
        	} else if (ruleItem.getRule_type() == FirewallStrategy.RULE_TYPE_S7) {
        		HSSFRow row = IEC104Sheet.createRow(s7RowIndex);
            	row.createCell(0).setCellValue(ruleItem.getSip());
                row.createCell(1).setCellValue(ruleItem.getDip());
                row.createCell(2).setCellValue(ruleItem.getExtension1());
                row.createCell(3).setCellValue(ruleItem.getExtension2());
                row.createCell(4).setCellValue(ruleItem.getExtension3());
                row.createCell(5).setCellValue(ruleItem.getExtension4());
                row.createCell(6).setCellValue(ruleItem.getExtension5());
                s7RowIndex ++;
        	} else if (ruleItem.getRule_type() == FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP) {
        		HSSFRow row = opcSheet.createRow(opcRowIndex);
            	row.createCell(0).setCellValue(ruleItem.getSip());
                row.createCell(1).setCellValue(ruleItem.getDip());
                row.createCell(2).setCellValue(ruleItem.getExtension1());
                row.createCell(3).setCellValue(ruleItem.getExtension2());
                opcRowIndex ++;
        	}
        	
		}
       
 
        //文档输出
        FileOutputStream out = new FileOutputStream(destFile);
        workbook.write(out);
        out.close();
        
        //缓存文件下载信息
        FileDownloadInfo downloadInfo = 
				new FileDownloadInfo(new Date().getTime(), 1*60*1000);
		downloadInfo.setToken(UUID.randomUUID().toString());
		downloadInfo.setJsonParam(paramJson);
		downloadInfo.setFilePath(destFile.getParent() + File.separator + destFileName);
		downloadInfo.setFileName(deviceName + "学习规则列表.xls");
		setCache(FIRE_DOWNLOAD_INFO+downloadInfo.getToken(), downloadInfo);
		
		String logDesc = "[设备名:"+deviceName+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		commonResult.setStatus(true);
		commonResult.put("fileMark", downloadInfo.getToken());
		return commonResult.toString();
		
	}
	
	public static String getIpAddress() {
	    try {
	      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	      InetAddress ip = null;
	      while (allNetInterfaces.hasMoreElements()) {
	        NetworkInterface netInterface = allNetInterfaces.nextElement();
	        if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
	          continue;
	        } else {
	          Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
	          String ipStr = "";
	          while (addresses.hasMoreElements()) {
	            ip = addresses.nextElement();
	            if (ip != null && ip instanceof Inet4Address) {
	            	ipStr += ip.getHostAddress();
	            }
	          }
	          return ipStr;
	        }
	      }
	    } catch (Exception e) {
	    	System.err.println("IP地址获取失败" + e.toString());
	    }
	    return "";
	  }
 
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] b= new byte[]{65,66,67,68};//字节bai数组du
		String s= new String(b,"ascii");//第二个zhi参数指定dao编zhuan码方shu式
		System.out.print(s);
	}

}
