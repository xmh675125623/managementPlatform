package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallAsset;

/**
* @author xingmh
* @version 创建时间：2019年1月24日 下午1:40:48
* 类说明
*/
@MapperScan
public interface FirewallAssetMapper {

	/**
	 * 查询所有
	 * @return
	 */
	public List<FirewallAsset> getAll();
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public FirewallAsset getById(@Param(value="id") int id);
	
	/**
	 * 插入一条记录
	 * @param asset
	 */
	public void insert(@Param(value="bean") FirewallAsset asset);
	
	/**
	 * 更新记录
	 * @param asset
	 */
	public void update(@Param(value="bean") FirewallAsset asset);
	
	/**
	 * 批量插入
	 * @param assets
	 */
	public void insertBatch(@Param(value="list") List<FirewallAsset> assets);
	
}
