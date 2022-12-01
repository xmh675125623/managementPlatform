package com.jiuzhou.firewall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallSessionKeep;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallSessionKeepMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallSessionKeepServie;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年3月17日 下午9:49:01
* 类说明
*/
@Service("FirewallSessionKeepServie")
public class FirewallSessionKeepServiceImpl implements FirewallSessionKeepServie {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallSessionKeepMapper firewallSessionKeepMapper;

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
		
		//获取列表
		List<FirewallSessionKeep> keeps = 
				firewallSessionKeepMapper.getByDeviceName(device.getDevice_name());
		if (keeps == null) {
			keeps = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("keeps", keeps);
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
		
		//协议类型
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
		}
		
		//源IP地址
		String sip = null;
		if (paramJson.has("sip")) {
			sip = paramJson.getString("sip");
		}
		
		//目的IP地址
		String dip = null;
		if (paramJson.has("dip")) {
			dip = paramJson.getString("dip");
		}
		
		//源端口
		String sport = null;
		if (paramJson.has("sport")) {
			sport = paramJson.getString("sport");
		}
		
		//目的端口
		String dport = null;
		if (paramJson.has("dport")) {
			dport = paramJson.getString("dport");
		}
		
		//维持时间
		String time = null;
		if (paramJson.has("time")) {
			time = paramJson.getString("time");
		}
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		FirewallSessionKeep keep = new FirewallSessionKeep();
		keep.setDevice_name(deviceName);
		keep.setSip(sip);
		keep.setDip(dip);
		keep.setSport(sport);
		keep.setDport(dport);
		keep.setTime(time);
		keep.setProtocol(protocol);
		keep.setAdd_time(new Date());
		keep.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallSessionKeepMapper.insert(keep);
		
		//操作日志描述
		String logDesc = "[设备："+deviceName+"]\n"
						+ "[协议类型："+ keep.getProtocol()+"]\n"
						+ "[源IP："+keep.getSip()+"]\n"
						+ "[目的IP："+keep.getDip()+"]\n"
						+ "[源端口："+keep.getSport()+"]\n"
						+ "[目的端口："+keep.getDport()+"]\n"
						+ "[维持时间："+keep.getTime()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveSessionKeepToDevice(device, keep);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * 下发指令
	 * @param device
	 * @throws Exception
	 */
	private String giveSessionKeepToDevice(FirewallDevice device, FirewallSessionKeep keep) {
		
		//端口IP信息
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("port","6860");//端口写死
		maps.put("mip", device.getIp_address());
		
		//组织指令
		StringBuffer natbuffer = new StringBuffer();
		natbuffer.append("0x20 0x211 ")
		.append("keep ");
		
		if (StringUtils.isNotBlank(keep.getSip())) {
			natbuffer.append(" -s ").append(keep.getSip());
		}
		
		if (StringUtils.isNotBlank(keep.getDip())) {
			natbuffer.append(" -d ").append(keep.getDip());
		}
		
		if (!"ICMP".equals(keep.getProtocol())) {
			natbuffer.append(" -p ").append(keep.getProtocol());
			if (StringUtils.isNotBlank(keep.getSport())) {
				natbuffer.append(" --sport ").append(keep.getSport());
			}
			if (StringUtils.isNotBlank(keep.getDport())) {
				natbuffer.append(" --dport ").append(keep.getDport());
			}
		}
		
		natbuffer.append(" -t ").append(keep.getTime())
		.append(";");
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(natbuffer.toString(), maps);
		
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
		
		//记录id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//协议类型
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
		}
		
		//源IP地址
		String sip = null;
		if (paramJson.has("sip")) {
			sip = paramJson.getString("sip");
		}
		
		//目的IP地址
		String dip = null;
		if (paramJson.has("dip")) {
			dip = paramJson.getString("dip");
		}
		
		//源端口
		String sport = null;
		if (paramJson.has("sport")) {
			sport = paramJson.getString("sport");
		}
		
		//目的端口
		String dport = null;
		if (paramJson.has("dport")) {
			dport = paramJson.getString("dport");
		}
		
		//维持时间
		String time = null;
		if (paramJson.has("time")) {
			time = paramJson.getString("time");
		}
		
		//获取keep信息
		FirewallSessionKeep keep = firewallSessionKeepMapper.getById(id);
		if (keep == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		keep.setSip(sip);
		keep.setDip(dip);
		keep.setSport(sport);
		keep.setDport(dport);
		keep.setTime(time);
		keep.setProtocol(protocol);
		keep.setUpdate_time(new Date());
		keep.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallSessionKeepMapper.update(keep);
		
		//操作日志描述
		String logDesc = "[ID："+keep.getId()+"]\n"
				+ "[协议类型："+ keep.getProtocol()+"]\n"
				+ "[源IP："+keep.getSip()+"]\n"
				+ "[目的IP："+keep.getDip()+"]\n"
				+ "[源端口："+keep.getSport()+"]\n"
				+ "[目的端口："+keep.getDport()+"]\n"
				+ "[维持时间："+keep.getTime()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(keep.getDevice_name());
//		String errorMsg = this.giveSessionKeepToDevice(device);
//		if  (StringUtils.isNotBlank(errorMsg)) {
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			commonResult.setErrorMsg(errorMsg);
//			return commonResult.toString();
//		}
		
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
		
		//获取keep信息
		FirewallSessionKeep keep = firewallSessionKeepMapper.getById(id);
		if (keep == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		keep.setDelete_flag(1);
		firewallSessionKeepMapper.update(keep);
		
		//操作日志描述
		String logDesc = "[ID："+keep.getId()+"]\n"
				+ "[协议类型："+ keep.getProtocol()+"]\n"
				+ "[源IP："+keep.getSip()+"]\n"
				+ "[目的IP："+keep.getDip()+"]\n"
				+ "[源端口："+keep.getSport()+"]\n"
				+ "[目的端口："+keep.getDport()+"]\n"
				+ "[维持时间："+keep.getTime()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		commonResult.setStatus(true);
		return commonResult.toString();
		
	}

}
