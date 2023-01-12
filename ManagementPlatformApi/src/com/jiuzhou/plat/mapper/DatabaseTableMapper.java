package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.DatabaseTableInfo;

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
	 * 获取防火墙日志表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getFirewallLogTableInfoList();
	
	/**
	 * 获取防火墙日志表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getFirewallLogTableInfoListOrderByDate();
	
	/**
	 * 获取防火墙日志记录表
	 * @return
	 */
	public String getFirstFirewallLogTableName();
	
	
	/**
	 * 获取隔离日志表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getIsolationLogTableInfoList();
	
	/**
	 * 获取隔离日志表的信息列表
	 * @return
	 */
	public List<DatabaseTableInfo> getIsolationLogTableInfoListOrderByDate();
	
	/**
	 * 获取隔离日志记录表
	 * @return
	 */
	public String getFirstIsolationLogTableName();
	
	
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
	 * 创建审计日志表
	 * @param tableName
	 */
	public void createAuditLogTable(@Param(value="table_name")String tableName);
	
	/**
	 * 创建防火墙日志表
	 * @param tableName
	 */
	public void createFirewallLogTable(@Param(value="table_name")String tableName);
	
	/**
	 * 创建隔离日志表
	 * @param tableName
	 */
	public void createIsolationLogTable(@Param(value="table_name")String tableName);
}
