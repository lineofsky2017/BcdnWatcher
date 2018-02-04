package com.ittianyu.bcdnwatcher.features.watcher.binds;

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
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.Status;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.utils.DialogUtils;
import com.ittianyu.bcdnwatcher.common.utils.LceeUtils;
import com.ittianyu.bcdnwatcher.databinding.ActivityBindSBinding;
import com.orhanobut.logger.Logger;

/**
 * Created by yu on 2018/1/30.
 */

public class BindSActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "item";

    private ActivityBindSBinding bind;
    private BindSViewModel bindSViewModel;
    private WatcherItemBean item;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_bind_s);

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

        // create view model
        bindSViewModel = ViewModelProviders.of(this).get(BindSViewModel.class);
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
                String code = bind.etSCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(BindSActivity.this, R.string.tips_code_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                onBindS(code);
            }
        });
    }

    private void onBindS(String code) {
        final LiveData<Lcee<BcdnCommonBean>> bindSLd = bindSViewModel.bindCode(item.getPhone(), item.getToken(), code);

        bindSLd.observe(this, new Observer<Lcee<BcdnCommonBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<BcdnCommonBean> lcee) {
                LceeUtils.removeObservers(bindSLd, lcee, BindSActivity.this);
                updateBindSView(lcee);
            }
        });
    }

    private void updateBindSView(Lcee<BcdnCommonBean> lcee) {
        Logger.d(lcee);
        if (lcee == null || lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (null == lcee)
            return;
        switch (lcee.status) {
            case Content: {
                handleBindSResult(lcee);
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

    private void handleBindSResult(Lcee<BcdnCommonBean> lcee) {
        switch (lcee.data.getCode()) {
            case BcdnCommonBean.CODE_TOKEN_TIMEOUT: {
                Toast.makeText(this, R.string.tips_token_timeout, Toast.LENGTH_SHORT).show();
                break;
            }
            case BcdnCommonBean.CODE_BIND_CODE_NOT_EXIST: {
                Toast.makeText(this, R.string.tips_wrong_code, Toast.LENGTH_SHORT).show();
                break;
            }
            default: {
                Toast.makeText(this, R.string.tips_bind_eth_success, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
            }
        }
    }

}
