package com.ittianyu.bcdnwatcher.features.watcher.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.base.BaseActivity;
import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.databinding.ActivityIncomeHistoryBinding;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yu on 2018/1/30.
 */

public class IncomeHistoryActivity extends BaseActivity {
    public static final String EXTRA_INCOME_HISTORY = "income_history";

    private ActivityIncomeHistoryBinding bind;
    private IncomeHistoryAdapter incomeHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_income_history);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        // init rv
        bind.rv.setLayoutManager(new LinearLayoutManager(this));
        incomeHistoryAdapter = new IncomeHistoryAdapter(new ArrayList<IncomeBean.DataBean.HistoryBean>(0));
        bind.rv.setAdapter(incomeHistoryAdapter);
        // rv driver
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_driver_gray));
        bind.rv.addItemDecoration(itemDecoration);

    }

    private void initData() {
        ArrayList<IncomeBean.DataBean.HistoryBean> history = (ArrayList<IncomeBean.DataBean.HistoryBean>) getIntent().getSerializableExtra(EXTRA_INCOME_HISTORY);
        if (null == history)
            return;
        Collections.reverse(history);
        incomeHistoryAdapter.setNewData(history);
    }

    private void initEvent() {
        bind.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
