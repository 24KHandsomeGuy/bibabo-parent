package com.bibabo.utils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 15:23
 * @Description
 */
public abstract class DateUtils {


    /**
     * 获取当天的结束时间点， 如： 2015-05-22 11:49:13， 返回 2015-05-22 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndOfDate(Date date) {
        return getEndOfLaterDay(date, 0);
    }

    /**
     * 获取某一天的结束时间点， 如： 2015-05-22 11:49:13， 返回 2015-05-29 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndOfLaterDay(Date date, int day) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }


    /**
     * 格式化日期
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        if (dateStr == null || "".equals(dateStr.trim())) {
            return null;
        }

        try {
            return formatter.parse(dateStr.trim());
        } catch (ParseException e) {
        }

        return null;
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 默认以 <code>yyyy-MM-dd HH:mm:ss</code>格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static void main(String[] args) {
        // System.out.println(format(getEndOfLaterDay(new Date(), 7)));
        Date date = new Date();
        for (int i = 0; i < 6; i++) {
            // eg: signIn_1_2024-01-30
            date = org.apache.commons.lang3.time.DateUtils.addDays(date, -1);
            System.out.println(format(date));
        }
    }

}
