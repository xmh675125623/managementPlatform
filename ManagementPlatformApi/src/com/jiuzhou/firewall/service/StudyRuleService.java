package com.jiuzhou.firewall.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiuzhou.firewall.bean.StudyRuleItem;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2020年9月1日 下午4:59:54
* 类说明
*/
public interface StudyRuleService {

	/**
	 * 将学习到的规则添加到策略
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String addStudyRuleToStrategy(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 根据设备标识打开学习规则
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String openStudyMode(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 根据设备标识获取学习规则列表
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
	public void add(StudyRuleItem studyRuleItem)  throws Exception;
	
	/**
	 * 根据设备名判断设备是否为学习模式
	 * @param deviceName
	 * @return
	 * @throws Exception
	 */
	public boolean isStudyMode(String deviceName) throws Exception;
	
	/**
	 * 根据设备名删除
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String deleteByDevice(JSONObject paramJson, HttpServletRequest request)  throws Exception;
	
	/**
	 * 导出学习到的规则
	 * @param paramJson
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String exportStudyRule(JSONObject paramJson, HttpServletResponse response, HttpServletRequest request) throws Exception;
	
}
