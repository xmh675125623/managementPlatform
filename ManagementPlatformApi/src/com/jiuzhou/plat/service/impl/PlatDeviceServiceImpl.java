package com.jiuzhou.plat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.PlatDevice;
import com.jiuzhou.plat.mapper.PlatDeviceMapper;
import com.jiuzhou.plat.service.PlatDeviceService;
import com.jiuzhou.plat.util.PropertiesUtils;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2021年11月22日 下午4:27:36
* 类说明
*/
@Service("PlatDeviceService")
public class PlatDeviceServiceImpl implements PlatDeviceService {
	
	private static Map<String, PlatDevice> deviceHostMap = null;
	
	private static String auditDeviceUrl = PropertiesUtils.getProp("auditDeviceUrl");
	private static String firewallDeviceUrl = PropertiesUtils.getProp("firewallDeviceUrl");
	
	@Autowired
	private PlatDeviceMapper platDeviceMapper;

	/**
	 * 添加设备
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {
		
		CommonResult result = new CommonResult(false, "");
		
		//设备名
		if (!paramJson.has("device_name")) {
			result.setErrorMsg("请输入设备名称");
			return result.toString();
		}
		String deviceName = paramJson.getString("device_name");
		
		//设备类型
		if (!paramJson.has("type")) {
			result.setErrorMsg("请输入设备类型");
			return result.toString();
		}
		int type = paramJson.getInt("type");
		
		//管理协议
		if (!paramJson.has("manage_protocol")) {
			result.setErrorMsg("请输入管理协议");
			return result.toString();
		}
		String manageProtocol = paramJson.getString("manage_protocol");
		
		//IP地址
		if (!paramJson.has("ip_address")) {
			result.setErrorMsg("请输入IP地址");
			return result.toString();
		}
		String ipAddress = paramJson.getString("ip_address");
		
		//端口
		String managePort = null;
		if (paramJson.has("manage_port")) {
			managePort = paramJson.getString("manage_port");
		}
		
		PlatDevice platDevice = new PlatDevice();
		platDevice.setDevice_name(deviceName);
		platDevice.setType(type);
		if (type == PlatDevice.TYPE_AUDIT) {
			platDevice.setAccess_url(auditDeviceUrl);
		} else if (type == PlatDevice.TYPE_AUDIT) {
			platDevice.setAccess_url(firewallDeviceUrl);
		}
		platDevice.setManage_protocol(manageProtocol);
		platDevice.setIp_address(ipAddress);
		platDevice.setManage_port(managePort);
		platDevice.setInsert_time(new Date());
		
		int resultInt = platDeviceMapper.insert(platDevice);
		if (resultInt > 0) {
			this.initDeviceHostMap();
			request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, platDevice.toLogDescription());
			result.setStatus(true);
			return result.toString();
		}
		result.setErrorMsg("添加失败");
		return result.toString();
	}

	@Override
	public String removeDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult result = new CommonResult(false, "");
		
		//设备名
		if (!paramJson.has("id")) {
			result.setErrorMsg("缺少参数id");
			return result.toString();
		}
		int id = paramJson.getInt("id");
		
		PlatDevice platDevice = platDeviceMapper.getById(id);
		
		platDeviceMapper.deleteById(id);
		this.initDeviceHostMap();
		
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, platDevice.toLogDescription());
		result.setStatus(true);
		
		result.setStatus(true);
		return result.toString();
	}

	
	@Override
	public String updateDevice(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult result = new CommonResult(false, "");
		
		//设备名
		if (!paramJson.has("id")) {
			result.setErrorMsg("缺少参数id");
			return result.toString();
		}
		int id = paramJson.getInt("id");
		
		PlatDevice device = platDeviceMapper.getById(id);
		if (device == null) {
			result.setErrorMsg("设备不存在");
			return result.toString();
		}
		
		//设备名
		if (paramJson.has("device_name")) {
			device.setDevice_name(paramJson.getString("device_name"));
		}
		
		//设备类型
		if (paramJson.has("type")) {
			device.setType(paramJson.getInt("type"));
		}
		
		//管理协议
		if (paramJson.has("manage_protocol")) {
			device.setManage_protocol(paramJson.getString("manage_protocol"));
		}
		
		//IP地址
		if (paramJson.has("ip_address")) {
			device.setIp_address(paramJson.getString("ip_address"));
		}
		
		//管理端口
		if (paramJson.has("manage_port")) {
			device.setManage_port(paramJson.getString("manage_port"));
		}
		
		if (device.getType() == PlatDevice.TYPE_AUDIT) {
			device.setAccess_url(auditDeviceUrl);
		} else if (device.getType() == PlatDevice.TYPE_AUDIT) {
			device.setAccess_url(firewallDeviceUrl);
		}
		
		int resultInt = platDeviceMapper.update(device);
		if (resultInt > 0) {
			this.initDeviceHostMap();
			request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, device.toLogDescription());
			result.setStatus(true);
			return result.toString();
		}
		result.setErrorMsg("变更失败");
		return result.toString(); 
		
	}

	@Override
	public String getListByPage(JSONObject paramJson) throws Exception {

		int page = 1;
		if (paramJson.has("page")) {
			page = paramJson.getInt("page");
		}
		
		int pageSize = 10;
		if (paramJson.has("pageSize")) {
			pageSize = paramJson.getInt("pageSize");
		}
		
		List<PlatDevice> list = platDeviceMapper.listByPage((page-1)*pageSize,
																pageSize);
		
		int listCount = platDeviceMapper.getCountForPage();
		
		CommonResult result = new CommonResult(true, "");
		result.put("list", list);
		result.put("count", listCount);
		result.put("page", page);
		result.put("pageSize", pageSize);
		
		return result.toString();
		
	}

	@Override
	public PlatDevice getByIp(String ipAddress) {
		if (deviceHostMap == null) {
			this.initDeviceHostMap();
		}
		return deviceHostMap.get(ipAddress);
	}

	private void initDeviceHostMap() {
		List<PlatDevice> devices = platDeviceMapper.getAll();
		if (devices != null && devices.size() > 0) {
			deviceHostMap = new HashMap<String, PlatDevice>();
			for (PlatDevice platDevice : devices) {
				deviceHostMap.put(platDevice.getIp_address() + "_" + platDevice.getType(), platDevice);
			}
		}
	}
}
