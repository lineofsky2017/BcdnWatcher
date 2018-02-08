package com.ittianyu.bcdnwatcher.common.base;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by 86839 on 2018/2/9.
 */

public class BaseActivity extends AppCompatActivity {
    // umeng
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    // umeng

}
