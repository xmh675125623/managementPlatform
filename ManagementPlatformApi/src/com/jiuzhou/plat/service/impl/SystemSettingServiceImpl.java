package com.jiuzhou.plat.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DatabaseGlobalVariables;
import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.mapper.SystemSettingMapper;
import com.jiuzhou.plat.service.SystemSettingService;

import net.sf.json.JSONObject;

/**
 * 
 * @author xingmh
 *
 */
@Service("SystemSettingService")
public class SystemSettingServiceImpl extends ServiceBase implements SystemSettingService {

	@Autowired
	SystemSettingMapper systemSettingMapper;
	
	@Override
	public SystemSetting getByName(String name) {

		SystemSetting setting = getCache(name, SystemSetting.class);
		if (setting == null) {
			setting = systemSettingMapper.getByName(name);
		}
		if (setting != null) {
			setCache(CACHE_SYSTEM_SETTING + name, setting);
			return setting;
		}
		return null;
	}

	@Override
	public String getList(JSONObject paramJson) throws Exception {
		
		List<SystemSetting> list = systemSettingMapper.getList();
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("list", list);
		return commonResult.toString();
	}

	@Override
	public String update(JSONObject paramJson, AdminUserLoginInfo loginInfo) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//判断是否存在
		SystemSetting setting = systemSettingMapper.getById(id);
		if (setting == null) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		
		if (!paramJson.has("value")) {
			commonResult.setErrorMsg("请输入设置值");
			return commonResult.toString();
		}
		String value = paramJson.getString("value");
		
		if ("Number".equals(setting.getType())) {
			
			try {
				Integer.parseInt(value);
			} catch (Exception e) {
				commonResult.setErrorMsg("请输入整数类型的值");
				return commonResult.toString();
			}
			
		}
		
		setting.setValue(value);
		setting.setUpdate_time(new Date());
		setting.setUpdate_user(loginInfo.getUserInfo().getUser_name());
		systemSettingMapper.update(setting);
		deleteCache(CACHE_SYSTEM_SETTING+setting.getName());
		commonResult.setStatus(true);
		//操作密码验证周期
		return commonResult.toString();
	}

	@Override
	public double getDiskUsage() throws Exception {
		String dataDir = getCache(CACHE_DATABASE_DATADIR, String.class);
		if (StringUtils.isBlank(dataDir)) {
			DatabaseGlobalVariables variables = systemSettingMapper.getDatadir();
			if (variables != null) {
				dataDir = variables.getValue();
				setCache(CACHE_DATABASE_DATADIR, dataDir);
			}
		}
		
		if (dataDir != null) {
//			System.out.println(dataDir);
			File file = new File(dataDir);
			double used = ((file.getTotalSpace() - file.getFreeSpace()) / 1024.0 / 1024 / 1024);  
			double total = file.getTotalSpace() / 1024.0 / 1024 / 1024;
//			System.out.println("total:" + file.getTotalSpace());
//			System.out.println("used:" + (file.getTotalSpace() - file.getFreeSpace()));
			double usage = ((int)(used/total*10000))/100.0;
			return usage;
		}
		
		return 0.0;
	}

}
