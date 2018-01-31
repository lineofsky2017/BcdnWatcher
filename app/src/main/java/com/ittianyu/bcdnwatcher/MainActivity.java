package com.ittianyu.bcdnwatcher;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ittianyu.bcdnwatcher.common.adapter.VpAdapter;
import com.ittianyu.bcdnwatcher.databinding.ActivityMainBinding;
import com.ittianyu.bcdnwatcher.features.me.MeFragment;
import com.ittianyu.bcdnwatcher.features.watcher.WatcherFragment;
import com.ittianyu.bcdnwatcher.features.wool.WoolFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int[] titles = new int[] {R.string.home, R.string.wool, R.string.me};

    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);


        initView();
        initData();
        initEvent();
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

}
