package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallStrategyGroup;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午4:38:22
* 类说明
*/
@MapperScan
public interface FirewallStrategyGroupMapper {
	
	/**
	 * 根据设备名查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallStrategyGroup> getByDeviceName(
			@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名分页查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallStrategyGroup> getByDeviceNameForPage(
			@Param(value="device_name") String deviceName,
			@Param(value="start") int start,
			@Param(value="pageSize") int pageSize);
	
	/**
	 * 根据分页数据获取
	 * @param deviceName
	 * @return
	 */
	public List<FirewallStrategyGroup> getByDeviceNameASC(
			@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名获取总条数
	 * @param deviceName
	 * @return
	 */
	public int getCountByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallStrategyGroup getById(@Param(value="id") int id);
	
	/**
	 * 插入记录
	 * @param strategyGroup
	 */
	public void insert(@Param(value="bean") FirewallStrategyGroup strategyGroup);
	
	/**
	 * 更新记录
	 * @param strategyGroup
	 */
	public void update(@Param(value="bean") FirewallStrategyGroup strategyGroup);
	
	/**
	 * 根据资产id查询
	 * @return
	 */
	public List<FirewallStrategyGroup> getByAssetId(@Param(value="asset_id") int assetId);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 批量插入
	 * @param groups
	 */
	public void insertBatch(@Param(value="list") List<FirewallStrategyGroup> groups);
	
}
