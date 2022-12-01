package com.jiuzhou.plat.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiuzhou.plat.bean.AuditModbusFunctionCode;
import com.jiuzhou.plat.bean.AuditRuleBase;
import com.jiuzhou.plat.bean.AuditRuleModbusTcp;
import com.jiuzhou.plat.bean.AuditWhitelist;
import com.jiuzhou.plat.bean.AuditWhitelistRule;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DataDictionaryItem;
import com.jiuzhou.plat.bean.OperateLog;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.mapper.AuditModbusFunctionCodeMapper;
import com.jiuzhou.plat.mapper.AuditWhitelistMapper;
import com.jiuzhou.plat.mapper.AuditWhitelistRuleMapper;
import com.jiuzhou.plat.mapper.DataDictionaryMapper;
import com.jiuzhou.plat.mapper.OperateLogMapper;
import com.jiuzhou.plat.service.AuditWhitelistService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年10月9日 下午7:42:53
* 类说明
*/
@Service("AuditWhitelistService")
public class AuditWhitelistServiceImpl implements AuditWhitelistService {
	
	@Autowired
	private AuditWhitelistMapper auditWhitelistMapper;
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;
	@Autowired
	private AuditModbusFunctionCodeMapper auditModbusFunctionCodeMapper;
	@Autowired
	private AuditWhitelistRuleMapper auditWhitelistRuleMapper;
	@Autowired
	private OperateLogMapper operateLogMapper;

	@Override
	public String getAuditWhitelistAll(JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		List<AuditWhitelist> whitelists = auditWhitelistMapper.getWhitelistAll();
		if (whitelists == null) {
			whitelists = new ArrayList<>();
		}
		
		//取出协议类型列表用于页面下拉选择
		List<DataDictionaryItem> dictionaryItems = 
				dataDictionaryMapper.getByDicCode(DataDictionaryItem.CODE_RULE_PROTOCOL);
		commonResult.put("protocols", dictionaryItems);
		
		//取出modbus功能码列表用于页面下拉选择
		List<AuditModbusFunctionCode> functionCodes = 
				auditModbusFunctionCodeMapper.getByType(AuditModbusFunctionCode.TYPE_MODBUS_TCP);
		commonResult.put("functionCodes", functionCodes);
		
		//取出白名单规则并进行匹配
		List<AuditWhitelistRule> rules = null;
		if (whitelists.size() > 0) {
			rules = auditWhitelistRuleMapper.getByWhitelistIds(whitelists);
		}
		if (rules != null && rules.size() > 0) {
			for (int i = 0; i < rules.size(); i++) {
				AuditWhitelistRule rule = rules.get(i);
				for (AuditWhitelist whitelist : whitelists) {
					if (whitelist.getId() == rule.getWhitelist_id()) {
						whitelist.getRules().add(rule);
						break;
					}
				}
			}
		}
		
		commonResult.setStatus(true);
		commonResult.put("list", whitelists);
		return commonResult.toString();
	}

	@Override
	public String updateAuditWhitelist(JSONObject paramJson, 
								AdminUserLoginInfo loginInfo,
								int logId, 
								String logTableName) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		String source_mac = null;
		if (paramJson.has("source_mac")) {
			source_mac = paramJson.getString("source_mac");
		}
		
		String target_mac = null;
		if (paramJson.has("target_mac")) {
			target_mac = paramJson.getString("target_mac");
		}
		
		String ip_type = null;
		if (paramJson.has("ip_type")) {
			ip_type = paramJson.getString("ip_type");
		}
		
		String source_ip = null;
		String target_ip = null;
		
