package com.jiuzhou.firewall.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallDeviceEthernet;
import com.jiuzhou.firewall.mapper.FirewallDeviceEthernetMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.service.FirewallDeviceEthernetService;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallStrategyService;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月17日 上午11:10:22
* 类说明
*/
@Service("FirewallDeviceEthernetService")
public class FirewallDeviceEthernetServiceImpl implements FirewallDeviceEthernetService {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallDeviceEthernetMapper firewallDeviceEthernetMapper;

	@Autowired
	FirewallStrategyService firewallStrategyService;
	
	/**
	 * 根据设备标识获取网口列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
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
		
		//获取网口列表
		List<FirewallDeviceEthernet> ethernets = 
				firewallDeviceEthernetMapper.getByDeviceName(device.getDevice_name());
		if (ethernets == null) {
			ethernets = new ArrayList<>();
		} else {
			//移除管理口
			for (FirewallDeviceEthernet ethernet : ethernets) {
				if (ethernet.getMode() == 0) {
					ethernets.remove(ethernet);
					break;
				}
			}
		}
		
		commonResult.put("device", device);
		commonResult.put("ethernets", ethernets);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 获取网口状态列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String listForStatus(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		
		if (device == null) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		
		//获取网口列表
		List<FirewallDeviceEthernet> ethernets = 
				firewallDeviceEthernetMapper.getByDeviceName(device.getDevice_name());
		if (ethernets == null) {
			ethernets = new ArrayList<>();
		} else {
			//移除管理口
			for (FirewallDeviceEthernet ethernet : ethernets) {
				if (ethernet.getMode() == 0) {
					ethernets.remove(ethernet);
					break;
				}
			}
		}
		
		//TODO 获取设备网口状态
		
		commonResult.put("device", device);
		commonResult.put("ethernets", ethernets);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 变更网口mode
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateMode(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
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
		
		//获取网口列表
		List<FirewallDeviceEthernet> ethernets = 
				firewallDeviceEthernetMapper.getByDeviceName(device.getDevice_name());
		if (ethernets == null) {
			ethernets = new ArrayList<>();
		}
		
		//操作日志
		String logDesc = "[设备名称："+deviceName+"]\n";
		
		//循环update网口模式
		for (FirewallDeviceEthernet ethernet : ethernets) {
			if (paramJson.has("mode"+ethernet.getNumber())) {
				ethernet.setMode(paramJson.getInt("mode"+ethernet.getNumber()));
				firewallDeviceEthernetMapper.update(ethernet);
				logDesc += "[eth"+(ethernet.getNumber()-1)+"："
						+(paramJson.getInt("mode"+ethernet.getNumber())
								==FirewallDeviceEthernet.MODE_BIRDGE?"网桥模式":"路由模式")+"]\n";
			}
		}
		
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveEthModeToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		errorMsg = firewallStrategyService.giveStrategyToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * 下发网口模式
	 * @param device
	 * @return
	 */
	private String giveEthModeToDevice(FirewallDevice device) {
		
		//获取网桥模式的网口列表
		List<FirewallDeviceEthernet> bridgeEthernets = 
				firewallDeviceEthernetMapper.getByMode(
						device.getDevice_name(), 
						FirewallDeviceEthernet.MODE_BIRDGE);
		
		//获取路由模式的网口列表
		List<FirewallDeviceEthernet> routeEthernets = 
				firewallDeviceEthernetMapper.getByMode(
						device.getDevice_name(), 
						FirewallDeviceEthernet.MODE_ROUTE);
		
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x206 set ");
		
		if (bridgeEthernets == null || bridgeEthernets.size() < 1) {
			//如果网桥模式的网口列表为空则说明全部为路由模式
			instruction.append("route;");
		} else if (routeEthernets == null || routeEthernets.size() < 1) {
			//如果路由模式的网口列表为空则说明全部为网桥模式
			instruction.append("bridge;");
		} else {
			//否则为混合模式
			instruction.append("multi");
			for (FirewallDeviceEthernet ethernet : bridgeEthernets) {
				instruction.append(" eth" + (ethernet.getNumber()-1));
			}
			instruction.append(";");
		}
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		
	}

	/**
	 * 变更网口网络信息，ip，网关等
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateNetworkInfo(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//IP地址
		String ipAddress = null;
		if (paramJson.has("ipAddress")) {
			ipAddress = paramJson.getString("ipAddress");
		}
		
		
		//子网掩码
		String mask = null;
		if (paramJson.has("mask")) {
			mask = paramJson.getString("mask");
		}
		
		
		//网关
		String gateway = null;
		if (paramJson.has("gateway")) {
			gateway = paramJson.getString("gateway");
		}
		
		
		FirewallDeviceEthernet ethernet = firewallDeviceEthernetMapper.getById(id);
		if (ethernet == null) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		ethernet.setIp_address(ipAddress);
		ethernet.setGateway(gateway);
		ethernet.setMask(mask);
		firewallDeviceEthernetMapper.update(ethernet);
		
		//操作日志
		String logDesc = "[设备名称："+ethernet.getDevice_name()+"]\n"
				+ "[网口："+(ethernet.getNumber() - 1)+"]\n"
				+ "[IP地址："+ethernet.getIp_address()+"]"
				+ "[子网掩码："+ethernet.getMask()+"]"
				+ "[网关："+ethernet.getGateway()+"]";
		
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(ethernet.getDevice_name());
		String errorMsg = this.giveEthInfoToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	
	/**
	 * 下发网口模式
	 * @param device
	 * @return
	 */
	private String giveEthInfoToDevice(FirewallDevice device) {
		
		//获取网口列表
		List<FirewallDeviceEthernet> bridgeEthernets = 
				firewallDeviceEthernetMapper.getByDeviceName(device.getDevice_name());
		
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x205 set flush;");
		for (FirewallDeviceEthernet ethernet : bridgeEthernets) {
			if (ethernet.getMode() == 0) {
				continue;
			}
			if (StringUtils.isBlank(ethernet.getIp_address())) {
				instruction
				.append("0x20 0x205 set rule eth").append(ethernet.getNumber()-1)
				.append(" 0.0.0.0;");
				continue;
			}
			instruction
			.append("0x20 0x205 set rule eth").append(ethernet.getNumber()-1)
			.append(" ").append(ethernet.getIp_address())
			.append(" ").append(StringUtils.isNotBlank(ethernet.getMask())?ethernet.getMask():"255.255.255.0")
			.append(StringUtils.isNotBlank(ethernet.getGateway())?(" "+ethernet.getGateway()):"")
			.append(";");
		}
		instruction.append("0x20 0x205 set last;");
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		
	}

}
