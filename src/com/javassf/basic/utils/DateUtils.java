package com.javassf.basic.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
  public static Boolean getSameHourTime(Integer hour)
  {
    if (hour == null) {
      return Boolean.valueOf(false);
    }
    Calendar cal = Calendar.getInstance();
    int h = cal.get(11);
    if (hour.intValue() == h) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }

  public static String getNowToString() {
    Calendar cal = Calendar.getInstance();
    int year = cal.get(1);
    int month = cal.get(2) + 1;
    int day = cal.get(5);
    StringBuilder sb = new StringBuilder();
    sb.append(year);
    if (month < 10) {
      sb.append("0");
    }
    sb.append(month);
    if (day < 10) {
      sb.append("0");
    }
    sb.append(day);
    return sb.toString();
  }

  public static Date getToday()
  {
    Calendar cld = Calendar.getInstance();
    cld.set(11, 0);
    cld.set(12, 0);
    cld.set(13, 0);
    cld.set(14, 0);
    return cld.getTime();
  }

  public static Date getEndOfDay()
  {
    Calendar cld = Calendar.getInstance();
    cld.set(11, 23);
    cld.set(12, 59);
    cld.set(13, 59);
    cld.set(14, 999);
    return cld.getTime();
  }

  public static Date getStartOfDay(int interval)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTimeInMillis(cld.getTimeInMillis() + interval * 24 * 60 * 60 * 
      1000L);
    cld.set(11, 0);
    cld.set(12, 0);
    cld.set(13, 0);
    cld.set(14, 0);
    return cld.getTime();
  }

  public static Date getStartOfDay(Date date)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.set(11, 0);
    cld.set(12, 0);
    cld.set(13, 0);
    cld.set(14, 0);
    return cld.getTime();
  }

  public static Date getEndOfDay(int interval)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTimeInMillis(cld.getTimeInMillis() + interval * 24 * 60 * 60 * 
      1000L);
    cld.set(11, 23);
    cld.set(12, 59);
    cld.set(13, 59);
    cld.set(14, 999);
    return cld.getTime();
  }

  public static Date getEndOfDay(Date date)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.set(11, 23);
    cld.set(12, 59);
    cld.set(13, 59);
    cld.set(14, 999);
    return cld.getTime();
  }

  public static Date getStartOfWeek(int interval)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTimeInMillis(cld.getTimeInMillis() + interval * 7 * 24 * 60 * 60 * 
      1000L);
    cld.set(7, 1);
    return getStartOfDay(cld.getTime());
  }

  public static Date getStartOfWeek(int year, int week)
  {
    Calendar cld = Calendar.getInstance();
    cld.set(1, year);
    cld.set(3, week);
    cld.set(7, 1);
    return getStartOfDay(cld.getTime());
  }

  public static Date getStartOfWeek(Date date, int interval)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.setTimeInMillis(cld.getTimeInMillis() + interval * 7 * 24 * 60 * 60 * 
      1000L);
    cld.set(7, 1);
    return getStartOfDay(cld.getTime());
  }

  public static Date getStartOfWeek(Date date)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.set(7, 1);
    return getStartOfDay(cld.getTime());
  }

  public static Date getEndOfWeek(int interval)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(getStartOfWeek(interval + 1));
    return new Date(cld.getTimeInMillis() - 1L);
  }

  public static Date getEndOfWeek(int year, int week)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(getStartOfWeek(year, week + 1));
    return new Date(cld.getTimeInMillis() - 1L);
  }

  public static Date getEndOfWeek(Date date, int interval)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.setTime(getStartOfWeek(date, interval + 1));
    return new Date(cld.getTimeInMillis() - 1L);
  }

  public static Date getEndOfWeek(Date date)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.set(7, 7);
    return getEndOfDay(cld.getTime());
  }

  public static Date getStartOfMonth(Date date)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.set(5, 1);
    return getStartOfDay(cld.getTime());
  }

  public static Date getStartOfMonth(int year, int month)
  {
    Calendar cld = Calendar.getInstance();
    cld.set(1, year);
    cld.set(2, month);
    cld.set(5, 1);
    return getStartOfDay(cld.getTime());
  }

  public static Date getStartOfMonth(int interval)
  {
    Calendar cld = Calendar.getInstance();
    int currentMonth = cld.get(2);
    cld.add(1, (interval + currentMonth) / 12);
    cld.set(2, (interval + currentMonth) % 12);
    cld.set(5, 1);
    return getStartOfDay(cld.getTime());
  }

  public static Date getEndOfMonth(Date date)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    int maxDay = cld.getActualMaximum(5);
    cld.set(5, maxDay);
    return getEndOfDay(cld.getTime());
  }

  public static Date getEndOfMonth(int interval)
  {
    return new Date(getStartOfMonth(interval + 1).getTime() - 1L);
  }

  public static Date getEndOfMonth(int year, int month)
  {
    return new Date(getStartOfMonth(year, month + 1).getTime() - 1L);
  }

  public static int monthsBetween(Date startDate, Date endDate)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(startDate);
    int startMonth = cld.get(2);
    int startYear = cld.get(1);
    cld.setTime(endDate);
    int endMonth = cld.get(2);
    int endYear = cld.get(1);
    return (endYear - startYear) * 12 + (endMonth - startMonth);
  }

  public static int daysBetween(Date startDate, Date endDate)
  {
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    c1.setTime(startDate);
    c2.setTime(endDate);
    return daysBetween(c1, c2);
  }

  public static int daysBetween(Calendar early, Calendar late) {
    return (int)(toJulian(late) - toJulian(early));
  }

  public static final float toJulian(Calendar c) {
    int Y = c.get(1);
    int M = c.get(2);
    int D = c.get(5);
    int A = Y / 100;
    int B = A / 4;
    int C = 2 - A + B;
    float E = (int)(365.25F * (Y + 4716));
    float F = (int)(30.6001F * (M + 1));
    float JD = C + D + E + F - 1524.5F;
    return JD;
  }

  public static int getAge(Date birthday)
  {
    Calendar c1 = Calendar.getInstance();
    c1.setTime(birthday);
    Calendar c2 = Calendar.getInstance();
    c2.setTime(new Date());
    int age = c2.get(1) - c1.get(1);
    return age < 0 ? 0 : age;
  }

  public static Date addDate(Date date, int day)
  {
    Calendar cld = Calendar.getInstance();
    cld.setTime(date);
    cld.setTimeInMillis(cld.getTimeInMillis() + day * 24L * 3600L * 
      1000L);
    return cld.getTime();
  }
}
