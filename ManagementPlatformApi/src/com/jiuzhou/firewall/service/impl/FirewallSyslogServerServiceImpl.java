package com.jiuzhou.firewall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallSyslogServer;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallSyslogServerMapper;
import com.jiuzhou.firewall.service.FirewallDeviceEthernetService;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallSyslogServerService;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2020年8月19日 下午4:21:36
* 类说明
*/
@Service("FirewallSyslogServerService")
public class FirewallSyslogServerServiceImpl implements FirewallSyslogServerService {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallSyslogServerMapper firewallSyslogServerMapper;
	
	@Autowired
	private FirewallDeviceEthernetService firewallDeviceEthernetService;

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
		
		//获取Server列表
		List<FirewallSyslogServer> servers  = 
				firewallSyslogServerMapper.getByDeviceName(device.getDevice_name());
		if (servers == null) {
			servers = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("servers", servers);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String add(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//syslog服务器ip
		if (!paramJson.has("serverIp")) {
			commonResult.setErrorMsg("缺少参数serverIp");
			return commonResult.toString();
		}
		String serverIp = paramJson.getString("serverIp");
		
		//协议类型
		if (!paramJson.has("protocol")) {
			commonResult.setErrorMsg("缺少参数protocol");
			return commonResult.toString();
		}
		String protocol = paramJson.getString("protocol");
		
		//端口
		if (!paramJson.has("port")) {
			commonResult.setErrorMsg("缺少参数port");
			return commonResult.toString();
		}
		String port = paramJson.getString("port");
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		FirewallSyslogServer server = new FirewallSyslogServer();
		server.setDevice_name(deviceName);
		server.setServer_ip(serverIp);
		server.setProtocol(protocol);
		server.setPort(port);
		server.setAdd_time(new Date());
		server.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallSyslogServerMapper.insert(server);
		
		//操作日志描述
		String logDesc = "[设备："+deviceName+"]\n"
						+ "[服务器IP："+ serverIp +"]\n"
						+ "[协议类型："+protocol+"]\n"
						+ "[端口："+port+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveSyslogToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String update(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//记录id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//syslog服务器ip
		if (!paramJson.has("serverIp")) {
			commonResult.setErrorMsg("缺少参数serverIp");
			return commonResult.toString();
		}
		String serverIp = paramJson.getString("serverIp");
		
		//协议类型
		if (!paramJson.has("protocol")) {
			commonResult.setErrorMsg("缺少参数protocol");
			return commonResult.toString();
		}
		String protocol = paramJson.getString("protocol");
		
		//端口
		if (!paramJson.has("port")) {
			commonResult.setErrorMsg("缺少参数port");
			return commonResult.toString();
		}
		String port = paramJson.getString("port");
		
		//获取server信息
		FirewallSyslogServer server = firewallSyslogServerMapper.getById(id);
		if (server == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		//操作日志描述
		String logDesc = "编辑前：\n"
				+ "[服务器IP：" + server.getServer_ip() + "]\n"
				+ "[协议类型：" + server.getProtocol() + "]\n"
				+ "[端口：" + server.getPort() + "]\n";
		
		server.setServer_ip(serverIp);
		server.setProtocol(protocol);
		server.setPort(port);
		server.setUpdate_time(new Date());
		server.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallSyslogServerMapper.update(server);
		
		FirewallDevice device = firewallDeviceMapper.getByName(server.getDevice_name());
		//操作日志描述
		logDesc += "编辑后：\n"
				+ "[服务器IP："+ serverIp +"]\n"
				+ "[协议类型："+protocol+"]\n"
				+ "[端口："+port+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveSyslogToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String delete(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//获取server信息
		FirewallSyslogServer server = firewallSyslogServerMapper.getById(id);
		if (server == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		server.setDelete_flag(1);
		firewallSyslogServerMapper.update(server);
		
		FirewallDevice device = firewallDeviceMapper.getByName(server.getDevice_name());
		//操作日志描述
		String logDesc = 
			"[设备："+server.getDevice_name()+"]\n"
			+ "[服务器IP：" + server.getServer_ip() + "]\n"
			+ "[协议类型：" + server.getProtocol() + "]\n"
			+ "[端口：" + server.getPort() + "]\n";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveSyslogToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String giveSyslogToDevice(FirewallDevice device) throws Exception {
		
		List<FirewallSyslogServer> servers = 
				firewallSyslogServerMapper.getByDeviceName(device.getDevice_name());
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x10 0x103 start ")
		.append(device.getSyslog_protocol()).append(" ")
		.append(device.getSyslog_ip()).append(" ")
		.append(device.getSyslog_port()).append("\n");
		
		if(servers != null && servers.size() > 0) {
			for (int i = 0; i < servers.size(); i++) {
				FirewallSyslogServer server = servers.get(i);
				//ip
				instruction.append(server.getServer_ip()).append(" ");
				//协议类型
				instruction.append(server.getProtocol().toLowerCase()).append(" ");
				//端口
				instruction.append(server.getPort());
				
				instruction.append("\n");
				
			}
		}
		
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
	}

}
