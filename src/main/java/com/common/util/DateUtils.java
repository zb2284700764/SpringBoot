/**
 * 时间工具类
 */
package com.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM", "yyyyMMdd" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前年份int格式
	 * 
	 * @return
	 */
	public static int getYearByInt() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当前月份int格式（从0开始）
	 * 
	 * @return
	 */
	public static int getMonthByInt() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH);
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前日（在一年中第几天）int格式（从1开始）
	 * 
	 * @return
	 */
	public static int getDayOfYearByInt() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 得到当前日（在一月中第几天）int格式（从1开始）
	 * 
	 * @return
	 */
	public static int getDayOfMonthByInt() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到当前日（在一周中第几天）int格式（周日是第一天）
	 * 
	 * @return
	 */
	public static int getDayOfWeekByInt() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
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
	public static final String addCalendar(final Date source, int field, int amount) {

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

	/**
	 * 判断两个日期大小
	 * 
	 * @param before
	 * @param after
	 * @return
	 * @author zhoubin
	 * @createDate 2016年8月3日 下午5:30:01
	 */
	public static boolean isBeforeGreaterThanAfter(Date before, Date after) {
		if (before.getTime() > after.getTime()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取两个日期之间的时间差
	 * 
	 * @param before
	 * @param after
	 * @return n天n时n分n秒
	 * @author zhoubin
	 * @createDate 2016年8月3日 下午4:07:57
	 */
	public static String getDifferenceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		long timeMillis = afterTime - beforeTime;

		long day = timeMillis / (24 * 60 * 60 * 1000); // 天
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24); // 时
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60); // 分
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60); // 秒
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000); // 毫秒

		return day + "天" + hour + "小时" + min + "分" + s + "秒";
	}

	/**
	 * 获取指定年、月的第一天
	 * 
	 * @param year
	 *            自然年的顺序
	 * @param month(java中的month顺序0开始)
	 * @return
	 * @author zhoubin
	 * @createDate 2016年8月31日 上午10:36:20
	 */
	public static String getMonthFirstDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, Calendar.DAY_OF_MONTH); // 设置年、月
		c.set(Calendar.DAY_OF_MONTH, 1); // 设置为1号，当前日期即为本月第一天
		c.set(Calendar.HOUR_OF_DAY, Calendar.AM);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return formatDateTime(c.getTime());
	}

	/**
	 * 获取指定年、月的最后一天
	 * 
	 * @param year
	 *            自然年的顺序
	 * @param month(java中的month顺序0开始)
	 * @return
	 * @author zhoubin
	 * @createDate 2016年8月31日 上午10:39:50
	 */
	public static String getMonthLastDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
		return formatDateTime(c.getTime());
	}

	public static void main(String[] args) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, Calendar.DAY_OF_MONTH); // 设置年、月
		c.set(Calendar.DAY_OF_MONTH, 1); // 设置为1号，当前日期即为本月第一天
		c.set(Calendar.HOUR_OF_DAY, Calendar.AM);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		String first = format.format(c.getTime());
		System.out.println("===============first:" + first);

		// 获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(2016, 1, Calendar.DAY_OF_MONTH);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, ca.getActualMaximum(Calendar.HOUR_OF_DAY));
		ca.set(Calendar.MINUTE, ca.getActualMaximum(Calendar.MINUTE));
		ca.set(Calendar.SECOND, ca.getActualMaximum(Calendar.SECOND));

		String last = format.format(ca.getTime());
		System.out.println("===============last:" + last);
	}
}
