package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallDeviceEthernet;

/**
* @author xingmh
* @version 创建时间：2019年1月16日 下午2:24:37
* 类说明
*/
@MapperScan
public interface FirewallDeviceEthernetMapper {
	
	/**
	 * 根据设备名查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallDeviceEthernet> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据模式查询
	 * @param mode
	 * @return
	 */
	public List<FirewallDeviceEthernet> getByMode(
			@Param(value="device_name") String deviceName, 
			@Param(value="mode") int mode);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallDeviceEthernet getById(@Param(value="id") int id);
	
	/**
	 * 根据设备名和序号查询
	 * @param deviceName
	 * @param number
	 * @return
	 */
	public FirewallDeviceEthernet getByDeviceNameAndNumber(
			@Param(value="device_name") String deviceName, 
			@Param(value="number") int number);
	
	/**
	 * 更新
	 * @param ethernet
	 */
	public void update(@Param(value="bean") FirewallDeviceEthernet ethernet);
	
	/**
	 * 插入
	 * @param ethernet
	 */
	public void insert(@Param(value="bean") FirewallDeviceEthernet ethernet);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
}
