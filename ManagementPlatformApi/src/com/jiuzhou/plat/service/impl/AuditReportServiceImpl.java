package com.jiuzhou.plat.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.bean.FirewallDeviceStatus;
import com.jiuzhou.firewall.bean.FirewallReportCounter;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.mapper.FirewallReportCounterMapper;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.cache.FileDownloadInfo;
import com.jiuzhou.plat.mapper.FirewallLogMapper;
import com.jiuzhou.plat.mapper.DatabaseTableMapper;
import com.jiuzhou.plat.pdfUtil.BackGroundImage;
import com.jiuzhou.plat.service.AuditReportService;
import com.jiuzhou.plat.thread.FirewallDeviceStatusThread;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2018年10月25日 下午3:00:41
* 类说明
*/
@Service("AuditReportService")
public class AuditReportServiceImpl implements AuditReportService {
	
	@Autowired
	private DatabaseTableMapper databaseTableMapper;
	@Autowired
	private FirewallLogMapper auditLogMapper;
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	@Autowired
	private FirewallReportCounterMapper firewallReportCounterMapper;
	
	@Override
	public String createReport(JSONObject paramJson) throws Exception {
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
			commonResult.put("list", new ArrayList<>());
			return commonResult.toString();
		}
		commonResult.put("device", device);
		
		//获取设备状态信息
		FirewallDeviceStatus deviceStatus = FirewallDeviceStatusThread.deviceStatusMap.get(device.getDevice_name());
		commonResult.put("deviceStatus", deviceStatus);
		//处理流量数据
		List<JSONObject> flowDataInput = new ArrayList<>();
		List<JSONObject> flowDataOutput = new ArrayList<>();
		List<LinkedList<String>> inputTotal = deviceStatus.getInputTotal();
		List<LinkedList<String>> outputTotal = deviceStatus.getOutputTotal();
		
		if (inputTotal.size() > 0) {
			
			LinkedList<String> inputList = inputTotal.get(0);
			
			if (inputList != null && inputList.size() > 0) {
				for (int i = 0; i < inputList.size(); i++) {
					for (int j = 0; j < inputTotal.size(); j++) {
						LinkedList<String> inputList2 = inputTotal.get(j);
						try {
							JSONObject object = new JSONObject();
							object.put("x", i);
							object.put("t", "eth" + j);
							object.put("y", Integer.parseInt(inputList2.get(i))/1024);
							flowDataInput.add(object);
						} catch (Exception e) {}
					}
				}
				
			}
			
			LinkedList<String> outputList = outputTotal.get(0);
			
			if (outputList != null && outputList.size() > 0) {
				for (int i = 0; i < outputList.size(); i++) {
					
					for (int j = 0; j < outputTotal.size(); j++) {
						LinkedList<String> outputList2 = outputTotal.get(j);
						try {
							JSONObject object = new JSONObject();
							object.put("x", i);
							object.put("t", "eth" + j);
							object.put("y", Integer.parseInt(outputList2.get(i))/1024);
							flowDataOutput.add(object);
						} catch (Exception e) {}
					}
					
				}
				
			}
			
		}
		commonResult.put("flowDataInput", flowDataInput);
		commonResult.put("flowDataOutput", flowDataOutput);
		
		
		if (!paramJson.has("startTime") || !paramJson.has("endTime")) {
			commonResult.setStatus(true);
			commonResult.put("list", new ArrayList<>());
			return commonResult.toString();
		}
		
//		String ip = paramJson.getString("ip");
//		String moduleStr = paramJson.getString("module");
//		String modules = moduleStr.replaceAll("\\[", "").replaceAll("\\]", "");
//		String levelStr = paramJson.getString("level");
//		String levels = levelStr.replaceAll("\\[", "").replaceAll("\\]", "");
		
		String startTimeStr = paramJson.getString("startTime");
		String endTimeStr = paramJson.getString("endTime");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = dateFormat.parse(startTimeStr);
		Date endTime = dateFormat.parse(endTimeStr);
		Calendar startTimeCalendar = Calendar.getInstance();
		startTimeCalendar.setTime(startTime);
		Calendar endTimeCalendar = Calendar.getInstance();
		endTimeCalendar.setTime(endTime);
		
		
		// 获取日期列表
		List<String> dateList = new ArrayList<>();
		dateList.add(startTimeStr + ".");
		while (true) {
			if (startTime.getTime() >= endTime.getTime()) {
				break;
			} else {
				startTimeCalendar.add(Calendar.DATE, 1);
				dateList.add(dateFormat.format(startTimeCalendar.getTime())  + ".");
				if (startTimeCalendar.getTime().getTime() >= endTime.getTime()) {
					break;
				}
			}
		}
		commonResult.put("dateList", dateList);
		
