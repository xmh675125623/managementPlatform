package com.jiuzhou.firewall.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiuzhou.firewall.bean.FirewallAntiAttackDos;
import com.jiuzhou.firewall.bean.FirewallAntiAttackScan;
import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallDeviceEthernet;
import com.jiuzhou.firewall.bean.FirewallStrategy;
import com.jiuzhou.firewall.mapper.FirewallAntiAttackMapper;
import com.jiuzhou.firewall.mapper.FirewallCustomRouteMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceEthernetMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceTempMapper;
import com.jiuzhou.firewall.mapper.FirewallFlowTotalMapper;
import com.jiuzhou.firewall.mapper.FirewallMacMapper;
import com.jiuzhou.firewall.mapper.FirewallNatMapper;
import com.jiuzhou.firewall.mapper.FirewallRouteMapper;
import com.jiuzhou.firewall.mapper.FirewallSessionControlMapper;
import com.jiuzhou.firewall.mapper.FirewallSessionKeepMapper;
import com.jiuzhou.firewall.mapper.FirewallSessionMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyGroupMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyMapper;
import com.jiuzhou.firewall.mapper.StudyRuleItemMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.utils.EventUtils;
import com.jiuzhou.firewall.utils.FirewallDeviceUtils;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.service.impl.ServiceBase;
import com.jiuzhou.plat.thread.FirewallDeviceStatusThread;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author xingmh
 * @version ???????????????2019???1???7??? ??????2:08:30 ?????????
 */
@Service("FirewallDeviceService")
public class FirewallDeviceServiceImpl extends ServiceBase implements FirewallDeviceService {

	@Autowired
	FirewallDeviceTempMapper firewallDeviceTempMapper;
	@Autowired
	FirewallDeviceMapper firewallDeviceMapper;
	@Autowired
	FirewallDeviceEthernetMapper firewallDeviceEthernetMapper;
	@Autowired
	FirewallSessionMapper firewallSessionMapper;
	@Autowired
	FirewallMacMapper firewallMacMapper;
	@Autowired
	FirewallNatMapper firewallNatMapper;
	@Autowired
	FirewallRouteMapper firewallRouteMapper;
	@Autowired
	FirewallStrategyMapper firewallStrategyMapper;
	@Autowired
	FirewallSessionControlMapper firewallSessionControlMapper;
	@Autowired
	FirewallFlowTotalMapper firewallFlowTotalMapper;
	@Autowired
	FirewallStrategyGroupMapper firewallStrategyGroupMapper;
	@Autowired
	FirewallAntiAttackMapper firewallAntiAttackMapper;
	@Autowired
	FirewallCustomRouteMapper firewallCustomRouteMapper;
	@Autowired
	FirewallSessionKeepMapper firewallSessionKeepMapper;
	@Autowired
	StudyRuleItemMapper studyRuleItemMapper;
	@Autowired
	SystemSettingService systemSettingService;
	
