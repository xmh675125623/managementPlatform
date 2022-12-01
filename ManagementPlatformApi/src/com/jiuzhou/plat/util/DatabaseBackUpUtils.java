package com.jiuzhou.plat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.SystemUtils;

/**
 * @author xingmh
 * @version 创建时间：2019年4月17日 下午3:03:52 类说明
 */
public class DatabaseBackUpUtils {

	public static boolean exportDatabase (String hostIP,
			String userName,
			String password,
			String savePath,
			String fileName,
			String databaseName,
			String tableName) {
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// 如果目录不存在  
			saveFile.mkdirs();// 创建文件夹  
		}
		if (!savePath.endsWith(File.separator)) {
			savePath = savePath + File.separator;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mysqldump").append(" -h").append(hostIP);
		stringBuilder.append(" --user=").append(userName) .append(" --password=").append(password);
		stringBuilder.append(" --max_allowed_packet=256M");
		stringBuilder.append(" --result-file=").append(savePath + fileName)
		.append(" --default-character-set=utf8 ")
		.append(databaseName)
		.append(" ")
		.append(tableName);
		try {
			Process process = Runtime.getRuntime().exec(stringBuilder.toString());
			if (process.waitFor() == 0) {// 0 表示线程正常终止。  
				return true;
			} else {
				System.out.println(stringBuilder.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean recoverDatabase (String hostIP,
			String userName,
			String password,
			String savePath,
			String fileName,
			String databaseName) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mysql").append(" -h").append(hostIP);
		stringBuilder.append(" -u").append(userName) .append(" -p").append(password);
		stringBuilder.append(" --default-character-set=utf8 ").append(databaseName);
		stringBuilder.append(" <").append(savePath).append("/").append(fileName);
		
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = null;
			if(SystemUtils.IS_OS_WINDOWS) {
				process = runtime.exec("cmd /c"+stringBuilder.toString());
			} else {
				process = runtime.exec(
						new String[]{
								"bash", 
								"-c",  
								stringBuilder.toString()
								}
						);
			}
			
			if (process.waitFor() == 0) {// 0 表示线程正常终止。  
				return true;
			} else {
				System.out.println(stringBuilder.toString());
				System.out.println("------------------------");
				InputStream fis=process.getInputStream();
	            //用一个读输出流类去读    
	             InputStreamReader isr=new InputStreamReader(fis);    
	            //用缓冲器读行    
	             BufferedReader br=new BufferedReader(isr);    
	             String line=null;    
	            //直到读完为止    
	            while((line=br.readLine())!=null)    
	             {    
	                 System.out.println(line);    
	             }    
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws InterruptedException {
		if (recoverDatabase("127.0.0.1", "root", "root", "D:/backupDatabase", "2019-04-17.sql", "jiuzhou_management_platform")) { 
			System.out.println("数据库备份成功！！！");
		} else { 
			System.out.println("数据库备份失败！！！");
		} 
	}

}
