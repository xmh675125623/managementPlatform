package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.Role;

/**
* @author xingmh
* @version 2018年9月5日 下午6:21:51
* 类说明
*/
@MapperScan
public interface RoleMapper {

	/**
	 * 获取角色列表
	 * @return
	 */
	public List<Role> getListNoPage();
	
	/**
	 * 获取角色列表根据id倒序
	 * @return
	 */
	public List<Role> getListNoPageDESC();
	
	/**
	 * 根据角色名称获取角色信息
	 * @param roleName
	 * @return
	 */
	public Role getByName(@Param(value="role_name") String role_name);
	
	/**
	 * 根据id获取角色信息
	 * @param id
	 * @return
	 */
	public Role getById(@Param(value="id") int id);
	
	/**
	 * 根据id变更角色信息
	 * @param bean
	 * @return
	 */
	public int update(@Param(value="bean") Role bean);
	
	/**
	 * 添加角色信息
	 * @param bean
	 * @return
	 */
	public int insert(@Param(value="bean") Role bean);
	
	
}
