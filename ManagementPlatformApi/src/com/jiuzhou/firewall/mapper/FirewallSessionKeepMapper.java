package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallSessionKeep;

/**
* @author xingmh
* @version 创建时间：2019年3月17日 下午9:33:52
* 类说明
*/
@MapperScan
public interface FirewallSessionKeepMapper {

	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	public FirewallSessionKeep getById(@Param(value="id") int id);
	
	/**
	 * 根据设备标识查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallSessionKeep> getByDeviceName(@Param(value="device_name") String deviceName);

	/**
	 * 插入记录
	 * @param nat
	 */
	public void insert(@Param(value="bean") FirewallSessionKeep keep);
	
	/**
	 * 更新
	 * @param nat
	 */
	public void update(@Param(value="bean") FirewallSessionKeep keep);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
}
