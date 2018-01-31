package com.ittianyu.bcdnwatcher.common.utils;

import android.app.Dialog;
import android.content.Context;

import com.ittianyu.bcdnwatcher.common.view.LoadingDialog;


/**
 * Created by yu on 2017/2/25.
 */

public class DialogUtils {
    public static Dialog showLoadingDialog(Context context) {
        if (context == context.getApplicationContext())
            return null;
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        return loadingDialog;
    }

}
