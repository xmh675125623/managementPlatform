package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.PlatIpWhitelist;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午7:18:57
* 类说明
*/
@MapperScan
public interface PlatIpWhitelistMapper {
	
	public int insert(@Param(value="bean") PlatIpWhitelist ipWhitelist);

	public List<PlatIpWhitelist> getAll();
	
	public PlatIpWhitelist getById(@Param(value="id") int id);
	
	public PlatIpWhitelist getByIpAddress(@Param(value="ip") String ipAddress);
	
	public int getAllCount();
	
	public int deleteById(@Param(value="id") int id);
}
