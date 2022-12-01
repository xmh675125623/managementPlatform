package com.jiuzhou.plat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class MD5 { 
	
	public static String toMD5(String source) {
		if (source == null) {
			return null;
		}
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return result;
	}
	
	public static String toMD5(String source, String charset) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if(StringUtils.isNotEmpty(charset)){
				md.update(source.getBytes(charset));
			}else{
				md.update(source.getBytes());
			}
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
}
