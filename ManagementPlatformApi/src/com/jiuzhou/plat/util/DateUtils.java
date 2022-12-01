package com.jiuzhou.plat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
* @author xingmh
* @version 2018年9月5日 上午10:09:19
* 类说明
*/
public class DateUtils {
	
	private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static String toSimpleDate(Date date) {
		if (date == null) {
			return "";
		}
		return simpleDateFormat.format(date);
	}
	
	public static String toSimpleDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return simpleDateTimeFormat.format(date);
	}
	
	public static String toTDate(Date date) {
		if (date == null) {
			return "";
		}
		return simpleDateTimeFormat.format(date).replace(" ", "T");
	}
	
	public static String toFormat(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static Date parseSimpleDate(String DateStr) throws ParseException {
		if (StringUtils.isBlank(DateStr)) {
			return null;
		}
		return simpleDateTimeFormat.parse(DateStr);
	}
	
}
