package com.jiuzhou.plat.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.AuditWhitelistRuleTemplate;

/**
* @author xingmh
* @version 创建时间：2018年10月17日 下午6:22:07
* 类说明
*/
@MapperScan
public interface AuditWhitelistRuleTemplateMapper {
	
	public AuditWhitelistRuleTemplate getByType(@Param(value="type") String type);
	
}
