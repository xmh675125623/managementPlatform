package com.jiuzhou.plat.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.AuditEventLevel;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.mapper.AuditEventLevelMapper;
import com.jiuzhou.plat.service.AuditEventlevelService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年12月4日 下午5:13:29
* 类说明
*/
@Service("AuditEventlevelService")
public class AuditEventlevelServiceImpl implements AuditEventlevelService {
	
	@Autowired
	private AuditEventLevelMapper auditEventLevelMapper;

	/**
	 * 获取事件分级列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getEventLevelList(JSONObject paramJson) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		List<AuditEventLevel> eventLevels = auditEventLevelMapper.getAll();
		if (eventLevels == null) {
			eventLevels = new ArrayList<>();
		}
		
		commonResult.setStatus(true);
		commonResult.put("list", eventLevels);
		
		return commonResult.toString();
	}

	/**
	 * 更新事件分级
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateEventLevel(JSONObject paramJson) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		if (!paramJson.has("event_level")) {
			commonResult.setErrorMsg("缺少参数event_level");
			return commonResult.toString();
		}
		int event_level = paramJson.getInt("event_level");
		
		if (event_level < 1 || event_level > 3) {
			commonResult.setErrorMsg("参数错误event_level");
			return commonResult.toString();
		}
		
		AuditEventLevel auditEventLevel = auditEventLevelMapper.getById(id);
		
		if (auditEventLevel == null) {
			commonResult.setErrorMsg("记录不存在");
			return commonResult.toString();
		}
		
		auditEventLevel.setEvent_level(event_level);
		auditEventLevelMapper.update(auditEventLevel);
		
		//ToDo 下发
		if (ServiceBase.isLinux()) {
			this.writeToSystem(id, event_level);
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	
	private void writeToSystem(int id, int level) throws Exception {
		
		String filePath = "/jiuzhou/adu/plugin_manager/level_conf/";
		switch (id) {
		case AuditEventLevel.ID_FTP:
			filePath += "ftp";
			break;
			
		case AuditEventLevel.ID_HTTP:
			filePath += "http";
			break;
			
		case AuditEventLevel.ID_MODBUS_TCP:
			filePath += "modbus";
			break;
			
		case AuditEventLevel.ID_POP3:
			filePath += "pop3";
			break;
			
		case AuditEventLevel.ID_SMTP:
			filePath += "smtp";
			break;
			
		case AuditEventLevel.ID_TELNET:
			filePath += "telnet";
			break;

		default:
			return;
		}
		
		//写modbus_tcp规则文件
		BufferedWriter modbusTcpRuleOut = new BufferedWriter(new FileWriter(filePath));
		modbusTcpRuleOut.write(level+"");
		modbusTcpRuleOut.flush();
		modbusTcpRuleOut.close();
	}
	
}
