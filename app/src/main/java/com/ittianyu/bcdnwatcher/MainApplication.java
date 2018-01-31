package com.ittianyu.bcdnwatcher;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.ittianyu.bcdnwatcher.common.repository.local.db.DBHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by yu on 2016/11/24.
 */
public class MainApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        initLogger();
        initLeakCanary();
        initRepository();
    }


    /**
     * use LeakCanary to check mey leak
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * init logger
     */
    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }


    private void initRepository() {
        DBHelper.getInstance().init(context);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * tinker
     * @param base
     */
    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }
}