	/**
	 * ?????????????????????
	 * 
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String sniffDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {

		CommonResult commonResult = new CommonResult(false, "");

		// ??????ip?????????(??????192.168.0.)
		if (!paramJson.has("ipss")) {
			commonResult.setErrorMsg("????????????ipss");
			return commonResult.toString();
		}
		String ipss = paramJson.getString("ipss");

		// ??????????????????ip?????????(??????100)
		if (!paramJson.has("ips")) {
			commonResult.setErrorMsg("????????????ips");
			return commonResult.toString();
		}
		int ips = paramJson.getInt("ips");

		if (!paramJson.has("ipf")) {
			commonResult.setErrorMsg("????????????ipf");
			return commonResult.toString();
		}
		int ipf = paramJson.getInt("ipf");

		Map<String, String> maps = new HashMap<String, String>();
		maps.put("port", "6860");// ????????????

		List<FirewallDevice> devices = new ArrayList<>();

		for (int i = 0; i < (ipf - ips + 1); i++) {
			String ip = ipss + (ips + i);
			maps.put("mip", ip);

			byte[] pjbuffer = FirewallDeviceUtils.predive("0x90", maps);
			System.out.println(new String(pjbuffer));
			byte[] recivebyte = TCPSocketUtil.recive(pjbuffer, maps);// ???????????????
			if (recivebyte == null) {
				continue;// ????????????
			} else if (new String(recivebyte).equals("ss")) {
				continue;// ????????????
			} else {
				byte[] decryptbyte = EventUtils.decrypt(recivebyte);// ???????????????

				if (new String(decryptbyte) != null && new String(decryptbyte).length() > 16) {
					String resu = new String(decryptbyte).substring(10, 11);
					if (resu.equals("1")) {
						String[] param = (new String(decryptbyte).substring(12)).split("@");
						String[] param1 = param[0].split(" ");
						FirewallDevice device = new FirewallDevice();
						device.setDevice_name(param1[0]);// mac??????????????? ????????????
						device.setIp_address(ip);
						device.setEdition(param1[1]);
						device.setModal(param1[2]);
						device.setInsert_time(new Date());
						devices.add(device);
						// TODO ??????????????????
					} else {
						continue;// ????????????
					}

				} else {
					continue;// ????????????
				}
			}
		}

		firewallDeviceTempMapper.deleteByAid(paramJson.getInt("aid"));
		if (devices != null && devices.size() > 0) {
			firewallDeviceTempMapper.insertBatch(devices, paramJson.getInt("aid"));
		}

		// ??????????????????
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION,
				"[??????IP:" + ipss + ips + "]\n[??????IP:" + ipss + ipf + "]");

		commonResult.put("devices", devices);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addToManage(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");

		int aid = paramJson.getInt("aid");

		if (!paramJson.has("deviceNames")) {
			commonResult.setErrorMsg("???????????????????????????");
			return commonResult.toString();
		}
		String deviceNamesStr = paramJson.getString("deviceNames");
		String[] deviceNames = deviceNamesStr.split(",");
		List<String> deviceNameList = new ArrayList<>(Arrays.asList(deviceNames));

		// ?????????????????????????????????????????????
		List<FirewallDevice> tempDevices = firewallDeviceTempMapper.getByDeviceNames(aid, deviceNameList);
		if (tempDevices == null || tempDevices.size() < 1) {
			commonResult.setErrorMsg("???????????????????????????");
			return commonResult.toString();
		}

		// ????????????????????????????????????????????????
		List<FirewallDevice> devices = firewallDeviceMapper.getByDeviceNames(deviceNameList);
		
		String platId = systemSettingService.getByName(SystemSetting.PLAT_ID).getValue();
		if (StringUtils.isBlank(platId)) {
			commonResult.setErrorMsg("?????????????????????????????????????????????");
			return commonResult.toString();
		}

		// ??????????????????????????????????????????????????????
		if (devices == null || devices.size() < 1) {
			String resultStr = "";// ?????????????????????????????????????????????
			for (FirewallDevice firewallDevice : tempDevices) {
				Map<String, String> maps = new HashMap<String, String>();
				maps.put("port", "6860");
				maps.put("mip", firewallDevice.getIp_address());
				byte[] decryptbyte = TCPSocketUtil.ActionWrite("jiuzhoucmp0x97", platId, 1, maps);// ???????????????
				if (decryptbyte != null) {
					decryptbyte = EventUtils.decrypt(decryptbyte);
					String decryptStr = new String(decryptbyte);
					String resu = decryptStr.substring(10, 11);
					if (resu.equals("1")) {
						insertDevice(firewallDevice);
					} else {// ????????????
						String[] param = (decryptStr.substring(12)).split("@");
						resultStr += firewallDevice.getDevice_name() + "???????????????" + param[0] + "\n";
					}
				} else {
					resultStr += firewallDevice.getDevice_name() + "????????????????????????????????????\n";
				}
			}

			if (StringUtils.isNotBlank(resultStr)) {
				commonResult.setErrorMsg(resultStr);
				return commonResult.toString();
			}

			commonResult.setStatus(true);
			return commonResult.toString();
		}

		// ???????????????????????????????????????????????????
		Map<String, FirewallDevice> devicesMap = new HashMap<>();
		for (FirewallDevice device : devices) {
			devicesMap.put(device.getDevice_name(), device);
		}

		List<FirewallDevice> insertDevices = new ArrayList<>();
		for (FirewallDevice firewallDevice : tempDevices) {
			if (devicesMap.get(firewallDevice.getDevice_name()) != null) {
				continue;
			}
			insertDevices.add(firewallDevice);
		}

		if (tempDevices.size() > 0) {
			String resultStr = "";// ?????????????????????????????????????????????
			for (FirewallDevice firewallDevice : tempDevices) {
				Map<String, String> maps = new HashMap<String, String>();
				maps.put("port", "6860");
				maps.put("mip", firewallDevice.getIp_address());
				byte[] decryptbyte = TCPSocketUtil.ActionWrite("jiuzhoucmp0x97", platId, 1, maps);// ???????????????
				if (decryptbyte != null) {
					decryptbyte = EventUtils.decrypt(decryptbyte);
					String decryptStr = new String(decryptbyte);
					String resu = decryptStr.substring(10, 11);
					if (resu.equals("1")) {
						if (devicesMap.get(firewallDevice.getDevice_name()) == null) {
							insertDevice(firewallDevice);
						}
					} else {// ????????????
						String[] param = (decryptStr.substring(12)).split("@");
						resultStr += firewallDevice.getDevice_name() + " ???????????????" + param[0] + "\n";
					}
				} else {
					resultStr += firewallDevice.getDevice_name() + "????????????????????????????????????\n";
				}
			}

			if (StringUtils.isNotBlank(resultStr)) {
				commonResult.setErrorMsg(resultStr);
				return commonResult.toString();
			}
		}
		
		FirewallDeviceStatusThread.deviceList = firewallDeviceMapper.getAll();

		// ??????????????????
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, "??????????????????" + deviceNamesStr);

		commonResult.setStatus(true);
		return commonResult.toString();
	}

	private void insertDevice(FirewallDevice device) {
		device.setSubnetmask("255.255.255.0");
		firewallDeviceMapper.insert(device);
		// ??????????????????
		if ("JZ-FWA-DG-3D".equals(device.getModal())) {
			this.insertJZ_FWA_DG_3DEthernet(device.getDevice_name());
		} else if ("JZ-FWA-1U-6D-BETA".equals(device.getModal())) {
			this.insertJZ_FWA_1U_6D_BETAEthernet(device.getDevice_name());
		} else if ("JZ-FWA-1U-6D-4G".equals(device.getModal())) {
			this.insertJZ_FWA_1U_6D_4GEthernet(device.getDevice_name());
		} else if ("JZ-FWA-1U-6D".equals(device.getModal())) {
			this.insertJZ_FWA_1U_6DEthernet(device.getDevice_name());
		} else if ("JZ-FWA-1U-7D".equals(device.getModal())) {
			this.insertJZ_FWA_1U_7DEthernet(device.getDevice_name());
		}
		//?????????????????????
		FirewallAntiAttackDos dos = new FirewallAntiAttackDos();
		dos.setDevice_name(device.getDevice_name());
		firewallAntiAttackMapper.insertDos(dos);
		FirewallAntiAttackScan scan = new FirewallAntiAttackScan();
		scan.setDevice_name(device.getDevice_name());
		firewallAntiAttackMapper.insertScan(scan);
	}

	private void insertJZ_FWA_DG_3DEthernet(String deviceName) {
		for (int i = 1; i <= 3; i++) {
			int mode = FirewallDeviceEthernet.MODE_BIRDGE;
			if (i == 3) {
				mode = 0;
			}
			this.insertEthernet(deviceName, i, mode);
		}
	}
	
	private void insertJZ_FWA_1U_6D_BETAEthernet(String deviceName) {
		for (int i = 1; i <= 6; i++) {
			int mode = FirewallDeviceEthernet.MODE_BIRDGE;
			if (i == 6) {
				mode = 0;
			}
			this.insertEthernet(deviceName, i, mode);
		}
	}

	private void insertJZ_FWA_1U_6DEthernet(String deviceName) {
		for (int i = 2; i <= 6; i++) {
			int mode = FirewallDeviceEthernet.MODE_BIRDGE;
			this.insertEthernet(deviceName, i, mode);
		}
	}
	
	private void insertJZ_FWA_1U_7DEthernet(String deviceName) {
		for (int i = 1; i <= 6; i++) {
			int mode = FirewallDeviceEthernet.MODE_BIRDGE;
			this.insertEthernet(deviceName, i, mode);
		}
	}
	
	private void insertJZ_FWA_1U_6D_4GEthernet(String deviceName) {
		for (int i = 1; i <= 10; i++) {
			int mode = FirewallDeviceEthernet.MODE_BIRDGE;
			if (i == 6) {
				mode = 0;
			}
			this.insertEthernet(deviceName, i, mode);
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param deviceName ?????????
	 * @param number ????????????
	 * @param mode ???????????????
	 */
	private void insertEthernet(String deviceName, int number, int mode) {
		FirewallDeviceEthernet ethernet = new FirewallDeviceEthernet();
		ethernet.setDevice_name(deviceName);
		ethernet.setNumber(number);
		ethernet.setMode(mode);
		firewallDeviceEthernetMapper.insert(ethernet);
	}

