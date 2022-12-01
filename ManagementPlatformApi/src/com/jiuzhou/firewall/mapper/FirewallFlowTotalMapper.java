package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallFlowTotal;

/**
* @author xingmh
* @version 创建时间：2019年2月15日 下午2:19:32
* 类说明
*/
@MapperScan
public interface FirewallFlowTotalMapper {

	/**
	 * 根据设备名查询
	 * @return
	 */
	public List<FirewallFlowTotal> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallFlowTotal getById(@Param(value="id") int id); 
	
	/**
	 * 插入
	 * @param firewallFlowTotal
	 */
	public void insert(@Param(value="bean") FirewallFlowTotal firewallFlowTotal);
	
	/**
	 * 更新
	 * @param firewallFlowTotal
	 */
	public void update(@Param(value="bean") FirewallFlowTotal firewallFlowTotal);
	
	/**
	 * 批量更新
	 * @param flowTotals
	 */
	public void updateBatch(@Param(value="list") List<FirewallFlowTotal> flowTotals);
	
	/**
	 * 根据设备名删除
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
}
