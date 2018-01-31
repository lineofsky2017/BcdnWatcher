package com.ittianyu.bcdnwatcher.features.watcher.history;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 86839 on 2018/1/28.
 */

public class IncomeHistoryAdapter extends BaseQuickAdapter<IncomeBean.DataBean.HistoryBean, BaseViewHolder> {

    public IncomeHistoryAdapter(@Nullable List<IncomeBean.DataBean.HistoryBean> data) {
        super(R.layout.item_income_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeBean.DataBean.HistoryBean item) {
        helper.setText(R.id.tv_code, item.getMachineId());
        helper.setText(R.id.tv_income, mContext.getString(R.string.income) + item.getIncome());
        // time
        Date date = new Date(Long.parseLong(item.getDate()) * 1000);
        helper.setText(R.id.tv_time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

}
