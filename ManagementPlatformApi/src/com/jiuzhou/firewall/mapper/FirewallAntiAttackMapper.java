package com.jiuzhou.firewall.mapper;

import org.apache.ibatis.annotations.Param;

import com.jiuzhou.firewall.bean.FirewallAntiAttackDos;
import com.jiuzhou.firewall.bean.FirewallAntiAttackScan;

/**
* @author xingmh
* @version 创建时间：2019年1月23日 下午1:52:14
* 类说明
*/
public interface FirewallAntiAttackMapper {
	
	/**
	 * 根据设备名获取dos攻击设置
	 * @param deviceName
	 * @return
	 */
	public FirewallAntiAttackDos getDosByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名获取扫描设置
	 * @param deviceName
	 * @return
	 */
	public FirewallAntiAttackScan getScanByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 更新dos攻击设置
	 * @param antiAttackDos
	 */
	public void updateDos(@Param(value="bean") FirewallAntiAttackDos antiAttackDos);
	
	/**
	 * 更新扫描设置
	 * @param antiAttackScan
	 */
	public void updateScan(@Param(value="bean") FirewallAntiAttackScan antiAttackScan);
	
	/**
	 * 插入dos攻击设置
	 * @param antiAttackDos
	 */
	public void insertDos(@Param(value="bean") FirewallAntiAttackDos antiAttackDos);
	
	/**
	 * 插入扫描设置
	 * @param antiAttackScan
	 */
	public void insertScan(@Param(value="bean") FirewallAntiAttackScan antiAttackScan);
	
	/**
	 * 根据设备名删除dos
	 * @param deviceName
	 */
	public void deleteDosByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名删除scan
	 * @param deviceName
	 */
	public void deleteScanByDeviceName(@Param(value="device_name") String deviceName);
	
}
