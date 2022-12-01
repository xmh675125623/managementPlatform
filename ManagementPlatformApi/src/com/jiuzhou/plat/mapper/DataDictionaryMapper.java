package com.jiuzhou.plat.mapper;
/**
* @author xingmh
* @version 创建时间：2018年10月17日 下午5:26:17
* 类说明
*/

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.DataDictionaryItem;

@MapperScan
public interface DataDictionaryMapper {
	
	public List<DataDictionaryItem> getByDicCode(@Param(value="dic_code") String dicCode);
	
	public List<DataDictionaryItem> getByDicId(@Param(value="dic_id") String dicId);
	
}
