package com.jiuzhou.plat.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.SystemWarning;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.mapper.AuditLogMapper;
import com.jiuzhou.plat.mapper.DatabaseTableMapper;
import com.jiuzhou.plat.service.IndexService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年11月6日 下午5:18:21
* 类说明
*/
@Service("IndexService")
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	DatabaseTableMapper databaseTableMapper;

	@Autowired
	AuditLogMapper auditLogMapper;
	
	@Autowired
	FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	FirewallDeviceService firewallDeviceService;
	
	/**
	 * 获取首页数据
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly=false)
	public String getIndexData(JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		JSONArray deviceArray = firewallDeviceService.getForSelectTag();
		commonResult.put("deviceArray", deviceArray);

		FirewallDevice device = null;
		if (paramJson.has("deviceName")) {
			device = firewallDeviceMapper.getByName(paramJson.getString("deviceName"));
		}else if (deviceArray.size() > 0) {
			device = firewallDeviceMapper.getByName(deviceArray.getJSONObject(0).getString("deviceName"));
		}
		
		//设置用户当前首页展示的设备，用于websocket数据广播
		if (device != null) {
			AdminUserLoginInfo loginInfo = 
					ServiceBase.getCacheStatic(
							ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
							AdminUserLoginInfo.class);
			loginInfo.setIndexCurrentDeviceName(device.getDevice_name());
			
		}
		
		commonResult.put("device", device);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 同步设备时间
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String syncTime(JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("setTime")) {
			commonResult.setErrorMsg("缺少参数setTime");
			return commonResult.toString();
		}

		String setTime = paramJson.getString("setTime");
		
		try {
			InputStreamReader stdISR = null;
			InputStreamReader errISR = null;
			String command = "/jiuzhou/adu/sys/scripts/adu_time.sh " + setTime;
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			String line = null;
			stdISR = new InputStreamReader(process.getInputStream());
			BufferedReader stdBR = new BufferedReader(stdISR);
			while ((line = stdBR.readLine()) != null) {
				if (line.indexOf("error") >= 0) {
					commonResult.setErrorMsg("时间同步失败");
					return commonResult.toString();
				}
			}

			errISR = new InputStreamReader(process.getErrorStream());
			BufferedReader errBR = new BufferedReader(errISR);
			String commandErrInfo = "";
			while ((line = errBR.readLine()) != null) {
				commandErrInfo += ("ERR line:" + line + "\n");
			}
			if (StringUtils.isNotBlank(commandErrInfo)) {
				commonResult.setErrorMsg("时间同步失败");
				return commonResult.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResult.setErrorMsg("时间同步失败");
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String systemWarning(JSONObject paramJson) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<SystemWarning> systemWarnings = ServiceBase.getCacheStatic(ServiceBase.CACHE_SYSTEM_WARNING, ArrayList.class);
		if (systemWarnings == null) {
			systemWarnings = new ArrayList<>();
		}
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("notices", systemWarnings);
		return commonResult.toString();
	}

}
