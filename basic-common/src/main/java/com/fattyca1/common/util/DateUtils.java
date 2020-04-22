package com.fattyca1.common.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;

/**
 * <br>时间工具类</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/21 11:29
 * @since 1.0
 */
public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String HH_MM_SS = "hh:mm:ss";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYMMDD = "yyMMdd";
    public static final String YYMMDDHHMMSS = "yyMMddHHmmss";


    public static final DateTimeFormatter YYYY_MM_DD_FORMAT = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_FORMAT = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
    public static final DateTimeFormatter YYYY_MM_FORMAT = DateTimeFormatter.ofPattern(YYYY_MM);
    public static final DateTimeFormatter HH_MM_SS_FORMAT = DateTimeFormatter.ofPattern(HH_MM_SS);
    public static final DateTimeFormatter HHMMSS_NO_SEPARATOR_FORMAT = DateTimeFormatter.ofPattern(HHMMSS);
    public static final DateTimeFormatter YYYYMM_NO_SEPARATOR_FORMAT = DateTimeFormatter.ofPattern(YYYYMM);
    public static final DateTimeFormatter YYMMDD_NO_SEPARATOR_FORMAT = DateTimeFormatter.ofPattern(YYMMDD);
    public static final DateTimeFormatter YYMMDDHHMMSS_NO_SEPARATOR_FORMAT = DateTimeFormatter.ofPattern(YYMMDDHHMMSS);


    /**
     * 获取当前的日期
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前日期时间
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * yyyyMMdd
     */
    public static String getCurrentDateStr() {
        return LocalDate.now().format(YYYY_MM_DD_FORMAT);
    }

    /**
     * yyMMdd
     */
    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(YYMMDD_NO_SEPARATOR_FORMAT);
    }

    /**
     * yyyyMM
     */
    public static String getCurrentMonthStr() {
        return LocalDate.now().format(YYYYMM_NO_SEPARATOR_FORMAT);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTimeStr() {
        return LocalDateTime.now().format(YYYY_MM_DD_HH_MM_SS_FORMAT);
    }

    /**
     * yyMMddHHmmss
     */
    public static String getCurrentShortDateTimeStr() {
        return LocalDateTime.now().format(YYMMDDHHMMSS_NO_SEPARATOR_FORMAT);
    }

    /**
     * HHmmss
     */
    public static String getCurrentTimeStr() {
        return LocalTime.now().format(HHMMSS_NO_SEPARATOR_FORMAT);
    }

    /**
     * 获取当前日期格式化的字符串
     *
     * @param pattern 日期格式
     */
    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前日期时间格式化的字符串
     *
     * @param pattern 日期时间格式
     */
    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间格式化的字符串
     *
     * @param pattern 时间格式
     */
    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串日期格式化为日期
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串日期格式化为日期
     *
     * @param dateStr 日期字符串
     */
    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, YYYY_MM_DD_FORMAT);
    }

    /**
     * 将字符串日期时间格式化为日期时间
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern 日期时间格式
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串日期时间格式化为日期时间
     *
     * @param dateTimeStr 日期时间字符串
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, YYYY_MM_DD_HH_MM_SS_FORMAT);
    }

    /**
     * 将字符串时间格式化为时间
     *
     * @param timeStr 字符串时间
     * @param pattern 时间格式
     */
    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串时间格式化为时间
     *
     * @param timeStr 字符串时间
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, HHMMSS_NO_SEPARATOR_FORMAT);
    }

    /**
     * 格式化传入的日期
     *
     * @param date 日期
     * @param pattern 日期格式
     */
    public static String formatLocalDate(LocalDate date, String pattern) {
        return Objects.isNull(date) ? "" : date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化传入的日期
     *
     * @param date 日期
     * @param formatter 日期格式
     */
    public static String formatLocalDate(LocalDate date, DateTimeFormatter formatter) {
        return Objects.isNull(date) ? "" : date.format(formatter);
    }

    /**
     * 格式化传入的日期
     *
     * @param date 日期
     */
    public static String formatLocalDate(LocalDate date) {
        return Objects.isNull(date) ? "" : date.format(YYYY_MM_DD_FORMAT);
    }

    /**
     * 格式化传入的日期时间
     *
     * @param datetime 日期时间
     * @param pattern 日期时间格式
     */
    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return Objects.isNull(datetime) ? "" : datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化传入的日期时间
     *
     * @param datetime 日期时间
     * @param formatter 日期时间格式
     */
    public static String formatLocalDateTime(LocalDateTime datetime, DateTimeFormatter formatter) {
        return Objects.isNull(datetime) ? "" : datetime.format(formatter);
    }

    /**
     * 格式化传入的日期时间
     *
     * @param datetime 日期时间
     */
    public static String formatLocalDateTime(LocalDateTime datetime) {
        return Objects.isNull(datetime) ? "" : datetime.format(YYYY_MM_DD_HH_MM_SS_FORMAT);
    }

    /**
     * 格式化传入的时间
     *
     * @param time 时间
     * @param pattern 时间格式
     */
    public static String formatLocalTime(LocalTime time, String pattern) {
        return Objects.isNull(time) ? "" : time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化传入的时间
     *
     * @param time 时间
     * @param formatter 时间格式
     */
    public static String formatLocalTime(LocalTime time, DateTimeFormatter formatter) {
        return Objects.isNull(time) ? "" : time.format(formatter);
    }

    /**
     * 格式化传入的时间
     *
     * @param time 时间
     */
    public static String formatLocalTime(LocalTime time) {
        return Objects.isNull(time) ? "" : time.format(HHMMSS_NO_SEPARATOR_FORMAT);
    }

    /**
     * 获取两个日期相隔的天数
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    public static long periodDays(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 获取两个日期相隔的小时
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    /**
     * 获取两个日期相隔的分钟
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    /**
     * 获取两个日期相隔的秒数
     */
    public static long durationSeconds(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).getSeconds();
    }

    /**
     * 获取两个日期相隔的毫秒数
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 是否当天
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     * 获取传入日期时间的时间戳
     */
    public static long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将date转换为LocalDateTime
     */
    public static LocalDateTime convertDate2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDate转换为date
     */
    public static Date convertLocalDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转换为date
     */
    public static Date convertLocalDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取相隔的秒数
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public static long getSecondsBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        return Duration.between(startDate, endDate).getSeconds();
    }

    /**
     * 获取相隔的分钟
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public static long getMinutesBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        return Duration.between(startDate, endDate).toMinutes();
    }

    /**
     * 获取相隔的天数
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public static long getDaysBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        return Duration.between(startDate, endDate).toDays();
    }

    /**
     * 获取当前日期间隔多少天的日期
     */
    public static <T extends Temporal> T getNextDateOffsetDate(T datetime, long amount, TemporalUnit unit) {
        return (T) datetime.plus(amount, unit);
    }

    /**
     * 时间戳转换为LocalDateTime
     */
    public static LocalDateTime parseTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}
