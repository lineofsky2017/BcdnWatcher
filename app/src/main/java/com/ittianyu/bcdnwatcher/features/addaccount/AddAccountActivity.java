package com.ittianyu.bcdnwatcher.features.addaccount;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.Status;
import com.ittianyu.bcdnwatcher.common.utils.DialogUtils;
import com.ittianyu.bcdnwatcher.common.utils.EncryptionUtils;
import com.ittianyu.bcdnwatcher.databinding.ActivityAddAccountBinding;
import com.orhanobut.logger.Logger;

/**
 * Created by 86839 on 2018/1/28.
 */

public class AddAccountActivity extends AppCompatActivity {

    private ActivityAddAccountBinding bind;
    private AccountBean account = new AccountBean();
    private AddAccountViewModel addAccountViewModel;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_add_account);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {
        bind.setAccount(account);

        addAccountViewModel = ViewModelProviders.of(this).get(AddAccountViewModel.class);
        addAccountViewModel.onAddOrUpdate().observe(this, new Observer<Lcee<Long>>() {
            @Override
            public void onChanged(@Nullable Lcee<Long> lcee) {
                updateView(lcee);
            }
        });
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
                addAccount();
            }
        });
    }

    private void addAccount() {
        boolean isUseMD5 = bind.sUseMd5.isChecked();
        String password = bind.etPassword.getText().toString();
        if (!isUseMD5) {
            password = EncryptionUtils.md5(password);
        }
        account.setPassword(password);
        addAccountViewModel.addAccount(account);
    }

    private void updateView(Lcee<Long> lcee) {
        Logger.d(lcee);
        switch (lcee.status) {
            case Content: {
                Toast.makeText(this, R.string.tips_add_account_success, Toast.LENGTH_SHORT).show();
                break;
            }
            case Empty: {

                break;
            }
            case Error: {
                Toast.makeText(this, R.string.tips_add_account_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case Loading: {
                if (null != loadingDialog)
                    loadingDialog.dismiss();
                loadingDialog = DialogUtils.showLoadingDialog(this);

                break;
            }
        }

        if (lcee.status != Status.Loading && loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

}
