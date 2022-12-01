package com.jiuzhou.plat.cache;

/**
 * 可定期清除缓存类，继承后，定期清理线程可根据此类进行清理动作
 * @author xingmh
 *
 */
public class TimedClearBase {
	
	private long		cache_add_time;		//缓存添加时间ms
	private long		cache_save_time;	//缓存保留时间ms
	
	public TimedClearBase(long cache_add_time, long cache_save_time) {
		this.setCache_add_time(cache_add_time);
		this.setCache_save_time(cache_save_time);
	}

	public long getCache_add_time() {
		return cache_add_time;
	}

	public void setCache_add_time(long cache_add_time) {
		this.cache_add_time = cache_add_time;
	}

	public long getCache_save_time() {
		return cache_save_time;
	}

	public void setCache_save_time(long cache_save_time) {
		this.cache_save_time = cache_save_time;
	}

	
}
