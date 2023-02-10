package com.jiuzhou.plat.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DeviceCount;
import com.jiuzhou.plat.mapper.PlatDeviceMapper;
import com.jiuzhou.plat.service.PlatStateService;

import net.sf.json.JSONObject;

/**
 * @author xingmh
 * @version 创建时间：2023年1月17日 上午10:25:28 类说明
 */
@Service("PlatStateService")
public class PlatStateServiceImpl extends ServiceBase implements PlatStateService {

	@Autowired
	PlatDeviceMapper platDeviceMapper;

	@Override
	public String getPlatState(JSONObject paramJson) throws Exception {

		CommonResult commonResult = new CommonResult(false, "");

		// 获取设备数量信息
		List<DeviceCount> deviceCountInfo = platDeviceMapper.getDeviceCounts();
		
		
		double cpuUsage = 0.0;
		double memUsage = 0.0;
		if (SystemUtils.IS_OS_LINUX) {
			// 获取cpu使用率
			cpuUsage = getCpuUsage();
			// 获取内存使用率
			memUsage = getMemUsage();
		}
		
		commonResult.setStatus(true);
		commonResult.put("deviceCountInfo", deviceCountInfo);
		commonResult.put("cpuUsage", cpuUsage);
		commonResult.put("memUsage", memUsage);
		return commonResult.toString();

	}

	public static double getCpuUsage() throws Exception {

		double cpuUsed = 0;

		double idleUsed = 0.0;

		Runtime rt = Runtime.getRuntime();

		Process p = rt.exec("top -b -n 1");// call "top" command in linux

		BufferedReader in = null;

		// try

		{

			in = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// in = new BufferedReader(new InputStreamReader(System.in));

			String str = null;

			int linecount = 0;

			while ((str = in.readLine()) != null) {

				linecount++;

				if (linecount == 3) {

					String[] s = str.split("%");

					String idlestr = s[3];

					String idlestr1[] = idlestr.split(" ");

					idleUsed = Double.parseDouble(idlestr1[idlestr1.length - 1]);

					// System.out.println("IdleUsed:XXXXXXXXXXXX"+idleUsed);

					cpuUsed = 100 - idleUsed;

					// System.out.println(cpuUsed);

					break;

				}

			}

			// System.out.println(str);

		}

		// }

		/*
		 * 
		 * }
		 * 
		 * catch (Exception e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } finally {
		 * 
		 * in.close();
		 * 
		 * }
		 */

		return cpuUsed;

	}

	public static double getMemUsage() throws Exception {

		long memUsed = 0;

		long memTotal = 0;

		double memUsage = 0.0;

		Runtime rt = Runtime.getRuntime();

		Process p = rt.exec("top -b -n 1");// call "top" command in linux

		BufferedReader in = null;

		// try

		{

			in = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// in = new BufferedReader(new InputStreamReader(System.in));

			String str = null;

			int linecount = 0;

			while ((str = in.readLine()) != null) {

				linecount++;

				if (linecount == 4) {

					String[] s = str.split("k ");

					String memUsedstr = s[1];

					String memTotalstr = s[0];

					String memUsedstr1[] = memUsedstr.split(" ");

					memUsed = Long.parseLong(memUsedstr1[memUsedstr1.length - 1]);

					String memTotalstr1[] = memTotalstr.split(" ");

					memTotal = Long.parseLong(memTotalstr1[memTotalstr1.length - 1]);

					// System.out.println("IdleUsed:XXXXXXXXXXXX"+idleUsed);

					memUsage = memUsed * 100 / memTotal;

					// System.out.println(memUsed+"\n"+memTotal);

					// System.out.println(memUsage);

					// System.out.println(cpuUsed);

					break;

				}

			}

			// System.out.println(str);

		}

		// }

		/*
		 * 
		 * }
		 * 
		 * catch (Exception e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } finally {
		 * 
		 * in.close();
		 * 
		 * }
		 */

		return memUsage;

	}


}
