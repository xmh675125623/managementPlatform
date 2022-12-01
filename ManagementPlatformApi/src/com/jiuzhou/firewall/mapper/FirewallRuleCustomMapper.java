package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRuleCustom;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午4:53:10
* 类说明
*/
@MapperScan
public interface FirewallRuleCustomMapper {

	public void insert(@Param(value="bean") FirewallRuleCustom ruleCustom);
	
	public void update(@Param(value="bean") FirewallRuleCustom ruleCustom);
	
	public FirewallRuleCustom getById(@Param(value="id") int id);
	
	public void insertBatch(@Param(value="list") List<FirewallRuleCustom> ruleCustoms);
	
}
