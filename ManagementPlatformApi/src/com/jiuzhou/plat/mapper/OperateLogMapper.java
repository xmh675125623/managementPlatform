package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.OperateLog;

/**
* @author xingmh
* @version 2018年9月3日 下午9:47:41
* 类说明
*/
@MapperScan
public interface OperateLogMapper {
	
	public void insert(@Param(value="bean") OperateLog bean, 
			@Param(value="table_name") String tableName);
	
	public void update(@Param(value="bean") OperateLog bean, 
			@Param(value="table_name") String tableName);
	
	public void insertBatch(@Param(value="list") List<OperateLog> list, 
			@Param(value="table_name") String tableName);
	
	public List<OperateLog> search(@Param(value="table_name") String tableName,
			@Param(value="start") int start,
			@Param(value="page_size") int pageSize);
	
	public int searchCount(@Param(value="table_name") String tableName);
	
}
