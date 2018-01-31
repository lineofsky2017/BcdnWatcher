package com.ittianyu.bcdnwatcher.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.ittianyu.bcdnwatcher.R;


/**
 * Created by yu on 2017/5/10.
 */

public class LoadingDialog extends Dialog {
    private String message;
    private TextView tv;

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        tv = (TextView) findViewById(R.id.tv);
        if (TextUtils.isEmpty(message)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(message);
        }

        // set random style
        SpinKitView skv = (SpinKitView) findViewById(R.id.skv);
        int position = (int) (Math.random() *  Style.values().length);
        Style style = Style.values()[position];
        Sprite drawable = SpriteFactory.create(style);
        skv.setIndeterminateDrawable(drawable);
    }

    public void setMessage(String message) {
        this.message = message;
        if (null != tv) {
            tv.setText(message);
            tv.setVisibility(View.VISIBLE);
        }
    }
}