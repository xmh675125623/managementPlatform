package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.Function;
import com.jiuzhou.plat.bean.RoleFunction;

/**
 * @author xingmh
 */
@MapperScan
public interface RoleFunctionMapper {

	/**
	 * 根据角色id获取一级菜单
	 * @param roleId 角色id
	 * @return
	 */
	public List<Function> getFirstMenusByRoleId(@Param("role_id") int roleId);
	
	/**
	 * 根据角色id获取二级菜单
	 * @param roleId
	 * @return
	 */
	public List<Function> getSecondMenusByRoleId(@Param("role_id") int roleId);
	
	/**
	 * 根据角色id获取三级菜单
	 * @param roleId
	 * @return
	 */
	public List<Function> getThirdMenusByRoleId(@Param("role_id") int roleId);
	
	/**
	 * 根据根据角色id获取功能列表
	 * @param roleId
	 * @return
	 */
	public List<Function> getFunctionsByRoleId(@Param("role_id") int roleId);
	
	/**
	 * 根绝角色id删除
	 * @param roleId
	 * @return
	 */
	public int deleteByRoleId(@Param("role_id") int roleId);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int insertBath(@Param("list") List<RoleFunction> list);
}
