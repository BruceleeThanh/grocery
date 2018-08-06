package com.grocery.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PC on 1/5/2017.
 */

public class DateUtils {

    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String YEAR_MONTH_DAY_HOUR_MINITES_SECONDS = "yyyy-MM-dd HH:mm:ss";
    public static final String DAY_MONTH_YEAR = "dd/MM/yyyy";
    public static final String MONTH_DAY_YEAR = "MM/dd/yyyy";
    //11/02/2016
    public static final String STAMP_DATE_NO_TIME = "%04d-%02d-%02d";
    public static final String YEAR_MONTH_DAY_HOUR_MINITES_SECONDS_NO_SPLITER = "yyyyMMddHHmmss";
    public static final String HOUR_MINITES_SECONDS = "HH:mm:ss";
    public static final String HOUR_MINITES_SECONDS_FORMAT = "%02d:%02d";

    public static final String YEAR_MONTH_DAY_T_HOUR_MINITES_SECONDS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String MMM_DD = "MMM dd";

    public static String getStampDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        String date = String.format(Locale.getDefault(),
                "%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour,
                minutes, seconds);
        return date;

    }

    public static String getTime(String spliter, boolean withMilliSecond) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        int milliseconds = c.get(Calendar.MILLISECOND);
        if (withMilliSecond)
            return String.format(Locale.getDefault(), "%02d" + spliter
                            + "%02d" + spliter + "%02d" + spliter + "%03d", hour,
                    minutes, seconds, milliseconds);
        else
            return String.format(Locale.getDefault(), "%02d" + spliter
                    + "%02d" + spliter + "%02d", hour, minutes, seconds);

    }

    public static String getDate(String skeleton) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(skeleton,Locale.getDefault());
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getStampDate(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        String date = String.format(Locale.getDefault(),"%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour,minutes, seconds);
        return date;

    }

    public static String getStampDateNoTime(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String date = String.format(Locale.getDefault(), "%04d-%02d-%02d",year, month, day);
        return date;

    }

    public static String getStampDate(String spliter) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        String date = String.format(Locale.getDefault(), "%04d" + spliter
                        + "%02d" + spliter + "%02d" + spliter + "%02d" + spliter
                        + "%02d" + spliter + "%02d", year, month, day, hour,
                minutes, seconds);
        return date;

    }

    public static String getDate(String currentSkeleton, String convertedSkeleton, String srtDate) {
        String newFormat = "";
        Date testDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(currentSkeleton,Locale.getDefault());
            testDate = sdf.parse(srtDate);
            SimpleDateFormat formatter = new SimpleDateFormat(convertedSkeleton, Locale.getDefault());
            newFormat = formatter.format(testDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newFormat;
    }

    public static long compareDate(String startDatestr,String startDateSkeleton, String endDatestr,	String endDateSkeleton) throws Exception {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(startDateSkeleton,
                Locale.getDefault());
        startDate = sdf.parse(startDatestr);

        SimpleDateFormat formatter = new SimpleDateFormat(endDateSkeleton,
                Locale.getDefault());
        endDate = formatter.parse(endDatestr);
        return startDate.compareTo(endDate);
    }

    public static long compareDate_inDays(String startDatestr,
                                          String startDateSkeleton, String endDatestr,
                                          String endDateSkeleton) throws Exception {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(startDateSkeleton,
                Locale.getDefault());
        startDate = sdf.parse(startDatestr);

        SimpleDateFormat formatter = new SimpleDateFormat(endDateSkeleton,
                Locale.getDefault());
        endDate = formatter.parse(endDatestr);
        return startDate.compareTo(endDate) / (1000 * 60 * 60 * 24);
    }

    public static String getTime(String time) {
        String strValue = "";
        try {
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(3, 5));
            strValue = String.format(Locale.getDefault(), "%02d:%02d",
                    hour, minute);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strValue;
    }

    public static Calendar getDate(String date, String skeleton)  throws Exception {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(skeleton,Locale.getDefault());
        cal.setTime(sdf.parse(date));// all done
        return cal;

    }

}
