package com.canyon.commons

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.*

object DateUtils {

    /**
     * 空白日期，定义为1900-1-1
     */
    val Date_EMPTY: Date = object : Date() {

        private val serialVersionUID = 8729434191329587401L

        init {
            val c = Calendar.getInstance()
            c.clear()
            c.set(1900, 1, 1)
            super.setTime(c.time.time)
        }
    }

    /**
     * 最大日期，定义为9999-12-31
     */
    val DATE_MAX: Date = object : Date() {
        init {
            val c = Calendar.getInstance()
            c.clear()
            c.set(9999, 12, 31)
            super.setTime(c.time.time)
        }
    }

    /**
     * 字符串转Date类型
     *
     * @param str
     * @param defaultVal
     * @param pattern
     * @return
     */
    fun formString(str: String, defaultVal: Date, pattern: String?): Date {
        var result = defaultVal
        var strPattern: String = pattern ?: ""
        if (pattern == null || pattern.isEmpty())
            strPattern = "yyyy-MM-dd HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(strPattern)
        try {
            result = simpleDateFormat.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 按指定日期类型转换成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    fun toString(date: Date, pattern: String?): String {
        var strPattern = pattern
        if (pattern == null || pattern.isEmpty())
            strPattern = "yyyy-MM-dd HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(strPattern)
        return simpleDateFormat.format(date)
    }

    /**
     * 将Data转换成Calendar
     *
     * @param date
     * @return
     */
    fun toCalendar(date: Date): Calendar {
        val c = Calendar.getInstance()
        c.time = date
        return c
    }

    /**
     * 将Calendar转换成Date
     *
     * @param calendar
     * @return
     */
    fun toDate(calendar: Calendar): Date {
        return calendar.time
    }

    /**
     * 将Date类型转换成LocalDate
     *
     * @param date
     * @param zone
     * @return
     */
    @JvmOverloads
    fun toLocalDate(date: java.util.Date, zone: ZoneId = ZoneId.systemDefault()): LocalDate {
        val instant = date.toInstant()
        val zdt = instant.atZone(zone)
        return zdt.toLocalDate()
    }

    /**
     * 获取两个时间差的毫秒数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalMilliseconds(min: Date, max: Date): Long {
        val lMin = min.time
        val lMax = max.time
        return lMax - lMin
    }

    /**
     * 获取两个时间差的秒数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalSeconds(min: Date, max: Date): Long {
        val milliseconds = getTotalMilliseconds(min, max)
        return milliseconds / 1000
    }

    /**
     * 获取两个时间差的分钟数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalMinutes(min: Date, max: Date): Long {
        val milliseconds = getTotalMilliseconds(min, max)
        return milliseconds / (60 * 1000)
    }

    /**
     * 获取两个时间差的小时数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalHours(min: Date, max: Date): Long {
        val milliseconds = getTotalMilliseconds(min, max)
        return milliseconds / (60 * 60 * 1000)
    }

    /**
     * 获取两个时间差的天数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalDays(min: Date, max: Date): Long {
        val milliseconds = getTotalMilliseconds(min, max)
        return milliseconds / (24 * 60 * 60 * 1000)
    }

    /**
     * 获取两个时间差的月数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalMonth(min: Date, max: Date): Long {
        val minLocalDate = toLocalDate(min)
        val maxLocalDate = toLocalDate(max)

        val period = Period.between(maxLocalDate, minLocalDate)
        return period.toTotalMonths()
    }

    /**
     * 获取两个时间差的周数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalWeek(min: Date, max: Date): Long {
        val totalDay = getTotalDays(min, max)
        return totalDay / 7
    }

    /**
     * 获取两个时间差的年数
     *
     * @param min
     * @param max
     * @return
     */
    fun getTotalYear(min: Date, max: Date): Long {
        val minLocalDate = toLocalDate(min)
        val maxLocalDate = toLocalDate(max)

        val period = Period.between(maxLocalDate, minLocalDate)
        return period.years.toLong()
    }

    /**
     * 增加日期
     * @param date
     * @param unit 单位 Calendar.SECOND
     * @param number 数量
     * @return
     */
    fun addCalendar(date: Date, unit: Int, number: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(unit, number)
        return calendar.time
    }

    /**
     * 增加日期
     * @param unit 单位 Calendar.SECOND
     * @param number 数量
     * @return
     */
    fun addCalendar(unit: Int, number: Int): Date {
        return addCalendar(Date(), unit, number)
    }

    /**
     * 增加秒数
     * @param date
     * @param second
     * @return
     */
    fun addSecond(date: Date, second: Int): Date {
        return addCalendar(date, Calendar.SECOND, second)
    }

    /**
     * 增加秒数
     * @param second
     * @return
     */
    fun addSecond(second: Int): Date {
        return addSecond(Date(), second)
    }

    /**
     * 增加分钟数
     * @param date
     * @param minute
     * @return
     */
    fun addMinute(date: Date, minute: Int): Date {
        return addCalendar(date, Calendar.MINUTE, minute)
    }

    /**
     * 增加分钟数
     * @param minute
     * @return
     */
    fun addMinute(minute: Int): Date {
        return addMinute(Date(), minute)
    }

    /**
     * 增加天数
     * @param date
     * @param day
     * @return
     */
    fun addDay(date: Date, day: Int): Date {
        return addCalendar(date, Calendar.DAY_OF_YEAR, day)
    }

    /**
     * 添加天数
     * @param day
     * @return
     */
    fun addDay(day: Int): Date {
        return addDay(Date(), day)
    }

    /**
     * 增加月数
     * @param date
     * @param month
     * @return
     */
    fun addMonth(date: Date, month: Int): Date {
        return addCalendar(date, Calendar.MONTH, month)
    }

    /**
     * 增加月数
     * @param month
     * @return
     */
    fun addMonth(month: Int): Date {
        return addMonth(Date(), month)
    }

    /**
     * 增加年数
     * @param date
     * @param year
     * @return
     */
    fun addYear(date: Date, year: Int): Date {
        return addCalendar(date, Calendar.YEAR, year)
    }

    /**
     * 增加年数
     * @param year
     * @return
     */
    fun addYear(year: Int): Date {
        return addYear(Date(), year)
    }

    /**
     * 获得当天的最小时间
     * @param date
     * @return
     */
    fun getMinDateOfDay(date: Date): Date {
        val c = Calendar.getInstance()
        c.time = date
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.time
    }

    /**
     * 获得当天的最大时间
     * @param date
     * @return
     */
    fun getMaxDateOfDay(date: Date): Date {
        val c = Calendar.getInstance()
        c.time = date
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        return c.time
    }
}