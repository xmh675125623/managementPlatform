package com.jiuzhou.firewall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.firewall.bean.FirewallAsset;
import com.jiuzhou.firewall.bean.FirewallStrategyGroup;
import com.jiuzhou.firewall.mapper.FirewallAssetMapper;
import com.jiuzhou.firewall.mapper.FirewallStrategyGroupMapper;
import com.jiuzhou.firewall.service.FirewallAssetService;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午1:53:58
* 类说明
*/
@Service("FirewallAssetService")
public class FirewallAssetServiceImpl implements FirewallAssetService {
	
	@Autowired
	private FirewallAssetMapper firewallAssetMapper;
	
	@Autowired
	private FirewallStrategyGroupMapper firewallStrategyGroupMapper;

	/**
	 * 获取所有资产列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getAll(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		List<FirewallAsset> assets = firewallAssetMapper.getAll();
		if (assets == null) {
			assets = new ArrayList<>();
		}
		
		commonResult.put("assets", assets);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 添加资产信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String add(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//资产名称
		if (!paramJson.has("asset_name")) {
			commonResult.setErrorMsg("缺少参数asset_name");
			return commonResult.toString();
		}
		String assetName = paramJson.getString("asset_name");
		
		//ip地址
		if (!paramJson.has("ip_address")) {
			commonResult.setErrorMsg("缺少参数ip_address");
			return commonResult.toString();
		}
		String ipAddress = paramJson.getString("ip_address");
		
		//备注
		String remark = null;
		if (paramJson.has("remark")) {
			remark = paramJson.getString("remark");
		}
		
		FirewallAsset asset = new FirewallAsset();
		asset.setAsset_name(assetName);
		asset.setIp_address(ipAddress);
		asset.setRemark(remark);
		asset.setAdd_time(new Date());
		asset.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallAssetMapper.insert(asset);
		
		//操作日志描述
		String logDesc = "[资产名称："+assetName+"]\n"
						+ "[IP地址："+ipAddress+"]\n"
						+ "[备注："+remark+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 编辑资产信息
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
		
		FirewallAsset asset = firewallAssetMapper.getById(id);
		if (asset == null ) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		//资产名称
		if (paramJson.has("asset_name")) {
			asset.setAsset_name(paramJson.getString("asset_name"));
		}
		
		//ip地址
		if (paramJson.has("ip_address")) {
			asset.setIp_address(paramJson.getString("ip_address"));
		}
		//备注
		String remark = null;
		if (paramJson.has("remark")) {
			remark = paramJson.getString("remark");
			asset.setRemark(remark);
		}
		
		asset.setUpdate_time(new Date());
		asset.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallAssetMapper.update(asset);
		
		//TODO 设置策略未同步
		
		//操作日志描述
		String logDesc = "[资产名称："+asset.getAsset_name()+"]\n"
						+ "[IP地址："+asset.getIp_address()+"]\n"
						+ "[备注："+asset.getRemark()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 删除资产信息
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
		
		FirewallAsset asset = firewallAssetMapper.getById(id);
		if (asset == null ) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		//判断资产是否被策略分组使用
		List<FirewallStrategyGroup> groups = firewallStrategyGroupMapper.getByAssetId(asset.getId());
		if (groups != null && groups.size() > 0) {
			commonResult.setErrorMsg("资产正在被策略组使用，无法删除");
			return commonResult.toString();
		}
		
		asset.setDelete_flag(1);
		asset.setUpdate_time(new Date());
		asset.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallAssetMapper.update(asset);
		
		//操作日志描述
		String logDesc = "[资产名称："+asset.getAsset_name()+"]\n"
						+ "[IP地址："+asset.getIp_address()+"]\n"
						+ "[备注："+asset.getRemark()+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

}
