package com.ittianyu.bcdnwatcher.features.watcher.withdraw;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.bean.BcdnCommonBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingAgainBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.Status;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.utils.DialogUtils;
import com.ittianyu.bcdnwatcher.common.utils.DownTimer;
import com.ittianyu.bcdnwatcher.common.utils.LceeUtils;
import com.ittianyu.bcdnwatcher.databinding.ActivityWithdrawBinding;
import com.orhanobut.logger.Logger;

import java.util.Locale;

/**
 * Created by yu on 2018/1/30.
 */

public class WithdrawActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "item";

    private static final long INTERVAL_TIME = 1000;
    private static final long TOTAL_TIME = 60 * 1000;

    private ActivityWithdrawBinding bind;
    private WithdrawViewModel withdrawViewModel;
    private WatcherItemBean item;
    private Dialog loadingDialog;
    private WithdrawBean withdrawBean;
    private DownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_withdraw);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {
        // get item info
        item = (WatcherItemBean) getIntent().getSerializableExtra(EXTRA_ITEM);
        if (null == item) {
            finish();
            return;
        }

        // bind bean with view
        withdrawBean = new WithdrawBean();
        withdrawBean.setTotalCount(getTotalIncome());
        bind.setBean(withdrawBean);

        // create view model
        withdrawViewModel = ViewModelProviders.of(this).get(WithdrawViewModel.class);
    }

    private void initEvent() {
        bind.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bind.btnWithdrawAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawBean.setWithdrawCount(withdrawBean.getTotalCount());
                bind.etWithdrawCount.setText(withdrawBean.getTotalCount());
            }
        });

        bind.btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerifyCodeWithdraw();
            }
        });

        bind.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(withdrawBean.getVerifyCode()) || TextUtils.isEmpty(withdrawBean.getWithdrawCount())) {
                    Toast.makeText(WithdrawActivity.this, R.string.tips_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                onWithdraw();
            }
        });
    }


    private String getTotalIncome() {
        return String.format(Locale.CHINESE, "%.2f", item.getTotalIncome());
    }

    private void sendVerifyCodeWithdraw() {
        final LiveData<Lcee<BookingAgainBean>> withdrawVerifyCodeLd = withdrawViewModel.getWithdrawVerifyCode(item.getPhone(), item.getAreaCode());
        withdrawVerifyCodeLd.observe(this, new Observer<Lcee<BookingAgainBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<BookingAgainBean> lcee) {
                LceeUtils.removeObservers(withdrawVerifyCodeLd, lcee, WithdrawActivity.this);
                updateVerifyCodeWithdrawView(lcee);
            }
        });
    }

    private void updateVerifyCodeWithdrawView(Lcee<BookingAgainBean> lcee) {
        Logger.d(lcee);

        if (null == lcee) {
            bind.btnGetVerifyCode.setEnabled(true);
            return;
        }
        switch (lcee.status) {
            case Content: {
                handleVerifyCodeWithdraResult(lcee);
                break;
            }
            case Empty:
            case Error: {
                bind.btnGetVerifyCode.setEnabled(true);
                Toast.makeText(this, R.string.tips_action_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case Loading: {
                bind.btnGetVerifyCode.setEnabled(false);
                break;
            }
        }
    }

    private void handleVerifyCodeWithdraResult(Lcee<BookingAgainBean> lcee) {
        if (lcee.data.getCode() != BcdnCommonBean.CODE_SUCCESS) {
            bind.btnGetVerifyCode.setEnabled(true);
            Toast.makeText(this, R.string.tips_action_error, Toast.LENGTH_SHORT).show();
            return;
        }

        startTimerAndUpdateButtonState();
    }

    private void startTimerAndUpdateButtonState() {
        // disable button
        bind.btnGetVerifyCode.setEnabled(false);

        if (null != timer)
            timer.cancel();
        timer = new DownTimer();
        timer.setIntervalTime(INTERVAL_TIME);
        timer.setTotalTime(TOTAL_TIME);
        timer.setTimerLiener(new DownTimer.TimeListener() {
            @Override
            public void onFinish() {
                // enable button
                bind.btnGetVerifyCode.setEnabled(true);
                bind.btnGetVerifyCode.setText(R.string.get_verify_code);
            }
            @Override
            public void onInterval(long remainTime) {
                bind.btnGetVerifyCode.setText((remainTime / 1000) + " s");
            }
        });
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != timer)
            timer.cancel();
    }

    private void onWithdraw() {
        final LiveData<Lcee<BcdnCommonBean>> withdrawLd = withdrawViewModel.withdraw(item.getPhone(), item.getToken(), withdrawBean.getWithdrawCount(), withdrawBean.getVerifyCode());

        withdrawLd.observe(this, new Observer<Lcee<BcdnCommonBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<BcdnCommonBean> lcee) {
                LceeUtils.removeObservers(withdrawLd, lcee, WithdrawActivity.this);
                updateWithdrawView(lcee);
            }
        });
    }

    private void updateWithdrawView(Lcee<BcdnCommonBean> lcee) {
        Logger.d(lcee);
        if (lcee == null || lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (null == lcee)
            return;
        switch (lcee.status) {
            case Content: {
                handleWithdrawResult(lcee);
                break;
            }
            case Empty:
            case Error: {
                Toast.makeText(this, R.string.tips_action_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case Loading: {
                if (null != loadingDialog)
                    loadingDialog.dismiss();
                loadingDialog = DialogUtils.showLoadingDialog(this);
                break;
            }
        }
    }

    private void handleWithdrawResult(Lcee<BcdnCommonBean> lcee) {
        switch (lcee.data.getCode()) {
            case BcdnCommonBean.CODE_TOKEN_TIMEOUT: {
                Toast.makeText(this, R.string.tips_token_timeout, Toast.LENGTH_SHORT).show();
                break;
            }
            case BcdnCommonBean.CODE_SUCCESS: {
                Toast.makeText(this, R.string.tips_withdraw_success, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    public static class WithdrawBean {
        private String withdrawCount;
        private String verifyCode;
        private String totalCount;

        public String getWithdrawCount() {
            return withdrawCount;
        }

        public void setWithdrawCount(String withdrawCount) {
            this.withdrawCount = withdrawCount;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        @Override
        public String toString() {
            return "WithdrawBean{" +
                    "withdrawCount='" + withdrawCount + '\'' +
                    ", verifyCode='" + verifyCode + '\'' +
                    ", totalCount='" + totalCount + '\'' +
                    '}';
        }
    }
}
