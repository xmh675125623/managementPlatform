package com.jiuzhou.firewall.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.jiuzhou.firewall.bean.FirewallAsset;
import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallDeviceEthernet;
import com.jiuzhou.firewall.bean.FirewallRuleCustom;
import com.jiuzhou.firewall.bean.FirewallRuleIEC104;
import com.jiuzhou.firewall.bean.FirewallRuleImportItem;
import com.jiuzhou.firewall.bean.FirewallRuleModbusTcp;
import com.jiuzhou.firewall.bean.FirewallRuleOpcClassicTcp;
import com.jiuzhou.firewall.bean.FirewallRuleS7;
import com.jiuzhou.firewall.bean.FirewallRuleTemp;
import com.jiuzhou.firewall.bean.FirewallStrategy;
import com.jiuzhou.firewall.bean.FirewallStrategyGroup;
import com.jiuzhou.firewall.mapper.FirewallAssetMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceEthernetMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleCustomMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleIEC104Mapper;
import com.jiuzhou.firewall.mapper.FirewallRuleModbusTcpMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleOpcClassicTcpMapper;
import com.jiuzhou.firewall.mapper.FirewallRuleS7Mapper;
import com.jiuzhou.firewall.mapper.FirewallRuleTempMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyGroupMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallStrategyService;
import com.jiuzhou.firewall.utils.FirewallStrategyInstructionUtil;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.AuditModbusFunctionCode;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.IEC104ASDUConstant;
import com.jiuzhou.plat.bean.S7FunctionCode;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.mapper.AuditModbusFunctionCodeMapper;
import com.jiuzhou.plat.mapper.IEC104ASDUConstantMapper;
import com.jiuzhou.plat.mapper.S7FunctionCodeMapper;
import com.jiuzhou.plat.service.impl.DiskSpaceServiceImpl;
import com.jiuzhou.plat.service.impl.ServiceBase;
import com.jiuzhou.plat.util.SearchCondition;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version ???????????????2019???1???29??? ??????5:20:40
* ?????????
*/
@Service("FirewallStrategyService")
public class FirewallStrategyServiceImpl implements FirewallStrategyService {

	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallStrategyGroupMapper firewallStrategyGroupMapper;
	
	@Autowired
	private FirewallStrategyMapper firewallStrategyMapper;
	
	@Autowired
	private FirewallRuleTempMapper firewallRuleTempMapper;
	
	@Autowired
	private FirewallRuleCustomMapper firewallRuleCustomMapper;
	
	@Autowired
	private FirewallRuleOpcClassicTcpMapper firewallRuleOpcClassicTcpMapper;
	
	@Autowired
	private FirewallRuleModbusTcpMapper firewallRuleModbusTcpMapper;
	
	@Autowired
	private FirewallRuleIEC104Mapper firewallRuleIEC104Mapper;
	
	@Autowired
	private FirewallRuleS7Mapper firewallRuleS7Mapper; 
	
	@Autowired
	private S7FunctionCodeMapper s7FunctionCodeMapper;
	
	@Autowired
	private IEC104ASDUConstantMapper iEC104ASDUConstantMapper;
	
	@Autowired
	private AuditModbusFunctionCodeMapper auditModbusFunctionCodeMapper;
	
	@Autowired
	private FirewallDeviceEthernetMapper firewallDeviceEthernetMapper;
	
