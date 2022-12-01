package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallSession;
import com.jiuzhou.firewall.bean.FirewallSessionSetting;

/**
* @author xingmh
* @version 创建时间：2019年1月21日 下午4:18:40
* 类说明
*/
@MapperScan
public interface FirewallSessionMapper {

	/**
	 * 根据设备名查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallSession> getByDeviceName(@Param(value="device_name") String deviceName,
												@Param(value="source_ip") String sourceIp);
	
	/**
	 * 批量插入
	 * @param sessions
	 */
	public void insertBatch(@Param(value="list") List<FirewallSession> sessions);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名获取会话管理设置信息
	 * @param deviceName
	 */
	public FirewallSessionSetting getSettingByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 插入会话管理相关设置
	 * @param setting
	 */
	public void insertSetting(@Param(value="bean") FirewallSessionSetting setting);
	
	/**
	 * 更新会话管理相关设置
	 * @param setting
	 */
	public void updateSetting(@Param(value="bean") FirewallSessionSetting setting);
	
	/**
	 * 根据设备名删除会话管理设置
	 * @param deviceName
	 */
	public void deleteSettingByDeviceName(@Param(value="device_name") String deviceName);
	
}
