package com.jiuzhou.plat.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.cache.FileDownloadInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

/**
* @author xingmh
* @version 创建时间：2019年4月3日 下午3:34:28
* 类说明
*/
@Controller
@RequestMapping("/file_download")
public class FileDownloadController {
	
	@ResponseBody
	@RequestMapping(value="/download.do", produces="text/html;charset=UTF-8;")
    public String createCode(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value="file", required=true)String token) throws Exception {
		
		//从缓存中获取下载文件信息
		FileDownloadInfo downloadInfo = ServiceBase.getCacheStatic(
				ServiceBase.FIRE_DOWNLOAD_INFO+token, 
				FileDownloadInfo.class);
		
		if (downloadInfo == null) {
			return "文件不存在";
		}
		downloadInfo.setDownloading(true);
		File destFile = new File(downloadInfo.getFilePath());
		
		// 设置响应
		response.setContentType("application/x-msdownload");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Content-Disposition", "attachment; filename="+new String(downloadInfo.getFileName().getBytes(),"iso-8859-1"));
		
		InputStream fis = new BufferedInputStream(new FileInputStream(destFile));   
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());   
		byte[] buffer = new byte[1024 * 1024 * 4];   
        int i = -1;   
        while ((i = fis.read(buffer)) != -1) {   
            toClient.write(buffer, 0, i);  
        }
        fis.close();
        toClient.flush();   
        toClient.close();
        //删除文件
        destFile.delete();
        //删除缓存
        ServiceBase.getCacheMap().remove(ServiceBase.FIRE_DOWNLOAD_INFO+token);
		
		return "";
		
    }
	
}
