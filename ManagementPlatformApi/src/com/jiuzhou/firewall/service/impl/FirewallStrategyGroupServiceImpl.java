package com.jiuzhou.firewall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.firewall.bean.FirewallAsset;
import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallStrategyGroup;
import com.jiuzhou.firewall.mapper.FirewallAssetMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyGroupMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallStrategyGroupService;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午5:04:42
* 类说明
*/
@Service("FirewallStrategyGroupService")
public class FirewallStrategyGroupServiceImpl implements FirewallStrategyGroupService {

	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallStrategyGroupMapper firewallStrategyGroupMapper;
	
	@Autowired
	private FirewallAssetMapper firewallAssetMapper;
	
	@Autowired
	private FirewallStrategyMapper firewallStrategyMapper;
	
	/**
	 * 根据设备标识获取分组列表
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
		
		int page = 1;
		if (paramJson.has("page")) {
			page = paramJson.getInt("page");
		}
		
		int pageSize = 10;
		if (paramJson.has("pageSize")) {
			pageSize = paramJson.getInt("pageSize");
		}
		
		//获取分组列表
		List<FirewallStrategyGroup> groups = 
				firewallStrategyGroupMapper.getByDeviceNameForPage(
						device.getDevice_name(),
						(page-1)*pageSize,
						pageSize);
		if (groups == null) {
			groups = new ArrayList<>();
		}
		
		int groupCount = firewallStrategyGroupMapper.getCountByDeviceName(device.getDevice_name());
		
		//获取资产列表
		List<FirewallAsset> assets = firewallAssetMapper.getAll();
		if (assets == null) {
			assets = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("groups", groups);
		commonResult.put("groupCount", groupCount);
		commonResult.put("assets", assets);
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
		
		//策略组名
		if (!paramJson.has("group_name")) {
			commonResult.setErrorMsg("缺少参数group_name");
			return commonResult.toString();
		}
		String groupName = paramJson.getString("group_name");
		
		//源资产id
		if (!paramJson.has("source_asset")) {
			commonResult.setErrorMsg("缺少参数source_asset");
			return commonResult.toString();
		}
		int sourceAsset = paramJson.getInt("source_asset");
		
		//目的资产id
		if (!paramJson.has("target_asset")) {
			commonResult.setErrorMsg("缺少参数target_asset");
			return commonResult.toString();
		}
		int targetAsset = paramJson.getInt("target_asset");
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		//获取源资产
		FirewallAsset sourceAssetBean = firewallAssetMapper.getById(sourceAsset);
		if (sourceAssetBean == null) {
			commonResult.setErrorMsg("源资产不存在");
			return commonResult.toString();
		}
		
		//获取目的资产
		FirewallAsset targetAssetBean = firewallAssetMapper.getById(targetAsset);
		if (targetAssetBean == null) {
			commonResult.setErrorMsg("目的资产不存在");
			return commonResult.toString();
		}
		
		FirewallStrategyGroup group = new FirewallStrategyGroup();
		group.setDevice_name(deviceName);
		group.setGroup_name(groupName);
		group.setSource_asset(sourceAsset);
		group.setTarget_asset(targetAsset);
		group.setAdd_time(new Date());
		group.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallStrategyGroupMapper.insert(group);
		
		//操作日志描述
		String logDesc = "[设备："+deviceName+"]\n"
						+ "[策略组名："+groupName+"]\n"
						+ "[源资产："+sourceAssetBean.getAsset_name()+"]\n"
						+ "[源资产IP："+sourceAssetBean.getIp_address()+"]\n"
						+ "[目的资产："+targetAssetBean.getAsset_name()+"]\n"
						+ "[目的资产IP："+targetAssetBean.getIp_address()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//TODO 设备下发
		commonResult.setStatus(true);
		return commonResult.toString();
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
		
		//id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//策略组名
		if (!paramJson.has("group_name")) {
			commonResult.setErrorMsg("缺少参数group_name");
			return commonResult.toString();
		}
		String groupName = paramJson.getString("group_name");
		
		//源资产id
		if (!paramJson.has("source_asset")) {
			commonResult.setErrorMsg("缺少参数source_asset");
			return commonResult.toString();
		}
		int sourceAsset = paramJson.getInt("source_asset");
		
		//目的资产id
		if (!paramJson.has("target_asset")) {
			commonResult.setErrorMsg("缺少参数target_asset");
			return commonResult.toString();
		}
		int targetAsset = paramJson.getInt("target_asset");
		
		
		//获取组信息
		FirewallStrategyGroup group = firewallStrategyGroupMapper.getById(id);
		if (group == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		//获取源资产
		FirewallAsset sourceAssetBean = firewallAssetMapper.getById(sourceAsset);
		if (sourceAssetBean == null) {
			commonResult.setErrorMsg("源资产不存在");
			return commonResult.toString();
		}
		
		//获取目的资产
		FirewallAsset targetAssetBean = firewallAssetMapper.getById(targetAsset);
		if (targetAssetBean == null) {
			commonResult.setErrorMsg("目的资产不存在");
			return commonResult.toString();
		}
		
		group.setGroup_name(groupName);
		group.setSource_asset(sourceAsset);
		group.setTarget_asset(targetAsset);
		group.setUpdate_time(new Date());
		group.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallStrategyGroupMapper.update(group);
		
		//操作日志描述
		String logDesc = "[设备："+group.getDevice_name()+"]\n"
				+ "[策略组名："+group.getGroup_name()+"]\n"
				+ "[源资产："+sourceAssetBean.getAsset_name()+"]\n"
				+ "[源资产IP："+sourceAssetBean.getIp_address()+"]\n"
				+ "[目的资产："+targetAssetBean.getAsset_name()+"]\n"
				+ "[目的资产IP："+targetAssetBean.getIp_address()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//TODO 设备下发
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
		
		//id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		
		
		//获取组信息
		FirewallStrategyGroup group = firewallStrategyGroupMapper.getById(id);
		if (group == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		//判断分组中是否含有未删除的策略
		int strateygCount = firewallStrategyMapper.getCountByGroupId(group.getId(), null);
		if (strateygCount > 0) {
			commonResult.setErrorMsg("分组中含有未删除的策略，无法删除");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		group.setDelete_flag(1);
		group.setUpdate_time(new Date());
		group.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallStrategyGroupMapper.update(group);
		
		//获取源资产
		FirewallAsset sourceAssetBean = firewallAssetMapper.getById(group.getSource_asset());
		if (sourceAssetBean == null) {
			commonResult.setErrorMsg("源资产不存在");
			return commonResult.toString();
		}
		
		//获取目的资产
		FirewallAsset targetAssetBean = firewallAssetMapper.getById(group.getTarget_asset());
		if (targetAssetBean == null) {
			commonResult.setErrorMsg("目的资产不存在");
			return commonResult.toString();
		}
		
		//操作日志描述
		String logDesc = "[设备："+group.getDevice_name()+"]\n"
				+ "[策略组名："+group.getGroup_name()+"]\n"
				+ "[源资产："+sourceAssetBean.getAsset_name()+"]\n"
				+ "[源资产IP："+sourceAssetBean.getIp_address()+"]\n"
				+ "[目的资产："+targetAssetBean.getAsset_name()+"]\n"
				+ "[目的资产IP："+targetAssetBean.getIp_address()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//TODO 设备下发
		commonResult.setStatus(true);
		return commonResult.toString();
	}

}
