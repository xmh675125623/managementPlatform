package com.jiuzhou.firewall.service.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallSession;
import com.jiuzhou.firewall.bean.FirewallSessionSetting;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallSessionMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallSessionService;
import com.jiuzhou.firewall.utils.EventUtils;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月21日 下午5:21:16
* 类说明
*/
@Service("FirewallSessionService")
public class FirewallSessionServiceImpl implements FirewallSessionService {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallSessionMapper firewallSessionMapper;

	/**
	 * 根据设备标识及源ip查询
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String search(JSONObject paramJson, HttpServletRequest request) throws Exception {
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
		
		String sourceIp = null;
		if (paramJson.has("sourceIp")) {
			sourceIp = paramJson.getString("sourceIp");
		}
		
		//查询会话列表
		List<FirewallSession> list = firewallSessionMapper.getByDeviceName(device.getDevice_name(), sourceIp);
		
		//获取会话相关设置
		FirewallSessionSetting sessionSetting = firewallSessionMapper.getSettingByDeviceName(device.getDevice_name());
		if (sessionSetting == null) {
			sessionSetting = new FirewallSessionSetting();
			sessionSetting.setDevice_name(device.getDevice_name());
			firewallSessionMapper.insertSetting(sessionSetting);
		}
		
		commonResult.put("device", device);
		commonResult.put("sessions", list);
		commonResult.put("sessionSetting", sessionSetting);
		return commonResult.toString();
	}

	/**
	 * 更新会话列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateSession(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//获取设备信息
		FirewallDevice device1 = firewallDeviceMapper.getByName(deviceName);
		if (device1 == null) {
			commonResult.setErrorMsg("设备不存在");
			return commonResult.toString();
		}
		
		Map<String, String> maps = TCPSocketUtil.getNetworkSocketConfig();
		maps.put("mip", device1.getIp_address());
		//maps.put("pwds", device1.getIntroduction());
		byte[] decryptbyte = null;
		try {
			decryptbyte = TCPSocketUtil.networkManageWrite("0x20 0x211 update", 1, maps);// 接受返回值
		} catch (SocketTimeoutException e) {
			commonResult.setErrorMsg("设备请求超时，更新失败");
			return commonResult.toString();
		}
		if (decryptbyte != null && decryptbyte.length > 10) {
			decryptbyte = EventUtils.decrypt(decryptbyte);
			String decryptStr = new String(decryptbyte);
			String resu = decryptStr.substring(10, 11);
			if (resu.equals("1")) {
				// 更新时有成功数据返回
				// 把之前数据库的会话删除
				firewallSessionMapper.deleteByDeviceName(deviceName);
				String[] param = decryptStr.substring(12).split("@");
				if (param[0].indexOf(";") > 0) {
					String[] paramss = param[0].split(";");
					// 有需要更新的
					// 解析字符串 并将解析后的内容插入数据库
					List<FirewallSession> sessionList = new ArrayList<>();
					for (int i = 0; i < paramss.length; i++) {
						String[] params = paramss[i].split(" ");
						FirewallSession session = new FirewallSession();
						session.setId(UUID.randomUUID().toString().replace("-", ""));
						try {
							session.setSource_ip("null".equals(params[0])?null:params[0]);
							session.setSource_port("null".equals(params[1])?null:params[1]);
							session.setTarget_ip("null".equals(params[2])?null:params[2]);
							session.setTarget_port("null".equals(params[3])?null:params[3]);
							session.setTcp_name("null".equals(params[4])?null:params[4]);
							session.setOuttime_time("null".equals(params[5])?null:params[5]);
							session.setName("null".equals(params[6])?null:params[6]);
							session.setStatus("null".equals(params[7])?null:params[7]);
							session.setDevice_name(deviceName);
							session.setAdd_time(new Date());
							// session.setTactics(params[7]);
							sessionList.add(session);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
					// 插入数据库
					if (sessionList.size() > 0) {
						firewallSessionMapper.insertBatch(sessionList);
					}
					commonResult.setStatus(true);
					return commonResult.toString();

				} else {
					// 成功 单没有会话
					commonResult.setStatus(true);
					return commonResult.toString();

				}
				// String[] param1 = param[1].split(";");

			} else {
				// 失败
				commonResult.setErrorMsg("数据返回错误，更新失败");
				return commonResult.toString();

			}
		}
		commonResult.setErrorMsg("设备请求超时，更新失败");
		return commonResult.toString();
	}
	
	/**
	 * 更新会话管理相关设置
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateSessionSetting(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//获取连接完整性
		int lineComplete = 0;
		if (paramJson.has("lineComplete")) {
			lineComplete = paramJson.getInt("lineComplete");
		}
		
		//获取tcp会话维持时间
		int tcpTime = 0;
		if (paramJson.has("tcpTime")) {
			tcpTime = paramJson.getInt("tcpTime");
		}
		
		//获取设置信息
		FirewallSessionSetting setting = firewallSessionMapper.getSettingByDeviceName(deviceName);
		if (setting == null) {
			setting = new FirewallSessionSetting();
			setting.setDevice_name(deviceName);
			setting.setLine_complete(lineComplete);
			if (tcpTime >= 0) {
				setting.setTcp_time(tcpTime);
			}
			firewallSessionMapper.insertSetting(setting);
		} else {
			setting.setLine_complete(lineComplete);
			if (tcpTime >= 0) {
				setting.setTcp_time(tcpTime);
			}
			firewallSessionMapper.updateSetting(setting);
		}
		
		//设备下发
		//socket配置信息
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x211 ")
		.append("tcpset ").append(lineComplete==0?"1":"0");
		if (tcpTime > 0) {
			instruction.append(" ").append(tcpTime);
		}
		instruction.append(";");
		
		//发送指令
		String errorMsg = TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 清空会话
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String clearSession(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//获取设备信息
		FirewallDevice device1 = firewallDeviceMapper.getByName(deviceName);
		if (device1 == null) {
			commonResult.setErrorMsg("设备不存在");
			return commonResult.toString();
		}
		
		//清空数据库
		firewallSessionMapper.deleteByDeviceName(deviceName);
		
		//socket配置信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device1.getIp_address());
		//发送指令
		String errorMsg = TCPSocketUtil.sendNetworkInstruction("0x20 0x211 flush;", socketConfig);
		if  (StringUtils.isNotBlank(errorMsg)) {
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
}