		// 获取模块计数信息
		List<FirewallReportCounter> moduleReportCounters = 
				firewallReportCounterMapper.getByDateAndType(
						device.getDevice_name(), 
						startTimeStr, 
						endTimeStr, 
						FirewallReportCounter.COUNT_TYPE_LOG_MODULE);
		List<JSONObject> moduleJsonObjs = new ArrayList<>();
		JSONObject usbObject = new JSONObject();
		usbObject.put("name", "USB");
		moduleJsonObjs.add(usbObject);
		
		JSONObject nicObject = new JSONObject();
		nicObject.put("name", "NIC");
		moduleJsonObjs.add(nicObject);
		
		JSONObject fw_filterObject = new JSONObject();
		fw_filterObject.put("name", "FW_FILTER");
		moduleJsonObjs.add(fw_filterObject);
		
		JSONObject modbus_tcpObject = new JSONObject();
		modbus_tcpObject.put("name", "MODBUS_TCP");
		moduleJsonObjs.add(modbus_tcpObject);
		
		JSONObject opc_classic_tcpObject = new JSONObject();
		opc_classic_tcpObject.put("name", "OPC_Classic_TCP");
		moduleJsonObjs.add(opc_classic_tcpObject);
		
		JSONObject iec104Object = new JSONObject();
		iec104Object.put("name", "IEC104");
		moduleJsonObjs.add(iec104Object);
		
		JSONObject s7Object = new JSONObject();
		s7Object.put("name", "S7");
		moduleJsonObjs.add(s7Object);
		
		JSONObject s7_plusObject = new JSONObject();
		s7_plusObject.put("name", "S7_PLUS");
		moduleJsonObjs.add(s7_plusObject);
		
		for (FirewallReportCounter reportCounter : moduleReportCounters) {
			if ("USB".equals(reportCounter.getCount_title())) {
				usbObject.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			} else if ("NIC".equals(reportCounter.getCount_title())) {
				nicObject.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			} else if ("FW_FILTER".equals(reportCounter.getCount_title())) {
				fw_filterObject.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			} else if ("MODBUS_TCP".equals(reportCounter.getCount_title())) {
				modbus_tcpObject.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			} else if ("OPC_Classic_TCP".equals(reportCounter.getCount_title())) {
				opc_classic_tcpObject.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			} else if ("IEC104".equals(reportCounter.getCount_title())) {
				iec104Object.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			} else if ("S7".equals(reportCounter.getCount_title())) {
				s7Object.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			} else if ("S7_PLUS".equals(reportCounter.getCount_title())) {
				s7_plusObject.put(reportCounter.getCount_date() + ".", reportCounter.getCount_num());
			}
		}
		commonResult.put("moduleJsonObjs", moduleJsonObjs);
		
		// 获取等级计数信息
		List<FirewallReportCounter> levelReportCounters = 
				firewallReportCounterMapper.selectGroupByTitle(
						device.getDevice_name(), 
						startTimeStr, 
						endTimeStr, 
						FirewallReportCounter.COUNT_TYPE_LOG_LEVEL);
		List<JSONObject> levelJsonObjs = new ArrayList<>();
		for (FirewallReportCounter reportCounter : levelReportCounters) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", reportCounter.getCount_title());
			jsonObject.put("value", reportCounter.getCount_num());
			levelJsonObjs.add(jsonObject);
		}
		commonResult.put("levelJsonObjs", levelJsonObjs);
		
		
		//根据起止日期获取表名列表
//		List<String> tableNames = new ArrayList<>();
//		DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
//		if (startTimeStr.equals(endTimeStr)) {
//			tableNames.add("firewall_log_" + device.getDevice_name() + "_" + dateFormat2.format(startTime));
//		} else {
//			tableNames.add("firewall_log_" + device.getDevice_name() + "_" + dateFormat2.format(startTimeCalendar.getTime()));
//			while (startTimeCalendar.before(endTimeCalendar)) {
//				startTimeCalendar.add(Calendar.DATE, 1);
//				tableNames.add("firewall_log_" + device.getDevice_name() + "_" + dateFormat2.format(startTimeCalendar.getTime()));
//			}
//		}
		
		//判断表名是否存在