		if (StringUtils.isNotBlank(ip_type)) {
			if ("ipv4".equals(ip_type)) {
				if (paramJson.has("source_ipv4")) {
					source_ip = paramJson.getString("source_ipv4");
				}
				if (paramJson.has("target_ipv4")) {
					target_ip = paramJson.getString("target_ipv4");
				}
			} else if ("ipv6".equals(ip_type)) {
				if (paramJson.has("source_ipv6")) {
					source_ip = paramJson.getString("source_ipv6");
				}
				if (paramJson.has("target_ipv6")) {
					target_ip = paramJson.getString("target_ipv6");
				}
			}
		}
		
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
		}
		
		int sport = -1;
		if (paramJson.has("sport")) {
			try {
				sport = paramJson.getInt("sport");
			} catch (Exception e) {
				sport = -1;
			}
			
		}
		
		int dport = -1;
		if (paramJson.has("dport")) {
			try {
				dport = paramJson.getInt("dport");
			} catch (Exception e) {
				dport = -1;
			}
		}
		
		int direction = 1;
		if (paramJson.has("direction")) {
			direction = paramJson.getInt("direction");
		}
		
		AuditWhitelist auditWhitelist = auditWhitelistMapper.getWhitelistById(id);
		if (auditWhitelist == null) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		String logDesctiption = "编辑前\n" + auditWhitelist.toLogDescription() + "\n编辑后\n";
		
		auditWhitelist.setSource_mac(source_mac);
		auditWhitelist.setTarget_mac(target_mac);
		auditWhitelist.setIp_type(ip_type);
		auditWhitelist.setSource_ip(source_ip);
		auditWhitelist.setTarget_ip(target_ip);
		auditWhitelist.setSport(sport);
		auditWhitelist.setDport(dport);
		auditWhitelist.setProtocol(protocol);
		auditWhitelist.setDirection(direction);
		auditWhitelist.setUpdate_time(new Date());
		auditWhitelist.setUpdate_user(loginInfo.getUserInfo().getUser_name());
		
		logDesctiption += auditWhitelist.toLogDescription();
		auditWhitelistMapper.updateWhitelistById(auditWhitelist);
		
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(logDesctiption);
		operateLogMapper.update(log, logTableName);
		
		//下发规则
		executeRule();
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String deleteAuditWhitelist(JSONObject paramJson,
										AdminUserLoginInfo loginInfo,
										int logId, 
										String logTableName) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		AuditWhitelist auditWhitelist = auditWhitelistMapper.getWhitelistById(id);
		
		//删除规则
		auditWhitelistRuleMapper.deleteByWhitelistId(id);
		//删除白名单
		auditWhitelistMapper.deleteWhitelistById(id);
		
		//下发规则
		executeRule();
		
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(auditWhitelist.toLogDescription());
		operateLogMapper.update(log, logTableName);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String addAuditWhitelist(JSONObject paramJson, 
										AdminUserLoginInfo loginInfo,
										int logId, 
										String logTableName) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		String source_mac = null;
		if (paramJson.has("source_mac")) {
			source_mac = paramJson.getString("source_mac");
		}
		
		String target_mac = null;
		if (paramJson.has("target_mac")) {
			target_mac = paramJson.getString("target_mac");
		}
		
		String ip_type = null;
		if (paramJson.has("ip_type")) {
			ip_type = paramJson.getString("ip_type");
		}
		
		String source_ip = null;
		String target_ip = null;
		
		if (StringUtils.isNotBlank(ip_type)) {
			if ("ipv4".equals(ip_type)) {
				if (paramJson.has("source_ipv4")) {
					source_ip = paramJson.getString("source_ipv4");
				}
				if (paramJson.has("target_ipv4")) {
					target_ip = paramJson.getString("target_ipv4");
				}
			} else if ("ipv6".equals(ip_type)) {
				if (paramJson.has("source_ipv6")) {
					source_ip = paramJson.getString("source_ipv6");
				}
				if (paramJson.has("target_ipv6")) {
					target_ip = paramJson.getString("target_ipv6");
				}
			}
		}
		
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
		}
		
		int sport = -1;
		if (paramJson.has("sport")) {
			try {
				sport = paramJson.getInt("sport");
			} catch (Exception e) {
				sport = -1;
			}
			
		}
		
		int dport = -1;
		if (paramJson.has("dport")) {
			try {
				dport = paramJson.getInt("dport");
			} catch (Exception e) {
				dport = -1;
			}
		}
		
		int direction = 1;
		if (paramJson.has("direction")) {
			direction = paramJson.getInt("direction");
		}
		
		
		
		AuditWhitelist auditWhitelist = new AuditWhitelist();
		auditWhitelist.setTarget_mac(target_mac);
		auditWhitelist.setSource_mac(source_mac);
		auditWhitelist.setIp_type(ip_type);
		auditWhitelist.setSource_ip(source_ip);
		auditWhitelist.setTarget_ip(target_ip);
		auditWhitelist.setSport(sport);
		auditWhitelist.setDport(dport);
		auditWhitelist.setProtocol(protocol);
		auditWhitelist.setDirection(direction);
		auditWhitelist.setAdd_time(new Date());
		auditWhitelist.setAdd_user(loginInfo.getUserInfo().getUser_name());
		
		auditWhitelistMapper.insert(auditWhitelist);
		
		//下发规则
		executeRule();
		
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(auditWhitelist.toLogDescription());
		operateLogMapper.update(log, logTableName);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String addAuditWhitelistRule(JSONObject paramJson, 
			AdminUserLoginInfo loginInfo,
			int logId, 
			String logTableName) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("whitelistId")) {
			commonResult.setErrorMsg("缺少参数whitelistId");
			return commonResult.toString();
		}
		int whtelistId = paramJson.getInt("whitelistId");
		
		if (!paramJson.has("ruleType")) {
			commonResult.setErrorMsg("缺少参数ruleType");
			return commonResult.toString();
		}
		int ruleType = paramJson.getInt("ruleType");
		
		//创建规则
		AuditRuleBase ruleBase = null;
		String logDescription = "";
		if (ruleType == AuditWhitelistRule.TYPE_MODBUS_TCP) {	//MODBUS_TCP规则
			AuditRuleModbusTcp modbusTcp = this.createRuleModbusTcp(paramJson, commonResult);
			modbusTcp.setWhitelistId(whtelistId);
			logDescription = modbusTcp.toLodDescription();
			ruleBase = modbusTcp;
		}
		
		if (ruleBase == null) {
			return commonResult.toString();
		}
		
		AuditWhitelistRule whitelistRule = new AuditWhitelistRule();
		whitelistRule.setWhitelist_id(whtelistId);
		whitelistRule.setType(ruleType);
		whitelistRule.setRule_json(JSONObject.fromObject(ruleBase).toString());
		whitelistRule.setAdd_time(new Date());
		whitelistRule.setAdd_user(loginInfo.getUserInfo().getUser_name());
		auditWhitelistRuleMapper.insert(whitelistRule);
		
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(logDescription);
		operateLogMapper.update(log, logTableName);
		
		//下发规则
		executeRule();
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	
	/**
	 * 创建ModbusTcp规则
	 * @param paramJson
	 * @param commonResult
	 * @return
	 */
	private AuditRuleModbusTcp createRuleModbusTcp(JSONObject paramJson, CommonResult commonResult) {
		//功能码
		if (!paramJson.has("functionCode")) {
			commonResult.setErrorMsg("缺少参数functionCode");
			return null;
		}
		int functionCode = paramJson.getInt("functionCode");
		
		//读写类型, 1：读，2：写，3：读写
		if (!paramJson.has("mode")) {	
			commonResult.setErrorMsg("缺少参数mode");
			return null;
		}
		int mode = paramJson.getInt("mode");
		
		//读起始地址
		int readStart = 0;
		if (paramJson.has("readStart")) {
			readStart = paramJson.getInt("readStart");
		}
		
		//读地址长度
		int readLength = 0;
		if (paramJson.has("readLength")) {
			readLength = paramJson.getInt("readLength");
		}
		
		//写起始地址
		int wirteStart = 0;
		if (paramJson.has("wirteStart")) {
			wirteStart = paramJson.getInt("wirteStart");
		}
		
		//写地址长度
		int writeLength = 0;
		if (paramJson.has("writeLength")) {
			writeLength = paramJson.getInt("writeLength");
		}
		
		//写值列表
		Integer[] writeValueIntegers = new Integer[writeLength];
		for (int i = 0; i < writeLength; i++) {
			if (!paramJson.has("writeValue_"+i)) {
				commonResult.setErrorMsg("缺少参数writeValue_"+i);
				return null;
			}
			writeValueIntegers[i] = paramJson.getInt("writeValue_"+i);
		}
		
		AuditRuleModbusTcp ruleModbusTcp = new AuditRuleModbusTcp();
		ruleModbusTcp.setFunctionCode(functionCode);
		ruleModbusTcp.setMode(mode);
		ruleModbusTcp.setReadLength(readLength);
		ruleModbusTcp.setReadStart(readStart);
		ruleModbusTcp.setWriteLength(writeLength);
		ruleModbusTcp.setWirteStart(wirteStart);
		ruleModbusTcp.setWriteValues(writeValueIntegers);
		return ruleModbusTcp;
		
	}

	
	
	@Override
