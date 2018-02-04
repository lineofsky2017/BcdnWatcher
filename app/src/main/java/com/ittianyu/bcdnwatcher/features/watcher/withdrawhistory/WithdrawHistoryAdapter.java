package com.ittianyu.bcdnwatcher.features.watcher.withdrawhistory;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.bean.WithdrawHistoryBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yu on 2018/1/28.
 */

public class WithdrawHistoryAdapter extends BaseItemDraggableAdapter<WithdrawHistoryBean.DataBean.HistoryBean, BaseViewHolder> {

    public WithdrawHistoryAdapter(@Nullable List<WithdrawHistoryBean.DataBean.HistoryBean> data) {
        super(R.layout.item_withdraw_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawHistoryBean.DataBean.HistoryBean item) {
        // count
        helper.setText(R.id.tv_count, item.getAmount() + "");
        // time
        Date date = new Date(Long.parseLong(item.getDate()) * 1000);
        helper.setText(R.id.tv_time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        // status
        String status = null;
        switch (item.getStatus()) {
            case WithdrawHistoryBean.DataBean.HistoryBean.STATUS_SUCCESS: {
                status = mContext.getString(R.string.withdraw_succsss);
                break;
            }
            case WithdrawHistoryBean.DataBean.HistoryBean.STATUS_NEED_CONFIRM: {
                status = mContext.getString(R.string.withdraw_need_confirm);
                break;
            }
            default: {
                status = mContext.getString(R.string.withdraw_need_error);
                break;
            }
        }
        helper.setText(R.id.tv_status, status);
    }

}
