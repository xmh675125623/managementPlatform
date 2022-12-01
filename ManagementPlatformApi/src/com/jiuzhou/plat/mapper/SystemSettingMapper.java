package com.jiuzhou.plat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.plat.bean.DatabaseGlobalVariables;
import com.jiuzhou.plat.bean.SystemSetting;

/**
 * 系统设置mapper
 * @author xingmh
 *
 */
@MapperScan
public interface SystemSettingMapper {
	
	/**
	 * 根据名称获取系统配置
	 * @param name
	 * @return
	 */
	public SystemSetting getByName(@Param(value="name") String name);
	
	/**
	 * 根据id获取系统配置
	 * @param name
	 * @return
	 */
	public SystemSetting getById(@Param(value="id") int id);
	
	/**
	 * 获取设置列表
	 * @return
	 */
	public List<SystemSetting> getList();
	
	/**
	 * 更新
	 * @param setting
	 * @return
	 */
	public int update(@Param(value="bean") SystemSetting setting);
	
	/**
	 * 获取数据库文件存储的文件夹路径
	 * @return
	 */
	public DatabaseGlobalVariables getDatadir();
	
}
