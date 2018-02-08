package com.ittianyu.bcdnwatcher.features.watcher;

import android.app.Activity;
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
import android.text.TextUtils;
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
import com.ittianyu.bcdnwatcher.common.bean.BcdnCommonBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingAgainBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingStatusBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.ListStatus;
import com.ittianyu.bcdnwatcher.common.bean.Status;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.utils.CollectionUtils;
import com.ittianyu.bcdnwatcher.common.utils.DialogUtils;
import com.ittianyu.bcdnwatcher.common.utils.LceeUtils;
import com.ittianyu.bcdnwatcher.common.view.PopupList;
import com.ittianyu.bcdnwatcher.databinding.FragmentWatcherBinding;
import com.ittianyu.bcdnwatcher.features.addaccount.AddAccountActivity;
import com.ittianyu.bcdnwatcher.features.watcher.bindeth.BindEthActivity;
import com.ittianyu.bcdnwatcher.features.watcher.binds.BindSActivity;
import com.ittianyu.bcdnwatcher.features.watcher.history.IncomeHistoryActivity;
import com.ittianyu.bcdnwatcher.features.watcher.withdraw.WithdrawActivity;
import com.ittianyu.bcdnwatcher.features.watcher.withdrawhistory.WithdrawHistoryActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 86839 on 2018/1/27.
 */

public class WatcherFragment extends LceeFragment {
    private static final int REQ_ADD_ACCOUNT = 1;
    private static final int REQ_BIND_S = 2;

    private FragmentWatcherBinding bind;
    private WatcherViewModel watcherViewModel;
    private WatcherAdapter watcherAdapter;
    private Dialog loadingDialog;
    PopupList itemMenu;

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

        // bind menu
        bindMainMenu();

