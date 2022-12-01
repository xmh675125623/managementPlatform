package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallReportCounter;

/**
* @author xingmh
* @version 创建时间：2020年9月29日 上午10:32:48
* 类说明
*/
@MapperScan
public interface FirewallReportCounterMapper {

	/**
	 * 根据设备名、日期、类型、题目获取记录
	 * @param deviceName
	 * @param counterDate
	 * @param counterType
	 * @param counterTitle
	 * @return
	 */
	public FirewallReportCounter getByDateAndTypeAndTitle(@Param(value="device_name") String deviceName,
			@Param(value="count_date") String countDate,
			@Param(value="count_type") String countType,
			@Param(value="count_title") String countTitle);
	
	/**
	 * 插入记录
	 * @param counter
	 */
	public void insert(@Param(value="bean") FirewallReportCounter counter);
	
	public void update(@Param(value="bean") FirewallReportCounter counter);
	
	/**
	 * 根据设备名、日期区间、类型获取记录列表
	 * @param deviceName
	 * @param counterDate
	 * @param counterType
	 * @return
	 */
	public List<FirewallReportCounter> getByDateAndType(@Param(value="device_name") String deviceName,
			@Param(value="count_date_start") String countDateStart,
			@Param(value="count_date_end") String countDateEnd,
			@Param(value="count_type") int countType);
	
	
	/**
	 * 根据设备名、日期区间、类型获取根据type分组后统计数据
	 * @param deviceName
	 * @param counterDate
	 * @param counterType
	 * @return
	 */
	public List<FirewallReportCounter> selectGroupByTitle(@Param(value="device_name") String deviceName,
			@Param(value="count_date_start") String countDateStart,
			@Param(value="count_date_end") String countDateEnd,
			@Param(value="count_type") int countType);
}
