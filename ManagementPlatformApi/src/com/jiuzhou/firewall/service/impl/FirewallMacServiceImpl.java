package com.jiuzhou.firewall.service.impl;

import java.net.SocketTimeoutException;
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
import com.jiuzhou.firewall.bean.FirewallMac;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallMacMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallMacService;
import com.jiuzhou.firewall.utils.EventUtils;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月14日 下午2:46:14
* 类说明
*/
@Service("FirewallMacService")
public class FirewallMacServiceImpl implements FirewallMacService {
	
	/**
	 * 缓存自动扫描的ip/mac信息
	 */
	public static final Map<String, Date> AUTO_MAC_CACHE = new HashMap<>();
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallMacMapper firewallMacMapper;

	/**
	 * 获取设备的绑定列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getListByDeviceName(JSONObject paramJson, HttpServletRequest request) throws Exception {
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
		
		//获取绑定列表
		List<FirewallMac> firewallMacs = firewallMacMapper.getByDeviceName(device.getDevice_name());
		if (firewallMacs == null) {
			firewallMacs = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("bindMacs", firewallMacs);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 获取自动绑定设备列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getAutoBindList(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("deviceName")) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		
		String deivceName = paramJson.getString("deviceName");
		
		//获取ip/mac列表
		List<FirewallMac> firewallMacs = firewallMacMapper.getScanMacByDeviceName(deivceName);
		
		commonResult.put("autoMacs", firewallMacs);
		commonResult.setStatus(true);
		
		return commonResult.toString();
	}

	/**
	 * 添加绑定
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String add(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		if (!paramJson.has("ipAddress")) {
			commonResult.setErrorMsg("缺少参数ipAddress");
			return commonResult.toString();
		}
		String ipAddress = paramJson.getString("ipAddress");
		
		if (!paramJson.has("macAddress")) {
			commonResult.setErrorMsg("缺少参数macAddress");
			return commonResult.toString();
		}
		String macAddress = paramJson.getString("macAddress");
		
		if (!paramJson.has("source")) {
			commonResult.setErrorMsg("缺少参数source");
			return commonResult.toString();
		}
		int source = paramJson.getInt("source");
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		//根据ip获取绑定信息判断ip是否已经被绑定
		FirewallMac mac = firewallMacMapper.getByDeviceNameAndIp(deviceName, ipAddress);
		if (mac != null) {
			commonResult.setErrorMsg("该IP地址已被绑定，请删除后再试");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		mac = new FirewallMac();
		mac.setDevice_name(deviceName);
		mac.setIp_address(ipAddress);
		mac.setMac_address(macAddress);
		mac.setSource(source);
		mac.setAdd_time(new Date());
		mac.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallMacMapper.insert(mac);
		
		//操作日志描述
		String logDesc = "[设备："+deviceName+"][IP："+ipAddress+"][MAC："+macAddress+"][来源："+(source==0?"手动添加":"扫描添加")+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveMacToDevice(device);
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
	private String giveMacToDevice(FirewallDevice device) {
		
		List<FirewallMac> macs = firewallMacMapper.getByDeviceName(device.getDevice_name());
		
		//端口IP信息
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("port","6860");//端口写死
		maps.put("mip", device.getIp_address());
		
		//组织指令
		StringBuffer flashbu = new StringBuffer();
		flashbu.append("0x20 0x203 set flush;");
		if (macs != null) {
			for (FirewallMac mac : macs) {
				flashbu.append("0x20 0x203 set rule ").append(mac.getIp_address())
				.append(" ").append(mac.getMac_address())
				.append(" ;");
			}
		}
		flashbu.append("0x20 0x203 set last;");
		
		//获取指令条数
		String[] len = flashbu.toString().split(";");
		
		//下发
		byte[] result = null;
		try {
			result = TCPSocketUtil.networkManageWrite(flashbu.toString(), len.length, maps);
		} catch (SocketTimeoutException e) {
			return "设备连接超时，操作失败";
		}
		
		//解密返回值
		if (result != null) {
			byte[] decryptbyte = EventUtils.decrypt(result);
			String resultStr = new String(decryptbyte);
			if (resultStr != null && resultStr.length() > 16) {
				String resu = resultStr.substring(10, 11);
				if (resu.equals("1")) {// 添加成功
					return null;
				} else {
					String[] param = (resultStr.substring(12)).split("@");
					return "设备下发失败：" + param[0];
				}
			} 
		} 
		
		
		return "设备下发失败， 操作失败";
	}

	/**
	 * 修改绑定
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public synchronized String update(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		if (!paramJson.has("ipAddress")) {
			commonResult.setErrorMsg("缺少参数ipAddress");
			return commonResult.toString();
		}
		String ipAddress = paramJson.getString("ipAddress");
		
		if (!paramJson.has("macAddress")) {
			commonResult.setErrorMsg("缺少参数macAddress");
			return commonResult.toString();
		}
		String macAddress = paramJson.getString("macAddress");
		
		//根据id获取绑定信息
		FirewallMac mac = firewallMacMapper.getById(id);
		if (mac == null) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		//判断新IP是否已绑定
		if (!mac.getIp_address().equals(ipAddress)) {
			FirewallMac  firewallMac = firewallMacMapper.getByDeviceNameAndIp(mac.getDevice_name(), ipAddress);
			if (firewallMac != null) {
				commonResult.setErrorMsg("该IP已绑定，请删除后重试");
				return commonResult.toString();
			}
		}
		
		String logDesc = "编辑前：\n[id："+mac.getId()+"][设备："+mac.getDevice_name()+"][IP："+mac.getIp_address()+"][MAC："+mac.getMac_address()+"]";
		
		mac.setIp_address(ipAddress);
		mac.setMac_address(macAddress);
		mac.setUpdate_time(new Date());
		mac.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallMacMapper.update(mac);
		
		logDesc = "编辑后：\n[id："+mac.getId()+"][设备："+mac.getDevice_name()+"][IP："+mac.getIp_address()+"][MAC："+mac.getMac_address()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(mac.getDevice_name());
		String errorMsg = this.giveMacToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 删除绑定
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String delete(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//根据id获取绑定信息
		FirewallMac mac = firewallMacMapper.getById(id);
		if (mac == null) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		mac.setDelete_flag(1);
		mac.setUpdate_time(new Date());
		mac.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		
		firewallMacMapper.update(mac);
		//操作日志
		String logDesc = "[id："+mac.getId()+"][设备："+mac.getDevice_name()+"][IP："+mac.getIp_address()+"][MAC："+mac.getMac_address()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);

		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(mac.getDevice_name());
		String errorMsg = this.giveMacToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 自动扫描开关
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateAutoPower(JSONObject paramJson, HttpServletRequest request) 
			throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名
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
		
		//动作
		if (!paramJson.has("action")) {
			commonResult.setErrorMsg("缺少参数action");
			return commonResult.toString();
		}
		String action = paramJson.getString("action");
		
		if ("close".equals(action)) {
			device.setAuto_ip_mac(0);
		} else if ("open".equals(action)) {
			device.setAuto_ip_mac(1);
		}
		
		firewallDeviceMapper.update(device);
		
		//socket端口IP信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		String errorMsg =  TCPSocketUtil.sendNetworkInstruction(
				"0x20 0x203 auto "+device.getAuto_ip_mac(), 
				socketConfig);
		
		if (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 插入扫描到的mac信息
	 * @param mac
	 */
	@Override
	public void insertScanMac(FirewallMac mac) {
		if (mac == null) {
			return;
		}
		
		String cache_key = mac.getDevice_name() + "_"  
							+ mac.getIp_address() + "_" 
							+ mac.getMac_address();
		Date cache_date = AUTO_MAC_CACHE.get(cache_key);
		if (cache_date == null) {
			FirewallMac insertedMac = firewallMacMapper.getScanMacByMacAndIp(
					mac.getDevice_name(), 
					mac.getMac_address(), 
					mac.getIp_address());
			if (insertedMac == null) {
				firewallMacMapper.insertScanMac(mac);
			}
		}
		AUTO_MAC_CACHE.put(cache_key, new Date());
		
	}

}
