package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallNat;

/**
* @author xingmh
* @version 创建时间：2019年1月15日 下午2:33:22
* 类说明
*/
@MapperScan
public interface FirewallNatMapper {
	
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	public FirewallNat getById(@Param(value="id") int id);
	
	/**
	 * 根据设备标识查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallNat> getByDeviceName(@Param(value="device_name") String deviceName);

	/**
	 * 插入记录
	 * @param nat
	 */
	public void insert(@Param(value="bean") FirewallNat nat);
	
	/**
	 * 更新
	 * @param nat
	 */
	public void update(@Param(value="bean") FirewallNat nat);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
}
