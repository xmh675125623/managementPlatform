package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.StudyRuleItem;

/**
* @author xingmh
* @version 创建时间：2020年9月1日 下午4:55:55
* 类说明
*/
@MapperScan
public interface StudyRuleItemMapper {

	/**
	 * 根据设备名称获取学习规则列表
	 * @param deviceName
	 * @return
	 */
	public List<StudyRuleItem> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 单条插入
	 * @param ruleItem
	 */
	public void insert(@Param(value="bean") StudyRuleItem ruleItem);
	
	/**
	 * 根据设备名称删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据条件查询
	 * @param condition
	 * @return
	 */
	public List<StudyRuleItem> search(@Param(value="device_name") String deviceName, 
			@Param(value="condition") String condition);
	
	/**
	 * 分页查询
	 * @return
	 */
	public List<StudyRuleItem> searchForPage(@Param(value="device_name") String deviceName, 
			@Param(value="start") long start,
			@Param(value="page_size") int pageSize,
			@Param(value="condition") String condition);
	
	/**
	 * 分页查询条数
	 * @param condition
	 * @return
	 */
	public int searchCount(@Param(value="device_name") String deviceName, 
			@Param(value="condition") String condition);
	
	
}
