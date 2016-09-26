/*
 * 
 */
package com.jaydenxiao.common.commonutils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 描述：日期处理类.
 *
 */
@SuppressWarnings("all")
public class TimeUtil {
    /**
     * one day millisecond count
     */
    public static final long ONE_DAY_MILLISECONDS = 1000 * 3600 * 24;

    public static final long ONE_HOUR_MILLISECONDS = 1000 * 3600;

    public static final long ONE_MIN_MILLISECONDS = 1000 * 60;

    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormatYMDHMS_f = "yyyyMMddHHmmss";
    public static String dateFormatMDHM = "MM-dd HH:mm";
    public static String dateFormat = "yyyy-MM-dd HH:mm";
    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";

    /**
     * 时间日期格式化到年月日时分.中文显示
     */
    public static String dateFormatYMDHMofChinese = "yyyy年MM月dd日 HH:mm";

    /**
     * 时间日期格式化到年月日.中文显示
     */
    public static String dateFormatYMDofChinese = "yyyy年MM月dd日";
    /**
     * 时间日期格式化到月日.中文显示
     */
    public static String dateFormatMDofChinese = "MM月dd日";
    /**
     * 时间日期格式化到月.中文显示
     */
    public static String dateFormatMofChinese = "MM月";
    /**
     * 时间日期格式化到年月.
     */
    public static String dateFormatYM = "yyyy-MM";

    /**
     * 时间日期格式化到年月日时分.
     */
    public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 时间日期格式化到月日.
     */
    public static String dateFormatMD = "MM/dd";
    public static String dateFormatM_D = "MM-dd";

    public static String dateFormatM = "MM月";
    public static String dateFormatD = "dd";
    public static String dateFormatM2 = "MM";

    public static String dateFormatMDHMofChinese = "MM月dd日HH时mm分";
    public static String dateFormatHMofChinese = "HH时mm分";

    /**
     * 时分秒.
     */
    public static String dateFormatHMS = "HH:mm:ss";

    /**
     * 时分.
     */
    public static String dateFormatHM = "HH:mm";

    /**
     * 上午/下午时分
     */
    public static String dateFormatAHM = "aHH:mm";