	/**
	 * ??????????????????????????????select??????
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONArray getForSelectTag() throws Exception {
		JSONArray array = new JSONArray();
		List<FirewallDevice> devices = firewallDeviceMapper.getAll();
		if (devices == null || devices.size() < 1) {
			return array;
		}
		for (FirewallDevice device : devices) {
			JSONObject object = new JSONObject();
			object.put("deviceName", device.getDevice_name());
			object.put("deviceMark", device.getDevice_mark());
			array.add(object);
		}
		return array;
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getDeviceInfo(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");

		// ??????????????????
		List<FirewallDevice> deviceArray = firewallDeviceMapper.getAll();
		commonResult.put("deviceArray", deviceArray);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * ??????????????????
	 * 
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		String logDescription = "";

		// ??????????????????
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("????????????deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		logDescription += "[????????????" + deviceName + "]\n";

		// ????????????????????????????????????
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("??????????????????????????????");
			return commonResult.toString();
		}

		// ??????action????????????
		if (!paramJson.has("action")) {
			commonResult.setErrorMsg("????????????action");
			return commonResult.toString();
		}
		String action = paramJson.getString("action");

		if ("baseInfo".equals(action)) {
			// ??????????????????
			String deviceMark = "";
			if (paramJson.has("device_mark")) {
				deviceMark = paramJson.getString("device_mark");
			}
			
			//IP
			if (!paramJson.has("ip_address")) {
				commonResult.setErrorMsg("????????????ip_address");
				return commonResult.toString();
			}
			String ip_address = paramJson.getString("ip_address");
			
			//????????????
			String subnetmask = "255.255.255.0";
			if (paramJson.has("subnetmask")) {
				subnetmask = paramJson.getString("subnetmask");
			}
			
			//??????
			String gateway = null;
			if (paramJson.has("gateway")) {
				gateway = paramJson.getString("gateway");
			}
			
			//?????????IP
			String ipBack= device.getIp_address();
			
			device.setDevice_mark(deviceMark);
			device.setIp_address(ip_address);
			device.setSubnetmask(subnetmask);
			device.setGateway(gateway);
			firewallDeviceMapper.update(device);
			
			this.giveIpToDevice(device, ipBack);
			String errorMsg = this.giveIpToDevice(device, device.getIp_address());
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
			
			logDescription += "[???????????????" + deviceMark + "]\n"
						+ "[IP?????????" + ip_address + "]\n"
						+ "[???????????????" + subnetmask + "]\n"
						+ "[?????????" + gateway + "]\n";

		} else if ("mode".equals(action)) {
			// ??????????????????
			int mode = device.getMode();
			if (paramJson.has("mode")) {
				mode = paramJson.getInt("mode");
			}
			device.setMode(mode);
			String modeName = "????????????";
			if (mode == 2) {
				modeName = "????????????";
			} else if (mode == 3) {
				modeName = "????????????";
			} else if (mode == 4) {
				modeName = "????????????";
			}
			
			firewallDeviceMapper.update(device);
			logDescription += "[?????????" + modeName + "]";
			//????????????
			String errorMsg = this.giveModeToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
			
			if (mode == 4) {
				StudyRuleServiceImpl.DEVICE_CACHE_MAP.put(device.getDevice_name(), device);
			}

		} else if ("npt".equals(action)) {
			// ??????npt
			if (!paramJson.has("ntp_ip")) {
				commonResult.setErrorMsg("????????????ntp_ip");
				return commonResult.toString();
			}
			String nptIp = paramJson.getString("ntp_ip");
			
			String ntpPassword = null;
			if (paramJson.has("ntp_password")) {
				ntpPassword = paramJson.getString("ntp_password");
			}
			
			device.setNtp_ip(nptIp);
			device.setNtp_password(ntpPassword);
			device.setNtp_power(1);
			firewallDeviceMapper.update(device);
			
			logDescription += "[??????NTP??????]\n";
			logDescription += "[nptIp???" + nptIp + "]\n";
			logDescription += "[npt?????????" + ntpPassword + "]";
			//????????????
			String errorMsg = this.giveNtpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("clear_ntp".equals(action)) {
			//??????ntp??????
			logDescription += "[??????NTP??????]";
			device.setNtp_power(0);
			firewallDeviceMapper.update(device);
			//????????????
			String errorMsg = this.clearNtpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
		} else if ("syncTime".equals(action)) {
			// ??????????????????
			if (!paramJson.has("syncTime")) {
				commonResult.setErrorMsg("????????????syncTime");
				return commonResult.toString();
			}
			String syncTime = paramJson.getString("syncTime");
			logDescription += "[?????????????????????"+syncTime+"]";
			//????????????
			String errorMsg = this.giveSyncTimeToDevice(device, syncTime);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("snmp".equals(action)) {
			// ??????snmp
			//snmpip
			if (!paramJson.has("snmp_ip")) {
				commonResult.setErrorMsg("????????????snmp_ip");
				return commonResult.toString();
			}
			String snmpIp = paramJson.getString("snmp_ip");
			
			//snmp??????
			String snmpPort = "161";
			if (paramJson.has("snmp_port")) {
				snmpPort = paramJson.getString("snmp_port");
			}
			
			//snmp version
			if (!paramJson.has("snmp_version")) {
				commonResult.setErrorMsg("????????????snmp_version");
				return commonResult.toString();
			}
			String snmpVersion = paramJson.getString("snmp_version");
			
			device.setSnmp_port(snmpPort);
			device.setSnmp_ip(snmpIp);
			device.setSnmp_version(snmpVersion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
			device.setSnmp_power(1);
			logDescription += "[??????SNMP??????]\n"
					+ "[snmpIp???" + snmpIp + "]\n"
					+ "[snmpVersion???" + snmpVersion + "]\n"
					+ "[snmpPort???" + snmpPort + "]";
			firewallDeviceMapper.update(device);
			//????????????
			String errorMsg = this.giveSnmpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("clear_snmp".equals(action)) {
			//??????snmp??????
			logDescription += "[??????SNMP??????]";
			device.setSnmp_power(0);
			firewallDeviceMapper.update(device);
			//????????????
			String errorMsg = this.clearSnmpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
			
		} else if ("syslog".equals(action)) {
			// ??????syslog
			if (!paramJson.has("syslog_ip")) {
				commonResult.setErrorMsg("syslog_ip");
				return commonResult.toString();
			}
			String syslogIp = paramJson.getString("syslog_ip");
			
			String syslogProtocol = "udp";
			if (paramJson.has("syslog_protocol")) {
				syslogProtocol = paramJson.getString("syslog_protocol");
			}
			
			String syslogPort = "514";
			if (paramJson.has("syslog_port")) {
				syslogPort = paramJson.getString("syslog_port");
			}
			
			device.setSyslog_ip(syslogIp);
			device.setSyslog_protocol(syslogProtocol);
			device.setSyslog_port(syslogPort);
			device.setSyslog_power(1);
			
			firewallDeviceMapper.update(device);
			
			logDescription += "[??????syslog??????]\n";
			logDescription += "[syslogIp???" + syslogIp + "]\n";
			logDescription += "[syslog?????????" + syslogProtocol + "]\n";
			logDescription += "[syslog?????????" + syslogPort + "]";
			
			//????????????
			String errorMsg = this.giveSyslogToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("clear_syslog".equals(action)) {
			//??????syslog
			logDescription += "[??????syslog??????]";
			device.setSyslog_power(0);
			firewallDeviceMapper.update(device);
			//????????????
			String errorMsg = this.clearSyslogToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
		} else if ("sync".equals(action)) {
			// ????????????????????????
			logDescription += "[????????????????????????]";
			// TODO ??????????????????
		} else if ("restart".equals(action)) {
			// ????????????
			logDescription += "[????????????]";
			//????????????
			String errorMsg = this.restartDevice(device);
			if (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
			
		} else if ("defaule_log".equals(action)) {
			// ??????????????????
			if (!paramJson.has("default_rule_action")) {
				commonResult.setErrorMsg("???????????????default_rule_action");
				return commonResult.toString();
			}
			int default_rule_action = paramJson.getInt("default_rule_action");
			
			if (!paramJson.has("default_rule_log")) {
				commonResult.setErrorMsg("???????????????default_rule_log");
				return commonResult.toString();
			}
			int default_rule_log = paramJson.getInt("default_rule_log");
			device.setDefault_rule_action(default_rule_action);
			device.setDefault_rule_log(default_rule_log);
			logDescription += "[?????????????????????" + (default_rule_action == 1?"??????":(default_rule_action == 2?"??????":"??????")) + "]";
			logDescription += "[?????????????????????" + (default_rule_log == 1?"??????":(default_rule_log == 2?"??????":"??????")) + "]";
			firewallDeviceMapper.update(device);
			//????????????
			String errorMsg = this.giveDefaultLogConfigToDevice(device, 
					default_rule_action,
					default_rule_log);
			if (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
		} else if ("remove".equals(action)) {
			// ????????????
			String errorMsg = this.removeDevice(device);
			if (StringUtils.isNotBlank(errorMsg)) {
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
		}

		
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDescription);
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * ????????????
	 * @param device
	 * @return
	 * @throws Exception 
	 */
	private String removeDevice(FirewallDevice device) throws Exception {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("port", "6860");
		maps.put("mip", device.getIp_address());
		String platId = systemSettingService.getByName(SystemSetting.PLAT_ID).getValue();
		if (StringUtils.isBlank(platId)) {
			return "?????????????????????????????????";
		}
		byte[] decryptbyte = TCPSocketUtil.ActionWrite("jiuzhoucmp0x98", platId, 1, maps);// ???????????????
		if (decryptbyte != null) {
			decryptbyte = EventUtils.decrypt(decryptbyte);
			String decryptStr = new String(decryptbyte);
			String resu = decryptStr.substring(10, 11);
			if (resu.equals("1")) {
				//????????????
				deleteCache(CACHE_AUDIT_LOG_TABLE + "_" + device.getDevice_name());
				//??????????????????
				firewallDeviceMapper.deleteByDeviceName(device.getDevice_name());
				//????????????
				firewallDeviceEthernetMapper.deleteByDeviceName(device.getDevice_name());
				//??????????????????
				firewallFlowTotalMapper.deleteByDeviceName(device.getDevice_name());
				//??????mac
				firewallMacMapper.deleteByDeviceName(device.getDevice_name());
				firewallMacMapper.deleteScanMacByDeviceName(device.getDevice_name());
				//??????nat
				firewallNatMapper.deleteByDeviceName(device.getDevice_name());
				//????????????route
				firewallRouteMapper.deleteByDeviceName(device.getDevice_name());
				//???????????????route
				firewallCustomRouteMapper.deleteByDeviceName(device.getDevice_name());
				//????????????
				firewallStrategyMapper.deleteCustomByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteModbusTcpByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteOpcClassicTcpByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteIEC104ByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteByDeviceName(device.getDevice_name());
				//??????????????????
				firewallSessionMapper.deleteByDeviceName(device.getDevice_name());
				//????????????????????????
				firewallSessionMapper.deleteSettingByDeviceName(device.getDevice_name());
				//????????????????????????
				firewallSessionKeepMapper.deleteByDeviceName(device.getDevice_name());
				//??????????????????
				firewallSessionControlMapper.deleteByDeviceName(device.getDevice_name());
				//???????????????
				firewallStrategyGroupMapper.deleteByDeviceName(device.getDevice_name());
				//???????????????dos
				firewallAntiAttackMapper.deleteDosByDeviceName(device.getDevice_name());
				//???????????????scan
				firewallAntiAttackMapper.deleteScanByDeviceName(device.getDevice_name());
				//??????????????????
				studyRuleItemMapper.deleteByDeviceName(device.getDevice_name());
				
			} else {// ????????????
				String[] param = (decryptStr.substring(12)).split("@");
				return "???????????????" + param[0] + "\n";
			}
		} else {
			return "????????????????????????????????????\n";
		}
		
		FirewallDeviceStatusThread.deviceList = firewallDeviceMapper.getAll();
		
		return null;
	}
	
