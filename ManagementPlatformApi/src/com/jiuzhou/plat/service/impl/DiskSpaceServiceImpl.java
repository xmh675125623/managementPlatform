package com.jiuzhou.plat.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.DatabaseTableInfo;
import com.jiuzhou.plat.cache.FileDownloadInfo;
import com.jiuzhou.plat.mapper.DatabaseTableMapper;
import com.jiuzhou.plat.service.DiskSpaceService;
import com.jiuzhou.plat.util.DatabaseBackUpUtils;
import com.jiuzhou.plat.util.FileEncodeUtils;
import com.jiuzhou.plat.util.PropertiesUtils;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月28日 上午11:00:19
* 类说明
*/
@Service("DiskSpaceService")
public class DiskSpaceServiceImpl extends ServiceBase implements DiskSpaceService {

	@Autowired
	private DatabaseTableMapper databaseTableMapper;
	
	@Override
	@Transactional(readOnly=false)
	public String getAuditLogTableInfos(JSONObject paramJson) throws Exception {
		List<DatabaseTableInfo> list = databaseTableMapper.getAuditLogTableInfoListOrderByDate();
		if (list == null) {
			list = new ArrayList<>();
		}
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("list", list);
		return commonResult.toString();
	}

	@Override
	@Transactional(readOnly=false)
	public String getOperateLogTableInfos(JSONObject paramJson) throws Exception {
		List<DatabaseTableInfo> list = databaseTableMapper.getOperateLogTableInfoList();
		if (list == null) {
			list = new ArrayList<>();
		}
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("list", list);
		return commonResult.toString();
	}

