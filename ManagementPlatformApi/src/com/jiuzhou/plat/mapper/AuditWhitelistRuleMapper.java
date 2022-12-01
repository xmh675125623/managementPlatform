package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.AuditWhitelist;
import com.jiuzhou.plat.bean.AuditWhitelistRule;

/**
* @author xingmh
* @version 创建时间：2018年10月17日 下午5:52:26
* 类说明
*/
@MapperScan
public interface AuditWhitelistRuleMapper {

	public List<AuditWhitelistRule> getByWhitelistIds(@Param(value="whitelists") List<AuditWhitelist> whitelists);
	
	public AuditWhitelistRule getById(@Param(value="id") int ruleId);
	
	public int insert(@Param(value="bean") AuditWhitelistRule auditWhitelistRule);
	
	public int update(@Param(value="bean") AuditWhitelistRule auditWhitelistRule);
	
	public int deleteById(@Param(value="id") int id);
	
	public int deleteByWhitelistId(@Param(value="whitelist_id") int whitelistId);
}
