package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.AdminUser;

/**
 * UserMapper
 * @author xingmh
 *
 */
@MapperScan
public interface AdminUserMapper {
	
	/**
	 * 查询用户信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public AdminUser getAdminUserByAccount(@Param(value="account") String account) throws Exception;
	
	/**
	 * 根据id获取用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AdminUser getAdminUserById(@Param(value="id") int id) throws Exception;
	
	/**
	 * 根据id获取用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AdminUser getAdminUserByUserName(@Param(value="userName") String userName) throws Exception;
	
	/**
	 * 变更用户信息
	 * @param adminUser
	 * @return
	 */
	public int updateAdminUser(AdminUser adminUser) throws Exception;
	
	/**
	 * 分页获取用户列表
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AdminUser> getListByPage(@Param(value="start") int start, 
								@Param(value="end") int end) throws Exception;
	
	/**
	 * 获取所有的用户
	 * @return
	 */
	public List<AdminUser> getListNoPage() throws Exception;
	
	/**
	 * 获取用户数量
	 * @return
	 * @throws Exception
	 */
	public int getListCount() throws Exception;
	
	/**
	 * 添加用户信息
	 * @return
	 * @throws Exception
	 */
	public int insert(@Param(value="user") AdminUser user) throws Exception;
	
	/**
	 * 根据用户删除信息
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public int deleteById(@Param(value="uid") int uid) throws Exception;
	
	/**
	 * 根绝角色id获取用户列表
	 * @param roleId
	 * @return
	 */
	public List<AdminUser> getListByRoleId(@Param(value="role_id") int roleId);
}
