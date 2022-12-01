package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jiuzhou.plat.bean.AuditWhitelist;

/**
* @author xingmh
* @version 2018年10月9日 下午6:52:46
* 类说明
*/
public interface AuditWhitelistMapper {
	
	/**
	 * 添加白名单
	 * @param auditWhitelist
	 * @return
	 */
	public int insert(@Param(value="bean") AuditWhitelist auditWhitelist);
	
	/**
	 * 获取白名单列表
	 * @return
	 */
	public List<AuditWhitelist> getWhitelistAll();
	
	/**
	 * 根据id获取白名单
	 * @param id
	 * @return
	 */
	public AuditWhitelist getWhitelistById(@Param(value="id") int id);
	
	/**
	 * 根据id变更百名单
	 * @param auditWhitelist
	 * @return
	 */
	public int updateWhitelistById(@Param(value="bean") AuditWhitelist auditWhitelist);
	
	/**
	 * 根据id删除白名单
	 * @param id
	 * @return
	 */
	public int deleteWhitelistById(@Param(value="id") int id);
	
}