	@Autowired
	private FirewallAssetMapper firewallAssetMapper;
	
	
	/**
	 * ????????????????????????????????????
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String listByDeviceName(JSONObject paramJson, HttpServletRequest request) 
			throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//??????????????????????????????????????????
		JSONArray deviceArray = firewallDeviceService.getForSelectTag();
		commonResult.put("deviceArray", deviceArray);
		
		FirewallDevice device = null;
		if (paramJson.has("deviceName")) {
			device = 
					firewallDeviceMapper.getByName(paramJson.getString("deviceName"));
		}else if (deviceArray.size() > 0) {
			device = 
					firewallDeviceMapper.getByName(deviceArray.getJSONObject(0).getString("deviceName"));
		}
		
		if (device == null) {
			FirewallStrategyGroup group = new FirewallStrategyGroup();
			commonResult.put("group", group);
			commonResult.put("strategies", new ArrayList<>());
			commonResult.put("strategyCount", 0);
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		commonResult.put("device", device);		
		
		//????????????group????????????
		List<FirewallStrategyGroup> groupList = 
				firewallStrategyGroupMapper.getByDeviceName(device.getDevice_name());
		commonResult.put("groupList", groupList);
		
		FirewallStrategyGroup group = null;
		if (paramJson.has("groupId")) {
			group = firewallStrategyGroupMapper.getById(paramJson.getInt("groupId"));
		} else if (groupList.size() > 0) {
			group = groupList.get(0);
		}
		commonResult.put("group", group);
		if (group == null) {
			group = new FirewallStrategyGroup();
			commonResult.put("group", group);
			commonResult.put("strategies", new ArrayList<>());
			commonResult.put("strategyCount", 0);
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
		
		if (page == 1) {
			//?????????????????????????????????????????????????????????????????????
			List<FirewallRuleTemp> commonRules = 
					firewallRuleTempMapper.getByType(FirewallRuleTemp.TYPE_COMMON);
			
			List<FirewallRuleTemp> specialRules = 
					firewallRuleTempMapper.getByType(FirewallRuleTemp.TYPE_SPECIAL);
			commonResult.put("commonRules", commonRules);
			commonResult.put("specialRules", specialRules);
			
			//??????modbus???????????????????????????????????????
			List<AuditModbusFunctionCode> functionCodes = 
					auditModbusFunctionCodeMapper.getByType(AuditModbusFunctionCode.TYPE_MODBUS_TCP);
			commonResult.put("functionCodes", functionCodes);
			
			//??????iec104????????????
			List<IEC104ASDUConstant> iecTypes = 
					iEC104ASDUConstantMapper.getByType(IEC104ASDUConstant.TYPE_TYPE);
			List<IEC104ASDUConstant> iecCots = 
					iEC104ASDUConstantMapper.getByType(IEC104ASDUConstant.TYPE_COT);
			
			//??????s7???????????????????????????????????????
			List<S7FunctionCode> s7JobFunctionCodes = 
					s7FunctionCodeMapper.getSuperByPduType(S7FunctionCode.PDU_TYPE_JOB);
			List<S7FunctionCode> s7UserDataFunctionGroups = 
					s7FunctionCodeMapper.getSuperByPduType(S7FunctionCode.PDU_TYPE_USERDATA);
			List<S7FunctionCode> s7UserDataSubFunctionCodes = 
					s7FunctionCodeMapper.getSubByPduType(S7FunctionCode.PDU_TYPE_USERDATA);
			Map<String, S7FunctionCode> s7UserDataFunctionGroupMap = new HashMap<>();
			for (S7FunctionCode s7FunctionCode : s7UserDataFunctionGroups) {
				s7UserDataFunctionGroupMap.put(s7FunctionCode.getId()+"", s7FunctionCode);
			}
			for (S7FunctionCode s7FunctionCode : s7UserDataSubFunctionCodes) {
				S7FunctionCode functionGroup = s7UserDataFunctionGroupMap.get(s7FunctionCode.getParent_id()+"");
				if (functionGroup != null) {
					functionGroup.getSubFunctions().add(s7FunctionCode);
				}
			}
			commonResult.put("s7JobFunctionCodes", s7JobFunctionCodes);
			commonResult.put("s7UserDataFunctionGroups", s7UserDataFunctionGroups);
					
			
			//??????????????????
			List<FirewallDeviceEthernet> ethernets = 
					firewallDeviceEthernetMapper.getByDeviceName(device.getDevice_name());
			if (ethernets == null) {
				ethernets = new ArrayList<>();
			} else {
				//???????????????
				for (FirewallDeviceEthernet ethernet : ethernets) {
					if (ethernet.getMode() == 0) {
						ethernets.remove(ethernet);
						break;
					}
				}
			}
			
			commonResult.put("ethernets", ethernets);
			commonResult.put("iecTypes", iecTypes);
			commonResult.put("iecCots", iecCots);
		}
		
		List<SearchCondition>  conditions = new ArrayList<>();
		//???????????? ????????????
		if (paramJson.has("ruleType")) {
			String ruleType = paramJson.getString("ruleType");
			if (StringUtils.isNotBlank(ruleType)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_IN, "rule_type", ruleType));
			}
		}
		
		//???????????? ?????????
		if (paramJson.has("strategyName")) {
			String strategyName = paramJson.getString("strategyName");
			if (StringUtils.isNotBlank(strategyName)) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_LIKE, "strategy_name", strategyName));
			}
		}
		
		//???????????? ????????????
		if (paramJson.has("ruleAction")) {
			int ruleAction = paramJson.getInt("ruleAction");
			if (ruleAction > 0) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "rule_action", ruleAction+""));
			}
		}
		
		//???????????? ????????????
		if (paramJson.has("logAction")) {
			int logAction = paramJson.getInt("logAction");
			if (logAction > 0) {
				conditions.add(new SearchCondition(SearchCondition.TYPE_EQUAL, "log_power", logAction+""));
			}
		}
		
		//????????????????????????
		List<FirewallStrategy> strategies = 
				firewallStrategyMapper.getByGroupIdAndPage(
						group.getId(), 
						(page-1)*pageSize,
						pageSize,
						SearchCondition.toConditions(conditions));
		if (strategies == null) {
			strategies = new ArrayList<>();
		}
		
		int strategyCount = 
				firewallStrategyMapper.getCountByGroupId(
						group.getId(),
						SearchCondition.toConditions(conditions));
		
		//????????????????????????
		if (strategies.size() > 0) {
			strategies = this.getRuleStrategys(strategies);
		}
		
		
		commonResult.put("strategies", strategies);
		commonResult.put("strategyCount", strategyCount);
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * ??????????????????????????????
	 * @param strategies
	 * @return
	 */
	private List<FirewallStrategy> getRuleStrategys(List<FirewallStrategy> strategies){
		
		List<Integer> customStrategyIds = new ArrayList<>();
		List<Integer> commonStrategyIds = new ArrayList<>();
		List<Integer> specialStrategyIds = new ArrayList<>();
		List<Integer> opcClassicTcpStrategyIds = new ArrayList<>();
		List<Integer> modbusTcpStrategyIds = new ArrayList<>();
		List<Integer> IEC104TcpStrategyIds = new ArrayList<>();
		List<Integer> s7StrategyIds = new ArrayList<>();
		
		for (FirewallStrategy strategy : strategies) {
			switch (strategy.getRule_type()) {
				case FirewallStrategy.RULE_TYPE_CUSTOM: //???????????????
					customStrategyIds.add(strategy.getStrategy_id());
					break;
					
				case FirewallStrategy.RULE_TYPE_COMMON: //?????????????????????
					commonStrategyIds.add(strategy.getStrategy_id());
					break;
					
				case FirewallStrategy.RULE_TYPE_SPECIAL: //?????????????????????
					specialStrategyIds.add(strategy.getStrategy_id());
					break;
					
				case FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP: //opc classic tcp ??????
					opcClassicTcpStrategyIds.add(strategy.getStrategy_id());
					break;
					
				case FirewallStrategy.RULE_TYPE_MODBUS_TCP: //modbus tcp ??????
					modbusTcpStrategyIds.add(strategy.getStrategy_id());
					break;
					
				case FirewallStrategy.RULE_TYPE_IEC104: //iec104 ??????
					IEC104TcpStrategyIds.add(strategy.getStrategy_id());
					break;
					
				case FirewallStrategy.RULE_TYPE_S7: //iec104 ??????
					s7StrategyIds.add(strategy.getStrategy_id());
					break;
					
				default:
					break;
			}
		}
		
		List<FirewallStrategy> strategies2 = new ArrayList<>();
		if (customStrategyIds.size() > 0) {
			strategies2.addAll(
					firewallStrategyMapper.getCustomByStrategyIds(customStrategyIds));
		}
		if (commonStrategyIds.size() > 0) {
			strategies2.addAll(
					firewallStrategyMapper.getCommonByStrategyIds(commonStrategyIds));
		}
		if (specialStrategyIds.size() > 0) {
			strategies2.addAll(
					firewallStrategyMapper.getSpecialByStrategyIds(specialStrategyIds));
		}
		if (opcClassicTcpStrategyIds.size() > 0) {
			strategies2.addAll(
					firewallStrategyMapper.getOpcClassicTcpByStrategyIds(opcClassicTcpStrategyIds));
		}
		if (modbusTcpStrategyIds.size() > 0) {
			strategies2.addAll(
					firewallStrategyMapper.getModbusTcpByStrategyIds(modbusTcpStrategyIds));
		}
		if (IEC104TcpStrategyIds.size() > 0) {
			strategies2.addAll(
					firewallStrategyMapper.getIEC104ByStrategyIds(IEC104TcpStrategyIds));
		}
		if (s7StrategyIds.size() > 0) {
			strategies2.addAll(
					firewallStrategyMapper.getS7ByStrategyIds(s7StrategyIds));
		}
		//??????
		Collections.sort(strategies2, new Comparator<FirewallStrategy>() {

			@Override
			public int compare(FirewallStrategy o1, FirewallStrategy o2) {
				if (o1.getStrategy_id() < o2.getStrategy_id()) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		
		return strategies2;
	}

	/**
	 * ??????
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String add(JSONObject paramJson, HttpServletRequest request) 
			throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//?????????id
		if (!paramJson.has("groupId")) {
			commonResult.setErrorMsg("????????????groupId");
			return commonResult.toString();
		}
		int groupId = paramJson.getInt("groupId");
		
		//????????????
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("????????????deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//????????????
		if (!paramJson.has("strategyName")) {
			commonResult.setErrorMsg("????????????strategyName");
			return commonResult.toString();
		}
		String strategyName = paramJson.getString("strategyName");
		
		//??????id??????????????????
		int ruleId = 0;
		if (paramJson.has("ruleId")) {
			ruleId = paramJson.getInt("ruleId");
		}
		
		//????????????
		if (!paramJson.has("ruleType")) {
			commonResult.setErrorMsg("????????????ruleType");
			return commonResult.toString();
		}
		int ruleTpe = paramJson.getInt("ruleType");
		
		//????????????
		int timeType = 1;
		if (paramJson.has("timeType")) {
			timeType = paramJson.getInt("timeType");
		}
		
		//????????????
		String relativeTime = null;
		if (paramJson.has("relativeTime")) {
			relativeTime = paramJson.getString("relativeTime");
		}
		
		//????????????
		String startTime = null;
		if (paramJson.has("startTime")) {
			startTime = paramJson.getString("startTime");
		}
		
		//????????????
		String endTime = null;
		if (paramJson.has("endTime")) {
			endTime = paramJson.getString("endTime");
		}
		
		//????????????
		int ruleAction = 1;
		if (paramJson.has("ruleAction")) {
			ruleAction = paramJson.getInt("ruleAction");
		}
		
		//??????????????????
		int logPower = 1;
		if (paramJson.has("logPower")) {
			logPower = paramJson.getInt("logPower");
		}
		
		//????????????
		int inputEth = 0;
		if (paramJson.has("inputEth")) {
			inputEth = paramJson.getInt("inputEth");
		}
		
		//????????????
		int outputEth = 0;
		if (paramJson.has("outputEth")) {
			outputEth = paramJson.getInt("outputEth");
		}
		
		//??????????????????????????????????????????
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("???????????????");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		FirewallStrategy strategy = null;
		String errorMsg = null;
		switch (ruleTpe) {
		
			case FirewallStrategy.RULE_TYPE_CUSTOM: //???????????????
				FirewallRuleCustom ruleCustom = new FirewallRuleCustom();
				strategy = ruleCustom;
				errorMsg = this.insertCustomRule(paramJson, ruleCustom);
				break;
				
			case FirewallStrategy.RULE_TYPE_COMMON: //?????????????????????
				strategy = firewallRuleTempMapper.getById(ruleId);
				if (strategy == null) {
					errorMsg =  "??????????????????????????????";
				}
				break;
				
			case FirewallStrategy.RULE_TYPE_SPECIAL: //?????????????????????
				strategy = firewallRuleTempMapper.getById(ruleId);
				if (strategy == null) {
					errorMsg =  "??????????????????????????????";
				}
				break;
				
			case FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP: //opc classic tcp ??????
				FirewallRuleOpcClassicTcp opcClassicTcp = new FirewallRuleOpcClassicTcp();
				strategy = opcClassicTcp;
				errorMsg = this.insertOpcClassicTcpTule(paramJson, opcClassicTcp);
				break;
				
			case FirewallStrategy.RULE_TYPE_MODBUS_TCP: //modbus tcp ??????
				FirewallRuleModbusTcp modbusTcp = new FirewallRuleModbusTcp();
				strategy = modbusTcp;
				errorMsg = this.insertModbusTcpRule(paramJson, modbusTcp);
				break;
				
			case FirewallStrategy.RULE_TYPE_IEC104: //iec104 ??????
				FirewallRuleIEC104 iec104 = new FirewallRuleIEC104();
				strategy = iec104;
				errorMsg = this.insertIEC104Rule(paramJson, iec104);
				break;
				
			case FirewallStrategy.RULE_TYPE_S7: //s7 ??????
				FirewallRuleS7 s7 = new FirewallRuleS7();
				strategy = s7;
				errorMsg = this.insertS7Rule(paramJson, s7);
				break;
				
			default:
				errorMsg = "??????????????????";
				break;
		}
		
		if (errorMsg != null || strategy == null) {
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		strategy.setGroup_id(groupId);
		strategy.setDevice_name(deviceName);
		strategy.setStrategy_name(strategyName);
		if (ruleTpe == FirewallStrategy.RULE_TYPE_COMMON || 
				ruleTpe == FirewallStrategy.RULE_TYPE_SPECIAL) {
			strategy.setRule_id(ruleId);
		}
		strategy.setRule_type(ruleTpe);
		strategy.setTime_type(timeType);
		strategy.setRelative_time(relativeTime);
		strategy.setStart_time(startTime);
		strategy.setEnd_time(endTime);
		strategy.setRule_action(ruleAction);
		strategy.setLog_power(logPower);
		strategy.setInput_eth(inputEth);
		strategy.setOutput_eth(outputEth);
		strategy.setAdd_time(new Date());
		strategy.setAdd_user(
				ServiceBase.getCacheStatic(
						ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
						AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name()
				);
		firewallStrategyMapper.insert(strategy);
		
		//??????????????????
		String logDesc = strategy.getLogDesc();
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//????????????
//		errorMsg = this.giveStrategyToDevice(device);
//		if  (StringUtils.isNotBlank(errorMsg)) {
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			commonResult.setErrorMsg(errorMsg);
//			return commonResult.toString();
//		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * ?????????????????????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String insertCustomRule(JSONObject paramJson, FirewallRuleCustom custom){
		
		//????????????
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
		}
		
		//?????????
		String sport = null;
		if (paramJson.has("sport")) {
			sport = paramJson.getString("sport");
		}	
		
		//????????????
		String dport = null;
		if (paramJson.has("dport")) {
			dport = paramJson.getString("dport");
		}
		
		//icmp????????????
		String icmpType = null;
		if (paramJson.has("icmpType")) {
			icmpType = paramJson.getString("icmpType");
		}
		
		if (StringUtils.isNotBlank(icmpType)) {
			String[] icmpTypeValues = icmpType.split("_");
			sport = icmpTypeValues[0];
			dport = icmpTypeValues[1];
		}
		
		custom.setProtocol(protocol);
		custom.setSport(sport);
		custom.setDport(dport);
		firewallRuleCustomMapper.insert(custom);
		custom.setRule_id(custom.getId());
		return null;
	}
	
	/**
	 * ??????opc??????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String insertOpcClassicTcpTule(
			JSONObject paramJson, 
			FirewallRuleOpcClassicTcp opcClassicTcp) {
		
		//????????????
		String session = null;
		if (paramJson.has("session")) {
			session = paramJson.getString("session");
		}
		
		//???????????????
		String inspect = null;
		if (paramJson.has("inspect")) {
			inspect = paramJson.getString("inspect");
		}
		
		//??????T/O
		String tos = null;
		if (paramJson.has("tos")) {
			tos = paramJson.getString("tos");
		}
		
		//??????
		String zcheck = null;
		if (paramJson.has("zcheck")) {
			zcheck = paramJson.getString("zcheck");
		}
		
		//UUID
		String uuid = null;
		if (paramJson.has("uuid")) {
			uuid = paramJson.getString("uuid");
		}
		
		//Opnum
		String opnum = null;
		if (paramJson.has("opnum")) {
			opnum = paramJson.getString("opnum");
		}
		
		opcClassicTcp.setSession(session);
		opcClassicTcp.setInspect(inspect);
		opcClassicTcp.setTos(tos);
		opcClassicTcp.setZcheck(zcheck);
		opcClassicTcp.setUuid(uuid);
		opcClassicTcp.setOpnum(opnum);
		
		firewallRuleOpcClassicTcpMapper.insert(opcClassicTcp);
		opcClassicTcp.setRule_id(opcClassicTcp.getId());
		
		return null;
	}
	
	/**
	 * ?????? modbus tcp ??????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String insertModbusTcpRule(
			JSONObject paramJson, 
			FirewallRuleModbusTcp modbusTcp) {
		//modbus??????ID
		String unitId = null;
		if (paramJson.has("unitId")) {
			unitId = paramJson.getString("unitId");
		}
		
//		//?????????
//		if (!paramJson.has("functionCode")) {
//			return "????????????functionCode";
//		}
//		int functionCode = paramJson.getInt("functionCode");
//		
//		//????????????, 1?????????2?????????3?????????
//		if (!paramJson.has("mode")) {	
//			return "????????????mode";
//		}
//		int mode = paramJson.getInt("mode");
//		
//		//???????????????
//		int readStart = 0;
//		if (paramJson.has("readStart")) {
//			readStart = paramJson.getInt("readStart");
//		}
//		
//		//???????????????
//		int readLength = 0;
//		if (paramJson.has("readLength")) {
//			readLength = paramJson.getInt("readLength");
//		}
//		
//		//???????????????
//		int wirteStart = 0;
//		if (paramJson.has("wirteStart")) {
//			wirteStart = paramJson.getInt("wirteStart");
//		}
//		
//		//???????????????
//		int writeLength = 0;
//		if (paramJson.has("writeLength")) {
//			writeLength = paramJson.getInt("writeLength");
//		}
		
		//?????????
		String functionCode = null;
		if (paramJson.has("functionCode")) {
			functionCode = paramJson.getString("functionCode");
		}
		
		//????????????, 1?????????2?????????3?????????
		String mode = null;
		if (paramJson.has("mode")) {	
			mode = paramJson.getString("mode");
		}
		
		//???????????????
		String readStart = null;
		if (paramJson.has("readStart")) {
			readStart = paramJson.getString("readStart");
		}
		
		//???????????????
		String readLength = null;
		if (paramJson.has("readLength")) {
			readLength = paramJson.getString("readLength");
		}
		
		//???????????????
		String wirteStart = null;
		if (paramJson.has("wirteStart")) {
			wirteStart = paramJson.getString("wirteStart");
		}
		
		//???????????????
		String writeLength = null;
		if (paramJson.has("writeLength")) {
			writeLength = paramJson.getString("writeLength");
		}
		
		//???????????????5
		if ("5".equals(functionCode) || "6".equals(functionCode)) {
			writeLength = "1";
		}
		
		int intWriteLength = 0;
		if (StringUtils.isNotBlank(writeLength)) {
			intWriteLength = Integer.parseInt(writeLength);
		}
		
		//????????????
		String wirteValues = "";
		for (int i = 0; i < intWriteLength; i++) {
			String value = null;
			if (paramJson.has("writeValue_"+i+"_"+functionCode)) {
				value = paramJson.getString("writeValue_"+i+"_"+functionCode);
			}
			
			if (StringUtils.isBlank(value)) {
				value = "0";
			}
			
			wirteValues += value;
			if (i < intWriteLength - 1) {
				wirteValues += ",";
			}
			
		}
		
		modbusTcp.setUnit_id(unitId);
		modbusTcp.setFunction_code(functionCode);
		modbusTcp.setMode(mode);
		modbusTcp.setRead_start(readStart);
		modbusTcp.setRead_length(readLength);
		modbusTcp.setWrite_start(wirteStart);
		modbusTcp.setWrite_length(writeLength);
		modbusTcp.setWrite_values(wirteValues);
		
		firewallRuleModbusTcpMapper.insert(modbusTcp);
		modbusTcp.setRule_id(modbusTcp.getId());
		return null;
	}
	
	private String insertIEC104Rule(JSONObject paramJson, FirewallRuleIEC104 iec104) {
//		//????????????
//		if (!paramJson.has("iec104Type")) {
//			return "????????????iec104Type";
//		}
//		int iec104Type = paramJson.getInt("iec104Type");
//		
//		//????????????
//		if (!paramJson.has("cot")) {
//			return "????????????cot";
//		}
//		int cot = paramJson.getInt("cot");
//		
//		//????????????
//		if (!paramJson.has("coa")) {	
//			return "????????????coa";
//		}
//		int coa = paramJson.getInt("coa");
//		
//		//????????????????????????
//		if (!paramJson.has("ioaStart")) {	
//			return "????????????ioaStart";
//		}
//		int ioaStart = paramJson.getInt("ioaStart");
//		
//		//????????????????????????
//		if (!paramJson.has("ioaLength")) {	
//			return "????????????ioaLength";
//		}
//		int ioaLength = paramJson.getInt("ioaLength");
		
		//????????????
		String iec104Type = null;
		if (paramJson.has("iec104Type")) {
			iec104Type = paramJson.getString("iec104Type");
		}
		
		//????????????
		String cot = null;
		if (paramJson.has("cot")) {
			cot = paramJson.getString("cot");
		}
		
		//????????????
		String coa = null;
		if (paramJson.has("coa")) {	
			coa = paramJson.getString("coa");
		}
		
		//????????????????????????
		String ioaStart = null;
		if (paramJson.has("ioaStart")) {	
			ioaStart = paramJson.getString("ioaStart");
		}
		
		//????????????????????????
		String ioaLength = null;
		if (paramJson.has("ioaLength")) {	
			ioaLength = paramJson.getString("ioaLength");
		}
		
		
		iec104.setType(iec104Type);
		iec104.setCot(cot);
		iec104.setCoa(coa);
		iec104.setIoa_start(ioaStart);
		iec104.setIoa_length(ioaLength);
		firewallRuleIEC104Mapper.insert(iec104);
		iec104.setRule_id(iec104.getId());
		return null;
	}
	
	
	private String insertS7Rule(JSONObject paramJson, FirewallRuleS7 s7) {
		
		//pdu??????
		String pduType = null;
		if (paramJson.has("pduType")) {
			pduType = paramJson.getString("pduType");
		}
		
		String functionCode = null;
		if (pduType != null && "JOB".equals(pduType.toUpperCase())) {
			if (paramJson.has("functionCode")) {
				functionCode = paramJson.getString("functionCode");
			}
		}
		
		String userdataType = null;
		String functionGroup = null;
		String subFunctionCode = null;
		if (pduType != null && "USERDATA".equals(pduType.toUpperCase())) {
			if (paramJson.has("userdataType")) {
				userdataType = paramJson.getString("userdataType");
			}
			if (paramJson.has("functionGroup")) {
				functionGroup = paramJson.getString("functionGroup");
			}
			if (paramJson.has("subFunctionCode")) {
				subFunctionCode = paramJson.getString("subFunctionCode");
			}
		}
		
		s7.setPdu_type(pduType.toUpperCase());
		s7.setFunction_code(functionCode);
		s7.setUser_data_type(userdataType);
		s7.setFunction_group_code(functionGroup);
		s7.setSub_function_code(subFunctionCode);
		
		firewallRuleS7Mapper.insert(s7);
		s7.setRule_id(s7.getId());
		return null;
	}
	
	

	/**
	 * ??????
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String update(JSONObject paramJson, HttpServletRequest request) 
			throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//??????id
		if (!paramJson.has("strategyId")) {
			commonResult.setErrorMsg("????????????strategyId");
			return commonResult.toString();
		}
		int strategyId = paramJson.getInt("strategyId");
		
		//?????????
		if (!paramJson.has("strategyName")) {
			commonResult.setErrorMsg("????????????strategyName");
			return commonResult.toString();
		}
		String strategyName = paramJson.getString("strategyName");
		
		//????????????
		if (!paramJson.has("ruleType")) {
			commonResult.setErrorMsg("????????????ruleType");
			return commonResult.toString();
		}
		int ruleType = paramJson.getInt("ruleType");
		
		//????????????
		int timeType = 1;
		if (paramJson.has("timeType")) {
			timeType = paramJson.getInt("timeType");
		}
		
		//????????????
		String relativeTime = null;
		if (paramJson.has("relativeTime")) {
			relativeTime = paramJson.getString("relativeTime");
		}
		
		//????????????
		String startTime = null;
		if (paramJson.has("startTime")) {
			startTime = paramJson.getString("startTime");
		}
		
		//????????????
		String endTime = null;
		if (paramJson.has("endTime")) {
			endTime = paramJson.getString("endTime");
		}
		
		//????????????
		int ruleAction = 1;
		if (paramJson.has("ruleAction")) {
			ruleAction = paramJson.getInt("ruleAction");
		}
		
		//??????????????????
		int logPower = 1;
		if (paramJson.has("logPower")) {
			logPower = paramJson.getInt("logPower");
		}
		
		//????????????
		int inputEth = 0;
		if (paramJson.has("inputEth")) {
			inputEth = paramJson.getInt("inputEth");
		}
		
		//????????????
		int outputEth = 0;
		if (paramJson.has("outputEth")) {
			outputEth = paramJson.getInt("outputEth");
		}
		
		//????????????????????????????????????
		FirewallStrategy strategy = null;
		String errorMsg = null;
		switch (ruleType) {
		
			case FirewallStrategy.RULE_TYPE_CUSTOM://?????????????????????
				FirewallRuleCustom custom = 
					firewallStrategyMapper.getCustomByStrategyId(strategyId);
				strategy = custom;
				errorMsg = this.updateCustomRule(paramJson, custom);
				break;
				
			case FirewallStrategy.RULE_TYPE_COMMON://???????????????????????????
				FirewallRuleTemp common = 
						firewallStrategyMapper.getCommonByStrategyId(strategyId);
				strategy = common;
				break;
				
			case FirewallStrategy.RULE_TYPE_SPECIAL://???????????????????????????
				FirewallRuleTemp special = 
						firewallStrategyMapper.getSpecialByStrategyId(strategyId);
				strategy = special;
				break;
				
			case FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP://?????? opc classic tcp ??????
				FirewallRuleOpcClassicTcp opcClassicTcp = 
						firewallStrategyMapper.getOpcClassicTcpByStrategyId(strategyId);
				strategy = opcClassicTcp;
				errorMsg = this.updateOpcClassicTcpRule(paramJson, opcClassicTcp);
				break;
				
			case FirewallStrategy.RULE_TYPE_MODBUS_TCP://?????? modbus tcp ??????
				FirewallRuleModbusTcp modbusTcp = 
						firewallStrategyMapper.getModbusTcpByStrategyId(strategyId);
				strategy = modbusTcp;
				errorMsg = this.updateModbusTcpRule(paramJson, modbusTcp);
				break;
				
			case FirewallStrategy.RULE_TYPE_IEC104://?????? iec104 ??????
				FirewallRuleIEC104 iec104 = 
				firewallStrategyMapper.getIEC104ByStrategyId(strategyId);
				strategy = iec104;
				errorMsg = this.updateIEC104Rule(paramJson, iec104);
				break;
				
			case FirewallStrategy.RULE_TYPE_S7://?????? s7 ??????
				FirewallRuleS7 s7 = 
				firewallStrategyMapper.getS7ByStrategyId(strategyId);
				strategy = s7;
				errorMsg = this.updateS7Rule(paramJson, s7);
				break;
				
			default:
				break;
		}
		if (errorMsg != null) {
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		strategy.setStrategy_name(strategyName);
		strategy.setTime_type(timeType);
		strategy.setRelative_time(relativeTime);
		strategy.setStart_time(startTime);
		strategy.setEnd_time(endTime);
		strategy.setRule_action(ruleAction);
		strategy.setLog_power(logPower);
		strategy.setInput_eth(inputEth);
		strategy.setOutput_eth(outputEth);
		strategy.setSync_flag(0);
		strategy.setUpdate_time(new Date());
		strategy.setUpdate_user(
					ServiceBase.getCacheStatic(
						ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
						AdminUserLoginInfo.class
					).getUserInfo().getUser_name()
				);
		firewallStrategyMapper.update(strategy);

		//??????????????????
		String logDesc = strategy.getLogDesc();
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//????????????
//		FirewallDevice device = firewallDeviceMapper.getByName(strategy.getDevice_name());
//		errorMsg = this.giveStrategyToDevice(device);
//		if  (StringUtils.isNotBlank(errorMsg)) {
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			commonResult.setErrorMsg(errorMsg);
//			return commonResult.toString();
//		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * ?????????????????????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String updateCustomRule(JSONObject paramJson, FirewallRuleCustom custom){
		
		if (custom == null) {
			return "???????????????";
		}
		
		//????????????
		if (paramJson.has("protocol")) {
			custom.setProtocol(paramJson.getString("protocol"));
		}
		
		//?????????
		if (paramJson.has("sport")) {
			custom.setSport(paramJson.getString("sport"));
		}	
		
		//????????????
		if (paramJson.has("dport")) {
			custom.setDport(paramJson.getString("dport"));
		}
		
		//icmp????????????
		String icmpType = null;
		if (paramJson.has("icmpType")) {
			icmpType = paramJson.getString("icmpType");
		}
		
		if (StringUtils.isNotBlank(icmpType)) {
			String[] icmpTypeValues = icmpType.split("_");
			custom.setSport(icmpTypeValues[0]);
			custom.setDport(icmpTypeValues[1]);
		} else {
			custom.setSport(null);
			custom.setDport(null);
		}
		
		firewallRuleCustomMapper.update(custom);
		return null;
	}
	
	/**
	 * ?????? Opc Classic Tcp ??????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String updateOpcClassicTcpRule(
			JSONObject paramJson, 
			FirewallRuleOpcClassicTcp opcClassicTcp){
		
		if (opcClassicTcp == null) {
			return "???????????????";
		}
		
		//????????????
		if (paramJson.has("session")) {
			opcClassicTcp.setSession(paramJson.getString("session"));
		}
		
		//???????????????
		if (paramJson.has("inspect")) {
			opcClassicTcp.setInspect(paramJson.getString("inspect"));
		}
		
		//??????T/O
		if (paramJson.has("tos")) {
			opcClassicTcp.setTos(paramJson.getString("tos"));
		}
		
		//??????
		if (paramJson.has("zcheck")) {
			opcClassicTcp.setZcheck(paramJson.getString("zcheck"));
		}
		
		//UUID
		if (paramJson.has("uuid")) {
			opcClassicTcp.setUuid(paramJson.getString("uuid"));
		}
		
		//UUID
		if (paramJson.has("Opnum")) {
			opcClassicTcp.setOpnum(paramJson.getString("opnum"));
		}
		
		firewallRuleOpcClassicTcpMapper.update(opcClassicTcp);
		return null;
	}
	
	/**
	 * ?????? modbus tcp ??????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String updateModbusTcpRule(
			JSONObject paramJson, 
			FirewallRuleModbusTcp modbusTcp){
		
		if (modbusTcp == null) {
			return "???????????????";
		}
		
		//modbus??????ID
		String unitId = null;
		if (paramJson.has("unitId")) {
			unitId = paramJson.getString("unitId");
		}
		
//		//?????????
//		if (!paramJson.has("functionCode")) {
//			return "????????????functionCode";
//		}
//		int functionCode = paramJson.getInt("functionCode");
//		
//		//????????????, 1?????????2?????????3?????????
//		if (!paramJson.has("mode")) {	
//			return "????????????mode";
//		}
//		int mode = paramJson.getInt("mode");
//		
//		//???????????????
//		int readStart = 0;
//		if (paramJson.has("readStart")) {
//			readStart = paramJson.getInt("readStart");
//		}
//		
//		//???????????????
//		int readLength = 0;
//		if (paramJson.has("readLength")) {
//			readLength = paramJson.getInt("readLength");
//		}
//		
//		//???????????????
//		int wirteStart = 0;
//		if (paramJson.has("wirteStart")) {
//			wirteStart = paramJson.getInt("wirteStart");
//		}
//		
//		//???????????????
//		int writeLength = 0;
//		if (paramJson.has("writeLength")) {
//			writeLength = paramJson.getInt("writeLength");
//		}
//		
//		//???????????????5
//		if (functionCode == 5) {
//			writeLength = 1;
//		}
//		
//		//????????????
//		String wirteValues = "";
//		for (int i = 0; i < writeLength; i++) {
//			if (!paramJson.has("writeValue_"+i)) {
//				return "????????????writeValue_"+i;
//			}
//			if (i > 0) {
//				wirteValues += ",";
//			}
//			wirteValues += paramJson.getInt("writeValue_"+i);
//		}
		
		//?????????
		String functionCode = null;
		if (paramJson.has("functionCode")) {
			functionCode = paramJson.getString("functionCode");
		}
		
		//????????????, 1?????????2?????????3?????????
		String mode = null;
		if (paramJson.has("mode")) {	
			mode = paramJson.getString("mode");
		}
		
		//???????????????
		String readStart = null;
		if (paramJson.has("readStart")) {
			readStart = paramJson.getString("readStart");
		}
		
		//???????????????
		String readLength = null;
		if (paramJson.has("readLength")) {
			readLength = paramJson.getString("readLength");
		}
		
		//???????????????
		String wirteStart = null;
		if (paramJson.has("wirteStart")) {
			wirteStart = paramJson.getString("wirteStart");
		}
		
		//???????????????
		String writeLength = null;
		if (paramJson.has("writeLength")) {
			writeLength = paramJson.getString("writeLength");
		}
		
		//???????????????5
		if ("5".equals(functionCode) || "6".equals(functionCode)) {
			writeLength = "1";
		}
		
		int intWriteLength = 0;
		if (StringUtils.isNotBlank(writeLength)) {
			intWriteLength = Integer.parseInt(writeLength);
		}
		
		//????????????
		String wirteValues = "";
		for (int i = 0; i < intWriteLength; i++) {
			String value = null;
			if (paramJson.has("writeValue_"+i+"_"+functionCode)) {
				value = paramJson.getString("writeValue_"+i+"_"+functionCode);
			}
			
			if (StringUtils.isBlank(value)) {
				value = "0";
			}
			
			wirteValues += value;
			if (i < intWriteLength - 1) {
				wirteValues += ",";
			}
			
		}
		
		modbusTcp.setUnit_id(unitId);
		modbusTcp.setFunction_code(functionCode);
		modbusTcp.setMode(mode);
		modbusTcp.setRead_start(readStart);
		modbusTcp.setRead_length(readLength);
		modbusTcp.setWrite_start(wirteStart);
		modbusTcp.setWrite_length(writeLength);
		modbusTcp.setWrite_values(wirteValues);
		firewallRuleModbusTcpMapper.update(modbusTcp);
		return null;
	}
	
	/**
	 * ?????? modbus tcp ??????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String updateIEC104Rule(JSONObject paramJson, FirewallRuleIEC104 iec104){
		
		if (iec104 == null) {
			return "???????????????";
		}
		
//		//????????????
//		if (!paramJson.has("iec104Type")) {
//			return "????????????iec104Type";
//		}
//		int iec104Type = paramJson.getInt("iec104Type");
//		
//		//????????????
//		if (!paramJson.has("cot")) {
//			return "????????????cot";
//		}
//		int cot = paramJson.getInt("cot");
//		
//		//????????????
//		if (!paramJson.has("coa")) {	
//			return "????????????coa";
//		}
//		int coa = paramJson.getInt("coa");
//		
//		//????????????????????????
//		if (!paramJson.has("ioaStart")) {	
//			return "????????????ioaStart";
//		}
//		int ioaStart = paramJson.getInt("ioaStart");
//		
//		//????????????????????????
//		if (!paramJson.has("ioaLength")) {	
//			return "????????????ioaLength";
//		}
//		int ioaLength = paramJson.getInt("ioaLength");
		
		//????????????
		String iec104Type = null;
		if (paramJson.has("iec104Type")) {
			iec104Type = paramJson.getString("iec104Type");
		}
		
		//????????????
		String cot = null;
		if (paramJson.has("cot")) {
			cot = paramJson.getString("cot");
		}
		
		//????????????
		String coa = null;
		if (paramJson.has("coa")) {	
			coa = paramJson.getString("coa");
		}
		
		//????????????????????????
		String ioaStart = null;
		if (paramJson.has("ioaStart")) {	
			ioaStart = paramJson.getString("ioaStart");
		}
		
		//????????????????????????
		String ioaLength = null;
		if (paramJson.has("ioaLength")) {	
			ioaLength = paramJson.getString("ioaLength");
		}
		
		iec104.setType(iec104Type);
		iec104.setCot(cot);
		iec104.setCoa(coa);
		iec104.setIoa_start(ioaStart);
		iec104.setIoa_length(ioaLength);
		firewallRuleIEC104Mapper.update(iec104);
		return null;
	}
	
	/**
	 * ?????? s7 ??????
	 * @param paramJson
	 * @param strategy
	 * @return
	 */
	private String updateS7Rule(JSONObject paramJson, FirewallRuleS7 s7) {
		
		if (s7 == null) {
			return "???????????????";
		}
		
		//pdu??????
		String pduType = null;
		if (paramJson.has("pduType")) {
			pduType = paramJson.getString("pduType");
		}
		
		String functionCode = null;
		if (pduType != null && "JOB".equals(pduType.toUpperCase())) {
			if (paramJson.has("functionCode")) {
				functionCode = paramJson.getString("functionCode");
			}
		}
		
		String userdataType = null;
		String functionGroup = null;
		String subFunctionCode = null;
		if (pduType != null && "USERDATA".equals(pduType.toUpperCase())) {
			if (paramJson.has("userdataType")) {
				userdataType = paramJson.getString("userdataType");
			}
			if (paramJson.has("functionGroup")) {
				functionGroup = paramJson.getString("functionGroup");
			}
			if (paramJson.has("subFunctionCode")) {
				subFunctionCode = paramJson.getString("subFunctionCode");
			}
		}
		
		s7.setPdu_type(pduType.toUpperCase());
		s7.setFunction_code(functionCode);
		s7.setUser_data_type(userdataType);
		s7.setFunction_group_code(functionGroup);
		s7.setSub_function_code(subFunctionCode);
		
		firewallRuleS7Mapper.update(s7);
		return null;
	}
	
	/**
	 * ??????
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String delete(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//??????id
		if (!paramJson.has("strategyId")) {
			commonResult.setErrorMsg("????????????strategyId");
			return commonResult.toString();
		}
		int strategyId = paramJson.getInt("strategyId");
		
		//????????????
		if (!paramJson.has("ruleType")) {
			commonResult.setErrorMsg("????????????ruleType");
			return commonResult.toString();
		}
		int ruleType = paramJson.getInt("ruleType");
		
		//????????????????????????????????????
		FirewallStrategy strategy = null;
		switch (ruleType) {
		
			case FirewallStrategy.RULE_TYPE_CUSTOM://???????????????
				FirewallRuleCustom custom = 
					firewallStrategyMapper.getCustomByStrategyId(strategyId);
				strategy = custom;
				break;
				
			case FirewallStrategy.RULE_TYPE_COMMON://???????????????????????????
				FirewallRuleTemp common = 
						firewallStrategyMapper.getCommonByStrategyId(strategyId);
				strategy = common;
				break;
				
			case FirewallStrategy.RULE_TYPE_SPECIAL://???????????????????????????
				FirewallRuleTemp special = 
						firewallStrategyMapper.getSpecialByStrategyId(strategyId);
				strategy = special;
				break;
				
			case FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP://opc classic tcp ??????
				FirewallRuleOpcClassicTcp opcClassicTcp = 
						firewallStrategyMapper.getOpcClassicTcpByStrategyId(strategyId);
				strategy = opcClassicTcp;
				break;
				
			case FirewallStrategy.RULE_TYPE_MODBUS_TCP://modbus tcp ??????
				FirewallRuleModbusTcp modbusTcp = 
						firewallStrategyMapper.getModbusTcpByStrategyId(strategyId);
				strategy = modbusTcp;
				break;
				
			case FirewallStrategy.RULE_TYPE_IEC104://iec104 ??????
				FirewallRuleIEC104 iec104 = 
						firewallStrategyMapper.getIEC104ByStrategyId(strategyId);
				strategy = iec104;
				break;
				
			case FirewallStrategy.RULE_TYPE_S7://s7 ??????
				FirewallRuleS7 s7 = 
						firewallStrategyMapper.getS7ByStrategyId(strategyId);
				strategy = s7;
				break;
				
			default:
				break;
		}
		
		if (strategy == null) {
			commonResult.setErrorMsg("???????????????");
			return commonResult.toString();
		}
		
		strategy.setDelete_flag(1);
		strategy.setUpdate_time(new Date());
		strategy.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallStrategyMapper.update(strategy);
		
		//????????????
		FirewallDevice device = firewallDeviceMapper.getByName(strategy.getDevice_name());
		String errorMsg = this.giveStrategyToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}

		//??????????????????
		String logDesc = strategy.getLogDesc();
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		commonResult.setStatus(true);
		return commonResult.toString();
		
	}
	
	
	@Override
	public String giveStrategyToDevice(FirewallDevice device) {
		
		//?????????????????????????????????
		FirewallDevice device2 = firewallDeviceMapper.getByName(device.getDevice_name());
		device2.setStudy_mode(0);
		firewallDeviceMapper.update(device2);
		StudyRuleServiceImpl.DEVICE_CACHE_MAP.remove(device2.getDevice_name());
		
		//??????????????????????????????
		List<FirewallStrategyGroup> groups = 
				firewallStrategyGroupMapper.getByDeviceNameASC(device.getDevice_name());
		if (groups == null || groups.size() < 1) {
			StringBuilder instruction = new StringBuilder();
			instruction.append("0x20 0x201 flush;0x30 flush modbus;0x30 flush opc;0x30 flush iec104;0x30 flush s7;");
			instruction.append("0x20 0x201 last;0x30 last modbus;0x30 last opc;0x30 last iec104;0x30 last s7;");
			Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
			socketConfig.put("mip", device.getIp_address());
			//????????????
			return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		}

		//??????????????????????????????
		List<FirewallStrategy> strategies = 
				firewallStrategyMapper.getByDeviceName(device.getDevice_name());
		if (strategies == null || strategies.size() < 1) {
			strategies = new ArrayList<>();
		}
		
		//??????????????????
		if (strategies.size() > 0) {
			strategies = this.getRuleStrategys(strategies);
		}
		
		//???????????????MAP
		Map<String, FirewallStrategyGroup> groupMap = new HashMap<>();
		for (FirewallStrategyGroup group : groups) {
			groupMap.put(group.getId()+"", group);
		}
		
		//???????????????????????????
		for (FirewallStrategy strategy : strategies) {
			FirewallStrategyGroup group = groupMap.get(strategy.getGroup_id()+"");
			if (group != null) {
				group.getStrategies().add(strategy);
			}
		}
		
		//????????????????????????
		List<FirewallDeviceEthernet> ethernets = 
				firewallDeviceEthernetMapper.getByDeviceName(device.getDevice_name());
		Map<String, FirewallDeviceEthernet> ethMap = new HashMap<>();
		for (FirewallDeviceEthernet firewallDeviceEthernet : ethernets) {
			ethMap.put(firewallDeviceEthernet.getNumber()+"", firewallDeviceEthernet);
		}
		
		//????????????
		StringBuilder instruction = new StringBuilder();
		instruction.append("0x20 0x201 flush;0x30 modbus flush;0x30 opc flush;0x30 iec104 flush;0x30 s7 flush;");
		boolean hasBuildModbusNew = false;
		boolean hasBuildIEC104New = false;
		boolean hasBuildS7New = false;
		for (FirewallStrategyGroup group : groups) {
			strategies = group.getStrategies();
			for (FirewallStrategy strategy : strategies) {
				
				//??????????????????????????????
				if (strategy.getInput_eth() > 0) {
					strategy.setInputEthInfo(ethMap.get(strategy.getInput_eth()+""));
				}
				
				if (strategy.getOutput_eth() > 0) {
					strategy.setOutputEthInfo(ethMap.get(strategy.getInput_eth()+""));
				}
				
				switch (strategy.getRule_type()) {
				
				case FirewallStrategy.RULE_TYPE_CUSTOM://???????????????
					instruction
					.append(
							FirewallStrategyInstructionUtil.
							buildCustomInstruction(group, (FirewallRuleCustom)strategy)
							);
					break;
					
				case FirewallStrategy.RULE_TYPE_COMMON://???????????????????????????
					instruction
					.append(
							FirewallStrategyInstructionUtil.
							buildCommonInstruction(group, (FirewallRuleTemp)strategy)
							);
					break;
					
				case FirewallStrategy.RULE_TYPE_SPECIAL://???????????????????????????
					instruction
					.append(
							FirewallStrategyInstructionUtil.
							buildSpecialInstruction(group, (FirewallRuleTemp)strategy)
							);
					break;
					
				case FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP://opc classic tcp ??????
					instruction
					.append(
							FirewallStrategyInstructionUtil.
							buildOpcClassicTcpInstruction(group, (FirewallRuleOpcClassicTcp)strategy)
							);
					break;
					
				case FirewallStrategy.RULE_TYPE_MODBUS_TCP://modbus tcp ??????
//					if (!hasBuildModbusNew) {
//						instruction.append(
//								FirewallStrategyInstructionUtil.
//								buildModbusTcpNewInstruction(group, (FirewallRuleModbusTcp)strategy)
//								);
//						hasBuildModbusNew = true;
//					}
					instruction
					.append(
							FirewallStrategyInstructionUtil.
							buildModbusTcpInstruction(group, (FirewallRuleModbusTcp)strategy)
							);
					break;
					
				case FirewallStrategy.RULE_TYPE_IEC104://iec104 ??????
//					if (!hasBuildIEC104New) {
//						instruction.append(
//								FirewallStrategyInstructionUtil.
//								buildIEC104NewInstruction(group, (FirewallRuleIEC104)strategy)
//								);
//						hasBuildIEC104New = true;
//					}
					instruction
					.append(
							FirewallStrategyInstructionUtil.
							buildIIEC104Instruction(group, (FirewallRuleIEC104)strategy)
							);
					break;
					
				case FirewallStrategy.RULE_TYPE_S7://s7 ??????
//					if (!hasBuildS7New) {
//						instruction.append(
//								FirewallStrategyInstructionUtil.
//								buildS7NewInstruction(group, (FirewallRuleS7)strategy)
//								);
//						hasBuildS7New = true;
//					}
					instruction
					.append(
							FirewallStrategyInstructionUtil.
							buildS7Instruction(group, (FirewallRuleS7)strategy)
							);
					break;
					
				default:
					break;
				}
			}
		}
		instruction.append("0x20 0x201 last;0x30 modbus last;0x30 opc last;0x30 iec104 last;0x30 s7 last;");
		
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		
	}

	@Override
	public String sync(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		//????????????
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("????????????deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		
		if (device == null) {
			commonResult.setErrorMsg("???????????????");
			return commonResult.toString();
		}
			
		//????????????
		String errorMsg = this.giveStrategyToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		//??????????????????
		String logDesc = "[????????????"+device.getDevice_name()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String importRule(HttpServletResponse response, MultipartFile file, JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		//????????????
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("????????????deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String sourcesFileName = System.currentTimeMillis() + "_importStrategyRule";
		File sourcesFile = new File(fileSavePath + sourcesFileName);
		
		int i = 1;
		while (sourcesFile.exists()) {
			sourcesFileName += i;
			sourcesFile = new File(fileSavePath + sourcesFileName);
			i++;
		}
		
		file.transferTo(sourcesFile);
		
		InputStream is = new FileInputStream(sourcesFile);
		Workbook workbook = new HSSFWorkbook(is);
		
		if (workbook.getNumberOfSheets() < 1) {
			deleteFile(sourcesFile, is);
			return new CommonResult(true, "").toString();
		}
		
		//ip??????????????????????????????ip??????
		Pattern ipv4Pattern = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
		Pattern ipv4MaskPattern = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\/(\\d{1}|1\\d|2\\d|3[0-2])$");
		Pattern ipv6Pattern = Pattern.compile("^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$");
		
		for (int j = 0; j < workbook.getNumberOfSheets(); j++) {
			
			Sheet sheet = workbook.getSheetAt(j);
			//??????sheet???????????????
            if (sheet.getPhysicalNumberOfRows() <= 1) {
                continue;
            }
            
            String ruleName = sheet.getSheetName();
            if (!"????????????".equals(ruleName) && 
            		!"MODBUS_TCP??????".equals(ruleName) && 
            		!"IEC104??????".equals(ruleName) && 
            		!"S7??????".equals(ruleName) &&
            		!"OPC??????".equals(ruleName)) {
            	continue;
            }
            
            List<FirewallRuleImportItem> importItems = new ArrayList<>();
            for (int k = 1; k <= sheet.getLastRowNum(); k++) {
            	
            	Row row = sheet.getRow(k);
            	if (row == null) {
            		break;
            	}
            	//??????row???????????????
            	if (row.getPhysicalNumberOfCells() <= 0) {
                    continue;
                }
            	FirewallRuleImportItem importItem = new FirewallRuleImportItem();
            	String errorMsg = null;
            	try {
            		errorMsg = this.analysisImportRule(ruleName, 
            			k, 
            			row, 
            			importItem, 
            			ipv4Pattern, 
            			ipv4MaskPattern, 
            			ipv6Pattern);
				} catch (Exception e) {
					deleteFile(sourcesFile, is);
					throw e;
				}
            	
            	if (StringUtils.isNotBlank(errorMsg)) {
            		deleteFile(sourcesFile, is);
                	return new CommonResult(false, errorMsg).toString();
            	}
            	importItem.setDevice_name(deviceName);
            	importItems.add(importItem);
            }
            
            try {
        		addImportRuleToStrategy(deviceName, importItems);
			} catch (Exception e) {
				deleteFile(sourcesFile, is);
				throw new Exception("??????????????????");
			}
            
		}
		
		//????????????
		deleteFile(sourcesFile, is);
		
		return new CommonResult(true, "").toString();
	}
	
	private void addImportRuleToStrategy(String deviceName, List<FirewallRuleImportItem> importItems) throws Exception {
		
		//?????????????????????
		List<FirewallAsset> assets = firewallAssetMapper.getAll();
		Map<String, Integer> exitAssetsMap = new HashMap<>();
		for (FirewallAsset firewallAsset : assets) {
			exitAssetsMap.put(firewallAsset.getIp_address(), firewallAsset.getId());
		}
		
		List<FirewallAsset> newAssets = new ArrayList<>();
		for (FirewallRuleImportItem importItem : importItems) {
			if (exitAssetsMap.get(importItem.getSip()) == null) {
				FirewallAsset newAsset = new FirewallAsset();
				newAsset.setIp_address(importItem.getSip());
				newAsset.setAdd_time(new Date());
				newAsset.setAsset_name(importItem.getSip());
				newAssets.add(newAsset);
				exitAssetsMap.put(newAsset.getIp_address(), -1);
			}
			if (exitAssetsMap.get(importItem.getDip()) == null) {
				FirewallAsset newAsset = new FirewallAsset();
				newAsset.setIp_address(importItem.getDip());
				newAsset.setAdd_time(new Date());
				newAsset.setAsset_name(importItem.getDip());
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
		
		//????????????????????????
		List<FirewallStrategyGroup> groups = 
				firewallStrategyGroupMapper.getByDeviceName(deviceName);
		
		Map<String, Integer> exitGroupMap = new HashMap<>();
		Map<String, String> exitGroupIdMap = new HashMap<>();
		for (FirewallStrategyGroup group : groups) {
			exitGroupMap.put(group.getSource_asset_ip()+"-"+group.getTarget_asset_ip(), group.getId());
			exitGroupIdMap.put(group.getId()+"", 
					(StringUtils.isBlank(group.getSource_asset_ip())?"-":group.getSource_asset_ip())+"-"
					+(StringUtils.isBlank(group.getTarget_asset_ip())?"-":group.getTarget_asset_ip())+"-");
		}
		
		List<FirewallStrategyGroup> newGroups = new ArrayList<>();
		for (FirewallRuleImportItem item : importItems) {
			if (exitGroupMap.get(item.getSip()+"-"+item.getDip()) == null) {
				FirewallStrategyGroup newGroup = new FirewallStrategyGroup();
				newGroup.setDevice_name(deviceName);
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
			exitGroupIdMap.put(group.getId()+"", 
					(StringUtils.isBlank(group.getSource_asset_ip())?"-":group.getSource_asset_ip())+"-"
					+(StringUtils.isBlank(group.getTarget_asset_ip())?"-":group.getTarget_asset_ip())+"-");
		}
		
		//???????????????
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
		
		
		//????????????
		List<FirewallRuleCustom> firewallRuleCustoms = new ArrayList<>();
		List<FirewallRuleModbusTcp> firewallRuleModbusTcps = new ArrayList<>();
		List<FirewallRuleIEC104> firewallRuleIEC104s = new ArrayList<>();
		List<FirewallRuleS7> firewallRuleS7s = new ArrayList<>();
		List<FirewallRuleOpcClassicTcp> firewallRuleOpcs = new ArrayList<>();
		for (FirewallRuleImportItem item : importItems) {
			
			if (exitRuleMap.get(item.toCompareString()) != null) {
				continue;
			}
			
			if (item.getRule_type() == FirewallStrategy.RULE_TYPE_CUSTOM) {
				FirewallRuleCustom ruleCustom = new FirewallRuleCustom();
				ruleCustom.setProtocol(StringUtils.isBlank(item.getProtocol())?"IP":item.getProtocol().toUpperCase());
				ruleCustom.setSport(item.getSport());
				ruleCustom.setDport(item.getDport());
				ruleCustom.setDevice_name(deviceName);
				ruleCustom.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleCustom.setStrategy_name(item.getProtocol().toUpperCase() + "????????????");
				ruleCustom.setRule_type(FirewallStrategy.RULE_TYPE_CUSTOM);
				ruleCustom.setTime_type(1);
				ruleCustom.setLog_power(2);
				ruleCustom.setRule_action(1);
				firewallRuleCustoms.add(ruleCustom);
				
			} else if (item.getRule_type() == FirewallStrategy.RULE_TYPE_MODBUS_TCP) {
				FirewallRuleModbusTcp ruleModbusTcp = new FirewallRuleModbusTcp();
				ruleModbusTcp.setDevice_name(deviceName);
				ruleModbusTcp.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleModbusTcp.setStrategy_name("MODBUS??????");
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
				
				if ("5".equals(item.getExtension2())) {
					if (item.getExtension7() != null) {
						if ("ON".equals(item.getExtension7().toUpperCase())) {
							ruleModbusTcp.setWrite_values("65280");
						} else if ("OFF".equals(item.getExtension7().toUpperCase())) {
							ruleModbusTcp.setWrite_values("0");
						}
					}
					
				} else if ("15".equals(item.getExtension2())) {
					if (item.getExtension7() != null) {
						String extension7Str = item.getExtension7().replaceAll("o", "O");
						extension7Str = extension7Str.replaceAll("n", "N");
						extension7Str = extension7Str.replaceAll("f", "F");
						extension7Str = extension7Str.replaceAll("ON", "1");
						extension7Str = extension7Str.replaceAll("OFF", "0");
						ruleModbusTcp.setWrite_values(extension7Str);
					}
				} else {
					ruleModbusTcp.setWrite_values(item.getExtension7());
				}
				
				firewallRuleModbusTcps.add(ruleModbusTcp);
				
			} else if (item.getRule_type() == FirewallStrategy.RULE_TYPE_IEC104) {
				FirewallRuleIEC104 ruleIEC104 = new FirewallRuleIEC104();
				ruleIEC104.setDevice_name(deviceName);
				ruleIEC104.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleIEC104.setStrategy_name("IEC104??????");
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
				ruleS7.setDevice_name(deviceName);
				ruleS7.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				ruleS7.setStrategy_name("S7??????");
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
				opcClassicTcp.setDevice_name(deviceName);
				opcClassicTcp.setGroup_id(exitGroupMap.get(item.getSip()+"-"+item.getDip()));
				opcClassicTcp.setStrategy_name("opc??????");
				opcClassicTcp.setRule_type(FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP);
				opcClassicTcp.setTime_type(1);
				opcClassicTcp.setLog_power(2);
				opcClassicTcp.setRule_action(1);
				opcClassicTcp.setUuid(item.getExtension1());
				opcClassicTcp.setOpnum(item.getExtension2());
				firewallRuleOpcs.add(opcClassicTcp);
				
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
		
	}
	
	private String analysisImportRule(
			String ruleName,
			int rowIndex,
			Row row, 
			FirewallRuleImportItem importItem, 
			Pattern ipv4Pattern, 
			Pattern ipv4MaskPattern, 
			Pattern ipv6Pattern) throws Exception {
		if ("????????????".equals(ruleName)) {
			importItem.setRule_type(FirewallStrategy.RULE_TYPE_CUSTOM);
		} else if ("MODBUS_TCP??????".equals(ruleName)) {
			importItem.setRule_type(FirewallStrategy.RULE_TYPE_MODBUS_TCP);
		} else if ("IEC104??????".equals(ruleName)) {
			importItem.setRule_type(FirewallStrategy.RULE_TYPE_IEC104);
		} else if ("S7??????".equals(ruleName)) {
			importItem.setRule_type(FirewallStrategy.RULE_TYPE_S7);
		} else if ("OPC??????".equals(ruleName)) {
			importItem.setRule_type(FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP);
		}
		
		//??????????????????????????????
		if ("????????????".equals(ruleName)) {
			return analysisImportCustomRule(ruleName, 
					rowIndex, 
					row, 
					importItem, 
					ipv4Pattern, 
					ipv4MaskPattern, 
					ipv6Pattern);
		} else {
			return analysisImportOtherRule(ruleName, 
					rowIndex, 
					row, 
					importItem, 
					ipv4Pattern, 
					ipv4MaskPattern, 
					ipv6Pattern);
		}
	}
	
	private String analysisImportCustomRule(
			String ruleName,
			int rowIndex,
			Row row, 
			FirewallRuleImportItem importItem, 
			Pattern ipv4Pattern, 
			Pattern ipv4MaskPattern, 
			Pattern ipv6Pattern) throws Exception {
		String errorMsg = null;
		//??????????????????????????????
        for (int x = 0; x < row.getLastCellNum(); x++) {
        	
        	Cell cell = row.getCell(x);
        	if (cell == null) {
        		continue;
        	}
        	
        	if (x == 0) {
        		String sip = cell.getStringCellValue();
        		if (sip != null) {
        			sip = sip.trim();
        		}
        		if (StringUtils.isNotBlank(sip) &&
        				!ipv4Pattern.matcher(sip).matches() && 
        				!ipv4MaskPattern.matcher(sip).matches() &&
        				!ipv6Pattern.matcher(sip).matches()) {
        			errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "??????IP??????????????????";
        		}
        		importItem.setSip(sip);
        	}
        	
        	if (x == 1) {
        		String dip = cell.getStringCellValue();
        		if (dip != null) {
        			dip = dip.trim();
        		}
        		if (StringUtils.isNotBlank(dip) &&
        				!ipv4Pattern.matcher(dip).matches() && 
        				!ipv4MaskPattern.matcher(dip).matches() &&
        				!ipv6Pattern.matcher(dip).matches()) {
        			errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "?????????IP??????????????????";
        		}
        		importItem.setDip(dip);
        	}
        	
        	if (x == 2) {
        		String protocol = cell.getStringCellValue();
        		if (protocol != null) {
        			protocol = protocol.trim();
        		}
        		if (StringUtils.isNotBlank(protocol) && 
        				!"IP".equals(protocol.toUpperCase()) &&
        				!"ICMP".equals(protocol.toUpperCase()) &&
        				!"TCP".equals(protocol.toUpperCase()) &&
        				!"UDP".equals(protocol.toUpperCase()) &&
        				!"IGMP".equals(protocol.toUpperCase())) {
        			errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "???, ???????????????????????????";
        		}
        		
        		importItem.setProtocol(protocol);
        	}
        	
        	if (x == 3) {
        		String sport = cell.getStringCellValue();
        		if (sport != null) {
        			sport = sport.trim();
        		}
        		if (StringUtils.isNotBlank(sport)) {
        			String[] ports = sport.split(":");
            		for (int l = 0; l < ports.length; l++) {
						if (StringUtils.isNotBlank(ports[l])) {
							int port = 0;
							try {
								port = Integer.parseInt(ports[l]);
							} catch (Exception e) {
								errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "????????????????????????";
							}
							if (port < 0 || port > 65535) {
								errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "????????????????????????";
							}
						} else {
							errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "????????????????????????";
						}
					}
        		}
        		
        		importItem.setSport(sport);
        	}
        	
        	if (x == 4) {
        		String dport = cell.getStringCellValue();
        		if (dport != null) {
        			dport = dport.trim();
        		}
        		if (StringUtils.isNotBlank(dport)) {
        			String[] ports = dport.split(":");
            		for (int l = 0; l < ports.length; l++) {
						if (StringUtils.isNotBlank(ports[l])) {
							int port = 0;
							try {
								port = Integer.parseInt(ports[l]);
							} catch (Exception e) {
								errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "???????????????????????????";
							}
							if (port < 0 || port > 65535) {
								errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "???????????????????????????";
							}
						} else {
							errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "???????????????????????????";
						}
					}
        		}
        		importItem.setDport(dport);
        	}
        	
        }
        
        return errorMsg;
	}
	
	private String analysisImportOtherRule(
			String ruleName,
			int rowIndex,
			Row row, 
			FirewallRuleImportItem importItem, 
			Pattern ipv4Pattern, 
			Pattern ipv4MaskPattern, 
			Pattern ipv6Pattern) throws Exception {
		String errorMsg = null;
		//??????????????????????????????
        for (int x = 0; x < row.getLastCellNum(); x++) {
        	
        	Cell cell = row.getCell(x);
        	if (cell == null) {
        		continue;
        	}
        	
        	if (x == 0) {
        		String sip = cell.getStringCellValue();
        		if (sip != null) {
        			sip = sip.trim();
        		}
        		if (StringUtils.isNotBlank(sip) &&
        				!ipv4Pattern.matcher(sip).matches() && 
        				!ipv4MaskPattern.matcher(sip).matches() &&
        				!ipv6Pattern.matcher(sip).matches()) {
        			errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "??????IP??????????????????";
        		}
        		importItem.setSip(sip);
        	}else if (x == 1) {
        		String dip = cell.getStringCellValue();
        		if (dip != null) {
        			dip = dip.trim();
        		}
        		if (StringUtils.isNotBlank(dip) &&
        				!ipv4Pattern.matcher(dip).matches() && 
        				!ipv4MaskPattern.matcher(dip).matches() &&
        				!ipv6Pattern.matcher(dip).matches()) {
        			errorMsg = "?????????" + ruleName + "???" + (rowIndex + 1) + "?????????IP??????????????????";
        		}
        		importItem.setDip(dip);
        	} else {
        		String extensionValue = cell.getStringCellValue();
        		if (extensionValue != null) {
        			extensionValue = extensionValue.trim();
        		}
        		if (StringUtils.isNotBlank(extensionValue)) {
        			switch (x) {
					case 2:importItem.setExtension1(extensionValue);break;
					case 3:importItem.setExtension2(extensionValue);break;
					case 4:importItem.setExtension3(extensionValue);break;
					case 5:importItem.setExtension4(extensionValue);break;
					case 6:importItem.setExtension5(extensionValue);break;
					case 7:importItem.setExtension6(extensionValue);break;
					case 8:importItem.setExtension7(extensionValue);break;
					case 9:importItem.setExtension8(extensionValue);break;
					case 10:importItem.setExtension9(extensionValue);break;
					case 11:importItem.setExtension10(extensionValue);break;
					case 12:importItem.setExtension11(extensionValue);break;
					case 13:importItem.setExtension12(extensionValue);break;
					case 14:importItem.setExtension13(extensionValue);break;
					case 15:importItem.setExtension14(extensionValue);break;
					case 16:importItem.setExtension15(extensionValue);break;
					case 17:importItem.setExtension16(extensionValue);break;
					case 18:importItem.setExtension17(extensionValue);break;
					case 19:importItem.setExtension18(extensionValue);break;
					case 20:importItem.setExtension19(extensionValue);break;
					case 21:importItem.setExtension20(extensionValue);break;
					default:
						break;
					}
        			
        		}
        	}
        	
        	
        }
        
        return errorMsg;
	}
	
	private void deleteFile(File file, InputStream is) {

		try {
			is.close();
		} catch (Exception e) {
		}
		boolean result = file.delete();
		int tryCount = 0;
		while (!result && tryCount++ < 10) {
			System.gc(); // ????????????
			try {
				is.close();
			} catch (Exception e) {
			}
			result = file.delete();
		}
	}

	
}
