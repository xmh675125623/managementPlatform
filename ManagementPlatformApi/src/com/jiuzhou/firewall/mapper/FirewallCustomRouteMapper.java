package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallCustomRoute;

/**
* @author xingmh
* @version 创建时间：2019年3月6日 下午3:29:56
* 类说明
*/
@MapperScan
public interface FirewallCustomRouteMapper {

	/**
	 * 根据设备名称查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallCustomRoute> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallCustomRoute getById(@Param(value="id") int id);
	
	/**
	 * 单条插入
	 * @param route
	 */
	public void insert(@Param(value="bean") FirewallCustomRoute route);
	
	/**
	 * 变更
	 * @param route
	 */
	public void update(@Param(value="bean") FirewallCustomRoute route);
	
	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteById(@Param(value="id") int id);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
}
