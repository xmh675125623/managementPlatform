package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jiuzhou.plat.bean.AuditReportItem;
import com.jiuzhou.plat.bean.IsolationLog;

/**
* @author xingmh
* @version 创建时间：2023年1月9日 上午10:17:33
* 类说明
*/
public interface IsolationLogMapper {
	
	/**
	  *  批量插入
	 * @param list
	 * @param tableName
	 */
	public void insertBatch(@Param(value="list") List<IsolationLog> list, 
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
	public List<IsolationLog> search(@Param(value="table_name") String tableName,
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
													@Param(value="modules") String modules,
													@Param(value="levels") String levels);
	
	/**
	 * 根据id删除
	 * @param tableName
	 * @param ids
	 */
	public void deleteByIds(@Param(value="table_name") String tableName,
							@Param(value="ids") String[] ids);
	
	/**
	 * 清空表
	 * @param tableName
	 */
	public void emptyLog(@Param(value="table_name") String tableName);
	
}
