package com.canyon.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Yang
 */
public class DateUtils {

    /**
     * 空白日期，定义为1900-1-1
     */
    public final static Date Date_EMPTY = new Date() {

        private static final long serialVersionUID = 8729434191329587401L;

        {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(1900, 1, 1);
            super.setTime(c.getTime().getTime());
        }
    };

    /**
     * 最大日期，定义为9999-12-31
     */
    public final static Date DATE_MAX = new Date() {
        {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(9999, 12, 31);
            super.setTime(c.getTime().getTime());
        }
    };

    /**
     * 字符串转Date类型
     *
     * @param str
     * @param defaultVal
     * @param pattern
     * @return
     */
    public static Date formString(String str, Date defaultVal, String pattern) {
        Date result = defaultVal;
        if (pattern == null || pattern.isEmpty())
            pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            result = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 按指定日期类型转换成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern) {
        if (pattern == null || pattern.isEmpty())
            pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 将Data转换成Calendar
     *
     * @param date
     * @return
     */
    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 将Calendar转换成Date
     *
     * @param calendar
     * @return
     */
    public static Date toDate(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 将Date类型转换成LocalDate
     *
     * @param date
     * @param zone
     * @return
     */
    public static LocalDate toLocalDate(Date date, ZoneId zone) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(zone);
        return zdt.toLocalDate();
    }

    /**
     * 将Date类型转换成LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * 获取两个时间差的毫秒数
     *
     * @param min
     * @param max
     * @return
     */
    public static Long getTotalMilliseconds(Date min, Date max) {
        long lMin = min.getTime();
        long lMax = max.getTime();
        return lMax - lMin;
    }

    /**
     * 获取两个时间差的秒数
     *
     * @param min
     * @param max
     * @return
     */
    public static long getTotalSeconds(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / 1000;
    }

    /**
     * 获取两个时间差的分钟数
     *
     * @param min
     * @param max
     * @return
     */
    public static long getTotalMinutes(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (60 * 1000);
    }

    /**
     * 获取两个时间差的小时数
     *
     * @param min
     * @param max
     * @return
     */
    public static long getTotalHours(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (60 * 60 * 1000);
    }

    /**
     * 获取两个时间差的天数
     *
     * @param min
     * @param max
     * @return
     */
    public static long getTotalDays(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取两个时间差的月数
     *
     * @param min
     * @param max
     * @return
     */
    public static long getTotalMonth(Date min, Date max) {
        LocalDate minLocalDate = toLocalDate(min);
        LocalDate maxLocalDate = toLocalDate(max);

        Period period = Period.between(maxLocalDate, minLocalDate);
        return period.toTotalMonths();
    }

    /**
     * 获取两个时间差的周数
     *
     * @param min
     * @param max
     * @return
     */
    public static long getTotalWeek(Date min, Date max) {
        long totalDay = getTotalDays(min, max);
        return totalDay / 7;
    }

    /**
     * 获取两个时间差的年数
     *
     * @param min
     * @param max
     * @return
     */
    public static long getTotalYear(Date min, Date max) {
        LocalDate minLocalDate = toLocalDate(min);
        LocalDate maxLocalDate = toLocalDate(max);

        Period period = Period.between(maxLocalDate, minLocalDate);
        return period.getYears();
    }

    /**
     * 增加日期
     * @param date
     * @param unit 单位 Calendar.SECOND
     * @param number 数量
     * @return
     */
    public static Date addCalendar(Date date, int unit, int number){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, number);
        return calendar.getTime();
    }

    /**
     * 增加日期
     * @param unit 单位 Calendar.SECOND
     * @param number 数量
     * @return
     */
    public static Date addCalendar(int unit, int number){
        return addCalendar(new Date(), unit, number);
    }

    /**
     * 增加秒数
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        return addCalendar(date, Calendar.SECOND, second);
    }

    /**
     * 增加秒数
     * @param second
     * @return
     */
    public static Date addSecond(int second) {
        return addSecond(new Date(), second);
    }

    /**
     * 增加分钟数
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        return addCalendar(date, Calendar.MINUTE, minute);
    }

    /**
     * 增加分钟数
     * @param minute
     * @return
     */
    public static Date addMinute(int minute) {
        return addMinute(new Date(), minute);
    }

    /**
     * 增加天数
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        return addCalendar(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 添加天数
     * @param day
     * @return
     */
    public static Date addDay(int day) {
        return addDay(new Date(), day);
    }

    /**
     * 增加月数
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        return addCalendar(date, Calendar.MONTH, month);
    }

    /**
     * 增加月数
     * @param month
     * @return
     */
    public static Date addMonth(int month) {
        return addMonth(new Date(), month);
    }

    /**
     * 增加年数
     * @param date
     * @param year
     * @return
     */
    public static Date addYear(Date date, int year) {
        return addCalendar(date, Calendar.YEAR, year);
    }

    /**
     * 增加年数
     * @param year
     * @return
     */
    public static Date addYear(int year) {
        return addYear(new Date(), year);
    }

    /**
     * 获得当天的最小时间
     * @param date
     * @return
     */
    public static Date getMinDateOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获得当天的最大时间
     * @param date
     * @return
     */
    public static Date getMaxDateOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
}
