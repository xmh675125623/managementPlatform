package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jiuzhou.plat.bean.AuditReportItem;
import com.jiuzhou.plat.bean.FirewallLog;

/**
* @author xingmh
* @version 创建时间：2023年1月5日 上午10:16:48
* 类说明
*/
public interface FirewallLogMapper {
	
	/**
	  *  批量插入
	 * @param list
	 * @param tableName
	 */
	public void insertBatch(@Param(value="list") List<FirewallLog> list, 
			@Param(value="table_name") String tableName);
	
	/**
	  *  批量插入
	 * @param list
	 * @param tableName
	 */
	public void insertBatchBySql(@Param(value="sql") String sql, 
			@Param(value="table_name") String tableName);
	
	/**
	  *  批量插入
	 * @param list
	 * @param tableName
	 */
	public void insertBatchBySqls(@Param(value="sql") List<String> sql, 
			@Param(value="table_name") String tableName);
	
	/**
	  *  分页条件查询 
	 * @param tableName
	 * @param start
	 * @param pageSize
	 * @param condition
	 */
	public List<FirewallLog> search(@Param(value="table_name") String tableName,
			@Param(value="start") long start,
			@Param(value="page_size") int pageSize,
			@Param(value="condition") String condition,
			@Param(value="sort_field") String sortField,
			@Param(value="sort_order") String sortOrder);
	
	/**
	  *  分页查询条数
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public int searchCount(@Param(value="table_name") String tableName,
			@Param(value="condition") String condition);
	
	/**
	 * 根据等级获取报表数据
	 * @param tableName
	 * @return
	 */
	public List<AuditReportItem> getReportByLevel(@Param(value="table_name") String tableName);
	
	/**
	 * 根据模块获取报表数据
	 * @param tableName
	 * @return
	 */
	public List<AuditReportItem> getReportByModule(@Param(value="table_name") String tableName,
													@Param(value="ip") String ip,
													@Param(value="modules") String modules,
													@Param(value="levels") String levels);
	
}
