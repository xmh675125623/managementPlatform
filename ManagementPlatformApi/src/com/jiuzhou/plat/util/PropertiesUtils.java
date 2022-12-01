package com.jiuzhou.plat.util;

import java.io.FileInputStream;
import java.util.Properties;

import com.jiuzhou.plat.filter.SimpleCORSFilter;

/**
* @author xingmh
* @version 创建时间：2018年11月14日 下午6:49:43
* 类说明
*/
public class PropertiesUtils {
	
	private static Properties prop = new Properties();
	
	static {
		try {
			FileInputStream in = new FileInputStream(SimpleCORSFilter.class.getResource("/").getFile()+"jdbc.properties");
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getProp(String name){
		return prop.getProperty(name);
	}
	
}
