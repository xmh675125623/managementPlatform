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

import com.jiuzhou.firewall.bean.FirewallCustomRoute;
import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallDeviceEthernet;
import com.jiuzhou.firewall.bean.FirewallRoute;
import com.jiuzhou.firewall.mapper.FirewallCustomRouteMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceEthernetMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallRouteMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallRouteService;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月16日 下午4:52:48
* 类说明
*/
@Service("FirewallRouteService")
public class FirewallRouteServiceImpl implements FirewallRouteService {

	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallRouteMapper firewallRouteMapper;
	
	@Autowired
	private FirewallDeviceEthernetMapper firewallDeviceEthernetMapper;
	
	@Autowired
	private FirewallCustomRouteMapper firewallCustomRouteMapper;
	
	/**
	 * 根据设备标识获取route列表
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
		
		//获取路由模式的网口
		List<FirewallDeviceEthernet> ethernets = 
				firewallDeviceEthernetMapper.getByMode(device.getDevice_name(), 
													FirewallDeviceEthernet.MODE_ROUTE);
		if (ethernets == null) {
			ethernets = new ArrayList<>();
		}
		
		
		//获取Route列表
		List<FirewallRoute> firewallRoutes = firewallRouteMapper.getByDeviceName(device.getDevice_name());
		if (firewallRoutes == null) {
			firewallRoutes = new ArrayList<>();
		}
		
		//获取自定义路由列表
		List<FirewallCustomRoute> customRoutes = firewallCustomRouteMapper.getByDeviceName(device.getDevice_name());
		if (customRoutes == null) {
			customRoutes = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("ethernets", ethernets);
		commonResult.put("routes", firewallRoutes);
		commonResult.put("customeRoutes", customRoutes);
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
		
		//路由类型
		if (!paramJson.has("routeType")) {
			commonResult.setErrorMsg("缺少参数routeType");
			return commonResult.toString();
		}
		String routeType = paramJson.getString("routeType");
		
		String errorMsg = null;
		if ("static".equals(routeType)) {//插入静态路由
			errorMsg = this.insertStaticRoute(deviceName, paramJson, request);
		} else if ("custom".equals(routeType)) {//插入自定义路由
			errorMsg = this.insertCustomRoute(deviceName, paramJson, request);
		} else {
			commonResult.setErrorMsg("路由类型错误");
			return commonResult.toString();
		}
		
		if (StringUtils.isNotBlank(errorMsg)) {
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * 插入静态路由
	 * @param deviceName
	 * @param paramJson
	 * @param request
	 * @return
	 */
	private String insertStaticRoute(String deviceName, 
			JSONObject paramJson, 
			HttpServletRequest request) {
		//路由类型
		if (!paramJson.has("type")) {
			return "缺少参数type";
		}
		int type = paramJson.getInt("type");
		
		//IP地址
		String ip = null;
		if (type == FirewallRoute.TYPE_HOST || type == FirewallRoute.TYPE_NETWORK) {
			if (!paramJson.has("ip")) {
				return "缺少参数ip";
			}
			ip = paramJson.getString("ip");
		}
		
		//网关
		String gateway = null;
		if (paramJson.has("gateway")) {
			gateway = paramJson.getString("gateway");
		}
		
		//出口设备
		if (!paramJson.has("outEth")) {
			return "缺少参数outEth";
		}
		int outEth = paramJson.getInt("outEth");
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			return "设备不存在";
		}
		
		FirewallRoute route = new FirewallRoute();
		route.setDevice_name(deviceName);
		route.setType(type);
		route.setIp(ip);
		route.setGateway(gateway);
		route.setOut_eth(outEth);
		route.setAdd_time(new Date());
		route.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		
		firewallRouteMapper.insert(route);
		
