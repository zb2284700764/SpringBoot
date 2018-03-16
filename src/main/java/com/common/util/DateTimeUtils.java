package com.common.util;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/*******************************************************************************
 * 
 * Title:本类提供日期时间常用的功能函数
 * 
 * @author WANGWEI
 * @version 1.0
 * 
 ******************************************************************************/
public final class DateTimeUtils {

	private static Logger log = LoggerFactory.getLogger(DateTimeUtils.class);

	public static String DB_DATE_FORMATTER = "YYYY-MM-DD";

	public static String DB_TIME_FORMATTER = "YYYY-MM-DD HH24:MI:SS";

	public static String JAVA_DATE_FORMATTER = "yyyy-MM-dd";
	
	public static String JAVA_DATE_FORMATTER_STAMP = "yyyyMMddHHmmssSSSS";
	
	public static String JAVA_DATE_FORMATTER_STAMP_ = "yyyyMMddHHmmss";

	public static String JAVA_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

	public static String JAVA_TIME_FORMATTER_ = "yyyy-MM-dd_HH:mm:ss";
	
	private static String defaultDatePattern = null;

	private static String timePattern = "HH:mm";

	/***************************************************************************
	 * 取得系统当前时间,类型为Timestamp
	 * 
	 * @return Timestamp
	 **************************************************************************/
	public static Timestamp getNowTimestamp() {
		Date d = new Date();
		Timestamp numTime = new Timestamp(d.getTime());
		return numTime;
	}

	/***************************************************************************
	 * 取得系统的当前时间,类型为java.util.Date
	 *
	 * @return java.util.Date
	 **************************************************************************/
	public static Date getNowDate() {
		Date d = new Date();
		return d;// new java.sql.Date(d.getTime());
	}

	/***************************************************************************
	 * 取得系统的当前日期，并以系统 yyyy-MM-dd 的格式转化为字符串返回
	 *
	 * @return String
	 **************************************************************************/
	public static String getNowStrDate() {
		return DateToString(getNowDate(), JAVA_DATE_FORMATTER);
	}

	/***************************************************************************
	 * 取得系统的当前日期，并以系统 yyyyMMddHHmmssSSSS 的格式转化为字符串返回
	 *
	 * @return String
	 **************************************************************************/
	public static String getNowStrDateStamp() {
		return DateToString(getNowDate(), JAVA_DATE_FORMATTER_STAMP);
	}

	/***************************************************************************
	 * 取得系统的当前日期，并以系统 yyyyMMddHHmmss 的格式转化为字符串返回
	 *
	 * @return String
	 **************************************************************************/
	public static String getNowStrDateStamp1() {
		return DateToString(getNowDate(), JAVA_DATE_FORMATTER_STAMP_);
	}

	/***************************************************************************
	 * 取得系统的当前时间，并以系统 yyyy-MM-dd HH:mm:ss 的格式转化为字符串返回
	 *
	 * @return String
	 **************************************************************************/
	public static String getNowStrTime() {
		return DateToString(getNowDate(), JAVA_TIME_FORMATTER);
	}

	/***************************************************************************
	 * 取得系统的当前时间，并以系统 yyyy-MM-dd_HH:mm:ss 的格式转化为字符串返回
	 *
	 * @return String
	 **************************************************************************/
	public static String getNowStrTime1() {
		return DateToString(getNowDate(), JAVA_TIME_FORMATTER_);
	}

	/***************************************************************************
	 * 取得系统的当前时间，并以系统 （数据库中的时间格式）yyyy-MM-dd 24HH:mm:ss 的格式转化为字符串返回
	 *
	 * @return String
	 **************************************************************************/
	public static String getNowStrTime_DB() {
		return DateToString(getNowDate(), DB_TIME_FORMATTER);
	}