//		tableNames = databaseTableMapper.getTableNameBath(tableNames);
//		if (tableNames == null || tableNames.size() < 1) {
//			commonResult.setStatus(true);
//			commonResult.put("list", new ArrayList<>());
//			return commonResult.toString();
//		}
//		
//		List<JSONObject> auditReportObjects = new ArrayList<>();
//		for (int i = 0; i < tableNames.size(); i++) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("table_date", dateFormat.format(dateFormat2.parse(tableNames.get(i).substring(26))));
//			List<AuditReportItem> reportItems = auditLogMapper.getReportByModule(tableNames.get(i), ip, modules, levels);
//			int count = 0;
//			for (int j = 0; j < reportItems.size(); j++) {
//				AuditReportItem reportItem = reportItems.get(j);
//				jsonObject.put(reportItem.getTitle(), reportItem.getValue());
//				count += reportItem.getValue();
//			}
//			jsonObject.put("count", count);
//			auditReportObjects.add(jsonObject);
//		}
//		
//		
//		Map<String, Integer> map = new HashMap<>();
//		modules += ",count";
//		String[] moduleArray = modules.split(",");
//		for (int i = 0; i < moduleArray.length; i++) {
//			map.put(moduleArray[i], 0);
//		}
//		JSONObject countObject = new JSONObject();
//		countObject.put("table_date", "合计");
//		for (int i = 0; i < auditReportObjects.size(); i++) {
//			JSONObject jsonObject = auditReportObjects.get(i);
//			for (int j = 0; j < moduleArray.length; j++) {
//				if (jsonObject.has(moduleArray[j])) {
//					map.put(moduleArray[j], map.get(moduleArray[j])+jsonObject.getInt(moduleArray[j]));
//				}
//			}
//		}
//		for (int j = 0; j < moduleArray.length; j++) {
//			countObject.put(moduleArray[j], map.get(moduleArray[j]));
//		}
//		auditReportObjects.add(countObject);
		
		commonResult.setStatus(true);