		//操作日志描述
		String logDesc = "[静态路由添加]\n"
						+ "[设备："+deviceName+"]\n"
						+ "[路由类型："+(type==3?"网络路由":(type==1?"主机路由":"默认路由"))+"]\n"
						+ "[IP地址："+ip+"]\n"
						+ "[网关："+gateway+"]\n"
						+ "[出口设备：eth"+(outEth-1)+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveRouteToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return errorMsg;
		}
		
		return null;
	}
	
	private String insertCustomRoute(String deviceName, 
			JSONObject paramJson, 
			HttpServletRequest request) {
		
		//入口设备
		int inEth = 0;
		if (paramJson.has("inEth")) {
			inEth = paramJson.getInt("inEth");
		}
		
		//出口设备
		if (!paramJson.has("outEth")) {
			return "缺少参数outEth";
		}
		int outEth = paramJson.getInt("outEth");
		
		//源ip
		String sip = null;
		if (paramJson.has("sip")) {
			sip = paramJson.getString("sip");
		}
		
		//目的ip
		String dip = null;
		if (paramJson.has("dip")) {
			dip = paramJson.getString("dip");
		}
		
		//协议
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
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
		
		//网关
		String gateway = null;
		if (paramJson.has("gateway")) {
			gateway = paramJson.getString("gateway");
		}
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			return "设备不存在";
		}
		
		FirewallCustomRoute route = new FirewallCustomRoute();
		route.setDevice_name(deviceName);
		route.setIn_eth(inEth);
		route.setOut_eth(outEth);
		route.setSip(sip);
		route.setDip(dip);
		route.setProtocol(protocol);
		route.setSport(sport);
		route.setDport(dport);
		route.setGateway(gateway);
		route.setAdd_time(new Date());
		route.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		
		firewallCustomRouteMapper.insert(route);
		
		//操作日志描述
		String logDesc = "[自定义路由添加]\n" 
						+ "[设备："+deviceName+"]\n"
						+ "[入口设备：eth"+(inEth>0?(inEth-1):"")+"]\n"
						+ "[出口设备：eth"+(outEth-1)+"]\n"
						+ "[源IP地址："+sip+"]\n"
						+ "[目的IP地址："+dip+"]\n"
						+ "[协议类型："+protocol+"]\n"
						+ "[源端口："+sport+"]\n"
						+ "[目的端口："+dport+"]\n"
						+ "[网关："+gateway+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveCustomRouteToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return errorMsg;
		}
		
		return null;
	}
	
	/**
	 * 下发静态路由信息
	 * @param device
	 * @return
	 */
	private String giveRouteToDevice(FirewallDevice device) {
		
		//获取静态路由列表
		List<FirewallRoute> routes = firewallRouteMapper.getByDeviceName(device.getDevice_name());
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x210 set 1 flush;");
		if(routes != null && routes.size() > 0) {
			for (FirewallRoute route : routes) {
				instruction.append("0x20 0x210 set rule 1 ");
        		switch (route.getType()) {
				case 0:
					instruction.append("default ");
					if (StringUtils.isNotBlank(route.getGateway())) {
						instruction.append(" via ").append(route.getGateway());
					}
					instruction.append(" dev ").append("eth"+(route.getOut_eth()-1))
					.append(";");
					break;
				case 1:
					instruction
					.append(route.getIp());
					if (StringUtils.isNotBlank(route.getGateway())) {
						instruction.append(" via ").append(route.getGateway());
					}
					instruction.append(" dev ").append("eth"+(route.getOut_eth()-1))
					.append(";");
					break;
				case 2:
					instruction
					.append(route.getIp() + ".0/24 ");
					if (StringUtils.isNotBlank(route.getGateway())) {
						instruction.append(" via ").append(route.getGateway());
					}
					instruction.append(" dev ").append("eth"+(route.getOut_eth()-1))
					.append(";");
					break;
				}
			}
		}
		
		instruction.append("0x20 0x210 set 1 last;");
		//socket配置信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		
	}
	
	/**
	 * 下发自定义路由信息
	 * @param device
	 * @return
	 */
	private String giveCustomRouteToDevice(FirewallDevice device) {
		
		//获取静态路由列表
		List<FirewallCustomRoute> routes = firewallCustomRouteMapper.getByDeviceName(device.getDevice_name());
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x210 set 3 flush;");
		if(routes != null && routes.size() > 0) {
			for (FirewallCustomRoute route : routes) {
				instruction.append("0x20 0x210 set rule 3 iprule -A DEFINED_ROUTE ");
        		if (StringUtils.isNotBlank(route.getSip())) {
        			instruction.append(" -s ").append(route.getSip()).append(" ");
        		}
        		if (StringUtils.isNotBlank(route.getDip())) {
        			instruction.append(" -d ").append(route.getDip()).append(" ");
        		}
        		if (StringUtils.isNotBlank(route.getProtocol())) {
        			instruction.append(" -p ").append(route.getProtocol()).append(" ");
        		}
        		if (StringUtils.isNotBlank(route.getSport())) {
        			instruction.append("-m multiport --sport " + route.getSport()).append(" ");
        		}
        		if (StringUtils.isNotBlank(route.getDport())) {
        			instruction.append("-m multiport --dport " + route.getDport()).append(" ");
        		}
        		if (route.getIn_eth() > 0) {
        			instruction.append("-i eth" + (route.getIn_eth()-1)).append(" ");
        		}
        		instruction.append("-j MARK --set-mark 2;");
        		
        		instruction.append("0x20 0x210 set 3 iproute ");
        		if (StringUtils.isNotBlank(route.getGateway())) {
					instruction.append(" via ").append(route.getGateway());
				}
				instruction.append(" dev ").append("eth"+(route.getOut_eth()-1))
				.append(";");
			}
		}
		
		instruction.append("0x20 0x210 set 3 last;");
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
		
		//路由类型
		if (!paramJson.has("routeType")) {
			commonResult.setErrorMsg("缺少参数routeType");
			return commonResult.toString();
		}
		String routeType = paramJson.getString("routeType");
		
		String errorMsg = null;
		if ("static".equals(routeType)) {//插入静态路由
			errorMsg = this.updateStaticRoute(paramJson, request);
		} else if ("custom".equals(routeType)) {//插入自定义路由
			errorMsg = this.updateCustomRoute(paramJson, request);
		} else {
			commonResult.setErrorMsg("路由类型错误");
			return commonResult.toString();
		}
		
		if (StringUtils.isNotBlank(errorMsg)) {
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 变更静态路由
	 * @param paramJson
	 * @param request
	 * @return
	 */
	private String updateStaticRoute(JSONObject paramJson, HttpServletRequest request) {
		//id
		if (!paramJson.has("id")) {
			return "缺少参数id";
		}
		int id = paramJson.getInt("id");
		
		//路由类型
		if (!paramJson.has("type")) {
			return "缺少参数type";
		}
		int type = paramJson.getInt("type");
		
		//IP地址
		String ip = null;
		if (type == FirewallRoute.TYPE_HOST || type == FirewallRoute.TYPE_NETWORK) {
			if (!paramJson.has("ip")) {
				return "缺少参数ip";
			}
			ip = paramJson.getString("ip");
		}
		
		//网关
		String gateway = null;
		if (paramJson.has("gateway")) {
			gateway = paramJson.getString("gateway");
		}
		
		
		//出口设备
		if (!paramJson.has("outEth")) {
			return "缺少参数outEth";
		}
		int outEth = paramJson.getInt("outEth");
		
		//获取route信息
		FirewallRoute route = firewallRouteMapper.getById(id);
		if (route == null) {
			return "记录不存在";
		}
		
		route.setType(type);
		route.setIp(ip);
		route.setGateway(gateway);
		route.setOut_eth(outEth);
		route.setUpdate_time(new Date());
		route.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		
		firewallRouteMapper.update(route);
		
		//操作日志描述
		String logDesc = "[静态路由编辑]\n"
				+ "[设备："+route.getDevice_name()+"]\n"
				+ "[路由类型："+(route.getType()==3?"网络路由":(route.getType()==1?"主机路由":"默认路由"))+"]\n"
				+ "[IP地址："+route.getIp()+"]\n"
				+ "[网关："+route.getGateway()+"]\n"
				+ "[出口设备：eth"+route.getOut_eth()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);

		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(route.getDevice_name());
		String errorMsg = this.giveRouteToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return errorMsg;
		}
		
		return null;
		
	}
	
	/**
	 * 变更自定义路由
	 * @param paramJson
	 * @param request
	 * @return
	 */
	private String updateCustomRoute(JSONObject paramJson, HttpServletRequest request) {
		//id
		if (!paramJson.has("id")) {
			return "缺少参数id";
		}
		int id = paramJson.getInt("id");
		
		//入口设备
		int inEth = 0;
		if (paramJson.has("inEth")) {
			inEth = paramJson.getInt("inEth");
		}
		
		//出口设备
		if (!paramJson.has("outEth")) {
			return "缺少参数outEth";
		}
		int outEth = paramJson.getInt("outEth");
		
		//源ip
		String sip = null;
		if (paramJson.has("sip")) {
			sip = paramJson.getString("sip");
		}
		
		//目的ip
		String dip = null;
		if (paramJson.has("dip")) {
			dip = paramJson.getString("dip");
		}
		
		//协议
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
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
		
		//网关
		String gateway = null;
		if (paramJson.has("gateway")) {
			gateway = paramJson.getString("gateway");
		}
		
		//获取route信息
		FirewallCustomRoute route = firewallCustomRouteMapper.getById(id);
		if (route == null) {
			return "记录不存在";
		}
		
		route.setIn_eth(inEth);
		route.setOut_eth(outEth);
		route.setSip(sip);
		route.setDip(dip);
		route.setProtocol(protocol);
		route.setSport(sport);
		route.setDport(dport);
		route.setGateway(gateway);
		route.setUpdate_time(new Date());
		route.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		
		firewallCustomRouteMapper.update(route);
		
		//操作日志描述
		String logDesc = "[自定义路由编辑]\n" 
				+ "[设备："+route.getDevice_name()+"]\n"
				+ "[入口设备：eth"+(inEth>0?(inEth-1):"")+"]\n"
				+ "[出口设备：eth"+(outEth-1)+"]\n"
				+ "[源IP地址："+sip+"]\n"
				+ "[目的IP地址："+dip+"]\n"
				+ "[协议类型："+protocol+"]\n"
				+ "[源端口："+sport+"]\n"
				+ "[目的端口："+dport+"]\n"
				+ "[网关："+gateway+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);

		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(route.getDevice_name());
		String errorMsg = this.giveCustomRouteToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return errorMsg;
		}
		
		return null;
		
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
		
		//路由类型
		if (!paramJson.has("routeType")) {
			commonResult.setErrorMsg("缺少参数routeType");
			return commonResult.toString();
		}
		String routeType = paramJson.getString("routeType");
		
		String errorMsg = null;
		if ("static".equals(routeType)) {//插入静态路由
			errorMsg = this.deleteStaticRoute(paramJson, request);
		} else if ("custom".equals(routeType)) {//插入自定义路由
			errorMsg = this.deleteCustomRoute(paramJson, request);
		} else {
			commonResult.setErrorMsg("路由类型错误");
			return commonResult.toString();
		}
		
		if (StringUtils.isNotBlank(errorMsg)) {
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
		
	}
	
	/**
	 * 删除静态路由
	 * @param paramJson
	 * @param request
	 * @return
	 */
	private String deleteStaticRoute(JSONObject paramJson, HttpServletRequest request) {
		//id
		if (!paramJson.has("id")) {
			return "缺少参数id";
		}
		int id = paramJson.getInt("id");
		
		//获取route信息
		FirewallRoute route = firewallRouteMapper.getById(id);
		if (route == null) {
			return "记录不存在";
		}
		route.setDelete_flag(1);
		firewallRouteMapper.update(route);
		//操作日志描述
		String logDesc = "[静态路由删除]\n" 
						+ "[设备："+route.getDevice_name()+"]\n"
						+ "[路由类型："+(route.getType()==3?"网络路由":(route.getType()==1?"主机路由":"默认路由"))+"]\n"
						+ "[IP地址："+route.getIp()+"]\n"
						+ "[网关："+route.getGateway()+"]\n"
						+ "[出口设备：eth"+route.getOut_eth()+"]\n";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);

		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(route.getDevice_name());
		String errorMsg = this.giveRouteToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return errorMsg;
		}
		
		return null;
	}
	
	/**
	 * 删除自定义路由
	 * @param paramJson
	 * @param request
	 * @return
	 */
	private String deleteCustomRoute(JSONObject paramJson, HttpServletRequest request) {
		//id
		if (!paramJson.has("id")) {
			return "缺少参数id";
		}
		int id = paramJson.getInt("id");
		
		//获取route信息
		FirewallCustomRoute route = firewallCustomRouteMapper.getById(id);
		if (route == null) {
			return "记录不存在";
		}
		firewallCustomRouteMapper.deleteById(route.getId());
		//操作日志描述
		String logDesc = "[自定义路由删除]\n" 
				+ "[设备："+route.getDevice_name()+"]\n"
				+ "[入口设备：eth"+(route.getIn_eth()>0?(route.getIn_eth()-1):"")+"]\n"
				+ "[出口设备：eth"+(route.getOut_eth()-1)+"]\n"
				+ "[源IP地址："+route.getSip()+"]\n"
				+ "[目的IP地址："+route.getDip()+"]\n"
				+ "[协议类型："+route.getProtocol()+"]\n"
				+ "[源端口："+route.getSport()+"]\n"
				+ "[目的端口："+route.getDport()+"]\n"
				+ "[网关："+route.getGateway()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);

		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(route.getDevice_name());
		String errorMsg = this.giveCustomRouteToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return errorMsg;
		}
		
		return null;
	}

	/**
	 * 设置动态路由
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateDynamicRouteSetting(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		//动态路由开关
		if (!paramJson.has("dynamicRroutePower")) {
			commonResult.setErrorMsg("缺少参数dynamicRroutePower");
			return commonResult.toString();
		}
		int dynamicRroutePower = paramJson.getInt("dynamicRroutePower");
		
		//动态路由开关
		if (!paramJson.has("dynamicRouteAction")) {
			commonResult.setErrorMsg("缺少参数dynamicRouteAction");
			return commonResult.toString();
		}
		String dynamicRouteAction = paramJson.getString("dynamicRouteAction");
		
		//获取设备信息
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		device.setDynamic_route_power(dynamicRroutePower);
		device.setDynamic_route_action(dynamicRouteAction);
		firewallDeviceMapper.update(device);
		
		//操作日志描述
		String logDesc = "[动态路由开关："+(dynamicRroutePower==0?'关':'开')+"]\n"
				+ "[RIP/OSPF："+dynamicRouteAction+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//socket配置信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		//发送指令
		String errorMsg = 
				TCPSocketUtil.sendNetworkInstruction(
						"0x20 0x210 set 2 "+(dynamicRroutePower==0?"stop":"start")+" "+dynamicRouteAction, 
						socketConfig);
		
		if (StringUtils.isNotBlank(errorMsg)) {
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

}
