package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallRuleTemp;

/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午2:52:11
* 类说明
*/
@MapperScan
public interface FirewallRuleTempMapper {

	/**
	 * 根据type查询规则库
	 * @param type
	 * @return
	 */
	public List<FirewallRuleTemp> getByType(@Param(value="type") int type);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallRuleTemp getById(@Param(value="id") int id);
	
}
