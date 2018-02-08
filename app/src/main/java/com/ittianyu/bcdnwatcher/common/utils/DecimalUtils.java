package com.ittianyu.bcdnwatcher.common.utils;

/**
 * Created by 86839 on 2018/2/8.
 */

public class DecimalUtils {
    public static boolean isZero(double number) {
        if (Math.abs(number) < 0.000001) {
            return true;
        }
        return false;
    }
}
