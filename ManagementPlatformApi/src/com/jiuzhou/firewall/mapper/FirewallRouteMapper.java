package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRoute;

/**
* @author xingmh
* @version 创建时间：2019年1月16日 下午4:33:14
* 类说明
*/
@MapperScan
public interface FirewallRouteMapper {
	
	/**
	 * 根据设备名称查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallRoute> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallRoute getById(@Param(value="id") int id);
	
	/**
	 * 单条插入
	 * @param route
	 */
	public void insert(@Param(value="bean") FirewallRoute route);
	
	/**
	 * 变更
	 * @param route
	 */
	public void update(@Param(value="bean") FirewallRoute route);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
}
