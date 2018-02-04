package com.ittianyu.bcdnwatcher.features.watcher.bindeth;

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
import com.ittianyu.bcdnwatcher.common.bean.BindEthBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.Status;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.utils.DialogUtils;
import com.ittianyu.bcdnwatcher.common.utils.LceeUtils;
import com.ittianyu.bcdnwatcher.databinding.ActivityBindEthBinding;
import com.orhanobut.logger.Logger;

import java.io.Serializable;

/**
 * Created by yu on 2018/1/30.
 */

public class BindEthActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "item";

    private ActivityBindEthBinding bind;
    private BindEthViewModel bindEthViewModel;
    private WatcherItemBean item;
    private BindEth bindEth;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_bind_eth);

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
        bindEth = new BindEth();
        bind.setBean(bindEth);

        // create view model
        bindEthViewModel = ViewModelProviders.of(this).get(BindEthViewModel.class);
    }

    private void initEvent() {
        bind.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bind.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bindEth.getEthAddress())) {
                    Toast.makeText(BindEthActivity.this, R.string.tips_eth_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                onBindEth();
            }
        });
    }

    private void onBindEth() {
        LiveData<Lcee<BindEthBean>> bindEthLd = null;
        if (bindEth.isGateIo()) {
            bindEthLd = bindEthViewModel.bindGateIo(item.getPhone(), item.getToken(), bindEth.getEthAddress());
        } else {
            bindEthLd = bindEthViewModel.bindEth(item.getPhone(), item.getToken(), bindEth.getEthAddress());
        }

        final LiveData<Lcee<BindEthBean>> finalBindEthLd = bindEthLd;
        bindEthLd.observe(this, new Observer<Lcee<BindEthBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<BindEthBean> lcee) {
                LceeUtils.removeObservers(finalBindEthLd, lcee, BindEthActivity.this);
                updateBindEthView(lcee);
            }
        });
    }

    private void updateBindEthView(Lcee<BindEthBean> lcee) {
        Logger.d(lcee);
        if (lcee == null || lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (null == lcee)
            return;
        switch (lcee.status) {
            case Content: {
                handleBindEthResult(lcee);
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

    private void handleBindEthResult(Lcee<BindEthBean> lcee) {
        switch (lcee.data.getCode()) {
            case BcdnCommonBean.CODE_TOKEN_TIMEOUT: {
                Toast.makeText(this, R.string.tips_token_timeout, Toast.LENGTH_SHORT).show();
                break;
            }
            case BindEthBean.CODE_WRONG_ETH: {
                Toast.makeText(this, R.string.tips_wrong_eth_address, Toast.LENGTH_SHORT).show();
                break;
            }
            case BindEthBean.CODE_NO_ENOUGH_COINS: {
                Toast.makeText(this, R.string.tips_no_enough_coins, Toast.LENGTH_SHORT).show();
                break;
            }
            default: {
                Toast.makeText(this, R.string.tips_bind_eth_success, Toast.LENGTH_SHORT).show();
                finish();
                break;
            }
        }
    }

    public static class BindEth implements Serializable {
        private String ethAddress;
        private boolean gateIo;

        public String getEthAddress() {
            return ethAddress;
        }

        public void setEthAddress(String ethAddress) {
            this.ethAddress = ethAddress;
        }

        public boolean isGateIo() {
            return gateIo;
        }

        public void setGateIo(boolean gateIo) {
            gateIo = gateIo;
        }

        @Override
        public String toString() {
            return "BindEth{" +
                    "ethAddress='" + ethAddress + '\'' +
                    ", gateIo=" + gateIo +
                    '}';
        }
    }
}
