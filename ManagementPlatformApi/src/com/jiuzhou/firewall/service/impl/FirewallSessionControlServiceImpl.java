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
import com.jiuzhou.firewall.bean.FirewallSessionControl;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallSessionControlMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallSessionControlService;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月22日 下午4:19:56
* 类说明
*/
@Service("FirewallSessionControlService")
public class FirewallSessionControlServiceImpl implements FirewallSessionControlService {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallSessionControlMapper firewallSessionControlMapper;

	/**
	 * 根据设备标识获取会话控制列表
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
		
		//获取会话控制列表
		List<FirewallSessionControl> sessionControls = firewallSessionControlMapper.getByDeviceName(device.getDevice_name());
		if (sessionControls == null) {
			sessionControls = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("sessionControls", sessionControls);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 添加
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String add(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//IP地址
		if (!paramJson.has("ip")) {
			commonResult.setErrorMsg("缺少参数ip");
			return commonResult.toString();
		}
		String ip = paramJson.getString("ip");
		
		//TCP端口
		if (!paramJson.has("tcp_port")) {
			commonResult.setErrorMsg("缺少参数tcp_port");
			return commonResult.toString();
		}
		String tcpPort = paramJson.getString("tcp_port");
		
		//最大会话数
		if (!paramJson.has("num")) {
			commonResult.setErrorMsg("缺少参数num");
			return commonResult.toString();
		}
		int num = paramJson.getInt("num");
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		FirewallSessionControl control = new FirewallSessionControl();
		control.setDevice_name(deviceName);
		control.setIp(ip);
		control.setTcp_port(tcpPort);
		control.setNum(num);
		control.setAdd_time(new Date());
		control.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallSessionControlMapper.insert(control);
		
		//操作日志描述
		String logDesc = "[设备："+deviceName+"]\n"
						+ "[IP地址："+ip+"]\n"
						+ "[最大会话数："+num+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);

		//设备下发
		String errorMsg = this.giveSessionControlToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * 下发会话控制信息
	 * @param device
	 * @return
	 */
	private String giveSessionControlToDevice(FirewallDevice device) {
		
		//获取会话控制列表
		List<FirewallSessionControl> controls = 
				firewallSessionControlMapper.getByDeviceName(device.getDevice_name());
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x212 set flush;");
		if (controls != null) {
			for (FirewallSessionControl control : controls) {
				instruction
				.append("0x20 0x212 set rule ")
				.append(control.getIp())
				.append(" ")
				.append(control.getTcp_port())
				.append(" ")
				.append(control.getNum())
				.append(";");
			}
		}
		instruction.append("0x20 0x212 set last;");
		
		//socket配置信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		
	}

	/**
	 * 编辑
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String update(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//IP地址
		if (!paramJson.has("ip")) {
			commonResult.setErrorMsg("缺少参数ip");
			return commonResult.toString();
		}
		String ip = paramJson.getString("ip");
		
		//TCP端口
		if (!paramJson.has("tcp_port")) {
			commonResult.setErrorMsg("缺少参数tcp_port");
			return commonResult.toString();
		}
		String tcpPort = paramJson.getString("tcp_port");
		
		//最大会话数
		if (!paramJson.has("num")) {
			commonResult.setErrorMsg("缺少参数num");
			return commonResult.toString();
		}
		int num = paramJson.getInt("num");
		
		//获取会话控制信息
		FirewallSessionControl control = firewallSessionControlMapper.getById(id);
		if (control == null) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		control.setIp(ip);
		control.setTcp_port(tcpPort);
		control.setNum(num);
		control.setUpdate_time(new Date());
		control.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallSessionControlMapper.update(control);
		
		//操作日志描述
		String logDesc = "[设备："+control.getDevice_name()+"]\n"
						+ "[IP地址："+ip+"]\n"
						+ "[最大会话数："+num+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(control.getDevice_name());
		String errorMsg = this.giveSessionControlToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}

		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 删除
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String delete(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//获取会话控制信息
		FirewallSessionControl control = firewallSessionControlMapper.getById(id);
		if (control == null) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		control.setDelete_flag(1);
		firewallSessionControlMapper.update(control);
		
		//操作日志描述
		String logDesc = "[设备："+control.getDevice_name()+"]\n"
						+ "[IP地址："+control.getIp()+"]\n"
						+ "[最大会话数："+control.getNum()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(control.getDevice_name());
		String errorMsg = this.giveSessionControlToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}

		commonResult.setStatus(true);
		return commonResult.toString();
	}

}
