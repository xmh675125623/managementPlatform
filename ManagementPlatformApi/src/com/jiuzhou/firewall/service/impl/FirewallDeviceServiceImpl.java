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
 * @version 创建时间：2019年1月7日 下午2:08:30 类说明
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
	 * 防火墙设备探索
	 * 
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String sniffDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {

		CommonResult commonResult = new CommonResult(false, "");

		// 获取ip地址头(例：192.168.0.)
		if (!paramJson.has("ipss")) {
			commonResult.setErrorMsg("缺少参数ipss");
			return commonResult.toString();
		}
		String ipss = paramJson.getString("ipss");

		// 获取起始结束ip地址尾(例：100)
		if (!paramJson.has("ips")) {
			commonResult.setErrorMsg("缺少参数ips");
			return commonResult.toString();
		}
		int ips = paramJson.getInt("ips");

		if (!paramJson.has("ipf")) {
			commonResult.setErrorMsg("缺少参数ipf");
			return commonResult.toString();
		}
		int ipf = paramJson.getInt("ipf");

		Map<String, String> maps = new HashMap<String, String>();
		maps.put("port", "6860");// 端口写死

		List<FirewallDevice> devices = new ArrayList<>();

		for (int i = 0; i < (ipf - ips + 1); i++) {
			String ip = ipss + (ips + i);
			maps.put("mip", ip);

			byte[] pjbuffer = FirewallDeviceUtils.predive("0x90", maps);
			System.out.println(new String(pjbuffer));
			byte[] recivebyte = TCPSocketUtil.recive(pjbuffer, maps);// 接受返回值
			if (recivebyte == null) {
				continue;// 探测失败
			} else if (new String(recivebyte).equals("ss")) {
				continue;// 探测失败
			} else {
				byte[] decryptbyte = EventUtils.decrypt(recivebyte);// 解密返回值

				if (new String(decryptbyte) != null && new String(decryptbyte).length() > 16) {
					String resu = new String(decryptbyte).substring(10, 11);
					if (resu.equals("1")) {
						String[] param = (new String(decryptbyte).substring(12)).split("@");
						String[] param1 = param[0].split(" ");
						FirewallDevice device = new FirewallDevice();
						device.setDevice_name(param1[0]);// mac地址存放到 设备名称
						device.setIp_address(ip);
						device.setEdition(param1[1]);
						device.setModal(param1[2]);
						device.setInsert_time(new Date());
						devices.add(device);
						// TODO 获取网口信息
					} else {
						continue;// 探测失败
					}

				} else {
					continue;// 探测失败
				}
			}
		}

		firewallDeviceTempMapper.deleteByAid(paramJson.getInt("aid"));
		if (devices != null && devices.size() > 0) {
			firewallDeviceTempMapper.insertBatch(devices, paramJson.getInt("aid"));
		}

		// 记录操作日志
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION,
				"[起始IP:" + ipss + ips + "]\n[结束IP:" + ipss + ipf + "]");

		commonResult.put("devices", devices);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 将探索到的设备添加到设备管理
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
			commonResult.setErrorMsg("请选择要添加的设备");
			return commonResult.toString();
		}
		String deviceNamesStr = paramJson.getString("deviceNames");
		String[] deviceNames = deviceNamesStr.split(",");
		List<String> deviceNameList = new ArrayList<>(Arrays.asList(deviceNames));

		// 在探索临时表里查找要添加的设备
		List<FirewallDevice> tempDevices = firewallDeviceTempMapper.getByDeviceNames(aid, deviceNameList);
		if (tempDevices == null || tempDevices.size() < 1) {
			commonResult.setErrorMsg("未找到要添加的设备");
			return commonResult.toString();
		}

		// 查找正式设备表里已存在的同名设备
		List<FirewallDevice> devices = firewallDeviceMapper.getByDeviceNames(deviceNameList);
		
		String platId = systemSettingService.getByName(SystemSetting.PLAT_ID).getValue();
		if (StringUtils.isBlank(platId)) {
			commonResult.setErrorMsg("请在先在系统设置中设置平台标识");
			return commonResult.toString();
		}

		// 如果没有同名设备则全部插入正式设备表
		if (devices == null || devices.size() < 1) {
			String resultStr = "";// 循环管理，用于收集管理失败原因
			for (FirewallDevice firewallDevice : tempDevices) {
				Map<String, String> maps = new HashMap<String, String>();
				maps.put("port", "6860");
				maps.put("mip", firewallDevice.getIp_address());
				byte[] decryptbyte = TCPSocketUtil.ActionWrite("jiuzhoucmp0x97", platId, 1, maps);// 接受返回值
				if (decryptbyte != null) {
					decryptbyte = EventUtils.decrypt(decryptbyte);
					String decryptStr = new String(decryptbyte);
					String resu = decryptStr.substring(10, 11);
					if (resu.equals("1")) {
						insertDevice(firewallDevice);
					} else {// 管理失败
						String[] param = (decryptStr.substring(12)).split("@");
						resultStr += firewallDevice.getDevice_name() + "管理失败：" + param[0] + "\n";
					}
				} else {
					resultStr += firewallDevice.getDevice_name() + "管理失败：设备未返回数据\n";
				}
			}

			if (StringUtils.isNotBlank(resultStr)) {
				commonResult.setErrorMsg(resultStr);
				return commonResult.toString();
			}

			commonResult.setStatus(true);
			return commonResult.toString();
		}

		// 如果存在同名设备则只插入不同名设备
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
			String resultStr = "";// 循环管理，用于收集管理失败原因
			for (FirewallDevice firewallDevice : tempDevices) {
				Map<String, String> maps = new HashMap<String, String>();
				maps.put("port", "6860");
				maps.put("mip", firewallDevice.getIp_address());
				byte[] decryptbyte = TCPSocketUtil.ActionWrite("jiuzhoucmp0x97", platId, 1, maps);// 接受返回值
				if (decryptbyte != null) {
					decryptbyte = EventUtils.decrypt(decryptbyte);
					String decryptStr = new String(decryptbyte);
					String resu = decryptStr.substring(10, 11);
					if (resu.equals("1")) {
						if (devicesMap.get(firewallDevice.getDevice_name()) == null) {
							insertDevice(firewallDevice);
						}
					} else {// 管理失败
						String[] param = (decryptStr.substring(12)).split("@");
						resultStr += firewallDevice.getDevice_name() + " 管理失败：" + param[0] + "\n";
					}
				} else {
					resultStr += firewallDevice.getDevice_name() + "管理失败：设备未返回数据\n";
				}
			}

			if (StringUtils.isNotBlank(resultStr)) {
				commonResult.setErrorMsg(resultStr);
				return commonResult.toString();
			}
		}
		
		FirewallDeviceStatusThread.deviceList = firewallDeviceMapper.getAll();

		// 记录操作日志
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, "添加设备名：" + deviceNamesStr);

		commonResult.setStatus(true);
		return commonResult.toString();
	}

	private void insertDevice(FirewallDevice device) {
		device.setSubnetmask("255.255.255.0");
		firewallDeviceMapper.insert(device);
		// 插入网口信息
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
		//插入抗攻击信息
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
	 * 插入网口信息
	 * 
	 * @param deviceName 设备名
	 * @param number 网口序号
	 * @param mode 网口模式的
	 */
	private void insertEthernet(String deviceName, int number, int mode) {
		FirewallDeviceEthernet ethernet = new FirewallDeviceEthernet();
		ethernet.setDevice_name(deviceName);
		ethernet.setNumber(number);
		ethernet.setMode(mode);
		firewallDeviceEthernetMapper.insert(ethernet);
	}

	/**
	 * 获取设备列表用于页面select标签
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
	 * 根据设备名获取设备信息
	 * 
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getDeviceInfo(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");

		// 获取设备列表
		List<FirewallDevice> deviceArray = firewallDeviceMapper.getAll();
		commonResult.put("deviceArray", deviceArray);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 更新设备信息
	 * 
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		String logDescription = "";

		// 获取设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		logDescription += "[设备名：" + deviceName + "]\n";

		// 通过设备名称获取设备信息
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在或已被移除");
			return commonResult.toString();
		}

		// 获取action更新动作
		if (!paramJson.has("action")) {
			commonResult.setErrorMsg("缺少参数action");
			return commonResult.toString();
		}
		String action = paramJson.getString("action");

		if ("baseInfo".equals(action)) {
			// 变更基础信息
			String deviceMark = "";
			if (paramJson.has("device_mark")) {
				deviceMark = paramJson.getString("device_mark");
			}
			
			//IP
			if (!paramJson.has("ip_address")) {
				commonResult.setErrorMsg("缺少参数ip_address");
				return commonResult.toString();
			}
			String ip_address = paramJson.getString("ip_address");
			
			//子网掩码
			String subnetmask = "255.255.255.0";
			if (paramJson.has("subnetmask")) {
				subnetmask = paramJson.getString("subnetmask");
			}
			
			//网关
			String gateway = null;
			if (paramJson.has("gateway")) {
				gateway = paramJson.getString("gateway");
			}
			
			//原来的IP
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
			
			logDescription += "[设备别名：" + deviceMark + "]\n"
						+ "[IP地址：" + ip_address + "]\n"
						+ "[子网掩码：" + subnetmask + "]\n"
						+ "[网关：" + gateway + "]\n";

		} else if ("mode".equals(action)) {
			// 变更设备模式
			int mode = device.getMode();
			if (paramJson.has("mode")) {
				mode = paramJson.getInt("mode");
			}
			device.setMode(mode);
			String modeName = "初始模式";
			if (mode == 2) {
				modeName = "测试模式";
			} else if (mode == 3) {
				modeName = "运行模式";
			} else if (mode == 4) {
				modeName = "学习模式";
			}
			
			firewallDeviceMapper.update(device);
			logDescription += "[模式：" + modeName + "]";
			//设备下发
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
			// 变更npt
			if (!paramJson.has("ntp_ip")) {
				commonResult.setErrorMsg("缺少参数ntp_ip");
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
			
			logDescription += "[启动NTP服务]\n";
			logDescription += "[nptIp：" + nptIp + "]\n";
			logDescription += "[npt密钥：" + ntpPassword + "]";
			//设备下发
			String errorMsg = this.giveNtpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("clear_ntp".equals(action)) {
			//清除ntp设置
			logDescription += "[停止NTP服务]";
			device.setNtp_power(0);
			firewallDeviceMapper.update(device);
			//设备下发
			String errorMsg = this.clearNtpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
		} else if ("syncTime".equals(action)) {
			// 手动同步时间
			if (!paramJson.has("syncTime")) {
				commonResult.setErrorMsg("缺少参数syncTime");
				return commonResult.toString();
			}
			String syncTime = paramJson.getString("syncTime");
			logDescription += "[手动同步时间："+syncTime+"]";
			//设备下发
			String errorMsg = this.giveSyncTimeToDevice(device, syncTime);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("snmp".equals(action)) {
			// 变更snmp
			//snmpip
			if (!paramJson.has("snmp_ip")) {
				commonResult.setErrorMsg("缺少参数snmp_ip");
				return commonResult.toString();
			}
			String snmpIp = paramJson.getString("snmp_ip");
			
			//snmp端口
			String snmpPort = "161";
			if (paramJson.has("snmp_port")) {
				snmpPort = paramJson.getString("snmp_port");
			}
			
			//snmp version
			if (!paramJson.has("snmp_version")) {
				commonResult.setErrorMsg("缺少参数snmp_version");
				return commonResult.toString();
			}
			String snmpVersion = paramJson.getString("snmp_version");
			
			device.setSnmp_port(snmpPort);
			device.setSnmp_ip(snmpIp);
			device.setSnmp_version(snmpVersion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
			device.setSnmp_power(1);
			logDescription += "[启动SNMP服务]\n"
					+ "[snmpIp：" + snmpIp + "]\n"
					+ "[snmpVersion：" + snmpVersion + "]\n"
					+ "[snmpPort：" + snmpPort + "]";
			firewallDeviceMapper.update(device);
			//设备下发
			String errorMsg = this.giveSnmpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("clear_snmp".equals(action)) {
			//清除snmp设置
			logDescription += "[停止SNMP服务]";
			device.setSnmp_power(0);
			firewallDeviceMapper.update(device);
			//设备下发
			String errorMsg = this.clearSnmpToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
			
		} else if ("syslog".equals(action)) {
			// 变更syslog
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
			
			logDescription += "[启动syslog服务]\n";
			logDescription += "[syslogIp：" + syslogIp + "]\n";
			logDescription += "[syslog协议：" + syslogProtocol + "]\n";
			logDescription += "[syslog端口：" + syslogPort + "]";
			
			//设备下发
			String errorMsg = this.giveSyslogToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}

		} else if ("clear_syslog".equals(action)) {
			//清空syslog
			logDescription += "[停止syslog服务]";
			device.setSyslog_power(0);
			firewallDeviceMapper.update(device);
			//设备下发
			String errorMsg = this.clearSyslogToDevice(device);
			if  (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
		} else if ("sync".equals(action)) {
			// 同步所有配置信息
			logDescription += "[同步所有配置信息]";
			// TODO 同步下发操作
		} else if ("restart".equals(action)) {
			// 重启设备
			logDescription += "[重启设备]";
			//设备下发
			String errorMsg = this.restartDevice(device);
			if (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
			
		} else if ("defaule_log".equals(action)) {
			// 默认规则配置
			if (!paramJson.has("default_rule_action")) {
				commonResult.setErrorMsg("缺少参数：default_rule_action");
				return commonResult.toString();
			}
			int default_rule_action = paramJson.getInt("default_rule_action");
			
			if (!paramJson.has("default_rule_log")) {
				commonResult.setErrorMsg("缺少参数：default_rule_log");
				return commonResult.toString();
			}
			int default_rule_log = paramJson.getInt("default_rule_log");
			device.setDefault_rule_action(default_rule_action);
			device.setDefault_rule_log(default_rule_log);
			logDescription += "[默认规则行为：" + (default_rule_action == 1?"放行":(default_rule_action == 2?"拦截":"未知")) + "]";
			logDescription += "[默认日志记录：" + (default_rule_log == 1?"开启":(default_rule_log == 2?"关闭":"未知")) + "]";
			firewallDeviceMapper.update(device);
			//设备下发
			String errorMsg = this.giveDefaultLogConfigToDevice(device, 
					default_rule_action,
					default_rule_log);
			if (StringUtils.isNotBlank(errorMsg)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonResult.setErrorMsg(errorMsg);
				return commonResult.toString();
			}
		} else if ("remove".equals(action)) {
			// 移除设备
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
	 * 移除设备
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
			return "平台标识为空，移除失败";
		}
		byte[] decryptbyte = TCPSocketUtil.ActionWrite("jiuzhoucmp0x98", platId, 1, maps);// 接受返回值
		if (decryptbyte != null) {
			decryptbyte = EventUtils.decrypt(decryptbyte);
			String decryptStr = new String(decryptbyte);
			String resu = decryptStr.substring(10, 11);
			if (resu.equals("1")) {
				//删除缓存
				deleteCache(CACHE_AUDIT_LOG_TABLE + "_" + device.getDevice_name());
				//删除设备信息
				firewallDeviceMapper.deleteByDeviceName(device.getDevice_name());
				//删除网口
				firewallDeviceEthernetMapper.deleteByDeviceName(device.getDevice_name());
				//删除流量统计
				firewallFlowTotalMapper.deleteByDeviceName(device.getDevice_name());
				//删除mac
				firewallMacMapper.deleteByDeviceName(device.getDevice_name());
				firewallMacMapper.deleteScanMacByDeviceName(device.getDevice_name());
				//删除nat
				firewallNatMapper.deleteByDeviceName(device.getDevice_name());
				//删除静态route
				firewallRouteMapper.deleteByDeviceName(device.getDevice_name());
				//删除自定义route
				firewallCustomRouteMapper.deleteByDeviceName(device.getDevice_name());
				//删除策略
				firewallStrategyMapper.deleteCustomByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteModbusTcpByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteOpcClassicTcpByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteIEC104ByDeviceName(device.getDevice_name());
				firewallStrategyMapper.deleteByDeviceName(device.getDevice_name());
				//删除会话管理
				firewallSessionMapper.deleteByDeviceName(device.getDevice_name());
				//删除会话管理设置
				firewallSessionMapper.deleteSettingByDeviceName(device.getDevice_name());
				//删除会话维持时间
				firewallSessionKeepMapper.deleteByDeviceName(device.getDevice_name());
				//删除会话控制
				firewallSessionControlMapper.deleteByDeviceName(device.getDevice_name());
				//删除策略组
				firewallStrategyGroupMapper.deleteByDeviceName(device.getDevice_name());
				//删除抗攻击dos
				firewallAntiAttackMapper.deleteDosByDeviceName(device.getDevice_name());
				//删除空攻击scan
				firewallAntiAttackMapper.deleteScanByDeviceName(device.getDevice_name());
				//删除学习规则
				studyRuleItemMapper.deleteByDeviceName(device.getDevice_name());
				
			} else {// 管理失败
				String[] param = (decryptStr.substring(12)).split("@");
				return "移除失败：" + param[0] + "\n";
			}
		} else {
			return "移除失败：设备未返回数据\n";
		}
		
		FirewallDeviceStatusThread.deviceList = firewallDeviceMapper.getAll();
		
		return null;
	}
	
	/**
	 * 下发默认规则跟log
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
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction, maps);
	}
	
	/**
	 * 下发管理口ip
	 * @param device
	 * @return
	 */
	private String giveIpToDevice(FirewallDevice device, String deviceIp) {
		//组织指令
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
		
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", deviceIp);
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig, 2000);
	}
	
	/**
	 * 下发设备模式
	 * @return
	 */
	private String giveModeToDevice(FirewallDevice device) {
		//组织指令
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
			return "选择的设备模式错误";
		}
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * 下发syslog配置
	 * @param device
	 * @return
	 */
	private String giveSyslogToDevice (FirewallDevice device) {
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x103 set ")
		.append(device.getSyslog_protocol()).append(" ")
		.append(device.getSyslog_ip()).append(" ")
		.append(device.getSyslog_port()).append(";");
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * 删除syslog配置
	 * @param device
	 * @return
	 */
	private String clearSyslogToDevice (FirewallDevice device) {
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x103 stop;", socketConfig);
	}
	
	/**
	 * 下发snmp配置
	 * @param device
	 * @return
	 */
	private String giveSnmpToDevice(FirewallDevice device) {
		//组织指令
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
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * 下发删除snmp配置
	 * @param device
	 * @return
	 */
	private String clearSnmpToDevice(FirewallDevice device) {
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x104 stop;", socketConfig);
	}
	
	/**
	 * 下发ntp时间同步
	 * @param device
	 * @return
	 */
	private String giveNtpToDevice(FirewallDevice device) {
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x102 ntp ")
		.append(device.getNtp_ip());
		if (StringUtils.isNotBlank(device.getNtp_password())) {
			instruction.append(" ").append(device.getNtp_password());
		}
		instruction.append(";");
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * 停止ntp时间同步
	 * @param device
	 * @return
	 */
	private String clearNtpToDevice(FirewallDevice device) {
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x102 stop;", socketConfig);
	}
	
	/**
	 * 下发手动同步时间
	 * @param device
	 * @return
	 */
	private String giveSyncTimeToDevice(FirewallDevice device, String time) {
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x102 manu ")
		.append(time).append(";");
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}
	
	/**
	 * 重启设备
	 * @param device
	 * @return
	 */
	private String restartDevice(FirewallDevice device) {
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction("0x10 0x106 reboot;", socketConfig);
	}

}