//		commonResult.put("list", auditReportObjects);
		return commonResult.toString();
	}

	@Override
	public String exportReport(JSONObject paramJson, HttpServletResponse response) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		FirewallDevice device = null;
		if (paramJson.has("deviceName")) {
			device = firewallDeviceMapper.getByName(paramJson.getString("deviceName"));
		}
		if (device == null) {
			commonResult.setStatus(true);
			commonResult.put("list", new ArrayList<>());
			return commonResult.toString();
		}
		
		if (!paramJson.has("startTime") || !paramJson.has("endTime")) {
			commonResult.setStatus(true);
			commonResult.put("list", new ArrayList<>());
			return commonResult.toString();
		}
		
		String startTimeStr = paramJson.getString("startTime");
		String endTimeStr = paramJson.getString("endTime");
		
		//获取设备状态信息
		FirewallDeviceStatus deviceStatus = FirewallDeviceStatusThread.deviceStatusMap.get(device.getDevice_name());
		commonResult.put("deviceStatus", deviceStatus);
		//处理流量数据
		List<JSONObject> flowDataInput = new ArrayList<>();
		List<JSONObject> flowDataOutput = new ArrayList<>();
		List<LinkedList<String>> inputTotal = deviceStatus.getInputTotal();
		List<LinkedList<String>> outputTotal = deviceStatus.getOutputTotal();
		
		if (inputTotal.size() > 0) {
			
			LinkedList<String> inputList = inputTotal.get(0);
			
			if (inputList != null && inputList.size() > 0) {
				for (int i = 0; i < inputList.size(); i++) {
					for (int j = 0; j < inputTotal.size(); j++) {
						LinkedList<String> inputList2 = inputTotal.get(j);
						try {
							JSONObject object = new JSONObject();
							object.put("x", i);
							object.put("t", "eth" + j);
							object.put("y", Integer.parseInt(inputList2.get(i))/1024);
							flowDataInput.add(object);
						} catch (Exception e) {}
					}
				}
				
			}
			
			LinkedList<String> outputList = outputTotal.get(0);
			
			if (outputList != null && outputList.size() > 0) {
				for (int i = 0; i < outputList.size(); i++) {
					
					for (int j = 0; j < outputTotal.size(); j++) {
						LinkedList<String> outputList2 = outputTotal.get(j);
						try {
							JSONObject object = new JSONObject();
							object.put("x", i);
							object.put("t", "eth" + j);
							object.put("y", Integer.parseInt(outputList2.get(i))/1024);
							flowDataOutput.add(object);
						} catch (Exception e) {}
					}
					
				}
				
			}
			
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = dateFormat.parse(startTimeStr);
		Date endTime = dateFormat.parse(endTimeStr);
		Calendar startTimeCalendar = Calendar.getInstance();
		startTimeCalendar.setTime(startTime);
		Calendar endTimeCalendar = Calendar.getInstance();
		endTimeCalendar.setTime(endTime);
		
		// 获取日期列表
		List<String> dateList = new ArrayList<>();
		dateList.add(startTimeStr + ".");
		while (true) {
			if (startTime.getTime() >= endTime.getTime()) {
				break;
			} else {
				startTimeCalendar.add(Calendar.DATE, 1);
				dateList.add(dateFormat.format(startTimeCalendar.getTime())  + ".");
				if (startTimeCalendar.getTime().getTime() >= endTime.getTime()) {
					break;
				}
			}
		}
		
		// 获取模块计数信息
		List<FirewallReportCounter> moduleReportCounters = 
				firewallReportCounterMapper.getByDateAndType(
						device.getDevice_name(), 
						startTimeStr, 
						endTimeStr, 
						FirewallReportCounter.COUNT_TYPE_LOG_MODULE);
		
		// 获取等级计数信息
		List<FirewallReportCounter> levelReportCounters = 
				firewallReportCounterMapper.selectGroupByTitle(
						device.getDevice_name(), 
						startTimeStr, 
						endTimeStr, 
						FirewallReportCounter.COUNT_TYPE_LOG_LEVEL);
		
		
		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String filePath = fileSavePath + System.currentTimeMillis() + "_report";
		OutputStream os = new FileOutputStream(new File(filePath));
		createPDF(os, startTimeStr, endTimeStr, deviceStatus, device, flowDataInput, flowDataOutput, moduleReportCounters, levelReportCounters);
		 // 写出响应
		os.flush();

		FileDownloadInfo downloadInfo = 
				new FileDownloadInfo(new Date().getTime(), 1*60*1000);
		downloadInfo.setToken(UUID.randomUUID().toString());
		downloadInfo.setJsonParam(paramJson);
		downloadInfo.setFilePath(filePath);
		downloadInfo.setFileName("report_" + System.currentTimeMillis() + ".pdf");
		ServiceBase.setCacheStatic(ServiceBase.FIRE_DOWNLOAD_INFO+downloadInfo.getToken(), downloadInfo);
		commonResult.put("fileMark", downloadInfo.getToken());
		return commonResult.toString();
	}
	
	/**
	 * 创建PDF文件
	 * @param os
	 * @throws Exception
	 */
	private static void createPDF(OutputStream os, 
								String startTime, 
								String endTime, 
								FirewallDeviceStatus deviceStatus,
								FirewallDevice device,
								List<JSONObject> flowDataInput,
								List<JSONObject> flowDataOutput,
								List<FirewallReportCounter> moduleReportCounters,
								List<FirewallReportCounter> levelReportCounters) throws Exception {
		
		// 新建document对象
        Document document = new Document(PageSize.A4,40,40,18,18);
        try {
            //添加中文字体
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //设置字体样式
            Font textFont = new Font(bfChinese,12,Font.NORMAL);//正常
            Font boldFont = new Font(bfChinese,11,Font.BOLD); //加粗
            Font firsetTitleFont = new Font(bfChinese,22,Font.BOLD); //一级标题
            Font secondTitleFont = new Font(bfChinese,15,Font.BOLD); //二级标题
            Font underlineFont = new Font(bfChinese,11,Font.UNDERLINE); //下划线斜体
            BaseColor tableTitleBgColor = new BaseColor(220, 220, 220);//表格标题背景色
 
            //创建输出流
            PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
            pdfWriter.setPageEvent(new BackGroundImage("classPath:temp/file/images/pdf_bg.jpg"));
            document.open();
            
            Paragraph p0 = new Paragraph("  ",textFont);
            p0.setLeading(60);

            String str = "设备：" + device.getDevice_name() + "        起始时间：" + startTime + "        结束时间：" + endTime;
            Paragraph ph1 = new Paragraph(str,textFont);
            ph1.setAlignment(Element.ALIGN_LEFT);
            p0.add(ph1);
            
            PdfPTable imgTable = new PdfPTable(3);
            imgTable.setSpacingBefore(10);
            imgTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            float[] titleWidth = new float[3];
            titleWidth[0] = 170;
            titleWidth[1] = 170;
            titleWidth[2] = 170;
            imgTable.setTotalWidth(titleWidth); //设置列宽
            imgTable.setLockedWidth(true); //锁定列宽
            imgTable.setSpacingAfter(30);
            PdfPCell cellDate1 = new PdfPCell(new Paragraph("型号：" + device.getModal(),textFont));
            cellDate1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDate1.setMinimumHeight(25);
            cellDate1.setPaddingTop(5);
//            cellDate1.setBackgroundColor(tableTitleBgColor);
        	imgTable.addCell(cellDate1);
        	
        	PdfPCell cellDate2 = new PdfPCell(new Paragraph("版本：" + device.getEdition(),textFont));
            cellDate2.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDate2.setMinimumHeight(25);
            cellDate2.setPaddingTop(5);
        	imgTable.addCell(cellDate2);
        	
        	PdfPCell cellDate3 = new PdfPCell(new Paragraph("运行时间：" + deviceStatus.getUpTime(),textFont));
            cellDate3.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDate3.setMinimumHeight(25);
            cellDate3.setPaddingTop(5);
        	imgTable.addCell(cellDate3);
        	
        	String cpuUsages = "";
        	if (deviceStatus.getCpuUsages().size() > 0) {
        		cpuUsages = deviceStatus.getCpuUsages().get(deviceStatus.getCpuUsages().size() - 1);
        	}
        	PdfPCell cellDate4 = new PdfPCell(new Paragraph("CPU占用率：" + cpuUsages + "%",textFont));
            cellDate4.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDate4.setMinimumHeight(25);
            cellDate4.setPaddingTop(5);
        	imgTable.addCell(cellDate4);
        	
        	PdfPCell cellDate5 = new PdfPCell(new Paragraph("内存使用率：" + deviceStatus.getMemoryUsage() + "%",textFont));
            cellDate5.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDate5.setMinimumHeight(25);
            cellDate5.setPaddingTop(5);
        	imgTable.addCell(cellDate5);
        	
        	PdfPCell cellDate6 = new PdfPCell(new Paragraph("硬盘使用率：" + deviceStatus.getDiskUsage() + "%",textFont));
            cellDate6.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDate6.setMinimumHeight(25);
            cellDate6.setPaddingTop(5);
        	imgTable.addCell(cellDate6);
            
        	
        	//折线图*************************************************************************************
        	StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        	//设置图表标题  
            mChartTheme.setExtraLargeFont(loadFont("黑体", java.awt.Font.BOLD, 12));  
        	//设置轴向字体  
            mChartTheme.setLargeFont(loadFont("宋体", java.awt.Font.PLAIN, 10));  
            //设置图例字体  
            mChartTheme.setRegularFont(loadFont("宋体", java.awt.Font.PLAIN, 10));
            ChartFactory.setChartTheme(mChartTheme);		
            
            DefaultCategoryDataset flowInputDataset = new DefaultCategoryDataset();
            for (JSONObject jsonObject : flowDataInput) {
            	flowInputDataset.addValue(jsonObject.getInt("y"), jsonObject.getString("t"), jsonObject.getString("x"));
			}
            JFreeChart flowInputChart = ChartFactory.createLineChart(
                "网口流量（进）",//图名字
                "时间（秒）",//横坐标
                "流量",//纵坐标
                flowInputDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                false, // 采用标准生成器 
                false);// 是否生成超链接
            
            CategoryPlot flowInputPlot = (CategoryPlot)flowInputChart.getPlot();
            flowInputPlot.setBackgroundPaint(Color.LIGHT_GRAY);
            flowInputPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
            flowInputPlot.setOutlinePaint(Color.RED);//边界线
            
//            ChartFrame flowInputChartFrame = new ChartFrame("网口流量（进）", flowInputChart);
//            flowInputChartFrame.pack();
//            flowInputChartFrame.setVisible(true);
            
            String flowInputFileName = System.currentTimeMillis() + "1";
            String flowInputFileSavePath = AuditReportServiceImpl.class.getResource("/").getFile() + "temp/file/images/" + flowInputFileName + ".jpg";
            File flowInputFile = new File(flowInputFileSavePath);
            FileOutputStream flowInputFps = new FileOutputStream(flowInputFile);
            ChartUtilities.writeChartAsJPEG(flowInputFps, flowInputChart, 500, 250);
            Image flowInputImage = Image.getInstance(flowInputFileSavePath);
            
            
            DefaultCategoryDataset flowOutputDataset = new DefaultCategoryDataset();
            for (JSONObject jsonObject : flowDataOutput) {
            	flowOutputDataset.addValue(jsonObject.getInt("y"), jsonObject.getString("t"), jsonObject.getString("x"));
			}
            JFreeChart flowOutputChart = ChartFactory.createLineChart(
                "网口流量（出）",//图名字
                "时间（秒）",//横坐标
                "流量",//纵坐标
                flowOutputDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                false, // 采用标准生成器 
                false);// 是否生成超链接
            
            CategoryPlot flowOutputPlot = (CategoryPlot)flowOutputChart.getPlot();
            flowOutputPlot.setBackgroundPaint(Color.LIGHT_GRAY);
            flowOutputPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
            flowOutputPlot.setOutlinePaint(Color.RED);//边界线
            
//            ChartFrame flowOutputChartFrame = new ChartFrame("网口流量（出）", flowOutputChart);
//            flowOutputChartFrame.pack();
//            flowOutputChartFrame.setVisible(true);
            
            String flowOutputFileName = System.currentTimeMillis() + "2";
            String flowOutputFileSavePath = AuditReportServiceImpl.class.getResource("/").getFile() + "temp/file/images/" + flowOutputFileName + ".jpg";
            File flowOutputFile = new File(flowOutputFileSavePath);
            FileOutputStream flowOutputFps = new FileOutputStream(flowOutputFile);
            ChartUtilities.writeChartAsJPEG(flowOutputFps, flowOutputChart, 500, 250);
            Image flowOutputImage = Image.getInstance(flowOutputFileSavePath);
            
            
            //柱状体*************************************************************************************
            //创建一个默认的图形
            DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
            //构造数据集合
            for (int i = 0; i < moduleReportCounters.size(); i++) {
            	FirewallReportCounter reportCounter = moduleReportCounters.get(i);
            	dataset.addValue(reportCounter.getCount_num(), reportCounter.getCount_title(), reportCounter.getCount_date());
            }
            JFreeChart chart = ChartFactory.createStackedBarChart(
                        "模块日志数量图",              // chart title
                         "日期",                      // x-domain axis label
                         "日志数",                      // y-range axis label
                         dataset,                   // data
                         PlotOrientation.VERTICAL,  // 图标方向:水平，垂直
                         true,                     // 是否生成图例
                         false,                     // 是否生成工具
                         false);                    // 是否生产链接
            chart.setTextAntiAlias(false); //图例字体清晰
            CategoryPlot plot = chart.getCategoryPlot();
            CategoryAxis axis = plot.getDomainAxis();
            axis.setMaximumCategoryLabelWidthRatio(6.0f);
            axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); //x轴刻度旋转45度
            
            BarRenderer renderer = new BarRenderer();
            renderer.setBarPainter( new StandardBarPainter() );
//            renderer.setMaximumBarWidth(0.08); //设置柱子宽度 
            renderer.setSeriesPaint(0,new Color(24, 145, 255, 255)); //设置柱的颜色 
            renderer.setShadowVisible(false);//去掉阴影
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());//显示柱子上的值
            renderer.setBaseItemLabelsVisible(true); //显示柱子上的值
            renderer.setBaseItemLabelPaint(Color.WHITE);
            ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.570796326794897D);
            renderer.setBasePositiveItemLabelPosition(position1);// 设置基础标签位置
            plot.setRenderer(renderer);
