package com.jiuzhou.firewall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.jiuzhou.firewall.bean.FirewallDevice;

/**
* @author xingmh
* @version 创建时间：2019年1月7日 下午5:05:40
* 类说明
*/
@MapperScan
public interface FirewallDeviceTempMapper {
	
	public void insertBatch(@Param(value="list") List<FirewallDevice> list, @Param(value="aid") int aid);
	
	public void deleteByAid(@Param(value="aid") int aid);
	
	public List<FirewallDevice> getByDeviceNames(@Param(value="aid") int aid, @Param(value="names") List<String> names);
	
}
