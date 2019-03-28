package com.egov.smartqr.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String curDate(Context context){
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		return dateFormat.format(date);
	}
	public static String curDay(Context context){
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}
	
	public static String format(long time){
		Date date = new Date(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	// 국가별 표기 - 년월일시간
	public static String localeFormat(Context context, long time) {
//		TimeZone tz = TimeZone.getDefault();
//		time -= tz.getOffset(time);
		
		int flags = 0;
		//flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
		flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
		flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
		flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;
		flags |= android.text.format.DateUtils.FORMAT_SHOW_WEEKDAY;
		String finalDateTime = android.text.format.DateUtils.formatDateTime(context, time, flags);
		
		return finalDateTime;
	}
	// 국가별 표기 - 년월일
	public static String localeFormatDay(Context context, long time) {
//		TimeZone tz = TimeZone.getDefault();
//		time -= tz.getOffset(time);

		int flags = 0;
		//flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
		flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
		flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
		flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;
		String finalDateTime = android.text.format.DateUtils.formatDateTime(context, time, flags);
		
		return finalDateTime;
	}
}