	/**
	 * ?????????????????????log
	 * @param device
	 * @param default_rule_action
	 * @param default_rule_log
	 * @return
	 */
	private String giveDefaultLogConfigToDevice(FirewallDevice device, 
			int default_rule_action, 
			int default_rule_log) {
		
		String instruction = "0x10 0x105 ";
		if (default_rule_action == FirewallStrategy.RULE_ACTION_INTERCEPT) {
			instruction += "0 ";
		} else {
			instruction += "1 ";
		}
		
		if (default_rule_log == FirewallStrategy.LOG_POWER_OFF) {
			instruction += "0;";
		} else {
			instruction += "1;";
		}
		
		Map<String, String> maps = TCPSocketUtil.getNetworkSocketConfig();
		maps.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction, maps);
	}
	
	/**
	 * ???????????????ip
	 * @param device
	 * @return
	 */
	private String giveIpToDevice(FirewallDevice device, String deviceIp) {
		//????????????
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x204 ");
		instruction.append(device.getIp_address()).append(" ");
		if (StringUtils.isNotBlank(device.getSubnetmask())) {
			instruction.append(device.getSubnetmask()).append(" ");
		} else {
			instruction.append("255.255.255.0 ");
		}
		if (StringUtils.isNotBlank(device.getGateway())) {
			instruction.append(device.getGateway());
		}
		instruction.append(";");
		
		
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", deviceIp);
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig, 2000);
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	private String giveModeToDevice(FirewallDevice device) {
		//????????????
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x101 ");
		if (device.getMode() == FirewallDevice.MODE_PASSIVE) {
			instruction.append("passive;");
		} else  if (device.getMode() == FirewallDevice.MODE_TEST) {
			instruction.append("test;");
		} else if (device.getMode() == FirewallDevice.MODE_OPERATIONAL) {
			instruction.append("operational;");
		} else if (device.getMode() == FirewallDevice.MODE_STUDY) {
			instruction.append("study;");
		}else {
			return "???????????????????????????";
		}
		
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * ??????syslog??????
	 * @param device
	 * @return
	 */
	private String giveSyslogToDevice (FirewallDevice device) {
		//????????????
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x103 set ")
		.append(device.getSyslog_protocol()).append(" ")
		.append(device.getSyslog_ip()).append(" ")
		.append(device.getSyslog_port()).append(";");
		
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * ??????syslog??????
	 * @param device
	 * @return
	 */
	private String clearSyslogToDevice (FirewallDevice device) {
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x103 stop;", socketConfig);
	}
	
	/**
	 * ??????snmp??????
	 * @param device
	 * @return
	 */
	private String giveSnmpToDevice(FirewallDevice device) {
		//????????????
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x104 set ")
		.append(device.getSnmp_ip()).append(" ")
		.append(device.getSnmp_port()).append(" ");
		if (device.getSnmp_version().indexOf("1") >= 0) {
			instruction.append("v1v2 ");
		} else {
			instruction.append("null ");
		}
		if (device.getSnmp_version().indexOf("3") >= 0) {
			instruction.append("v3");
		} else {
			instruction.append("null");
		}
		instruction.append(";");
		
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * ????????????snmp??????
	 * @param device
	 * @return
	 */
	private String clearSnmpToDevice(FirewallDevice device) {
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x104 stop;", socketConfig);
	}
	
	/**
	 * ??????ntp????????????
	 * @param device
	 * @return
	 */
	private String giveNtpToDevice(FirewallDevice device) {
		//????????????
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x102 ntp ")
		.append(device.getNtp_ip());
		if (StringUtils.isNotBlank(device.getNtp_password())) {
			instruction.append(" ").append(device.getNtp_password());
		}
		instruction.append(";");
		
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * ??????ntp????????????
	 * @param device
	 * @return
	 */
	private String clearNtpToDevice(FirewallDevice device) {
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x102 stop;", socketConfig);
	}
	
	/**
	 * ????????????????????????
	 * @param device
	 * @return
	 */
	private String giveSyncTimeToDevice(FirewallDevice device, String time) {
		//????????????
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x102 manu ")
		.append(time).append(";");
		
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * ????????????
	 * @param device
	 * @return
	 */
	private String restartDevice(FirewallDevice device) {
		//socket??????IP??????
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//????????????
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x106 reboot;", socketConfig);
	}

}
