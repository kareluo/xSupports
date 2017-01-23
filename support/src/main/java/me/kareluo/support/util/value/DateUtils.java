package me.kareluo.support.util.value;

/**
 * Created by felix on 16/12/15.
 */
public class DateUtils {

    private DateUtils() {
        /* cannot be instantiated */
    }

    /**
     * 判断是否是闰年
     *
     * @param year 年份
     * @return true闰年, false平年
     */
    public static boolean isLeapYear(long year) {
        return year > 0 && (year % 400 == 0 || year % 4 == 0 && year % 100 != 0);
    }
}
