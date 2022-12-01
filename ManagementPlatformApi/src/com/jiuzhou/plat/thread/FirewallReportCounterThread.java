package com.jiuzhou.plat.thread;

import java.util.Map;

import org.elasticsearch.common.netty.util.internal.ConcurrentHashMap;

import com.jiuzhou.firewall.bean.FirewallReportCounter;
import com.jiuzhou.firewall.mapper.FirewallReportCounterMapper;
import com.jiuzhou.plat.init.SpringContextHolder;

/**
* @author xingmh
* @version 创建时间：2020年10月20日 上午9:36:57
* 类说明
*/
public class FirewallReportCounterThread implements Runnable {
	
	public static final Map<String, FirewallReportCounter> COUNTER_MAP = new ConcurrentHashMap<>();
	
	private FirewallReportCounterMapper firewallReportCounterMapper;
	
	public FirewallReportCounterThread() {
		this.firewallReportCounterMapper = SpringContextHolder.getBean(FirewallReportCounterMapper.class);
	}

	@Override
	public void run() {
		
		while (true) {
			
			this.counterDataUpdate();
			
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
		
	
	/**
	 * 增加计数
	 * @param deviceName
	 * @param DateStr
	 * @param countType
	 * @param countTitle
	 */
	public void countAdd(String deviceName, String DateStr, int countType, String countTitle) {
		
		String counterKey = DateStr+"-"+countType+"-"+countTitle;
		FirewallReportCounter counter = COUNTER_MAP.get(counterKey);
		if (counter == null) {
			counter = new FirewallReportCounter();
			counter.setCount_date(DateStr);
			counter.setCount_type(countType);
			counter.setDevice_name(deviceName);
			counter.setCount_title(countTitle);
			counter.setCount_num(1);
			COUNTER_MAP.put(counterKey, counter);
		} else {
			
			if (!DateStr.equals(counter.getCount_date())) {
				firewallReportCounterMapper.update(counter);
				counter.setCount_date(DateStr);
				counter.setCount_num(1);
			} else {
				counter.setCount_num(counter.getCount_num() + 1);
			}
			
		}
		
		
	}
	
	/**
	 * 数据存储
	 */
	private void counterDataUpdate() {
		
		for (FirewallReportCounter counter : COUNTER_MAP.values()) {
			
			FirewallReportCounter existCounter = 
					firewallReportCounterMapper.getByDateAndTypeAndTitle(
							counter.getDevice_name(), 
							counter.getCount_date(), 
							counter.getCount_type()+"", 
							counter.getCount_title());
			
			if (existCounter == null) {
				firewallReportCounterMapper.insert(counter);
			} else {
				firewallReportCounterMapper.update(counter);
			}
			
		}
		
	}

}
