package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.S7FunctionCode;

/**
* @author xingmh
* @version 创建时间：2020年8月25日 下午2:42:30
* 类说明
*/
@MapperScan
public interface S7FunctionCodeMapper {
	
	/**
	 * 根据pduType获取最高等级的功能码列表
	 * @param pduType
	 * @return
	 */
	public List<S7FunctionCode> getSuperByPduType(@Param(value="pdu_type") String pduType);
	
	/**
	 * 根据pduType获取非最高等级的功能码列表
	 * @param pduType
	 * @return
	 */
	public List<S7FunctionCode> getSubByPduType(@Param(value="pdu_type") String pduType);
	
	
}