//	@Transactional(rollbackFor=Exception.class)
	public String updateAuditWhitelistRule(JSONObject paramJson, 
			AdminUserLoginInfo loginInfo,
			int logId, 
			String logTableName) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("ruleId")) {
			commonResult.setErrorMsg("缺少参数ruleId");
			return commonResult.toString();
		}
		int ruleId = paramJson.getInt("ruleId");
		
		AuditWhitelistRule whitelistRule = auditWhitelistRuleMapper.getById(ruleId);
		if (whitelistRule == null) {
			commonResult.setErrorMsg("规则不存在");
			return commonResult.toString();
		}
		
		if (!paramJson.has("ruleType")) {
			commonResult.setErrorMsg("缺少参数ruleType");
			return commonResult.toString();
		}
		int ruleType = paramJson.getInt("ruleType");
		
		//创建规则
		AuditRuleBase ruleBase = null;
		String logDescription = "编辑前\n";
		if (ruleType == AuditWhitelistRule.TYPE_MODBUS_TCP) {	//MODBUS_TCP规则
			JSONObject jsonObject = JSONObject.fromObject(whitelistRule.getRule_json());
			AuditRuleModbusTcp modbusTcp = (AuditRuleModbusTcp) JSONObject.toBean(jsonObject, AuditRuleModbusTcp.class);
			modbusTcp.setWhitelistId(whitelistRule.getWhitelist_id());
			logDescription += modbusTcp.toLodDescription();
			modbusTcp = this.createRuleModbusTcp(paramJson, commonResult);
			modbusTcp.setWhitelistId(whitelistRule.getWhitelist_id());
			logDescription += "\n编辑后\n" + modbusTcp.toLodDescription();
			
			ruleBase = modbusTcp;
		}
		
		if (ruleBase == null) {
			return commonResult.toString();
		}
		
		
		whitelistRule.setType(ruleType);
		whitelistRule.setRule_json(JSONObject.fromObject(ruleBase).toString());
		whitelistRule.setUpdate_time(new Date());
		whitelistRule.setUpdate_user(loginInfo.getUserInfo().getUser_name());
		auditWhitelistRuleMapper.update(whitelistRule);
		
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(logDescription);
		operateLogMapper.update(log, logTableName);
		
		//下发规则
		executeRule();
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	

	@Override
	public String deleteAuditWhitelistRule(JSONObject paramJson, 
			AdminUserLoginInfo loginInfo,
			int logId, 
			String logTableName) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("ruleId")) {
			commonResult.setErrorMsg("缺少参数ruleId");
			return commonResult.toString();
		}
		int ruleId = paramJson.getInt("ruleId");
		
		AuditWhitelistRule whitelistRule = auditWhitelistRuleMapper.getById(ruleId);
		if (whitelistRule == null) {
			commonResult.setErrorMsg("规则不存在");
			return commonResult.toString();
		}
		
		String logDescription = "";
		
		if (whitelistRule.getType() == AuditWhitelistRule.TYPE_MODBUS_TCP) {
			JSONObject jsonObject = JSONObject.fromObject(whitelistRule.getRule_json());
			AuditRuleModbusTcp modbusTcp = (AuditRuleModbusTcp) JSONObject.toBean(jsonObject, AuditRuleModbusTcp.class);
			modbusTcp.setWhitelistId(whitelistRule.getWhitelist_id());
			logDescription = modbusTcp.toLodDescription();
		}
		
		auditWhitelistRuleMapper.deleteById(whitelistRule.getId());
		
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(logDescription);
		operateLogMapper.update(log, logTableName);
		
		//下发规则
		executeRule();
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	
	/**
	 * 下发规则
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void executeRule() throws Exception {
		
		//获取白名单列表
		List<AuditWhitelist> whitelists = auditWhitelistMapper.getWhitelistAll();
		if (whitelists == null) {
			whitelists = new ArrayList<>();
		}
		
		//取出白名单规则
		List<AuditWhitelistRule> rules = new ArrayList<>();
		if (whitelists.size() > 0) {
			rules = auditWhitelistRuleMapper.getByWhitelistIds(whitelists);
		}
		
		//拼接字符串
		StringBuilder commonRuleBuilder = new StringBuilder();
		StringBuilder modbusTcpBuilder = new StringBuilder();
		int i = 1;//modbustcp规则组编号
		for (AuditWhitelist auditWhitelist : whitelists) {
			//普通规则列表
			//modbusTCP规则列表
			List<AuditRuleModbusTcp> modbusTcpRules = new ArrayList<>();
			
			for (AuditWhitelistRule auditWhitelistRule : rules) {
				if (auditWhitelist.getId() == auditWhitelistRule.getWhitelist_id()) {
					if (auditWhitelistRule.getType() == AuditWhitelistRule.TYPE_MODBUS_TCP) {
						modbusTcpRules.add((AuditRuleModbusTcp)auditWhitelistRule.getRule());
					}
				}
			}
			
			boolean isAll = StringUtils.isBlank(auditWhitelist.getSource_mac())
					&& StringUtils.isBlank(auditWhitelist.getTarget_mac())
					&& StringUtils.isBlank(auditWhitelist.getSource_ip())
					&& StringUtils.isBlank(auditWhitelist.getTarget_ip())
					&& StringUtils.isBlank(auditWhitelist.getProtocol())
					&& auditWhitelist.getSport() == -1
					&& auditWhitelist.getDport() == -1;
			
			if (modbusTcpRules.size() < 1) {
				if (isAll) {
					commonRuleBuilder.append(buildCommonRule(auditWhitelist, i));
					i ++;
					continue;
				}
				if (auditWhitelist.getDirection() == 1) {//正向
					commonRuleBuilder.append(buildCommonRule(auditWhitelist, i));
				} else if (auditWhitelist.getDirection() == 2) {//反向
					commonRuleBuilder.append(buildCommonRuleContrary(auditWhitelist, i));
				} else if (auditWhitelist.getDirection() == 3) {//双向
					commonRuleBuilder.append(buildCommonRule(auditWhitelist, i));
					commonRuleBuilder.append(buildCommonRuleContrary(auditWhitelist, i));
				}
			} else {
				if (isAll) {
					commonRuleBuilder.append(buildCommonRule(auditWhitelist, i));
					modbusTcpBuilder.append(buildModbusTcpRule(modbusTcpRules, i));
					i ++;
					continue;
				}
				if (auditWhitelist.getDirection() == 1) {//正向
					commonRuleBuilder.append(buildCommonRule(auditWhitelist, i));
				} else if (auditWhitelist.getDirection() == 2) {//反向
					commonRuleBuilder.append(buildCommonRuleContrary(auditWhitelist, i));
				} else if (auditWhitelist.getDirection() == 3) {//双向
					commonRuleBuilder.append(buildCommonRule(auditWhitelist, i));
					commonRuleBuilder.append(buildCommonRuleContrary(auditWhitelist, i));
				}
				modbusTcpBuilder.append(buildModbusTcpRule(modbusTcpRules, i));
			}
			
			i ++;
			
		}
		
		System.out.println(commonRuleBuilder);
		System.out.println(modbusTcpBuilder);
		
		if (!ServiceBase.isLinux()) {
			return;
		}
		
		//写普通规则文件
		BufferedWriter commonRuleOut = new BufferedWriter(new FileWriter("/jiuzhou/adu/filter/conf/rules_ebtables_set"));
		commonRuleOut.write(commonRuleBuilder.toString());
		commonRuleOut.flush();
		commonRuleOut.close();
		
		//执行脚本
		InputStreamReader stdISR = null;
		InputStreamReader errISR = null;
		String command = "/jiuzhou/adu/filter/scripts/filter_setup.sh";
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
		String line = null;
		stdISR = new InputStreamReader(process.getInputStream());
		BufferedReader stdBR = new BufferedReader(stdISR);
		while ((line = stdBR.readLine()) != null) {
			System.out.println("STD line:" + line);
		}

		errISR = new InputStreamReader(process.getErrorStream());
		BufferedReader errBR = new BufferedReader(errISR);
		String commandErrInfo = "";
		while ((line = errBR.readLine()) != null) {
			commandErrInfo += ("ERR line:" + line + "\n");
		}
		if (StringUtils.isNotBlank(commandErrInfo)) {
			throw new Exception("调用脚本发生错误");
		}
		
		//写modbus_tcp规则文件
		BufferedWriter modbusTcpRuleOut = new BufferedWriter(new FileWriter("/jiuzhou/adu/plugin_manager/rule_conf/modbus"));
		modbusTcpRuleOut.write(modbusTcpBuilder.toString());
		modbusTcpRuleOut.flush();
		modbusTcpRuleOut.close();
		
	}
	
	
	private String buildCommonRule(AuditWhitelist auditWhitelist, int index	)throws Exception{
		StringBuilder builder = new StringBuilder();
		builder.append("-A CMP_RULE_ETH");
		//源MAC
		if (StringUtils.isNotBlank(auditWhitelist.getSource_mac())) {
			builder.append(" -s ").append(auditWhitelist.getSource_mac());
		}
		//目的MAC
		if (StringUtils.isNotBlank(auditWhitelist.getTarget_mac())) {
			builder.append(" -d ").append(auditWhitelist.getTarget_mac());
		}
		
		if (StringUtils.isBlank(auditWhitelist.getIp_type())) {
			builder.append(" --ulog-prefix ");
			builder.append(index);
			builder.append(" --ulog-nlgroup 10 -j DROP\n");
			return builder.toString();
		}
		
		//ip类型
		builder.append(" -p ");
		builder.append(auditWhitelist.getIp_type());
		
		//协议
		if (StringUtils.isNotBlank(auditWhitelist.getProtocol())) {
			builder.append(" --ip-proto ");
			builder.append(auditWhitelist.getProtocol());
		}
		
		//源IP
		if (StringUtils.isNotBlank(auditWhitelist.getSource_ip())) {
			builder.append(" --ip-src ")
			.append(auditWhitelist.getSource_ip());
		}
		//目的IP
		if (StringUtils.isNotBlank(auditWhitelist.getTarget_ip())) {
			builder.append(" --ip-dst ")
			.append(auditWhitelist.getTarget_ip());
		}
		if (!"icmp".equals(auditWhitelist.getProtocol())) {
			//源端口
			if (auditWhitelist.getSport() != -1) {
				builder.append(" --ip-sport ").append(auditWhitelist.getSport());
			}
			//目的端口
			if (auditWhitelist.getDport() != -1) {
				builder.append(" --ip-dport ").append(auditWhitelist.getDport());
			}
		}
		builder.append(" --ulog-prefix ");
		builder.append(index);
		builder.append(" --ulog-nlgroup 10 -j DROP\n");
		return builder.toString();
	}
	
	
	private String buildCommonRuleContrary(AuditWhitelist auditWhitelist, int index	)throws Exception{
		StringBuilder builder = new StringBuilder();
		builder.append("-A CMP_RULE_ETH");
		//源MAC
		if (StringUtils.isNotBlank(auditWhitelist.getTarget_mac())) {
			builder.append(" -s ").append(auditWhitelist.getTarget_mac());
		}
		//目的MAC
		if (StringUtils.isNotBlank(auditWhitelist.getSource_mac())) {
			builder.append(" -d ").append(auditWhitelist.getSource_mac());
		}
		
		if (StringUtils.isBlank(auditWhitelist.getIp_type())) {
			builder.append(" --ulog-prefix ");
			builder.append(index);
			builder.append(" --ulog-nlgroup 10 -j DROP\n");
			return builder.toString();
		}
		
		//ip类型
		builder.append(" -p ");
		builder.append(auditWhitelist.getIp_type());
		
		//协议
		if (StringUtils.isNotBlank(auditWhitelist.getProtocol())) {
			builder.append(" --ip-proto ");
			builder.append(auditWhitelist.getProtocol());
		}
		
		//源IP
		if (StringUtils.isNotBlank(auditWhitelist.getTarget_ip())) {
			builder.append(" --ip-src ")
			.append(auditWhitelist.getTarget_ip());
		}
		//目的IP
		if (StringUtils.isNotBlank(auditWhitelist.getSource_ip())) {
			builder.append(" --ip-dst ")
			.append(auditWhitelist.getSource_ip());
		}
		if (!"icmp".equals(auditWhitelist.getProtocol())) {
			//源端口
			if (auditWhitelist.getDport() != -1) {
				builder.append(" --ip-sport ").append(auditWhitelist.getDport());
			}
			//目的端口
			if (auditWhitelist.getSport() != -1) {
				builder.append(" --ip-dport ").append(auditWhitelist.getSport());
			}
		}
		builder.append(" --ulog-prefix ");
		builder.append(index);
		builder.append(" --ulog-nlgroup 10 -j DROP\n");
		return builder.toString();
	}
	
	
	private String buildModbusTcpRule(List<AuditRuleModbusTcp> modbusTcpRules, int index)throws Exception {
		StringBuilder builder = new StringBuilder();
		for (AuditRuleModbusTcp auditRuleModbusTcp : modbusTcpRules) {
			builder.append("-rn ").append(index)
			.append(" -fc ").append(auditRuleModbusTcp.getFunctionCode())
			.append(" -ra ").append(auditRuleModbusTcp.getReadStart())
			.append(" -rc ").append(auditRuleModbusTcp.getReadLength())
			.append(" -wa ").append(auditRuleModbusTcp.getWirteStart())
			.append(" -wc ").append(auditRuleModbusTcp.getWriteLength());
			if (auditRuleModbusTcp.getWriteValues().length > 0) {
				builder.append(" -vl ");
				Integer[] writeValues = auditRuleModbusTcp.getWriteValues();
				for (int j = 0; j < writeValues.length; j++) {
					if (j != 0) {
						builder.append(",");
					}
					builder.append(writeValues[j]);
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	
	
}
