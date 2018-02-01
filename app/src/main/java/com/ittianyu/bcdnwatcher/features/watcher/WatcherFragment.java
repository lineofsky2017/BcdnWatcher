package com.ittianyu.bcdnwatcher.features.watcher;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.base.LceeFragment;
import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.ListStatus;
import com.ittianyu.bcdnwatcher.common.bean.Status;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.utils.DialogUtils;
import com.ittianyu.bcdnwatcher.databinding.FragmentWatcherBinding;
import com.ittianyu.bcdnwatcher.features.addaccount.AddAccountActivity;
import com.ittianyu.bcdnwatcher.features.watcher.history.IncomeHistoryActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 86839 on 2018/1/27.
 */

public class WatcherFragment extends LceeFragment {
    private static final int REQ_ADD_ACCOUNT = 1;

    private FragmentWatcherBinding bind;
    private WatcherViewModel watcherViewModel;
    private WatcherAdapter watcherAdapter;
    private Dialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watcher, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = DataBindingUtil.bind(view);
        initView(view);
        initData();
        initEvent();

        reload();
    }

    private void initView(View view) {
        setLcee(bind.vContent, view.findViewById(R.id.v_error),
                view.findViewById(R.id.v_loading), view.findViewById(R.id.v_empty));
        // init rv
        bind.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        watcherAdapter = new WatcherAdapter(new ArrayList<WatcherItemBean>(0));
        bind.rv.setAdapter(watcherAdapter);
        // rv driver
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayout.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_driver_gray));
        bind.rv.addItemDecoration(itemDecoration);


        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(watcherAdapter) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
                return false;
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(bind.rv);

        // 开启拖拽
        watcherAdapter.enableDragItem(itemTouchHelper);
        // 滑动删除
        watcherAdapter.enableSwipeItem();
    }

    private void initData() {
        watcherViewModel = ViewModelProviders.of(this).get(WatcherViewModel.class);
        watcherViewModel.getItems().observe(this, new Observer<Lcee<List<WatcherItemBean>>>() {
            @Override
            public void onChanged(@Nullable Lcee<List<WatcherItemBean>> data) {
                updateView(data);
            }
        });
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

        // fab add
        bind.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), AddAccountActivity.class), REQ_ADD_ACCOUNT);
            }
        });

        // delete listener
        watcherAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.d("onItemSwiped:" + pos);
                deleteAccount(pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });

        watcherAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), IncomeHistoryActivity.class);
                WatcherItemBean bean = watcherAdapter.getData().get(position);
                intent.putExtra(IncomeHistoryActivity.EXTRA_INCOME_HISTORY, (ArrayList)bean.getIncomeHistory());
                startActivity(intent);
            }
        });
    }


    @Override
    public void reload() {
        if (watcherAdapter.getData().size() > 0) {
            setListStatus(ListStatus.Refreshing);
        } else {
            setListStatus(ListStatus.Content);
        }
//        Logger.d("reload list status:" + getListStatus());
        watcherViewModel.reload();
    }

    private void updateView(Lcee<List<WatcherItemBean>> lcee) {
        if (null == lcee)
            return;
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


    private void updateContentView(List<WatcherItemBean> list) {
        switch (getListStatus()) {
            case LoadingMore: {// load more complete
                watcherAdapter.addData(list);
                watcherAdapter.loadMoreComplete();
                break;
            }
            case Refreshing: {// refreshing complete
                bind.srl.setRefreshing(false);
                watcherAdapter.setNewData(list);
                break;
            }
            default: {// first load complete
                showContent();
                watcherAdapter.setNewData(list);
                break;
            }
        }
    }

    private void updateEmptyView() {
        switch (getListStatus()) {
            case LoadingMore: {// no more data
                watcherAdapter.loadMoreEnd();
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
                watcherAdapter.loadMoreFail();
                break;
            }
            case Refreshing: {// refreshing error
                bind.srl.setRefreshing(false);
                Toast.makeText(getContext(), R.string.tips_refresh_error, Toast.LENGTH_SHORT).show();
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
                // do nothing here
                break;
            }
            default: {
                showLoading();
                break;
            }
        }
    }


    private void deleteAccount(int position) {
        String phone = watcherAdapter.getData().get(position).getPhone();
        AccountBean account = new AccountBean();
        account.setPhone(phone);
        final LiveData<Lcee<Object>> ldDeleteAccount = watcherViewModel.deleteAccount(account);
        ldDeleteAccount.observe(this, new Observer<Lcee<Object>>() {
            @Override
            public void onChanged(@Nullable Lcee<Object> lcee) {
                updateDeleteView(lcee, ldDeleteAccount);
            }
        });
    }


    private void updateDeleteView(Lcee<Object> lcee, LiveData<Lcee<Object>> ldDeleteAccount) {
        Logger.d(lcee);
        switch (lcee.status) {
            case Content: {
                Toast.makeText(getContext(), R.string.tips_delete_account_success, Toast.LENGTH_SHORT).show();
                break;
            }
            case Empty: {

                break;
            }
            case Error: {
                Toast.makeText(getContext(), R.string.tips_delete_account_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case Loading: {
                if (null != loadingDialog)
                    loadingDialog.dismiss();
                loadingDialog = DialogUtils.showLoadingDialog(getContext());
                break;
            }
        }

        if (lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
            ldDeleteAccount.removeObservers(this);
        }

    }

}
