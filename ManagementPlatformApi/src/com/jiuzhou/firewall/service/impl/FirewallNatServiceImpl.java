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
import com.jiuzhou.firewall.bean.FirewallNat;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallNatMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallNatService;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月15日 下午3:26:05
* 类说明
*/
@Service("FirewallNatService")
public class FirewallNatServiceImpl implements FirewallNatService {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallNatMapper firewallNatMapper;

	/**
	 * 根据设备标识获取nat列表
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
		
		//获取nat列表
		List<FirewallNat> firewallNats = firewallNatMapper.getByDeviceName(device.getDevice_name());
		if (firewallNats == null) {
			firewallNats = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("nats", firewallNats);
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
		
		//Nat类型
		if (!paramJson.has("natType")) {
			commonResult.setErrorMsg("缺少参数natType");
			return commonResult.toString();
		}
		int natType = paramJson.getInt("natType");
		
		//协议类型
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
		}
		
		//IP地址
		if (!paramJson.has("ipAddress")) {
			commonResult.setErrorMsg("缺少参数ipAddress");
			return commonResult.toString();
		}
		String ipAddress = paramJson.getString("ipAddress");
		
		//端口
		String port = null;
		if (paramJson.has("port")) {
			port = paramJson.getString("port");
		}
		
		//natIP
		String natIpAddress = null;
		String natSipAddress = null;
		String natDipAddress = null;
		String natPort = null;
		if (natType == 1 || natType == 2) {
			if (!paramJson.has("natIpAddress")) {
				commonResult.setErrorMsg("缺少参数natIpAddress");
				return commonResult.toString();
			}
			natIpAddress = paramJson.getString("natIpAddress");
			
			if (paramJson.has("natPort")) {
				natPort = paramJson.getString("natPort");
			}
		} else if (natType == 3 || natType == 4) {
			if (!paramJson.has("natSipAddress")) {
				commonResult.setErrorMsg("缺少参数natSipAddress");
				return commonResult.toString();
			}
			natSipAddress = paramJson.getString("natSipAddress");
			
			if (!paramJson.has("natDipAddress")) {
				commonResult.setErrorMsg("缺少参数natDipAddress");
				return commonResult.toString();
			}
			natDipAddress = paramJson.getString("natDipAddress");
		} else {
			commonResult.setErrorMsg("nat类型错误");
			return commonResult.toString();
		}
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		FirewallNat nat = new FirewallNat();
		nat.setDevice_name(deviceName);
		nat.setNat_type(natType);
		nat.setProtocol(protocol);
		nat.setIp_address(ipAddress);
		nat.setPort(port);
		nat.setNat_ip_address(natIpAddress);
		nat.setNat_sip_address(natSipAddress);
		nat.setNat_dip_address(natDipAddress);
		nat.setNat_port(natPort);
		nat.setAdd_time(new Date());
		nat.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallNatMapper.insert(nat);
		
		//操作日志描述
		String logDesc = "[设备："+deviceName+"]\n"
						+ "[NAT类型："+ FirewallNat.getNatName(natType)+"]\n"
						+ "[协议类型："+protocol+"]\n"
						+ "[IP地址："+ipAddress+"]\n"
						+ "[端口："+port+"]\n"
						+ "[NAT IP地址："+natIpAddress+"]\n"
						+ "[NAT IP起始地址："+natSipAddress+"]\n"
						+ "[NAT IP结束地址："+natDipAddress+"]\n"
						+ "[NAT 端口："+natPort+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveNatToDevice(device);
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
	private String giveNatToDevice(FirewallDevice device) {
		
		List<FirewallNat> nats = firewallNatMapper.getByDeviceName(device.getDevice_name());
		
		//端口IP信息
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("port","6860");//端口写死
		maps.put("mip", device.getIp_address());
		
		//组织指令
		StringBuffer natbuffer = new StringBuffer();
		natbuffer.append("0x20 0x207 set flush;");
		if(nats != null && nats.size() > 0) {
			for (FirewallNat nat : nats) {
				natbuffer.append("0x20 0x207 set rule ");
				if(nat.getNat_type() == FirewallNat.NAT_TYPE_SNAT 
						|| nat.getNat_type() == FirewallNat.NAT_TYPE_DYNAMIC_SNAT) {
					natbuffer.append("SNAT ");
				}
				if(nat.getNat_type() == FirewallNat.NAT_TYPE_DNAT
						|| nat.getNat_type() == FirewallNat.NAT_TYPE_DYNAMIC_DNAT) {
					natbuffer.append("DNAT ");
				}
				
				//协议类型
				if (StringUtils.isNotBlank(nat.getProtocol())) {
					natbuffer.append(nat.getProtocol()).append(" ");
				}
				
				//ip:端口
				natbuffer.append(nat.getIp_address());
				if (StringUtils.isNotBlank(nat.getPort())) {
					natbuffer.append(":").append(nat.getPort());
				}
				natbuffer.append(" ");
				
				//natip:端口
				if (nat.getNat_type() == FirewallNat.NAT_TYPE_SNAT 
						|| nat.getNat_type() == FirewallNat.NAT_TYPE_DNAT) {
					natbuffer.append(nat.getNat_ip_address());
					if (StringUtils.isNotBlank(nat.getNat_port())) {
						natbuffer.append(":").append(nat.getNat_port());
					}
				} else {
					natbuffer
					.append(nat.getNat_sip_address())
					.append("-")
					.append(nat.getNat_dip_address());
				}
				
				
				
				natbuffer.append(";");
			}
		}
		
		natbuffer.append("0x20 0x207 set last;");
		
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
		
		//Nat类型
		if (!paramJson.has("natType")) {
			commonResult.setErrorMsg("缺少参数natType");
			return commonResult.toString();
		}
		int natType = paramJson.getInt("natType");
		
		//协议类型
		String protocol = null;
		if (paramJson.has("protocol")) {
			protocol = paramJson.getString("protocol");
		}
		
		//IP地址
		if (!paramJson.has("ipAddress")) {
			commonResult.setErrorMsg("缺少参数ipAddress");
			return commonResult.toString();
		}
		String ipAddress = paramJson.getString("ipAddress");
		
		//端口
		String port = null;
		if (paramJson.has("port")) {
			port = paramJson.getString("port");
		}
		
		//natIP
		String natIpAddress = null;
		String natSipAddress = null;
		String natDipAddress = null;
		String natPort = null;
		if (natType == 1 || natType == 2) {
			if (!paramJson.has("natIpAddress")) {
				commonResult.setErrorMsg("缺少参数natIpAddress");
				return commonResult.toString();
			}
			natIpAddress = paramJson.getString("natIpAddress");
			
			if (paramJson.has("natPort")) {
				natPort = paramJson.getString("natPort");
			}
			
		} else if (natType == 3 || natType == 4) {
			if (!paramJson.has("natSipAddress")) {
				commonResult.setErrorMsg("缺少参数natSipAddress");
				return commonResult.toString();
			}
			natSipAddress = paramJson.getString("natSipAddress");
			
			if (!paramJson.has("natDipAddress")) {
				commonResult.setErrorMsg("缺少参数natDipAddress");
				return commonResult.toString();
			}
			natDipAddress = paramJson.getString("natDipAddress");
		} else {
			commonResult.setErrorMsg("nat类型错误");
			return commonResult.toString();
		}
		
		//获取Nat信息
		FirewallNat nat = firewallNatMapper.getById(id);
		if (nat == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		//操作日志描述
		String logDesc = "编辑前：\n"
						+ "[设备："+nat.getDevice_name()+"]\n"
						+ "[NAT类型："+FirewallNat.getNatName(nat.getNat_type())+"]\n"
						+ "[协议类型："+nat.getProtocol()+"]\n"
						+ "[IP地址："+nat.getIp_address()+"]\n"
						+ "[端口："+nat.getPort()+"]\n"
						+ "[NAT IP地址："+nat.getNat_ip_address()+"]\n"
						+ "[NAT IP起始地址："+nat.getNat_sip_address()+"]\n"
						+ "[NAT IP结束地址："+nat.getNat_dip_address()+"]\n"
						+ "[NAT 端口："+nat.getNat_port()+"]\n";
		
		nat.setNat_type(natType);
		nat.setProtocol(protocol);
		nat.setIp_address(ipAddress);
		nat.setPort(port);
		nat.setNat_ip_address(natIpAddress);
		nat.setNat_sip_address(natSipAddress);
		nat.setNat_dip_address(natDipAddress);
		nat.setNat_port(natPort);
		nat.setUpdate_time(new Date());
		nat.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallNatMapper.update(nat);
		
		//操作日志描述
		logDesc += "编辑后：\n"
					+ "[设备："+nat.getDevice_name()+"]\n"
					+ "[NAT类型："+FirewallNat.getNatName(nat.getNat_type())+"]\n"
					+ "[协议类型："+protocol+"]\n"
					+ "[IP地址："+ipAddress+"]\n"
					+ "[端口："+port+"]\n"
					+ "[NAT IP地址："+natIpAddress+"]\n"
					+ "[NAT IP起始地址："+natSipAddress+"]\n"
					+ "[NAT IP结束地址："+natDipAddress+"]\n"
					+ "[NAT 端口："+natPort+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(nat.getDevice_name());
		String errorMsg = this.giveNatToDevice(device);
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
		
		//获取Nat信息
		FirewallNat nat = firewallNatMapper.getById(id);
		if (nat == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		nat.setDelete_flag(1);
		firewallNatMapper.update(nat);
		
		//操作日志描述
		String logDesc = 
			"[设备："+nat.getDevice_name()+"]\n"
			+ "[NAT类型："+FirewallNat.getNatName(nat.getNat_type())+"]\n"
			+ "[协议类型："+nat.getProtocol()+"]\n"
			+ "[IP地址："+nat.getIp_address()+"]\n"
			+ "[端口："+nat.getPort()+"]\n"
			+ "[NAT IP地址："+nat.getNat_ip_address()+"]\n"
			+ "[NAT IP起始地址："+nat.getNat_sip_address()+"]\n"
			+ "[NAT IP结束地址："+nat.getNat_dip_address()+"]\n"
			+ "[NAT 端口："+nat.getNat_port()+"]\n";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(nat.getDevice_name());
		String errorMsg = this.giveNatToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
		
	}

}