	/***************************************************************************
	 * 从Timestamp类型转化为pattern的字符串，如果date为null 则返回null
	 *
	 * @param date
	 *            进行转化的Timestamp
	 * @param pattern
	 *            转化的格式
	 * @return
	 **************************************************************************/
	public static String TimestampToString(Timestamp date, String pattern) {
		String strTemp = null;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/***************************************************************************
	 * 从Timestamp类型转化为pattern的字符串，如果date为null 则返回strDefault
	 *
	 * @param date
	 *            进行转化的Timestamp
	 * @param pattern
	 *            转化的格式
	 * @return
	 **************************************************************************/
	public static String TimestampToString(Timestamp date, String pattern, String strDefault) {
		String strTemp = strDefault;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/***************************************************************************
	 * 把date型日期转化为String 格式为pattern，如果被转化的对象为null 则返回null
	 *
	 * @param date
	 * @param pattern
	 *            日期的格式模板
	 * @return
	 **************************************************************************/
	public static String DateToString(Date date, String pattern) {
		String strTemp = null;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/***************************************************************************
	 * 把date型日期转化为String 格式为pattern，如果被转化的对象为null 则返回strDefault
	 *
	 * @param date
	 * @param pattern
	 *            日期的格式模板
	 * @param strDefault
	 *            缺省值
	 * @return
	 **************************************************************************/
	public static String DateToString(Date date, String pattern, String strDefault) {
		String strTemp = strDefault;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/***************************************************************************
	 * 把pattern 格式的字符串转化为Timestamp类型对象
	 *
	 * @param strDate
	 * @param pattern
	 *            日期的格式
	 * @return 返回转化后的Timestamp对象如果strDate为null或""或转换失败 返回null
	 **************************************************************************/
	public static Timestamp StringToTimestamp(String strDate, String pattern) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(pattern);
				Date d = formatter.parse(strDate);
				Timestamp numTime = new Timestamp(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/***************************************************************************
	 * 把format格式的字符串转化为java.sql.date类型
	 *
	 * @param strDate
	 * @return 返回转化后的Date对象如果strDate为null或""或转换失败 返回null
	 **************************************************************************/
	public static Date StringToDate(String strDate, String format) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				Date d = formatter.parse(strDate);
				return d;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/***************************************************************************
	 * 把format格式的字符串转化为java.sql.date类型
	 *
	 * @param strDate
	 * @return 返回转化后的Date对象如果strDate为null或""或转换失败 返回null
	 **************************************************************************/
	public static Date StringToDate(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(JAVA_TIME_FORMATTER);
				Date d = formatter.parse(strDate);
				return d;
			} catch (Exception e) {
				return new Date();
			}
		} else {
			return new Date();
		}
	}

	/***************************************************************************
	 * 把format格式的字符串转化为java.sql.date类型
	 *
	 * @param strDate
	 * @return 返回转化后的Date对象如果strDate为null或""或转换失败 返回null
	 **************************************************************************/
	public static Date StringToDateNull(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(JAVA_DATE_FORMATTER);
				Date d = formatter.parse(strDate);
				return d;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/***************************************************************************
	 * 判断字符串是否是日期格式 yyyy/mm/dd，分隔符可不同
	 *
	 * @param strExp
	 *            类型String，进行判断的字符串
	 * @return 类型Boolean，如果strExp是日期型的则返回true， 否则返回false。
	 **************************************************************************/
	public static boolean isDate(String strExp) {
		if (strExp.length() != 10)
			return false;

		try {
			Calendar cal = new GregorianCalendar();
			cal.setLenient(false);
			cal.set(Integer.parseInt(strExp.substring(0, 4)), Integer
					.parseInt(strExp.substring(5, 7)) - 1, Integer
					.parseInt(strExp.substring(8, 10)));
			Date ud = cal.getTime();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/***************************************************************************
	 * 判断字符串是否是java.sql.date类型
	 *
	 * @param strDate
	 * @return true：是date类型对象 false：不是
	 **************************************************************************/
	public static boolean isDate(String strDate, String format) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				Date d = formatter.parse(strDate);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/***************************************************************************
	 * 判断输入的三个整形是否能构成一个合法的日期。
	 *
	 * @param intYear
	 *            类型int 输入的年
	 * @param intMonth
	 *            类型int 输入的月
	 * @param intDay
	 *            类型int 输入的日
	 * @return 类型boolean
	 *         ************************************************************
	 */
	public static boolean isDate(int intYear, int intMonth, int intDay) {
		try {
			Calendar cal = new GregorianCalendar();
			cal.setLenient(false);
			cal.set(intYear, intMonth, intDay);
			Date ud = cal.getTime();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/***************************************************************************
	 * 判断pattern 格式的字符串转化为是否是Timestamp类型对象
	 *
	 * @param strDate
	 * @param pattern
	 *            日期的格式
	 * @return true：是Timestamp类型对象;false:不是。
	 **************************************************************************/
	public static boolean isTimestamp(String strDate, String pattern) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(pattern);
				Date d = formatter.parse(strDate);
				Timestamp numTime = new Timestamp(d.getTime());
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static final Date getDate(int year, int month, int day, int hour,
			int minute) {
		Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month),
				day, hour, minute);
		return cal.getTime();
	}

	public static final Date getDate(int year, int month, int day) {
		Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month),
				day);
		return cal.getTime();
	}

	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public static Timestamp getTimestamp(Calendar date) {
		return new Timestamp(date.getTime().getTime());
	}

	public static Timestamp getTimestamp(String date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Timestamp timestamp = new Timestamp(formatter.parse(date).getTime());
			return timestamp;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static final Date addDays(Date target, int days) {
		long msPerDay = 0x5265c00L;
		long msTarget = target.getTime();
		long msSum = msTarget + msPerDay * (long) days;
		Date result = new Date();
		result.setTime(msSum);
		return result;
	}

	/**
	 * 在给定日期上增加(减少)特定的周期数
	 *
	 * @param source
	 *            基准时间
	 * @param field
	 *            要增加的周期类型，请使用Calendar中的常量,Calendar.YEAR,Calendar.MONTH,Calendar.WEEK等
	 * @param amount
	 *            要增加或减少的数，正数是增加及未来的时间，负数是减少及过去的时间
	 * @return 返回格式化好的时间串，形如2005-07-06
	 */
	public static final String addCalendar(final Date source, int field,
			int amount) {

		Calendar greCal = new GregorianCalendar();
		if (source != null) {
			greCal.setTime(source);
		}
		greCal.add(field, amount);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return (f.format(greCal.getTime()));
	}

	/**
	 * 在给定日期上增加(减少)特定的周期数
	 *
	 * @param source
	 *            基准时间
	 * @param field
	 *            要增加的周期类型，请使用Calendar中的常量,Calendar.YEAR,Calendar.MONTH,Calendar.WEEK等
	 * @param amount
	 *            要增加或减少的数，正数是增加及未来的时间，负数是减少及过去的时间
	 * @return 返回格式化好的时间串，形如2005-07-06 01:01:01
	 */
	public static final String addTime(final Date source, int field, int amount) {

		Calendar greCal = new GregorianCalendar();
		if (source != null) {
			greCal.setTime(source);
		}
		greCal.add(field, amount);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (f.format(greCal.getTime()));
	}

	public static int dayDiff(Date first, Date second) {
		long msPerDay = 0x5265c00L;
		long diff = first.getTime() / msPerDay - second.getTime() / msPerDay;
		Long convertLong = new Long(diff);
		return convertLong.intValue();
	}

	public static int getYear(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(1);
	}

	public static int getMonth(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		int calendarMonth = cal.get(2);
		return calendarMonthToInt(calendarMonth);
	}

	public static int getDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(5);
	}

	public static int getHour(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(11);
	}

	public static int getMinute(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(12);
	}

	private static int calendarMonthToInt(int calendarMonth) {
		if (calendarMonth == 0)
			return 1;
		if (calendarMonth == 1)
			return 2;
		if (calendarMonth == 2)
			return 3;
		if (calendarMonth == 3)
			return 4;
		if (calendarMonth == 4)
			return 5;
		if (calendarMonth == 5)
			return 6;
		if (calendarMonth == 6)
			return 7;
		if (calendarMonth == 7)
			return 8;
		if (calendarMonth == 8)
			return 9;
		if (calendarMonth == 9)
			return 10;
		if (calendarMonth == 10)
			return 11;
		return calendarMonth != 11 ? 1 : 12;
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	private static int intToCalendarMonth(int month) {
		if (month == 1)
			return 0;
		if (month == 2)
			return 1;
		if (month == 3)
			return 2;
		if (month == 4)
			return 3;
		if (month == 5)
			return 4;
		if (month == 6)
			return 5;
		if (month == 7)
			return 6;
		if (month == 8)
			return 7;
		if (month == 9)
			return 8;
		if (month == 10)
			return 9;
		if (month == 11)
			return 10;
		return month != 12 ? 0 : 11;
	}

	/**
	 * 比较两个String格式(2004-01-01)的时间.
	 *
	 * @param date1
	 *            被比较时间
	 * @param date2
	 *            比较时间
	 * @return result 前一个值大返回正1，后一个值大返回负1，相等返回0
	 */
	public static int compareTwoDate(String date1, String date2) {

		if ((date1 == null || "".equals(date1))
				&& (date2 == null || "".equals(date2))) {
			return 0;
		} else if (date1 == null || "".equals(date1)) {
			return -1;
		} else if (date2 == null || "".equals(date2)) {
			return 1;
		}

		int result = 0;
		Date date = null;
		Date dateAnother = null;
		SimpleDateFormat dateFomat = null;

		try {

			dateFomat = new SimpleDateFormat("yyyy-MM-dd");

			date = dateFomat.parse(date1);
			dateAnother = dateFomat.parse(date2);

			result = date.compareTo(dateAnother);

		} catch (ParseException pe) {
			pe.printStackTrace();
			System.err.println("Exception in compareTwoDate.");
			result = 1;
		}

		return result;

	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}


	/**
	 * 转换字符串为java.sql.Date类型
	 *
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Date convertStrToSqlDate(String dateStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date timeStartDateUtil = simpleDateFormat.parse(dateStr);
			return new java.sql.Date(timeStartDateUtil.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date convertTimestampToDate(Timestamp date){
		return  StringToDate(TimestampToString(date, JAVA_TIME_FORMATTER), JAVA_TIME_FORMATTER);
		
	}
	/**
	 * 将整型转换成日期格式
	 * @param date
	 * @return
	 */
	public static Date longToDate(long date){
		return new Date(date);
	}
	
	/**
	 * 将整型转换成日期格式字符串
	 * @param date
	 * @return
	 */
	public static String longToDateString(long date){
		return DateToString(longToDate(date), JAVA_TIME_FORMATTER);
	}
	
}
