package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallDevice;

/**
* @author xingmh
* @version 创建时间：2019年1月8日 下午5:05:40
* 类说明
*/
@MapperScan
public interface FirewallDeviceMapper {
	
	/**
	 * 批量插入
	 * @param list
	 */
	public void insertBatch(@Param(value="list") List<FirewallDevice> list);
	
	/**
	 * 单条插入
	 * @param bean
	 */
	public void insert(@Param(value="bean") FirewallDevice bean);
	
	/**
	 * 根据设备名称列表获取设备
	 * @param names
	 * @return
	 */
	public List<FirewallDevice> getByDeviceNames(@Param(value="names") List<String> names);
	
	/**
	 * 获取所有设备
	 * @return
	 */
	public List<FirewallDevice> getAll();
	
	/**
	 * 根据设备名称获取
	 * @param name
	 * @return
	 */
	public FirewallDevice getByName(@Param(value="name") String name);
	
	public void update(@Param(value="bean") FirewallDevice device);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
}
