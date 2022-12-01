package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRuleCustom;
import com.jiuzhou.firewall.bean.FirewallRuleIEC104;
import com.jiuzhou.firewall.bean.FirewallRuleModbusTcp;
import com.jiuzhou.firewall.bean.FirewallRuleOpcClassicTcp;
import com.jiuzhou.firewall.bean.FirewallRuleS7;
import com.jiuzhou.firewall.bean.FirewallRuleTemp;
import com.jiuzhou.firewall.bean.FirewallStrategy;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午3:48:20
* 类说明
*/
@MapperScan
public interface FirewallStrategyMapper {

	public void insert(@Param(value="bean") FirewallStrategy strategy);
	
	public void insertBatch(@Param(value="list") List<FirewallStrategy> strategys);
	
	public void update(@Param(value="bean") FirewallStrategy strategy);
	
	/**
	 * 根据设备名称查询
	 * @param deviceName
	 * @return
	 */
	public List<FirewallStrategy> getByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据id获取策略
	 * @param id
	 * @return
	 */
	public FirewallStrategy getById(@Param(value="id") int id);
	
	/**
	 * 根据分组id和分页查询策略
	 * @param groupId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<FirewallStrategy> getByGroupIdAndPage(
			@Param(value="groupId") int groupId, 
			@Param(value="start") int start, 
			@Param(value="pageSize") int pageSize,
			@Param(value="condition") String condition);
	
	/**
	 * 根据分组id获取条数
	 * @param groupId
	 * @return
	 */
	public int getCountByGroupId(
			@Param(value="groupId") int groupId,
			@Param(value="condition") String condition);
	
	/**
	 * 获取自定义规则列表
	 * @param groupIds
	 * @return
	 */
	public List<FirewallRuleCustom> getCustomByStrategyIds(
			@Param(value="strategyIds") List<Integer> strategyIds);
	
	/**
	 * 根据策略id获取自定义策略规则
	 * @param StrategyId
	 * @return
	 */
	public FirewallRuleCustom getCustomByStrategyId(
			@Param(value="strategy_id") int strategyId);
	
	
	/**
	 * 根据设备名获取自定义策略规则
	 * @param deviceName
	 * @return
	 */
	public List<FirewallRuleCustom> getCustomByDeviceName(
			@Param(value="device_name") String deviceName);
	
	
	/**
	 * 获取普通/规则列表
	 * @param groupIds
	 * @return
	 */
	public List<FirewallRuleTemp> getCommonByStrategyIds(
			@Param(value="strategyIds") List<Integer> strategyIds);
	 
	/**
	 * 根据策略id获取普通策略规则
	 * @param StrategyId
	 * @return
	 */
	public FirewallRuleTemp getCommonByStrategyId(
			@Param(value="strategy_id") int strategyId);
	
	
	/**
	 * 获取特殊规则列表
	 * @param groupIds
	 * @return
	 */
	public List<FirewallRuleTemp> getSpecialByStrategyIds(
			@Param(value="strategyIds") List<Integer> strategyIds);
	
	/**
	 * 根据策略id获取特殊策略规则
	 * @param StrategyId
	 * @return
	 */
	public FirewallRuleTemp getSpecialByStrategyId(
			@Param(value="strategy_id") int strategyId);
	
	/**
	 * 获取 Opc Classic Tcp 规则列表
	 * @param groupIds
	 * @return
	 */
	public List<FirewallRuleOpcClassicTcp> getOpcClassicTcpByStrategyIds(
			@Param(value="strategyIds") List<Integer> strategyIds);
	
	/**
	 * 根据策略id获取 Opc Classic Tcp 策略规则
	 * @param StrategyId
	 * @return
	 */
	public FirewallRuleOpcClassicTcp getOpcClassicTcpByStrategyId(
			@Param(value="strategy_id") int strategyId);
	
	/**
	 * 根据设备名获取 Opc Classic Tcp 策略规则
	 * @param StrategyId
	 * @return
	 */
	public List<FirewallRuleOpcClassicTcp> getOpcClassicTcpByDeviceName(
			@Param(value="device_name") String device_name);
	
	/**
	 * 获取 Modbus Tcp 规则列表
	 * @param groupIds
	 * @return
	 */
	public List<FirewallRuleModbusTcp> getModbusTcpByStrategyIds(
			@Param(value="strategyIds") List<Integer> strategyIds);
	
	/**
	 * 根据策略id获取 Modbus Tcp 策略规则
	 * @param StrategyId
	 * @return
	 */
	public FirewallRuleModbusTcp getModbusTcpByStrategyId(
			@Param(value="strategy_id") int strategyId);
	
	
	/**
	 * 根据设备名获取Modbus Tcp 策略规则
	 * @param device_name
	 * @return
	 */
	public List<FirewallRuleModbusTcp> getModbusTcpByDeviceName(
			@Param(value="device_name") String device_name);
	
	/**
	 * 获取 IEC104 规则列表
	 * @param groupIds
	 * @return
	 */
	public List<FirewallRuleIEC104> getIEC104ByStrategyIds(
			@Param(value="strategyIds") List<Integer> strategyIds);
	
	/**
	 * 根据策略id获取 IEC104策略规则
	 * @param StrategyId
	 * @return
	 */
	public FirewallRuleIEC104 getIEC104ByStrategyId(
			@Param(value="strategy_id") int strategyId);
	
	/**
	 * 根据设备名获取IEC104策略规则
	 * @param deviceName
	 * @return
	 */
	public List<FirewallRuleIEC104> getIEC104ByDeviceName(
			@Param(value="device_name") String deviceName);
	
	/**
	 * 获取S7规则列表
	 * @param strategyIds
	 * @return
	 */
	public List<FirewallRuleS7> getS7ByStrategyIds(
			@Param(value="strategyIds") List<Integer> strategyIds);
	
	/**
	 * 根据策略id获取S7策略规则
	 * @param StrategyId
	 * @return
	 */
	public FirewallRuleS7 getS7ByStrategyId(
			@Param(value="strategy_id") int strategyId);
	
	/**
	 * 根据设备名获取s7策略规则
	 * @param deviceName
	 * @return
	 */
	public List<FirewallRuleS7> getS7ByDeviceName(
			@Param(value="device_name") String deviceName);
	
	/**
	 * 根绝设备名删除策略
	 * @param deviceName
	 */
	public void deleteByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根绝设备名删除自定义规则
	 * @param deviceName
	 */
	public void deleteCustomByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根据设备名删除modbus tcp规则
	 * @param deviceName
	 */
	public void deleteModbusTcpByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根绝设备名删除opc classic tcp规则
	 * @param deviceName
	 */
	public void deleteOpcClassicTcpByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根绝设备名删除opc classic tcp规则
	 * @param deviceName
	 */
	public void deleteIEC104ByDeviceName(@Param(value="device_name") String deviceName);
	
	/**
	 * 根绝设备名删除s7规则
	 * @param deviceName
	 */
	public void deleteS7ByDeviceName(@Param(value="device_name") String deviceName);
	
}
