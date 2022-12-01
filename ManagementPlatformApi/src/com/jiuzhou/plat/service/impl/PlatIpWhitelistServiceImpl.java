package com.jiuzhou.plat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.PlatIpWhitelist;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.mapper.PlatIpWhitelistMapper;
import com.jiuzhou.plat.service.PlatIpWhitelistService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午7:40:10
* 类说明
*/
@Service("PlatIpWhitelistService")
public class PlatIpWhitelistServiceImpl implements PlatIpWhitelistService {
	
	@Autowired
	private PlatIpWhitelistMapper platIpWhitelistMapper;

	/**
	 * 获取ip地址白名单列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getIpWhitelists(JSONObject paramJson) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		List<PlatIpWhitelist> platIpWhitelists = platIpWhitelistMapper.getAll();
		if (platIpWhitelists == null) {
			platIpWhitelists = new ArrayList<>();
		}
		
		commonResult.setStatus(true);
		commonResult.put("list", platIpWhitelists);
		return commonResult.toString();
	}

	/**
	 * 添加ip地址白名单
	 * @param paramJson
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addIpWhitelist(JSONObject paramJson, AdminUserLoginInfo loginInfo) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("ip_address")) {
			commonResult.setErrorMsg("缺少参数ip_address");
			return commonResult.toString();
		}
		String ip_address = paramJson.getString("ip_address");
		
		String remark = null;
		if (paramJson.has("remark")) {
			remark = paramJson.getString("remark");
		}
		
		PlatIpWhitelist ipWhitelist = new PlatIpWhitelist();
		ipWhitelist.setIp_address(ip_address);
		ipWhitelist.setRemark(remark);
		ipWhitelist.setAdd_time(new Date());
		ipWhitelist.setAdd_user(loginInfo.getUserInfo().getUser_name());
		platIpWhitelistMapper.insert(ipWhitelist);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 删除ip地址白名单
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String deleteIpWhitelist(JSONObject paramJson) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		platIpWhitelistMapper.deleteById(id);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

}
