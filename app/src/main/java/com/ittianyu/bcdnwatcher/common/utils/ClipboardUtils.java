package com.ittianyu.bcdnwatcher.common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by 86839 on 2018/2/3.
 */

public class ClipboardUtils {

    public static void copyText(Context context, String label, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (null == cm)
            return;
        cm.setPrimaryClip(ClipData.newPlainText(label, text));
    }

}
