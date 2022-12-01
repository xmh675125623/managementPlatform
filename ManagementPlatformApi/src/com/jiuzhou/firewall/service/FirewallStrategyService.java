package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.jiuzhou.firewall.bean.FirewallDevice;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午5:19:49
* 类说明
*/
public interface FirewallStrategyService {

	/**
	 * 根据设备标识获取策略列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String listByDeviceName(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 添加
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String add(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 编辑
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String update(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 删除
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String delete(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 策略同步
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String sync(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 下发
	 * @param device
	 * @return
	 */
	public String giveStrategyToDevice(FirewallDevice device);
	
	/**
	 * 规则导入
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String importRule(HttpServletResponse response, MultipartFile file, JSONObject paramJson) throws Exception;
	
}
