package com.minyan.utils;

/**
 * @decription
 * @author minyan.he
 * @date 2024/8/1 18:15
 */
import com.minyan.Enum.CycleEnum;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {
  // 日期格式化器
  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
      new SimpleDateFormat(DEFAULT_DATE_FORMAT);
  public static final SimpleDateFormat SIMPLE_DATE_FORMAT_SECONDS =
      new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

  // String 转 Date
  public static Date stringToDate(String dateString) throws ParseException {
    return SIMPLE_DATE_FORMAT.parse(dateString);
  }

  // String 转 LocalDate
  public static LocalDate stringToLocalDate(String dateString) {
    return LocalDate.parse(dateString, DATE_FORMATTER);
  }

  // String 转 LocalDateTime
  public static LocalDateTime stringToLocalDateTime(String dateTimeString) {
    return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
  }

  // Date 转 String (日)
  public static String dateToString(Date date) {
    return SIMPLE_DATE_FORMAT.format(date);
  }

  // Date 转 String (秒)
  public static String dateToStringSeconds(Date date) {
    return SIMPLE_DATE_FORMAT_SECONDS.format(date);
  }

  // LocalDate 转 String
  public static String localDateToString(LocalDate date) {
    return date.format(DATE_FORMATTER);
  }

  // LocalDateTime 转 String
  public static String localDateTimeToString(LocalDateTime dateTime) {
    return dateTime.format(DATE_TIME_FORMATTER);
  }

  // Date 转 LocalDateTime
  public static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  // LocalDateTime 转 Date
  public static Date localDateTimeToDate(LocalDateTime dateTime) {
    return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * 时间推移
   *
   * @param originalDate
   * @param cycle
   * @param amount
   * @return
   */
  public static Date adjustDate(Date originalDate, CycleEnum cycle, long amount) {
    Instant instant = originalDate.toInstant();

    switch (cycle) {
      case SECONDS:
        instant = instant.plus(amount, ChronoUnit.SECONDS);
        break;
      case MINUTES:
        instant = instant.plus(amount, ChronoUnit.MINUTES);
        break;
      case HOURS:
        instant = instant.plus(amount, ChronoUnit.HOURS);
        break;
      case DAY:
        instant = instant.plus(amount, ChronoUnit.DAYS);
        break;
      case WEEK:
        instant = instant.plus(amount, ChronoUnit.WEEKS);
        break;
      case MONTH:
        // 注意：对于月份和年份，使用Period更合适，因为它们不是恒定长度的。
        instant =
            instant
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .plusMonths(amount)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        break;
      case YEAR:
        instant =
            instant
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .plusYears(amount)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        break;
      default:
        throw new IllegalArgumentException(
            String.format("%s%s", "[TimeUtil][adjustDate]时间类型传入有误，传入类型：", cycle));
    }

    return Date.from(instant);
  }

  /**
   * 通过时长计算秒数
   *
   * @param period
   * @param cycle
   * @return
   */
  Long getSecondByPeriodAndCycle(Integer period, CycleEnum cycle) {
    switch (cycle) {
      case SECONDS:
        return period.longValue();
      case MINUTES:
        return period.longValue() * 60;
      case HOURS:
        return period.longValue() * 60 * 60;
      case DAY:
        return period.longValue() * 60 * 60 * 24;
      case WEEK:
        return period.longValue() * 60 * 60 * 24 * 7;
      case MONTH:
        return period.longValue() * 60 * 60 * 24 * 30;
    }
    return 0L;
  }
}
