package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.AuditModbusFunctionCode;

/**
* @author xingmh
* @version 创建时间：2018年10月18日 上午10:57:50
* 类说明
*/
@MapperScan
public interface AuditModbusFunctionCodeMapper {

	/**
	 * 根据协议类型获取功能码
	 * @param String
	 * @return
	 */
	public List<AuditModbusFunctionCode> getByType(@Param(value="type") int String);
	
}
