package com.ittianyu.bcdnwatcher;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.ittianyu.bcdnwatcher.common.adapter.VpAdapter;
import com.ittianyu.bcdnwatcher.common.bean.VersionBean;
import com.ittianyu.bcdnwatcher.common.utils.UpdateUtils;
import com.ittianyu.bcdnwatcher.databinding.ActivityMainBinding;
import com.ittianyu.bcdnwatcher.features.me.MeFragment;
import com.ittianyu.bcdnwatcher.features.watcher.WatcherFragment;
import com.ittianyu.bcdnwatcher.features.wool.WoolFragment;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final int[] titles = new int[] {R.string.home, R.string.wool, R.string.me};

    private ActivityMainBinding bind;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initPermission();
        initView();
        initData();
        initEvent();
    }

    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
//                        Logger.d(granted);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e(throwable, throwable.getMessage());
                    }
                });
    }

    private void initView() {
        initVp();
        initBnve();
    }

    private void initVp() {
        List<Fragment> fragments = Arrays.asList(
                new WatcherFragment(),
                new WoolFragment(),
                new MeFragment()
        );
        bind.vp.setOffscreenPageLimit(2);
        bind.vp.setAdapter(new VpAdapter(getSupportFragmentManager(), fragments));
    }

    private void initBnve() {
        bind.bnve.enableAnimation(false);
        bind.bnve.enableItemShiftingMode(false);
        bind.bnve.enableShiftingMode(false);

        bind.bnve.setupWithViewPager(bind.vp);
    }

    private void initData() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        checkUpdate();
    }

    private void initEvent() {
        bind.vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bind.tvTitle.setText(titles[position]);
            }
        });
    }

    private void checkUpdate() {
        final LiveData<VersionBean> versionLd = mainViewModel.queryVersion();
        versionLd.observe(this, new Observer<VersionBean>() {
            @Override
            public void onChanged(@Nullable VersionBean version) {
                if (null == version)
                    return;
                Logger.d(version);
                versionLd.removeObservers(MainActivity.this);
                if (version.getLastVersion() > BuildConfig.VERSION_CODE && !TextUtils.isEmpty(version.getUrl())) {
                    UpdateUtils.showUpdateDialog(MainActivity.this, getString(R.string.tips_update_title),
                            version.getContent(), version.getUrl());
                }
            }
        });
    }

}