	@Override
	public String deleteOperateLogTable(JSONObject paramJson) throws Exception {
		
		CommonResult commonResult = new CommonResult(false, "");
		if (!paramJson.has("table_name") 
				|| paramJson.getString("table_name").indexOf("plat_operate_log") < 0) {
			commonResult.setErrorMsg("表名错误");
			return commonResult.toString();
		}
		String tableName = paramJson.getString("table_name");
		databaseTableMapper.dropTable(tableName);
		setCache(CACHE_OPERATE_LOG_TABLES, new ArrayList<String>());
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	@Override
	public String deleteAuditLogTable(JSONObject paramJson) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		if (!paramJson.has("table_name") 
				|| paramJson.getString("table_name").indexOf("firewall_log") < 0) {
			commonResult.setErrorMsg("表名错误");
			return commonResult.toString();
		}
		String tableName = paramJson.getString("table_name");
		databaseTableMapper.dropTable(tableName);
		deleteCache(CACHE_AUDIT_LOG_TABLE);
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	@Override
	public String getExportOperateLogToken(JSONObject paramJson, HttpServletResponse response) throws Exception {
		FileDownloadInfo downloadInfo = 
				new FileDownloadInfo(new Date().getTime(), 1*60*1000);
		downloadInfo.setToken(UUID.randomUUID().toString());
		downloadInfo.setJsonParam(paramJson);
		setCache(FIRE_DOWNLOAD_INFO+downloadInfo.getToken(), downloadInfo);
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("fileMark", downloadInfo.getToken());
		return commonResult.toString();
	}

	@Override
	public String exportOperateLog(JSONObject paramJson, HttpServletResponse response) throws Exception {
		if (!paramJson.has("table_name") 
				|| paramJson.getString("table_name").indexOf("plat_operate_log") < 0) {
			throw new Exception("表名错误");
		}
		String tableName = paramJson.getString("table_name");
		
		
		//创建文件
		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String destFileName = System.currentTimeMillis() + "_exportOperateLogEncrypt";
		File destFile = new File(fileSavePath + destFileName);
		
		//导出数据库sql文件
		boolean isExportComplate  = DatabaseBackUpUtils.exportDatabase(
				PropertiesUtils.getProp("jdbc.hostname"),
				PropertiesUtils.getProp("jdbc.username"), 
				PropertiesUtils.getProp("jdbc.password"), 
				destFile.getParent(), 
				destFileName, 
				PropertiesUtils.getProp("jdbc.dbname"),
				tableName);
		
		//加密数据库sql文件
		boolean isEncodeComplate = new FileEncodeUtils(
				true, 
				destFile.getParent() + File.separator + destFileName, 
				"operate").run();
		
		if (!isExportComplate || !isEncodeComplate) {
			return new CommonResult(false, "备份导出失败，请刷新页面重试").toString();
		}
		
		//缓存文件下载信息
        FileDownloadInfo downloadInfo = 
				new FileDownloadInfo(new Date().getTime(), 1*60*1000);
		downloadInfo.setToken(UUID.randomUUID().toString());
		downloadInfo.setJsonParam(paramJson);
		downloadInfo.setFilePath(destFile.getParent() + File.separator + destFileName);
		downloadInfo.setFileName(tableName+".operate");
		setCache(FIRE_DOWNLOAD_INFO+downloadInfo.getToken(), downloadInfo);
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("fileMark", downloadInfo.getToken());
		return commonResult.toString();
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String importOperateLog(HttpServletResponse response, MultipartFile file)
			throws Exception {
		
		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String sourcesFileName = System.currentTimeMillis() + "_importOperateLogEncrypt";
		
		File sourcesFile = new File(fileSavePath + sourcesFileName);
		file.transferTo(sourcesFile);
		
		//解密数据库文件
		boolean isEncodeComplate = new FileEncodeUtils(
				false, 
				sourcesFile.getParent() + File.separator + sourcesFileName, 
				"operate").run();
		
		//导入数据库
		boolean isImportComplate = DatabaseBackUpUtils.recoverDatabase(
				PropertiesUtils.getProp("jdbc.hostname"),
				PropertiesUtils.getProp("jdbc.username"), 
				PropertiesUtils.getProp("jdbc.password"), 
				sourcesFile.getParent(), 
				sourcesFileName, 
				PropertiesUtils.getProp("jdbc.dbname"));
		
		if (!isImportComplate || !isEncodeComplate) {
			return new CommonResult(false, "备份导入失败，请检查备份文件是否被篡改或磁盘空间是否充足").toString();
		}
		
		return new CommonResult(true, "").toString();
	}

	@Override
	public String getExportAuditLogToken(JSONObject paramJson, HttpServletResponse response) throws Exception {
		FileDownloadInfo downloadInfo = 
				new FileDownloadInfo(new Date().getTime(), 1*60*1000);
		downloadInfo.setToken(UUID.randomUUID().toString());
		downloadInfo.setJsonParam(paramJson);
		setCache(FIRE_DOWNLOAD_INFO+downloadInfo.getToken(), downloadInfo);
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("fileMark", downloadInfo.getToken());
		return commonResult.toString();
	}
	
	@Override
	public String exportAuditLog(JSONObject paramJson, 
												HttpServletResponse response) throws Exception {
		
		if (!paramJson.has("table_name") 
				|| paramJson.getString("table_name").indexOf("firewall_log") < 0) {
			throw new Exception("表名错误");
		}
		String tableName = paramJson.getString("table_name");
		
		//创建文件
		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String destFileName = System.currentTimeMillis() + "_exportAuditLogEncrypt";
		File destFile = new File(fileSavePath + destFileName);
		
		
		//导出数据库sql文件
		boolean isExportComplate  = DatabaseBackUpUtils.exportDatabase(
				PropertiesUtils.getProp("jdbc.hostname"),
				PropertiesUtils.getProp("jdbc.username"), 
				PropertiesUtils.getProp("jdbc.password"), 
				destFile.getParent(), 
				destFileName, 
				PropertiesUtils.getProp("jdbc.dbname"),
				tableName);
		
		//加密数据库sql文件
		boolean isEncodeComplate = new FileEncodeUtils(
				true, 
				destFile.getParent() + File.separator + destFileName, 
				"firewall").run();
		
		if (!isExportComplate || !isEncodeComplate) {
			return new CommonResult(false, "备份导出失败，请刷新页面重试").toString();
		}
		
        FileDownloadInfo downloadInfo = 
				new FileDownloadInfo(new Date().getTime(), 1*60*1000);
		downloadInfo.setToken(UUID.randomUUID().toString());
		downloadInfo.setJsonParam(paramJson);
		downloadInfo.setFilePath(destFile.getParent() + File.separator + destFileName);
		downloadInfo.setFileName(tableName+".firewall");
		setCache(FIRE_DOWNLOAD_INFO+downloadInfo.getToken(), downloadInfo);
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("fileMark", downloadInfo.getToken());
		return commonResult.toString();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public synchronized String importAuditLog(HttpServletResponse response, 
								MultipartFile file) throws Exception {

		String fileSavePath = DiskSpaceServiceImpl.class.getResource("/").getFile()  + "temp/file/images/";
		String sourcesFileName = System.currentTimeMillis() + "_importAuditLogEncrypt";
		File sourcesFile = new File(fileSavePath + sourcesFileName);
		file.transferTo(sourcesFile);
		
		//解密数据库文件
		boolean isEncodeComplate = new FileEncodeUtils(
				false, 
				sourcesFile.getParent() + File.separator + sourcesFileName, 
				"firewall").run();
		
		//导入数据库
		boolean isImportComplate = DatabaseBackUpUtils.recoverDatabase(
				PropertiesUtils.getProp("jdbc.hostname"),
				PropertiesUtils.getProp("jdbc.username"), 
				PropertiesUtils.getProp("jdbc.password"), 
				sourcesFile.getParent(), 
				sourcesFileName, 
				PropertiesUtils.getProp("jdbc.dbname"));
		
		if (!isImportComplate || !isEncodeComplate) {
			return new CommonResult(false, "备份导入失败，请检查备份文件是否被篡改或磁盘空间是否充足").toString();
		}
		
		return new CommonResult(true, "").toString();
		
	}

	@Override
	@Transactional(readOnly=false)
	public String getFirstAuditLogTableName() throws Exception {
		return databaseTableMapper.getFirstAuditLogTableName();
	}

	@Override
	@Transactional(readOnly=false)
	public String getFirstOperateLogTableName() throws Exception {
		return databaseTableMapper.getFirstOperateLogTableName();
	}

}
