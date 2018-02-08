package com.ittianyu.bcdnwatcher.features.watcher.withdrawhistory;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.base.LceeActivity;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.ListStatus;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.bean.WithdrawHistoryBean;
import com.ittianyu.bcdnwatcher.common.utils.CollectionUtils;
import com.ittianyu.bcdnwatcher.common.utils.LceeUtils;
import com.ittianyu.bcdnwatcher.databinding.ActivityWithdrawHistoryBinding;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 86839 on 2018/1/27.
 */

public class WithdrawHistoryActivity extends LceeActivity {
    public static final String EXTRA_ITEM = "item";

    private ActivityWithdrawHistoryBinding bind;
    private WithdrawHistoryViewModel withdrawHistoryViewModel;
    private WithdrawHistoryAdapter withdrawHistoryAdapter;
    private WatcherItemBean item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_withdraw_history);
        initView();
        initData();
        initEvent();
        reload();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        setLcee(bind.vContent, findViewById(R.id.v_error),
                findViewById(R.id.v_loading), findViewById(R.id.v_empty));
        // init rv
        bind.rv.setLayoutManager(new LinearLayoutManager(this));
        withdrawHistoryAdapter = new WithdrawHistoryAdapter(new ArrayList<WithdrawHistoryBean.DataBean.HistoryBean>(0));
        bind.rv.setAdapter(withdrawHistoryAdapter);
        // rv driver
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_driver_gray));
        bind.rv.addItemDecoration(itemDecoration);
    }

    private void initData() {
        // get item info
        item = (WatcherItemBean) getIntent().getSerializableExtra(EXTRA_ITEM);
        if (null == item) {
            finish();
            return;
        }

        // create view model
        withdrawHistoryViewModel = ViewModelProviders.of(this).get(WithdrawHistoryViewModel.class);
    }

    private void initEvent() {
        // refresh
        bind.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading()) {
                    bind.srl.setRefreshing(false);
                    return;
                }
                setListStatus(ListStatus.Refreshing);
                reload();
            }
        });
    }

    @Override
    public void reload() {
        if (withdrawHistoryAdapter.getData().size() > 0) {
            setListStatus(ListStatus.Refreshing);
        } else {
            setListStatus(ListStatus.Content);
        }
        Logger.d("reload list status:" + getListStatus());
        final LiveData<Lcee<WithdrawHistoryBean>> items = withdrawHistoryViewModel.queryWithdrawHistory(item.getPhone(), item.getToken());
        items.observe(this, new Observer<Lcee<WithdrawHistoryBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<WithdrawHistoryBean> lcee) {
                LceeUtils.removeObservers(items, lcee, WithdrawHistoryActivity.this);
                updateView(lcee);
            }
        });
    }

    private void updateView(Lcee<WithdrawHistoryBean> lcee) {
        if (null == lcee) {
            updateEmptyView();
            return;
        }
        Logger.d(lcee);
        setStatus(lcee.status);
        switch (lcee.status) {
            case Content: {
                updateContentView(lcee.data);
                break;
            }
            case Empty: {
                updateEmptyView();
                break;
            }
            case Error: {
                updateErrorView();
                break;
            }
            case Loading: {
                updateLoadingView();
                break;
            }
        }
    }

    private void updateContentView(WithdrawHistoryBean data) {
        List<WithdrawHistoryBean.DataBean.HistoryBean> list = data.getData().getHistory();
        // check empty
        if (CollectionUtils.isEmpty(list)) {
            updateEmptyView();
            return;
        }

        // sort
        Collections.reverse(list);
        switch (getListStatus()) {
            case LoadingMore: {// load more complete
                withdrawHistoryAdapter.addData(list);
                withdrawHistoryAdapter.loadMoreComplete();
                break;
            }
            case Refreshing: {// refreshing complete
                bind.srl.setRefreshing(false);
                withdrawHistoryAdapter.setNewData(list);
                break;
            }
            default: {// first load complete
                showContent();
                withdrawHistoryAdapter.setNewData(list);
                break;
            }
        }
    }

    private void updateEmptyView() {
        switch (getListStatus()) {
            case LoadingMore: {// no more data
                withdrawHistoryAdapter.loadMoreEnd();
                break;
            }
            case Refreshing: {// refresh return empty
                bind.srl.setRefreshing(false);
                showEmpty();
                break;
            }
            default: {// first load empty
                showEmpty();
                break;
            }
        }
    }

    private void updateErrorView() {
        switch (getListStatus()) {
            case LoadingMore: {// load more error
                withdrawHistoryAdapter.loadMoreFail();
                break;
            }
            case Refreshing: {// refreshing error
                bind.srl.setRefreshing(false);
                Toast.makeText(this, R.string.tips_refresh_error, Toast.LENGTH_SHORT).show();
                break;
            }
            default: {// first load error
                showError();
                break;
            }
        }
    }

    private void updateLoadingView() {
        switch (getListStatus()) {
            case LoadingMore: {// show loading more view in list footer
                // do nothing here.
                break;
            }
            case Refreshing: {// show refreshing view
                bind.srl.setRefreshing(true);
                break;
            }
            default: {
                showLoading();
                break;
            }
        }
    }

}
