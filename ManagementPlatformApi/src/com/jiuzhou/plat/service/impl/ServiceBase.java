package com.jiuzhou.plat.service.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.SystemUtils;
/**
 * service 父类
 * @author xingmh
 *
 */
public class ServiceBase {
	
	private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<>();
	
	public static final String CURRENT_LOG_DESCRIPTION = "currentLogDescription";
	
	/**
	 * 登录验证码
	 */
	public static final String CACHE_LOGIN_VALIDATE_CODE = "login_validate_code_";

	/**
	 * 登录信息
	 */
	public static final String CACHE_LOGIN_INFO = "login_info_";
	
	/**
	 * 系统设置
	 */
	public static final String CACHE_SYSTEM_SETTING = "system_setting_";
	
	/**
	 * 功能
	 */
	public static final String CACHE_FUNCTION = "function_";
	
	/**
	 * 操作日志表名列表
	 */
	public static final String CACHE_OPERATE_LOG_TABLES = "operate_log_tables";
	
	/**
	 * 审计日志表名
	 */
	public static final String CACHE_AUDIT_LOG_TABLE = "audit_log_table";
	
	/**
	 * 防火墙日志表名
	 */
	public static final String CACHE_FIREWALL_LOG_TABLE = "firewall_log_table";
	
	/**
	 * 网关日志表名
	 */
	public static final String CACHE_GATEWAY_LOG_TABLE = "gateway_log_table";
	
	/**
	 * IDS日志表名
	 */
	public static final String CACHE_IDS_LOG_TABLE = "ids_log_table";
	
	/**
	 * 隔离日志表名
	 */
	public static final String CACHE_ISOLATION_LOG_TABLE = "isolation_log_table";
	
	/**
	 * 系统告警列表
	 */
	public static final String CACHE_SYSTEM_WARNING = "cache_system_warning";
	
	/**
	 * 数据库文件存储目录
	 */
	public static final String CACHE_DATABASE_DATADIR = "cache_database_datadir";
	
	/**
	 * 文件下载信息
	 */
	public static final String FIRE_DOWNLOAD_INFO = "fire_download_info_";
	
	
	static{
		CACHE_MAP.put(CACHE_OPERATE_LOG_TABLES, new ArrayList<String>());
	}
	
	/**
	 * 添加缓存信息
	 * @param object
	 */
	public final void setCache(String key, Object object) {
		CACHE_MAP.put(key, object);
	}
	
	/**
	 * 添加缓存信息
	 * @param object
	 */
	public static final void setCacheStatic(String key, Object object) {
		CACHE_MAP.put(key, object);
	}
	
	/**
	 * 获取缓存信息
	 * @param key
	 * @param clazz
	 * @return
	 */
	public final <T> T getCache(String key, Class<T> clazz) {
		Object obj = CACHE_MAP.get(key);
		if (obj == null) {
			return null;
		}
		T t = null;
		try {
			t = clazz.cast(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static final <T> T getCacheStatic(String key, Class<T> clazz) {
		Object obj = CACHE_MAP.get(key);
		if (obj == null) {
			return null;
		}
		T t = null;
		try {
			t = clazz.cast(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 根据key值删除缓存信息
	 * @param key
	 */
	public final void deleteCache(String key) {
		CACHE_MAP.remove(key);
	}
	
	/**
	 * 获取缓存Map
	 * @return
	 */
	public static Map<String, Object> getCacheMap() {
		return CACHE_MAP;
	}
	
	public static boolean isLinux() { 
		return SystemUtils.IS_OS_LINUX;
	}
	
}
