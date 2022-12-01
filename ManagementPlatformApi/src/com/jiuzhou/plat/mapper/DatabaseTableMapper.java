package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.AuditLog;
import com.jiuzhou.plat.bean.DatabaseTableInfo;
import com.jiuzhou.plat.bean.OperateLog;

/**
* @author xingmh
* @version 2018年9月28日 上午10:14:27
* 类说明
*/
@MapperScan
public interface DatabaseTableMapper {

	/**
	 * 获取审计记录表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getAuditLogTableInfoList();
	
	/**
	 * 获取审计记录表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getAuditLogTableInfoListOrderByDate();
	
	/**
	 * 获取最早的审计记录表
	 * @return
	 */
	public String getFirstAuditLogTableName();
	
	/**
	 * 获取操作日志表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getOperateLogTableInfoList();
	
	/**
	 * 获取操作日志表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getOperateLogTableInfoListOrderByDate();
	
	/**
	 * 获取最早的操作日志表
	 * @return
	 */
	public String getFirstOperateLogTableName();
	
	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 */
	public String getTableName(@Param(value="table_name")String tableName);
	
	/**
	 * 批量判断表是否存在
	 * @param tableName
	 * @return
	 */
	public List<String> getTableNameBath(@Param(value="table_names") List<String> tableNames);
	
	/**
	 * 创建操作日志表
	 * @param tableName
	 */
	public void createOperateLogTable(@Param(value="table_name")String tableName);
	
	/**
	 * 删除表
	 * @param tableName
	 */
	public void dropTable(@Param(value="table_name")String tableName);
	
	/**
	 * 获取操作日志记录
	 * @param tableName
	 * @return
	 */
	public List<OperateLog> getOperateLogData(@Param(value="table_name")String tableName, 
											@Param(value="startId")int startId, 
											@Param(value="endId")int endId, 
											@Param(value="limit")int limit);
	
	/**
	 * 获取操作日志记录总条数
	 * @param tableName
	 * @return
	 */
	public int getOperateLogDataCount(@Param(value="table_name")String tableName, @Param(value="end_id") int endId);
	
	/**
	 * 获取操作日志记录总条数
	 * @param tableName
	 * @return
	 */
	public int getLastOperateLogId(@Param(value="table_name")String tableName);
	
	
	/**
	 * 获取审计日志记录
	 * @param tableName
	 * @return
	 */
	public List<AuditLog> getAuditLogData(@Param(value="table_name")String tableName, 
										@Param(value="startId")long startId, 
										@Param(value="endId")long endId, 
										@Param(value="limit")int limit);
	
	/**
	 * 获取审计日志记录总条数
	 * @param tableName
	 * @return
	 */
	public int getAuditLogDataCount(@Param(value="table_name")String tableName, @Param(value="end_id") long endId);
	
	/**
	 * 获取最后一条审计记录id
	 * @param tableName
	 * @return
	 */
	public long getLastAuditLogId(@Param(value="table_name")String tableName);
	
	/**
	 * 创建审计日志表
	 * @param tableName
	 */
	public void createAuditLogTable(@Param(value="table_name")String tableName);
}
