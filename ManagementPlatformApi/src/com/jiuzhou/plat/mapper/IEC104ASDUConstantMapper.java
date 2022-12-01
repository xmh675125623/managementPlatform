package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.IEC104ASDUConstant;

/**
* @author xingmh
* @version 创建时间：2019年3月2日 下午3:54:16
* 类说明
*/
@MapperScan
public interface IEC104ASDUConstantMapper {

	public List<IEC104ASDUConstant> getByType(@Param(value="type") int type);
	
}
