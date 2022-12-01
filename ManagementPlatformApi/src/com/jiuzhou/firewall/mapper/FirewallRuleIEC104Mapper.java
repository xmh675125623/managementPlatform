package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRuleIEC104;

/**
* @author xingmh
* @version 创建时间：2019年3月2日 下午4:03:20
* 类说明
*/
@MapperScan
public interface FirewallRuleIEC104Mapper {

	public void insert(@Param(value="bean") FirewallRuleIEC104 ruleIEC104);
	
	public void update(@Param(value="bean") FirewallRuleIEC104 ruleIEC104);
	
	public FirewallRuleIEC104 getById(@Param(value="id") int id);
	
	public void insertBatch(@Param(value="list") List<FirewallRuleIEC104> ruleIEC104s);
}
