package com.jiuzhou.plat.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.NetworkSettingService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年11月8日 下午4:04:12
* 类说明
*/
@Service("NetworkSettingService")
public class NetworkSettingServiceImpl implements NetworkSettingService {

	/**
	 * 获取当前网络设置信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getNetworkSettingInfo(JSONObject paramJson) throws Exception {

		FileReader fileReader = new FileReader("/jiuzhou/adu/sys/conf/mgmt_port_conf");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String result = bufferedReader.readLine();
		fileReader.close();
		bufferedReader.close();
		
		if (StringUtils.isNotBlank(result)) {
			String[] results = result.split(" ");
			CommonResult commonResult = new CommonResult(true, "");
			if (results.length >= 3) {
				commonResult.put("ip", results[1]);
				commonResult.put("netmask", results[2]);
			}
			
			if (results.length >= 4) {
				commonResult.put("gateway", results[3]);
			}
			return commonResult.toString();
		}
		
		return new CommonResult(false, "获取网络设置信息失败").toString();
	}

	/**
	 * 变更网络设置信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateNetworkSetting(JSONObject paramJson) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取IP
		if (!paramJson.has("ip")) { 
			commonResult.setErrorMsg("缺少参数ip");
			return commonResult.toString();
		}
		String ip = paramJson.getString("ip");
		
		//获取子网掩码
		if (!paramJson.has("netmask")) {
			commonResult.setErrorMsg("缺少参数netmask");
			return commonResult.toString();
		}
		String netmask = paramJson.getString("netmask");
		
		//获取网关
		String gateway = null;
		if (paramJson.has("gateway")) {
			gateway = paramJson.getString("gateway");
		}
		
		String param = "eth5 " + ip + " " + netmask;
		if (StringUtils.isNotBlank(gateway)) {
			param += " " + gateway;
		}
		
		try {
			String command = "/jiuzhou/adu/sys/scripts/adu_port.sh " + param;
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			commonResult.setErrorMsg("网络设置失败");
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

}
