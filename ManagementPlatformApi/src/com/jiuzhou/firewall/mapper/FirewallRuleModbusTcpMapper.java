package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRuleModbusTcp;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午5:14:04
* 类说明
*/
@MapperScan
public interface FirewallRuleModbusTcpMapper {
	
	public void insert(@Param(value="bean") FirewallRuleModbusTcp ruleModbusTcp);
	
	public void update(@Param(value="bean") FirewallRuleModbusTcp ruleModbusTcp);
	
	public FirewallRuleModbusTcp getById(@Param(value="id") int id);
	
	public void insertBatch(@Param(value="list") List<FirewallRuleModbusTcp> ruleModbusTcps);
	
}
