package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallMac;

/**
* @author xingmh
* @version 创建时间：2019年1月14日 下午2:11:11
* 类说明
*/
@MapperScan
public interface FirewallMacMapper {
	
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	public FirewallMac getById(@Param(value="id") int id);
	
	/**
	 * 根据设备名称获取列表
	 * @param deviceName
	 * @return
	 */
	public List<FirewallMac> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名称和ip地址获取
	 * @param deviceName
	 * @param ip
	 * @return
	 */
	public FirewallMac getByDeviceNameAndIp(@Param(value="device_name") String deviceName,
											@Param(value="ip_address") String ip);
	
	/**
	 * 插入
	 * @param firewallMac
	 */
	public void insert(@Param(value="bean") FirewallMac firewallMac);
	
	/**
	 * 变更
	 * @param firewallMac
	 */
	public void update(@Param(value="bean") FirewallMac firewallMac);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名获取扫描到的mac绑定信息
	 * @param deviceName
	 */
	public List<FirewallMac> getScanMacByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据ip地址和mac地址查询扫描mac
	 * @param deviceName
	 * @return
	 */
	public FirewallMac getScanMacByMacAndIp(@Param(value="device_name") String deviceName,
											@Param(value="mac") String mac, 
											@Param(value="ip") String ip);
	
	/**
	 * 根据设备名删除扫描到的mac信息
	 * @param deviceName
	 */
	public void deleteScanMacByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 插入扫描mac信息
	 * @param firewallMac
	 */
	public void insertScanMac(@Param(value="bean") FirewallMac firewallMac);
	
}