//            plot.setBackgroundPaint(Color.WHITE);
            String fileName = System.currentTimeMillis() + "3";
            String fileSavePath = AuditReportServiceImpl.class.getResource("/").getFile() + "temp/file/images/" + fileName + ".jpg";
            File file = new File(fileSavePath);
            FileOutputStream fps = new FileOutputStream(file);
            ChartUtilities.writeChartAsJPEG(fps, chart, 500, 300);
            Image image1 = Image.getInstance(fileSavePath);
            
            
            //饼图********************************************************************
            DefaultPieDataset pds = new DefaultPieDataset();
            
            for (FirewallReportCounter firewallReportCounter : levelReportCounters) {
            	pds.setValue(firewallReportCounter.getCount_title(), firewallReportCounter.getCount_num());
			}
            // 分别是:显示图表的标题、需要提供对应图表的DateSet对象、是否显示图例、是否生成贴士以及是否生成URL链接
            JFreeChart pieChart = ChartFactory.createPieChart("等级占比", pds, false, false, true);
            // 如果不使用Font,中文将显示不出来
            java.awt.Font font = loadFont("宋体", Font.BOLD, 12);
            // 设置图片标题的字体
            pieChart.getTitle().setFont(font);
            // 得到图块,准备设置标签的字体
            PiePlot piePlot = (PiePlot) pieChart.getPlot();
            // 设置标签字体
            piePlot.setLabelFont(loadFont("宋体", Font.BOLD, 10));
            piePlot.setStartAngle(new Float(3.14f / 2f));
            // 设置plot的前景色透明度
            piePlot.setForegroundAlpha(0.7f);
            // 设置plot的背景色透明度
            piePlot.setBackgroundAlpha(0.0f);
            // 设置标签生成器(默认{0})
            // {0}:key {1}:value {2}:百分比 {3}:sum
            piePlot.setLabelGenerator(
            		new StandardPieSectionLabelGenerator(
            				"{0}({1}占{2})", 
            				NumberFormat.getNumberInstance(),
        					new DecimalFormat("0.00%")
        					)
            		);
            
            piePlot.setOutlineVisible(false);
