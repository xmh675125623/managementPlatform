package com.jiuzhou.plat.cache;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年4月2日 下午4:59:24
* 类说明
*/
public class FileDownloadInfo extends TimedClearBase {
	
	private String token;
	private JSONObject jsonParam;
	private String filePath;
	private String fileName;
	private boolean isDownloading;
	
	public FileDownloadInfo(long cache_add_time, long cache_save_time) {
		super(cache_add_time, cache_save_time);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JSONObject getJsonParam() {
		return jsonParam;
	}

	public void setJsonParam(JSONObject jsonParam) {
		this.jsonParam = jsonParam;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isDownloading() {
		return isDownloading;
	}

	public void setDownloading(boolean isDownloading) {
		this.isDownloading = isDownloading;
	}
	
	
	
}
