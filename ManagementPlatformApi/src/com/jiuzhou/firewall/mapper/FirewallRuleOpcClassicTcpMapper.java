package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRuleOpcClassicTcp;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午5:10:08
* 类说明
*/
@MapperScan
public interface FirewallRuleOpcClassicTcpMapper {

	public void insert(@Param(value="bean") FirewallRuleOpcClassicTcp ruleOpcClassicTcp);
	
	public void update(@Param(value="bean") FirewallRuleOpcClassicTcp ruleOpcClassicTcp);
	
	public FirewallRuleOpcClassicTcp getById(@Param(value="id") int id);
	
	public void insertBatch(@Param(value="list") List<FirewallRuleOpcClassicTcp> opcClassicTcps);
	
}