//            piePlot.setSectionPaint("信息", new Color(47, 194, 90, 255));        
//            piePlot.setSectionPaint("注意", new Color(249, 204, 20, 255));        
//            piePlot.setSectionPaint("告警", new Color(240, 72, 100, 255));        
            
            String pieFileName = System.currentTimeMillis() + "";
            String pieFileSavePath = AuditReportServiceImpl.class.getResource("/").getFile() + "temp/file/images/" + pieFileName + ".jpg";
            File pieFile = new File(pieFileSavePath);
            FileOutputStream pieFps = new FileOutputStream(pieFile);
            ChartUtilities.writeChartAsJPEG(pieFps, pieChart, 500, 250);
            Image pieImage = Image.getInstance(pieFileSavePath);

            
            p0.add(imgTable);
            document.add(p0);
            document.add(flowInputImage);
            document.add(flowOutputImage);
            document.newPage();
            Paragraph p1 = new Paragraph("  ",textFont);
            p1.setLeading(60);
            document.add(p1);
            document.add(image1);
            Paragraph p2 = new Paragraph("  ",textFont);
            p2.setLeading(30);
            document.add(p2);
            document.add(pieImage);
            document.close();
            
            flowInputFps.flush();
            flowInputFps.close();
            flowOutputFps.flush();
            flowOutputFps.close();
            fps.flush();
            fps.close();
            pieFps.flush();
            pieFps.close();
            if (file.exists()) {
            	file.delete();
            }
            if (pieFile.exists()) {
            	pieFile.delete();
            }
            if (flowInputFile.exists()) {
            	flowInputFile.delete();
            }
            if (flowOutputFile.exists()) {
            	flowInputFile.delete();
            }
 
 
        } catch (DocumentException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
 
        // 关闭文档
        if (document.isOpen()) {
        	document.close();
        }
        
	}
	
	
	public static java.awt.Font loadFont(String fontName, int style, float fontSize) throws Exception {
		
		String fileName = AuditReportServiceImpl.class.getResource("/").getFile() + "temp/file/fonts/";
		if ("黑体".equals(fontName) || "simhei".equals(fontName)) {
			fileName += "simhei.ttf";
		} else if ("宋体".equals(fontName) || "simsun".equals(fontName)) {
			fileName += "simsun.ttc";
		}
		
		FileInputStream aixing = new FileInputStream(fileName);
		java.awt.Font dynamicFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, aixing);
		java.awt.Font dynamicFontPt = dynamicFont.deriveFont(style, fontSize);
		aixing.close();
		return dynamicFontPt;
	}
	
}
