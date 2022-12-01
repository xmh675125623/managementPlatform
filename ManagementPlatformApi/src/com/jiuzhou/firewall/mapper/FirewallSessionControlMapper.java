package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallSessionControl;

/**
* @author xingmh
* @version 创建时间：2019年1月22日 下午4:00:13
* 类说明
*/
@MapperScan
public interface FirewallSessionControlMapper {
	
	/**
	 * 根据设备名查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallSessionControl> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallSessionControl getById(@Param(value="id") int id);
	
	/**
	 * 插入一条记录
	 * @param sessionControl
	 */
	public void insert(@Param(value="bean") FirewallSessionControl sessionControl);
	
	/**
	 * 更新记录
	 * @param sessionControl
	 */
	public void update(@Param(value="bean") FirewallSessionControl sessionControl);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
}
