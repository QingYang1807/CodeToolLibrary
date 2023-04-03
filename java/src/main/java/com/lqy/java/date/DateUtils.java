package com.lqy.java.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 日期和时间相关工具类
 * Date and time related tool classes
 */
public class DateUtils {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 由于 SimpleDateFormat 是非线程安全的，如果在多线程环境下使用同一个实例，可能会导致问题。
    // 通过创建一个 ThreadLocal 的 SimpleDateFormat 来解决这个问题。
    // Since SimpleDateFormat is not thread safe, if the same instance is used in a multi-threaded environment, it may cause problems.
    // Solve this problem by creating a ThreadLocal of SimpleDateFormat.
    private static final ThreadLocal<SimpleDateFormat> DEFAULT_DATE_FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_DATE_FORMAT));
    private static final ThreadLocal<SimpleDateFormat> DEFAULT_DATETIME_FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_DATETIME_FORMAT));

    // 格式化日期
    // Format date
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    // 格式化日期时间
    // Format date and time
    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_FORMAT);
    }

    // 自定义格式化
    // Custom format
    public static String formatDate(Date date, String format) {
        return getThreadSafeFormatter(format).format(date);
    }

    // 获取线程安全的 SimpleDateFormat
    // Get thread-safe SimpleDateFormat
    private static SimpleDateFormat getThreadSafeFormatter(String format) {
        if (DEFAULT_DATE_FORMAT.equals(format)) {
            return DEFAULT_DATE_FORMATTER.get();
        } else if (DEFAULT_DATETIME_FORMAT.equals(format)) {
            return DEFAULT_DATETIME_FORMATTER.get();
        } else {
            return new SimpleDateFormat(format);
        }
    }

    // 解析日期字符串
    // Parse date string
    public static Date parseDate(String dateStr) throws ParseException {
        return parseDate(dateStr, DEFAULT_DATE_FORMAT);
    }

    // 解析日期时间字符串
    // Parse date and time string
    public static Date parseDateTime(String dateTimeStr) throws ParseException {
        return parseDate(dateTimeStr, DEFAULT_DATETIME_FORMAT);
    }

    // 自定义解析
    // Custom parse
    public static Date parseDate(String dateStr, String format) throws ParseException {
        return getThreadSafeFormatter(format).parse(dateStr);
    }

    // 计算两个日期之间相差的天数
    // Calculate the number of days between two dates
    public static long daysBetween(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    // 计算两个日期之间相差的小时数
    // Calculate the number of hours between two dates
    public static long hoursBetween(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
    }

    // 计算两个日期之间相差的分钟数
    // Calculate the number of minutes between two dates
    public static long minutesBetween(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }

    // 计算两个日期之间相差的秒数
    // Calculate the number of seconds between two dates
    public static long secondsBetween(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS);
    }


    // 在给定日期的基础上增加天数
    // Add days to the given date
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    // 在给定日期的基础上增加小时(24h)
    // Add hours to the given date
    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    // 在给定日期的基础上增加分钟
    // Add minutes to the given date
    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    // 在给定日期的基础上增加秒数
    // Add seconds to the given date
    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    // 获取当前日期和时间
    // Get current date and time
    public static Date now() {
        return new Date();
    }

    // 获取当前日期
    // Get current date as string
    public static String nowAsString() {
        return formatDateTime(now());
    }

    // 获取当天开始时间
    // Get the start time of the day
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // 获取当天结束时间
    // Get the end time of the day
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    // 判断两个日期是否为同一天
    // Judge whether two dates are the same day
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    // 判断某个日期是否为工作日或周末
    // Judge whether a date is a working day or weekend
    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    // 判断某个日期是否为工作日
    // Judge whether a date is a working day
    public static boolean isWeekday(Date date) {
        return !isWeekend(date);
    }

    // 获取某个日期所在月份的第一天
    // Get the first day of the month in which a date is located
    public static Date getStartOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStartOfDay(calendar.getTime());
    }

    // 获取某个日期所在月份的最后一天
    // Get the last day of the month in which a date is located
    public static Date getEndOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getEndOfDay(calendar.getTime());
    }

    public static void main(String[] args) {
        // 编写以上方法的测试代码
        System.out.println(DateUtils.now());
        System.out.println(DateUtils.nowAsString());
        System.out.println(DateUtils.getStartOfDay(DateUtils.now()));
        System.out.println(DateUtils.getEndOfDay(DateUtils.now()));
        System.out.println(DateUtils.getStartOfMonth(DateUtils.now()));
        System.out.println(DateUtils.getEndOfMonth(DateUtils.now()));
        System.out.println(DateUtils.isSameDay(DateUtils.now(), DateUtils.now()));
        System.out.println(DateUtils.isWeekend(DateUtils.now()));
        System.out.println(DateUtils.isWeekday(DateUtils.now()));
        System.out.println(DateUtils.daysBetween(DateUtils.now(), DateUtils.addDays(DateUtils.now(), 1)));
        System.out.println(DateUtils.hoursBetween(DateUtils.now(), DateUtils.addHours(DateUtils.now(), 2)));
        System.out.println(DateUtils.minutesBetween(DateUtils.now(), DateUtils.addMinutes(DateUtils.now(), 3)));
        System.out.println(DateUtils.secondsBetween(DateUtils.now(), DateUtils.addSeconds(DateUtils.now(), 4)));
        System.out.println(DateUtils.addDays(DateUtils.now(), 1));
        System.out.println(DateUtils.addHours(DateUtils.now(), 1));
        System.out.println(DateUtils.addMinutes(DateUtils.now(), 1));
        System.out.println(DateUtils.addSeconds(DateUtils.now(), 1));
    }
}