        itemMenu = new PopupList(getContext());
    }

    private void initData() {
        watcherViewModel = ViewModelProviders.of(this).get(WatcherViewModel.class);
//        items = watcherViewModel.getItems();
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

        // list item event
        watcherAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), IncomeHistoryActivity.class);
                WatcherItemBean bean = watcherAdapter.getData().get(position);
                intent.putExtra(IncomeHistoryActivity.EXTRA_INCOME_HISTORY, (ArrayList) bean.getIncomeHistory());
                startActivity(intent);
            }
        });
        // item button event
        watcherAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WatcherItemBean item = watcherAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_menu: {
                        showItemMenu(view, position);
                        break;
                    }
                }
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
        Logger.d("reload list status:" + getListStatus());
        final LiveData<Lcee<List<WatcherItemBean>>> items = watcherViewModel.getItems();
        items.observe(this, new Observer<Lcee<List<WatcherItemBean>>>() {
            @Override
            public void onChanged(@Nullable Lcee<List<WatcherItemBean>> data) {
                LceeUtils.removeObservers(items, data, WatcherFragment.this);
                updateView(data);
            }
        });
    }

    private void updateView(Lcee<List<WatcherItemBean>> lcee) {
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
                bind.srl.setRefreshing(true);
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
                LceeUtils.removeObservers(ldDeleteAccount, lcee, WatcherFragment.this);
                updateDeleteView(lcee);
            }
        });
    }


    private void updateDeleteView(Lcee<Object> lcee) {
        Logger.d(lcee);
        if (lcee == null || lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (null == lcee)
            return;
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQ_ADD_ACCOUNT || requestCode == REQ_BIND_S) {
            reload();
        }
    }

    private void onBookingW(final WatcherItemBean item) {
        final LiveData<Lcee<BookingStatusBean>> bookingStatusLd = watcherViewModel.queryBookingStatus(item.getPhone(), item.getToken());
        bookingStatusLd.observe(this, new Observer<Lcee<BookingStatusBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<BookingStatusBean> lcee) {
                LceeUtils.removeObservers(bookingStatusLd, lcee, WatcherFragment.this);
                updateBookingWQueryView(lcee, item);
            }
        });
    }

    private void updateBookingWQueryView(Lcee<BookingStatusBean> lcee, WatcherItemBean item) {
        Logger.d(lcee);
        if (lcee == null || lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (null == lcee)
            return;
        switch (lcee.status) {
            case Content: {
                handleBookingWQueryResult(lcee, item);
                break;
            }
            case Empty:
            case Error: {
                Toast.makeText(getContext(), R.string.tips_action_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case Loading: {
                if (null != loadingDialog)
                    loadingDialog.dismiss();
                loadingDialog = DialogUtils.showLoadingDialog(getContext());
                break;
            }
        }
    }

    private void handleBookingWQueryResult(Lcee<BookingStatusBean> lcee, WatcherItemBean item) {
        if (lcee.data.getCode() == BcdnCommonBean.CODE_TOKEN_TIMEOUT) {
            Toast.makeText(getContext(), R.string.tips_token_timeout, Toast.LENGTH_SHORT).show();
            return;
        }

        switch (lcee.data.getData().getType()) {
            case BookingStatusBean.TYPE_BOOKED: {
                Toast.makeText(getContext(), R.string.tips_booked, Toast.LENGTH_SHORT).show();
                break;
            }
            case BookingStatusBean.TYPE_SUCCESS: {
                Toast.makeText(getContext(), R.string.tips_booked_success, Toast.LENGTH_SHORT).show();
                break;
            }
            case BookingStatusBean.TYPE_NO_ETH: {// 跳到绑定 eth 地址
                Intent intent = new Intent(getContext(), BindEthActivity.class);
                intent.putExtra(BindEthActivity.EXTRA_ITEM, item);
                startActivity(intent);
                break;
            }
            case BookingStatusBean.TYPE_FAILED: {// 抽奖失败，重新预约
                bookingWAgain(item);
                break;
            }
        }
    }

    private void bookingWAgain(WatcherItemBean item) {
        final LiveData<Lcee<BookingAgainBean>> bookingAgainLd = watcherViewModel.bookingAgain(item.getPhone(), item.getToken());
        bookingAgainLd.observe(this, new Observer<Lcee<BookingAgainBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<BookingAgainBean> lcee) {
                LceeUtils.removeObservers(bookingAgainLd, lcee, WatcherFragment.this);
                updateBookingWAgainView(lcee);
            }
        });
    }

    private void updateBookingWAgainView(Lcee<BookingAgainBean> lcee) {
        Logger.d(lcee);
        if (lcee == null || lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (null == lcee)
            return;
        switch (lcee.status) {
            case Content: {
                if (lcee.data.getCode() == BcdnCommonBean.CODE_TOKEN_TIMEOUT) {
                    Toast.makeText(getContext(), R.string.tips_token_timeout, Toast.LENGTH_SHORT).show();
                    return;
                }
                BookingAgainBean.DataBean data = lcee.data.getData();
                if (data == null || TextUtils.isEmpty(data.getReservationCode())) {
                    Toast.makeText(getContext(), R.string.tips_booking_again_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.tips_booking_again_success, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case Empty:
            case Error: {
                Toast.makeText(getContext(), R.string.tips_action_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case Loading: {
                if (null != loadingDialog)
                    loadingDialog.dismiss();
                loadingDialog = DialogUtils.showLoadingDialog(getContext());
                break;
            }
        }
    }

    private void bindMainMenu() {
        PopupList popupList = new PopupList(getContext());
        popupList.bind(bind.ivMenu, Arrays.asList(getString(R.string.batch_booking_w),
                getString(R.string.add_account)),
                new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        switch (position) {
                            case 0: {// 批量预约 w 码
                                batchBookingW();
                                break;
                            }
                            case 1: {// 添加帐号
                                startActivityForResult(new Intent(getContext(), AddAccountActivity.class), REQ_ADD_ACCOUNT);
                                break;
                            }
                        }
                    }
                });
    }

    private void batchBookingW() {
        List<WatcherItemBean> items = watcherAdapter.getData();
        if (CollectionUtils.isEmpty(items)) {
            Toast.makeText(getContext(), R.string.tips_no_account, Toast.LENGTH_SHORT).show();
            return;
        }

        final LiveData<Lcee<List<BookingAgainBean>>> batchBookingWLd = watcherViewModel.batchBookingW(items);
        batchBookingWLd.observe(this, new Observer<Lcee<List<BookingAgainBean>>>() {
            @Override
            public void onChanged(@Nullable Lcee<List<BookingAgainBean>> lcee) {
                LceeUtils.removeObservers(batchBookingWLd, lcee, WatcherFragment.this);
                updateBatchBookingWView(lcee);
            }
        });
    }

    private void updateBatchBookingWView(Lcee<List<BookingAgainBean>> lcee) {
        Logger.d(lcee);
        if (lcee == null || lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (null == lcee)
            return;
        switch (lcee.status) {
            case Empty:
            case Content: {
                int count = getSuccessCountFromBatchBookingW(lcee.data);
                int totalCount = watcherAdapter.getItemCount();
                Toast.makeText(getContext(), getString(R.string.tips_batch_booking, totalCount, count), Toast.LENGTH_SHORT).show();
                break;
            }
            case Error: {
                Toast.makeText(getContext(), R.string.tips_action_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case Loading: {
                if (null != loadingDialog)
                    loadingDialog.dismiss();
                loadingDialog = DialogUtils.showLoadingDialog(getContext());
                break;
            }
        }
    }

    private int getSuccessCountFromBatchBookingW(List<BookingAgainBean> items) {
        int count = 0;
        for (BookingAgainBean item : items) {
            if (item.getCode() == BcdnCommonBean.CODE_TOKEN_TIMEOUT) {
                Toast.makeText(getContext(), R.string.tips_token_timeout, Toast.LENGTH_SHORT).show();
                return count;
            }
            BookingAgainBean.DataBean data = item.getData();
            if (data == null || TextUtils.isEmpty(data.getReservationCode())) {
            } else {
                count++;
            }
        }
        return count;
    }


    private void showItemMenu(View view, int contextPosition) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
//        Logger.d("x:" + location[0] + ", y:" + location[1]);
        itemMenu.showPopupListWindow(view, contextPosition, location[0] + view.getWidth() / 2, location[1],
                Arrays.asList(getString(R.string.booking_w), getString(R.string.withdraw_history),
                        getString(R.string.withdraw), getString(R.string.bind_s), getString(R.string.delete)),
                new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        onCheckItemMenu(contextPosition, position);
                    }
                });
    }

    private void onCheckItemMenu(int contextPosition, int position) {
        WatcherItemBean item = watcherAdapter.getData().get(contextPosition);
        switch (position) {
            case 0: {// w 码预约
                onBookingW(item);
                break;
            }
            case 1: {// 提现记录
                Intent intent = new Intent(getContext(), WithdrawHistoryActivity.class);
                intent.putExtra(BindSActivity.EXTRA_ITEM, item);
                startActivity(intent);
                break;
            }
            case 2: {// 提币
                Intent intent = new Intent(getContext(), WithdrawActivity.class);
                intent.putExtra(BindSActivity.EXTRA_ITEM, item);
                startActivity(intent);
                break;
            }
            case 3: {// 绑定 s 码
                Intent intent = new Intent(getContext(), BindSActivity.class);
                intent.putExtra(BindSActivity.EXTRA_ITEM, item);
                startActivityForResult(intent, REQ_BIND_S);
                break;
            }
            case 4: {// 删除
                watcherAdapter.remove(contextPosition);
                deleteAccount(contextPosition);
                break;
            }

        }
    }

}
