package com.ittianyu.bcdnwatcher.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yu on 2018-1-27
 */

public class SpUtils {

    private static Context context;

    public static void init(Context context) {
        SpUtils.context = context;
    }

    public static Set<String> getStringAsSet(String name, String key, Set<String> defaultValue) {
        SharedPreferences sp = getSp(name);
        String value = sp.getString(key, null);
        if (null == value)
            return defaultValue;
        String[] values = value.split(",");
        if (null == values || values.length == 0)
            return Collections.emptySet();
        Set<String> set = new HashSet<>(values.length);
        Collections.addAll(set, values);
        return set;
    }

    public static void putSetAsString(String name, String key, Set<String> value) {
        SharedPreferences sp = getSp(name);
        String result = null;
        if (null == value || value.size() == 0)
            result = "";
        else {
            result = TextUtils.join(",", value);
        }
        sp.edit().putString(key, result).apply();
    }

    public static void put(String name, String key, long value) {
        SharedPreferences sp = getSp(name);
        sp.edit().putLong(key, value).apply();
    }

    public static long get(String name, String key, long defaultValue) {
        SharedPreferences sp = getSp(name);
        return sp.getLong(key, defaultValue);
    }

    public static void put(String name, String key, float value) {
        SharedPreferences sp = getSp(name);
        sp.edit().putFloat(key, value).apply();
    }

    public static float get(String name, String key, float defaultValue) {
        SharedPreferences sp = getSp(name);
        return sp.getFloat(key, defaultValue);
    }


    public static void put(String name, String key, String value) {
        SharedPreferences sp = getSp(name);
        sp.edit().putString(key, value).apply();
    }

    public static String get(String name, String key, String defaultValue) {
        SharedPreferences sp = getSp(name);
        return sp.getString(key, defaultValue);
    }

    public static void put(String name, String key, boolean value) {
        SharedPreferences sp = getSp(name);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean get(String name, String key, boolean defaultValue) {
        SharedPreferences sp = getSp(name);
        return sp.getBoolean(key, defaultValue);
    }

    public static void put(String name, String key, Set<String> value) {
        putSetAsString(name, key, value);
    }

    public static Set<String> get(String name, String key, Set<String> defaultValue) {
        return getStringAsSet(name, key, defaultValue);
    }

    private static SharedPreferences getSp(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

}