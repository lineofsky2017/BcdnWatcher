package com.ittianyu.bcdnwatcher.common.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.ittianyu.bcdnwatcher.R;
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

    public static Dialog showOkCancelDialog(Context context, String title, String content,
                                            boolean cancelable, DialogInterface.OnClickListener onOk) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setCancelable(cancelable)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ok, onOk)
                .create();
        dialog.show();
        return dialog;
    }

    public static Dialog showInfoDialog(Context context, String title, String content) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, null)
                .create();
        dialog.show();
        return dialog;
    }


}
