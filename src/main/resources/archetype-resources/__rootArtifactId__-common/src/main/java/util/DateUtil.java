package ${groupId}.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author ：zhang guo dong
 */
public class DateUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 返回当前格式化后的时间字符串。yyyy-MM-dd hh:mm:ss
     *
     * @return yyyy-MM-dd hh:mm:ss
     */
    public static String nowDateTimeString() {
        return date2string(new Date());
    }

    /**
     * 时间转换为字符串 精确到毫秒
     *
     * @param date 时间
     * @return 格式化后的时间字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String date2string(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        return format.format(date);
    }

    /**
     * 时间转换为字符串 精确到天
     *
     * @param date 时间
     * @return 格式化后的时间字符串 yyyy-MM-dd
     */
    public static String date2dayString(Date date) {
        String dateTime2String = date2string(date);
        return dateTime2String.split(" ")[0];
    }

    /**
     * 时间转换为字符串 精确到天，忽略时分秒
     *
     * @param date 时间
     * @return 格式化后的时间字符串 yyyy-MM-dd 00:00:00
     */
    public static String date2allDayString(Date date) {
        return date2dayString(date) + " 00:00:00";
    }

    /**
     * 字符串转时间
     *
     * @param str 字符串 yyyy-MM-dd
     * @return date时间
     */
    public static Date string2dateTime(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 获取未来某天的某一时刻的时间
     *
     * @param day    天
     * @param hour   小时
     * @param minute 分钟
     * @param sec    秒
     * @return DateTime
     */
    public static Date nextDay(Integer day, Integer hour, Integer minute, Integer sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                hour, minute, sec);
        return calendar.getTime();
    }

    /**
     * 获取未来某天的 0点时间
     *
     * @param day 天
     * @return DateTime
     */
    public static Date nextDay(Integer day) {
        return nextDay(day, 0, 0, 0);
    }

    /**
     * 获取未来时间
     *
     * @param minute 未来 分钟
     * @return 未来分钟时间
     */
    public static Date nextMinutes(Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取两个时间相差的天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 天数
     */
    public static int getDistanceDays(Date start, Date end) {
        int days = 0;
        long time1 = start.getTime();
        long time2 = end.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = (int) (diff / (24 * 60 * 60 * 1000));
        return days;
    }

    /**
     * 获取时间区间内的每一天
     *
     * @param begin 开始时间， 包含开始时间
     * @param end   结束时间， 包含结束时间
     * @return 时间区间(闭区间)内的每一天字符串 yyyy-MM-dd
     */
    public static List<String> listAllDayInSection(Date begin, Date end) {
        List<String> res = new ArrayList<>();

        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        calEnd.add(Calendar.DATE, 1);

        while (calEnd.getTime().after(calBegin.getTime())) {
            res.add(DateUtil.date2dayString(calBegin.getTime()));
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        return res;
    }

}
