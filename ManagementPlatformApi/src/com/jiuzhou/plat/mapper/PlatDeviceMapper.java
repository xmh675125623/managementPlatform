package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallReportCounter;
import com.jiuzhou.plat.bean.DeviceCount;
import com.jiuzhou.plat.bean.PlatDevice;

/**
* @author xingmh
* @version 创建时间：2019年1月8日 下午5:05:40
* 类说明
*/
@MapperScan
public interface PlatDeviceMapper {
	
	
	/**
	 * 单条插入
	 * @param bean
	 */
	public int insert(@Param(value="bean") PlatDevice bean);
	
	
	/**
	 * 获取所有设备
	 * @return
	 */
	public List<PlatDevice> getAll();
	
	/**
	 * 根据id获取设备信息
	 * @param id
	 * @return
	 */
	public PlatDevice getById(@Param(value="id") int id);
	
	/**
	 * 分页获取设备列表
	 * @return
	 */
	public List<PlatDevice> listByPage(@Param(value="start") int start, @Param(value="end") int end);
	
	/**
	 * 获取条数信息
	 * @return
	 */
	public int getCountForPage();
	
	
	public int update(@Param(value="bean") PlatDevice device);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteById(@Param(value="id") int id);
	
	/**
	 * 获取各类设备数量信息
	 * @return
	 */
	public List<DeviceCount> getDeviceCounts();
	
	/**
	 * 获取平台总日志数列表
	 * @return
	 */
	public List<FirewallReportCounter> getPlatLogCountList();
	
	/**
	 * 根据计数类型获取日志数统计列表
	 * @param countTypes
	 * @return
	 */
	public List<FirewallReportCounter> getLastLogCountByType(
			@Param(value="countTypes") int[] countTypes, 
			@Param(value="countDate") String countDate);
	
}
