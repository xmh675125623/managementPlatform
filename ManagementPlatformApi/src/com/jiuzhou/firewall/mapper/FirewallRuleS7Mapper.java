package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRuleS7;

/**
* @author xingmh
* @version 创建时间：2020年8月25日 下午4:58:51
* 类说明
*/
@MapperScan
public interface FirewallRuleS7Mapper {
	
	public void insert(@Param(value="bean") FirewallRuleS7 firewallRuleS7);
	
	public void update(@Param(value="bean") FirewallRuleS7 firewallRuleS7);
	
	public FirewallRuleS7 getById(@Param(value="id") int id);
	
	public void insertBatch(@Param(value="list") List<FirewallRuleS7> ruleS7s);
}
