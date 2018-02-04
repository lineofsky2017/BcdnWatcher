package com.ittianyu.bcdnwatcher.common.utils;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;

import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.Status;

/**
 * Created by 86839 on 2018/2/4.
 */

public class LceeUtils {
    public static void removeObservers(LiveData<?> ld, Lcee<?> lcee, LifecycleOwner owner) {
        if (lcee == null || lcee.status != Status.Loading)
            ld.removeObservers(owner);
    }
}
