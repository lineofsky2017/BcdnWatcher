package com.ittianyu.bcdnwatcher.features.watcher;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.databinding.ItemWatcherMinerBinding;

import java.util.List;

/**
 * Created by 86839 on 2018/1/28.
 */

public class WatcherAdapter extends BaseItemDraggableAdapter<WatcherItemBean, BaseViewHolder> {

    public WatcherAdapter(@Nullable List<WatcherItemBean> data) {
        super(R.layout.item_watcher, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WatcherItemBean item) {
        // 状态
        helper.setVisible(R.id.tv_status, !item.isLogin());
        // 其他信息
        helper.setText(R.id.tv_area_code, "+" + item.getAreaCode());
        helper.setText(R.id.tv_phone, item.getPhone());
        helper.setText(R.id.tv_yesterday_income, mContext.getString(R.string.yesterday_income) + item.getYesterdayIncome());
        helper.setText(R.id.tv_total_income, mContext.getString(R.string.total_income) + item.getTotalIncome());

        // 一个帐号可能有多个码，动态生成对应的 view
        ViewGroup miners = helper.getView(R.id.v_miners);
        miners.removeAllViews();
        List<WatcherItemBean.MinerBean> minerList = item.getMiners();
        if (null == minerList)
            return;
        for (WatcherItemBean.MinerBean miner : minerList) {
            miners.addView(createMinerView(miner));
        }
    }

    private View createMinerView(WatcherItemBean.MinerBean miner) {
        View view = View.inflate(mContext, R.layout.item_watcher_miner, null);
        ItemWatcherMinerBinding bind = DataBindingUtil.bind(view);

        bind.tvCode.setText(miner.getCode());
        if (miner.isOnline()) {
            bind.tvStatus.setText(mContext.getText(R.string.online));
            bind.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.shape_online));
        } else {
            bind.tvStatus.setText(mContext.getText(R.string.offline));
            bind.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.shape_offline));
        }

        return view;
    }
}