    public static String dateFormatYMDE = "yyyy/MM/dd E";
    public static String dateFormatYMD2 = "yyyy/MM/dd";

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @SuppressLint("SimpleDateFormat")
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 时间戳转特定格式时间
     * @param dataFormat
     * @param timeStamp
     * @return
     */
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        return format.format(new Date(timeStamp));
    }

    /**
     * 将毫秒转换成秒
     *
     * @param time
     * @return
     */
    public static int convertToSecond(Long time) {
        Date date = new Date();
        date.setTime(time);
        return date.getSeconds();
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date          日期时间
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public Date getDateByOffset(Date date, int calendarField, int offset) {
        Calendar c = new GregorianCalendar();
        try {
            c.setTime(date);
            c.add(calendarField, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 描述：获取指定日期时间的字符串(可偏移).
     *
     * @param strDate       String形式的日期时间
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getStringByOffset(String strDate, String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：Date类型转化为String类型(可偏移).
     *
     * @param date          the date
     * @param format        the format
     * @param calendarField the calendar field
     * @param offset        the offset
     * @return String String类型日期时间
     */
    public static String getStringByOffset(Date date, String format, int calendarField, int offset) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(date);
            c.add(calendarField, offset);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * from yyyy-MM-dd HH:mm:ss to MM-dd HH:mm
     */
    public static String formatDate(String before) {
        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            return before;
        }
        return after;
    }

    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds, String format) {
        String thisDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            thisDateTime = mSimpleDateFormat.format(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;

    }


    //获取当前系统当天日期
    public static String getCurrentDay() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, 0);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统当天日期
    public static String getCurrentDay2() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, 0);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统前后第几天
    public static String getNextDay(int i) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, i);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }
    //获取当前系统前后第几小时
    public static String getNextHour(int i) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.HOUR_OF_DAY, i);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串(可偏移).
     *
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getCurrentDateByOffset(String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;

    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的天数
     */
    public static int getOffectDay(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffectDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffectHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：获取本周一.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    /**
     * 描述：获取本周日.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.SUNDAY);
    }

    /**
     * 描述：获取本周的某一天.
     *
     * @param format        the format
     * @param calendarField the calendar field
     * @return String String类型日期时间
     */
    private static String getDayOfWeek(String format, int calendarField) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == calendarField) {
                strDate = mSimpleDateFormat.format(c.getTime());
            } else {
                int offectDay = calendarField - week;
                if (calendarField == Calendar.SUNDAY) {
                    offectDay = 7 - Math.abs(offectDay);
                }
                c.add(Calendar.DATE, offectDay);
                strDate = mSimpleDateFormat.format(c.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //当前月的第一天
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月最后一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // 当前月的最后一天
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * 描述：获取表示当前日期的0点时间毫秒数.
     *
     * @return the first time of day
     */
    public static long getFirstTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 00:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：获取表示当前日期24点时间毫秒数.
     *
     * @return the last time of day
     */
    public static long getLastTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 24:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：判断是否是闰年()
     * <p>(year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 描述：根据时间返回几天前或几分钟的描述.
     *
     * @param strDate the str date
     * @return the string
     */
    public static String formatDateStr2Desc(String strDate, String outFormat) {

        DateFormat df = new SimpleDateFormat(dateFormatYMDHM);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(df.parse(strDate));
            c1.setTime(new Date());
            int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
            if (d == 0) {
                int h = getOffectHour(c1.getTimeInMillis(), c2.getTimeInMillis());
                if (h > 0) {
                    return h + "小时前";
                } else if (h < 0) {
                    return Math.abs(h) + "小时后";
                } else if (h == 0) {
                    int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
                    if (m > 0) {
                        return m + "分钟前";
                    } else if (m < 0) {
                        return Math.abs(m) + "分钟后";
                    } else {
                        return "刚刚";
                    }
                }
            } else if (d > 0) {
                if (d == 1) {
                    return "昨天";
                } else if (d == 2) {
                    return "前天";
                }
            } else if (d < 0) {
                if (d == -1) {
                    return "明天";
                } else if (d == -2) {
                    return "后天";
                }
                return Math.abs(d) + "天后";
            }

            String out = getStringByFormat(strDate, outFormat);
            if (!TextUtils.isEmpty(out)) {
                return out;
            }
        } catch (Exception e) {
        }

        return strDate;
    }


    /**
     * 取指定日期为星期几
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String   星期几
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "星期日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "星期日";
                break;
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    private static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param ms
     * @return
     */
    public static String getfriendlyTime(Long ms) {
        if (ms == null) return "";
//		Date time = toDate(sdate);
        Date time = new Date();
        time.setTime(ms);

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                if (((cal.getTimeInMillis() - time.getTime()) / 60000) < 1) {
                    ftime = "刚刚";
                } else {
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 距离当前多少个小时
     *
     * @param dateStr
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getExpiredHour(String dateStr) {
        int ret = -1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date;
        try {
            date = sdf.parse(dateStr);
            Date dateNow = new Date();

            long times = date.getTime() - dateNow.getTime();
            if (times > 0) {
                ret = ((int) (times / ONE_HOUR_MILLISECONDS));
            } else {
                ret = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 过了多少个小时
     * @param dateStr
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getExpiredHour2(String dateStr) {
        int ret = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sendDate;
        try {
            sendDate = sdf.parse(dateStr);
            Date dateNow = new Date(System.currentTimeMillis());
            Log.e("JPush","date="+sendDate);
            long times = dateNow.getTime() - sendDate.getTime();
            Log.e("JPush","date.getTime()="+sendDate.getTime());
            if (times > 0) {
                ret = ((int) (times / ONE_HOUR_MILLISECONDS));
                int sdqf =(int)Math.floor(times /ONE_HOUR_MILLISECONDS);
            } else {
                ret = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("JPush","ret="+ret);
        return ret;
    }


    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(long sdate) {
        boolean b = false;
        Date time = new Date(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    /**
     * 友好显示时间差
     *
     * @param diff 毫秒
     * @return
     */
    public static String getFriendTimeOffer(long diff) {
        int day = (int) (diff / (24 * 60 * 60 * 1000));
        if (day > 0)
            return day + "天";
        int time = (int) (diff / (60 * 60 * 1000));
        if (time > 0)
            return time + "小时";
        int min = (int) (diff / (60 * 1000));
        if (min > 0)
            return min + "分钟";
        int sec = (int) diff / 1000;
        if (sec > 0)
            return sec + "秒";
        return "1秒";
    }

    /**
     * 友好的时间间隔
     *
     * @param duration 秒
     * @return
     */
    public static String getFriendlyDuration(long duration) {
        String str = "";
        long tmpDuration = duration;
        str += (tmpDuration / 60 > 10 ? tmpDuration / 60 : "0" + tmpDuration / 60) + ":";
        tmpDuration %= 60;
        str += (tmpDuration / 1 >= 10 ? tmpDuration / 1 : "0" + tmpDuration / 1);
        tmpDuration %= 1;
        return str;
    }
    /**
     * 友好的时间间隔2
     *
     * @param duration 秒
     * @return
     */
    public static String getFriendlyDuration2(long duration) {
        String str = "";
        long tmpDuration = duration;
        str += (tmpDuration / 60>0?tmpDuration / 60+"'":"");
        tmpDuration %= 60;
        str += (tmpDuration / 1>=10?tmpDuration / 1+"''":"0"+tmpDuration / 1+"''");
        tmpDuration %= 1;
        return str;
    }

    public static String getFriendlyMusicDuration(long duration) {
        String str = "-";
        int tmpDuration = (int) (duration / 1000);//秒
        str += (tmpDuration / 3600 > 10 ? tmpDuration / 3600 : "0" + tmpDuration / 3600) + ":";
        tmpDuration %= 3600;
        str += (tmpDuration / 60 > 10 ? tmpDuration / 60 : "0" + tmpDuration / 60) + ":";
        tmpDuration %= 60;
        str += (tmpDuration / 1 >= 10 ? tmpDuration / 1 : "0" + tmpDuration / 1);
        tmpDuration %= 1;
        return str;
    }

    /**
     * 通过日期来确定星座
     *
     * @param mouth
     * @param day
     * @return
     */
    public static String getStarSeat(int mouth, int day) {
        String starSeat = null;
        if ((mouth == 3 && day >= 21) || (mouth == 4 && day <= 19)) {
            starSeat = "白羊座";
        } else if ((mouth == 4 && day >= 20) || (mouth == 5 && day <= 20)) {
            starSeat = "金牛座";
        } else if ((mouth == 5 && day >= 21) || (mouth == 6 && day <= 21)) {
            starSeat = "双子座";
        } else if ((mouth == 6 && day >= 22) || (mouth == 7 && day <= 22)) {
            starSeat = "巨蟹座";
        } else if ((mouth == 7 && day >= 23) || (mouth == 8 && day <= 22)) {
            starSeat = "狮子座";
        } else if ((mouth == 8 && day >= 23) || (mouth == 9 && day <= 22)) {
            starSeat = "处女座";
        } else if ((mouth == 9 && day >= 23) || (mouth == 10 && day <= 23)) {
            starSeat = "天秤座";
        } else if ((mouth == 10 && day >= 24) || (mouth == 11 && day <= 22)) {
            starSeat = "天蝎座";
        } else if ((mouth == 11 && day >= 23) || (mouth == 12 && day <= 21)) {
            starSeat = "射手座";
        } else if ((mouth == 12 && day >= 22) || (mouth == 1 && day <= 19)) {
            starSeat = "摩羯座";
        } else if ((mouth == 1 && day >= 20) || (mouth == 2 && day <= 18)) {
            starSeat = "水瓶座";
        } else {
            starSeat = "双鱼座";
        }
        return starSeat;
    }

    /**
     * 返回聊天时间
     * @return
     */
    public static  String getChatTimeForShow(long time){
        if(TimeUtil.isToday(time)){
            return TimeUtil.getStringByFormat(time, TimeUtil.dateFormatHMofChinese);
        }else{
            return TimeUtil.getStringByFormat(time, TimeUtil.dateFormatMDHMofChinese);
        }
    }

    /**
     * 获取指定时间的毫秒值
     */
    public static long getDatelongMills(String fomat,String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(fomat);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long longDate = date.getTime();
        return longDate;
    }

    /**
     * 两个日期比较
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() - dt2.getTime()>0) {//date1>date2
                return 1;
            } else {
                return -1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
