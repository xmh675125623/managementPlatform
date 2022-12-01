package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.AuditEventLevel;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午4:25:04
* 类说明
*/
@MapperScan
public interface AuditEventLevelMapper {
	
	public AuditEventLevel getById(@Param(value="id") int id);
	
	public List<AuditEventLevel> getAll();
	
	public int update(@Param(value="bean") AuditEventLevel bean);
	
}
