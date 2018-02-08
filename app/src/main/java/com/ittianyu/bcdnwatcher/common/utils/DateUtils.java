package com.ittianyu.bcdnwatcher.common.utils;

import java.util.Date;

/**
 * Created by yu on 2018/2/8.
 */

public class DateUtils {
    public static boolean isToday(long time) {
        Date date = new Date(time);
        Date now = new Date();
        return date.getYear() == now.getYear() &&
                date.getMonth() == now.getMonth() &&
                date.getDay() == now.getDay();
    }
    
}
