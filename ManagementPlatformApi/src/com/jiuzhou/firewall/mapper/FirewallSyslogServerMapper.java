package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallSyslogServer;

/**
* @author xingmh
* @version 创建时间：2020年8月19日 下午4:11:49
* 类说明
*/
@MapperScan
public interface FirewallSyslogServerMapper {
	
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	public FirewallSyslogServer getById(@Param(value="id") int id);
	
	/**
	 * 根据设备标识查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallSyslogServer> getByDeviceName(@Param(value="device_name") String deviceName);

	/**
	 * 插入记录
	 * @param nat
	 */
	public void insert(@Param(value="bean") FirewallSyslogServer bean);
	
	/**
	 * 更新
	 * @param nat
	 */
	public void update(@Param(value="bean") FirewallSyslogServer bean);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
}
