package com.jiuzhou.firewall.service.impl;

import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallFlowTotal;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallFlowTotalMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.service.FirewallFlowTotalService;
import com.jiuzhou.firewall.utils.EventUtils;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;
import com.jiuzhou.plat.util.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年2月15日 下午2:46:48
* 类说明
*/
@Service("FirewallFlowTotalService")
public class FirewallFlowTotalServiceImpl implements FirewallFlowTotalService {

	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallFlowTotalMapper firewallFlowTotalMapper;
	
	/**
	 * 根据设备标识获取统计列表
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String listByDeviceName(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取设备列表用于页面下拉选择
		JSONArray deviceArray = firewallDeviceService.getForSelectTag();
		commonResult.put("deviceArray", deviceArray);
		
		FirewallDevice device = null;
		if (paramJson.has("deviceName")) {
			device = firewallDeviceMapper.getByName(paramJson.getString("deviceName"));
		}else if (deviceArray.size() > 0) {
			device = firewallDeviceMapper.getByName(deviceArray.getJSONObject(0).getString("deviceName"));
		}
		
		if (device == null) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		
		//获取统计列表
		List<FirewallFlowTotal> firewallFlowTotals = firewallFlowTotalMapper.getByDeviceName(device.getDevice_name());
		if (firewallFlowTotals == null) {
			firewallFlowTotals = new ArrayList<>();
		}
		
		commonResult.put("device", device);
		commonResult.put("totals", firewallFlowTotals);
		
		Map<String, String> maps = TCPSocketUtil.getNetworkSocketConfig();
		maps.put("mip", device.getIp_address());
		byte[] decryptbyte = null;
		try {
			decryptbyte = TCPSocketUtil.networkManageWrite("0x20 0x209 cacl", 1, maps);// 接受返回值
		} catch (SocketTimeoutException e) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		if (decryptbyte != null && decryptbyte.length > 10) {
			decryptbyte = EventUtils.decrypt(decryptbyte);
			String decryptStr = new String(decryptbyte);
			String resu = decryptStr.substring(10, 11);
			if (resu.equals("1")) {
				String[] param = decryptStr.substring(12).split("@");
				if (param[0].indexOf(";") > 0) {
					String[] paramss = param[0].split(";");
					for (int i = 0; i < paramss.length; i++) {
						if (firewallFlowTotals.size() > i) {
							firewallFlowTotals.get(i).setFlow(paramss[i] + "B");
						}
					}
					commonResult.setStatus(true);
					return commonResult.toString();
				} else {
					// 成功 单没有会话
					commonResult.setStatus(true);
					return commonResult.toString();
				}

			} else {
				// 失败
				commonResult.setStatus(true);
				return commonResult.toString();
			}
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 添加
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String add(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		
		//协议类型
		if (!paramJson.has("protocol")) {
			commonResult.setErrorMsg("缺少参数protocol");
			return commonResult.toString();
		}
		String protocol = paramJson.getString("protocol");
		
		//源ip
		String sip = null;
		if (paramJson.has("sip")) {
			sip = paramJson.getString("sip");
		}
		
		//目的ip
		String dip = null;
		if (paramJson.has("dip")) {
			dip = paramJson.getString("dip");
		}
		
		//源端口
		String sport = null;
		if (paramJson.has("sport")) {
			sport = paramJson.getString("sport");
		}
		
		//目的端口
		String dport = null;
		if (paramJson.has("dport")) {
			dport = paramJson.getString("dport");
		}
		
		//开始时间
		String startTime = null;
		if (paramJson.has("startTime")) {
			startTime = paramJson.getString("startTime");
		}
		
		//结束时间
		String endTime = null;
		if (paramJson.has("endTime")) {
			endTime = paramJson.getString("endTime");
		}
		
		//获取设备信息判断设备是否存在
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		FirewallFlowTotal flowTotal = new FirewallFlowTotal();
		flowTotal.setDevice_name(deviceName);
		flowTotal.setProtocol(protocol);
		flowTotal.setSip(sip);
		flowTotal.setDip(dip);
		flowTotal.setSport(sport);
		flowTotal.setDport(dport);
		if (StringUtils.isNotBlank(startTime)) {
			flowTotal.setStart_time(DateUtils.parseSimpleDate(startTime));
		}
		if (StringUtils.isNotBlank(endTime)) {
			flowTotal.setEnd_time(DateUtils.parseSimpleDate(endTime));
		}
		flowTotal.setAdd_time(new Date());
		flowTotal.setAdd_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallFlowTotalMapper.insert(flowTotal);
		
		//操作日志描述
		String logDesc = "[设备："+deviceName+"]\n"
						+ "[协议类型："+protocol+"]\n"
						+ "[源IP："+sip+"]\n"
						+ "[目的IP："+dip+"]\n"
						+ "[源端口："+sport+"]\n"
						+ "[目的端口："+dport+"]\n"
						+ "[开始时间："+startTime+"]\n"
						+ "[结束时间："+endTime+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		String errorMsg = this.giveFlowTotalToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}

		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * 下发流量统计配置
	 * @return
	 */
	private String giveFlowTotalToDevice(FirewallDevice device) {
		
		//获取流量统计列表
		List<FirewallFlowTotal> totals = 
				firewallFlowTotalMapper.getByDeviceName(device.getDevice_name());
		
		//组织指令
		StringBuffer instruction = new StringBuffer();
		instruction.append("0x20 0x209 flush;");
		if (totals != null) {
			for (FirewallFlowTotal total : totals) {
				//时间处理,减8小时
				Calendar calendar = Calendar.getInstance();
				if (total.getStart_time() != null) {
					calendar.setTime(total.getStart_time());
					calendar.add(Calendar.HOUR_OF_DAY, -8);
					total.setStart_time(calendar.getTime());
				}
				if (total.getEnd_time() != null) {
					calendar.setTime(total.getEnd_time());
					calendar.add(Calendar.HOUR_OF_DAY, -8);
					total.setEnd_time(calendar.getTime());
				}
				
				StringBuffer tempBuffer = new StringBuffer();
				tempBuffer.append("0x20 0x209 ");
				if(StringUtils.isNotBlank(total.getProtocol()) && !("IP").equals(total.getProtocol())){
					tempBuffer.append("-p ").append(total.getProtocol());
				}
				if(StringUtils.isNotBlank(total.getSip())){
					tempBuffer.append(" -s ").append(total.getSip());
				}
				if(StringUtils.isNotBlank(total.getSport())){
					tempBuffer.append(" --sport ").append(total.getSport());
				}
				if(StringUtils.isNotBlank(total.getDip())){
					tempBuffer.append(" -d ").append(total.getDip());
				}
				if(StringUtils.isNotBlank(total.getDport())){
					tempBuffer.append(" --dport ").append(total.getDport());
				}
				
				if(total.getStart_time() != null){
					tempBuffer
					.append(" -m time --datestart ")
					.append(DateUtils.toTDate(total.getStart_time()));
				}
				if(total.getEnd_time() != null){
					tempBuffer
					.append(" --datestop ")
					.append(DateUtils.toTDate(total.getEnd_time()));
				}
				
				//如果是全部则拼接-j ACCEPT
				if ("0x20 0x209 ".equals(tempBuffer.toString())) {
					tempBuffer.append("-s 0.0.0.0/0 -d 0.0.0.0/0 ");
				}
				
				instruction.append(tempBuffer).append(";");
			}
		}
		instruction.append("0x20 0x209 last;");
		
		//socket配置信息
		Map<String, String> socketConfig = TCPSocketUtil.getNetworkSocketConfig();
		socketConfig.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction.toString(), socketConfig);
		
	}

	/**
	 * 编辑
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String update(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//记录id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		
		//协议类型
		if (!paramJson.has("protocol")) {
			commonResult.setErrorMsg("缺少参数protocol");
			return commonResult.toString();
		}
		String protocol = paramJson.getString("protocol");
		
		//源ip
		String sip = null;
		if (paramJson.has("sip")) {
			sip = paramJson.getString("sip");
		}
		
		//目的ip
		String dip = null;
		if (paramJson.has("dip")) {
			dip = paramJson.getString("dip");
		}
		
		//源端口
		String sport = null;
		if (paramJson.has("sport")) {
			sport = paramJson.getString("sport");
		}
		
		//目的端口
		String dport = null;
		if (paramJson.has("dport")) {
			dport = paramJson.getString("dport");
		}
		
		//开始时间
		String startTime = null;
		if (paramJson.has("startTime")) {
			startTime = paramJson.getString("startTime");
		}
		
		//结束时间
		String endTime = null;
		if (paramJson.has("endTime")) {
			endTime = paramJson.getString("endTime");
		}
		
		//获取记录
		FirewallFlowTotal flowTotal = firewallFlowTotalMapper.getById(id);
		if (flowTotal == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		flowTotal.setProtocol(protocol);
		flowTotal.setSip(sip);
		flowTotal.setDip(dip);
		flowTotal.setSport(sport);
		flowTotal.setDport(dport);
		if (StringUtils.isNotBlank(startTime)) {
			flowTotal.setStart_time(DateUtils.parseSimpleDate(startTime));
		} else {
			flowTotal.setStart_time(null);
		}
		if (StringUtils.isNotBlank(endTime)) {
			flowTotal.setEnd_time(DateUtils.parseSimpleDate(endTime));
		} else {
			flowTotal.setEnd_time(null);
		}
		flowTotal.setUpdate_time(new Date());
		flowTotal.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallFlowTotalMapper.update(flowTotal);
		
		//操作日志描述
		String logDesc = "[设备："+flowTotal.getDevice_name()+"]\n"
						+ "[协议类型："+protocol+"]\n"
						+ "[源IP："+sip+"]\n"
						+ "[目的IP："+dip+"]\n"
						+ "[源端口："+sport+"]\n"
						+ "[目的端口："+dport+"]\n"
						+ "[开始时间："+startTime+"]\n"
						+ "[结束时间："+endTime+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(flowTotal.getDevice_name());
		String errorMsg = this.giveFlowTotalToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}

		commonResult.setStatus(true);
		return commonResult.toString();

	}
	
	/**
	 * 删除
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String delete(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//记录id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//获取记录
		FirewallFlowTotal flowTotal = firewallFlowTotalMapper.getById(id);
		if (flowTotal == null) {
			commonResult.setErrorMsg("记录不存在");
			commonResult.setStatus(false);
			return commonResult.toString();
		}
		
		flowTotal.setDelete_flag(1);
		flowTotal.setUpdate_time(new Date());
		flowTotal.setUpdate_user(ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		firewallFlowTotalMapper.update(flowTotal);
		
		//操作日志描述
		String logDesc = "[设备："+flowTotal.getDevice_name()+"]\n"
						+ "[协议类型："+flowTotal.getProtocol()+"]\n"
						+ "[源IP："+flowTotal.getSip()+"]\n"
						+ "[目的IP："+flowTotal.getDip()+"]\n"
						+ "[源端口："+flowTotal.getSport()+"]\n"
						+ "[目的端口："+flowTotal.getDport()+"]\n"
						+ "[开始时间："+DateUtils.toSimpleDateTime(flowTotal.getStart_time())+"]\n"
						+ "[结束时间："+DateUtils.toSimpleDateTime(flowTotal.getEnd_time())+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);

		//设备下发
		FirewallDevice device = firewallDeviceMapper.getByName(flowTotal.getDevice_name());
		String errorMsg = this.giveFlowTotalToDevice(device);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 更新统计信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateFlow(JSONObject paramJson, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 导出流量统计信息
	 * @param paramJson
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	public String exportFlow(JSONObject paramJson, HttpServletResponse response) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			return commonResult.toString();
		}
		
		//获取统计列表
		List<FirewallFlowTotal> firewallFlowTotals = firewallFlowTotalMapper.getByDeviceName(device.getDevice_name());
		if (firewallFlowTotals == null) {
			firewallFlowTotals = new ArrayList<>();
		}
		
		Map<String, String> maps = TCPSocketUtil.getNetworkSocketConfig();
		maps.put("mip", device.getIp_address());
		byte[] decryptbyte = null;
		try {
			decryptbyte = TCPSocketUtil.networkManageWrite("0x20 0x209 cacl", 1, maps);// 接受返回值
		} catch (SocketTimeoutException e) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		if (decryptbyte != null && decryptbyte.length > 10) {
			decryptbyte = EventUtils.decrypt(decryptbyte);
			String decryptStr = new String(decryptbyte);
			String resu = decryptStr.substring(10, 11);
			if (resu.equals("1")) {
				String[] param = decryptStr.substring(12).split("@");
				if (param[0].indexOf(";") > 0) {
					String[] paramss = param[0].split(";");
					for (int i = 0; i < paramss.length; i++) {
						if (firewallFlowTotals.size() > i) {
							firewallFlowTotals.get(i).setFlow(paramss[i] + "B");
						}
					}
				}
			}
		}
		
		//声明一个工作簿  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        //生成一个表格  
        HSSFSheet sheet = workbook.createSheet(device.getDevice_name()+"_"+device.getDevice_mark()+"_流量统计");  
        //设置表格默认列宽度为15个字符  
        sheet.setDefaultColumnWidth(20);  
        //生成一个样式，用来设置标题样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        //设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式,用于设置内容样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        //产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        HSSFCell cell = row.createCell(0);  
        cell.setCellStyle(style);  
        HSSFRichTextString text = new HSSFRichTextString("协议类型");  
        cell.setCellValue(text);  
        
        HSSFCell cell1 = row.createCell(1);  
        cell1.setCellStyle(style);  
        HSSFRichTextString text1 = new HSSFRichTextString("源ip");  
        cell1.setCellValue(text1); 
        
        HSSFCell cell2 = row.createCell(2);  
        cell2.setCellStyle(style);  
        HSSFRichTextString text2 = new HSSFRichTextString("目的ip");  
        cell2.setCellValue(text2); 
        
        HSSFCell cell3 = row.createCell(3);  
        cell3.setCellStyle(style);  
        HSSFRichTextString text3 = new HSSFRichTextString("源端口");  
        cell3.setCellValue(text3); 
        
        HSSFCell cell4 = row.createCell(4);  
        cell4.setCellStyle(style);  
        HSSFRichTextString text4 = new HSSFRichTextString("目的端口");  
        cell4.setCellValue(text4); 
        
        HSSFCell cell5 = row.createCell(5);  
        cell5.setCellStyle(style);  
        HSSFRichTextString text5 = new HSSFRichTextString("开始时间");  
        cell5.setCellValue(text5); 
        
        HSSFCell cell6 = row.createCell(6);  
        cell6.setCellStyle(style);  
        HSSFRichTextString text6 = new HSSFRichTextString("结束时间");  
        cell6.setCellValue(text6); 
        
        HSSFCell cell7 = row.createCell(7);  
        cell7.setCellStyle(style);  
        HSSFRichTextString text7 = new HSSFRichTextString("流量统计");  
        cell7.setCellValue(text7); 
        
        for (int i=0;i<firewallFlowTotals.size();i++) {  
        	FirewallFlowTotal flowTotal = firewallFlowTotals.get(i);
            row = sheet.createRow(i+1);  
            int j = 0;  
            row.createCell(j++).setCellValue(new HSSFRichTextString(flowTotal.getProtocol()));
            row.createCell(j++).setCellValue(new HSSFRichTextString(flowTotal.getSip()));
            row.createCell(j++).setCellValue(new HSSFRichTextString(flowTotal.getDip()));
            row.createCell(j++).setCellValue(new HSSFRichTextString(flowTotal.getSport()));
            row.createCell(j++).setCellValue(new HSSFRichTextString(flowTotal.getDport()));
            row.createCell(j++).setCellValue(new HSSFRichTextString(DateUtils.toSimpleDateTime(flowTotal.getStart_time())));
            row.createCell(j++).setCellValue(new HSSFRichTextString(DateUtils.toSimpleDateTime(flowTotal.getEnd_time())));
            row.createCell(j++).setCellValue(new HSSFRichTextString(flowTotal.getFlow()));
        }  
        // 设置响应
 		response.setContentType("application/ms-txt.numberformat:@");
 		response.setContentType("multipart/form-data");
 		response.setCharacterEncoding("UTF-8");
 		response.setHeader("Pragma", "public");
 		response.setHeader("Cache-Control", "max-age=30");
 		response.setHeader("Content-Disposition", "attachment; filename=report");
 		response.setStatus(HttpStatus.CREATED.value());
 		OutputStream out = response.getOutputStream();
 		workbook.write(out);  
 		out.flush();
 		out.close();
 		
		return "";
	}

}
