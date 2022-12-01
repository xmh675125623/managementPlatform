package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.Function;
/**
 * 
 * @author xingmh
 *
 */
@MapperScan
public interface FunctionMapper {
	
	public List<Function> getFirstMenus();
	
	public List<Function> getSecondMenus();
	
	public List<Function> getThirdMenus();

	public List<Function> getFunctions();
	
	public Function findByMethod(@Param(value="method") String method);
}
