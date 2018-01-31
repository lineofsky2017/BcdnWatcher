package com.ittianyu.bcdnwatcher.common.repository;

import android.arch.lifecycle.LiveData;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.livedata.LiveDataObservableAdapter;
import com.ittianyu.bcdnwatcher.common.repository.local.LocalAccountDataSource;

import java.util.List;


/**
 * Created by 86839 on 2017/10/6.
 */

public class AccountRepository {
    private static final AccountRepository instance = new AccountRepository();

    private AccountRepository() {
    }

    public static AccountRepository getInstance() {
        return instance;
    }


    private AccountDataSource accountLds = LocalAccountDataSource.getInstance();

    public LiveData<Lcee<Long>> addOrUpdate(AccountBean account) {
        return LiveDataObservableAdapter.fromObservableLcee(accountLds.addOrUpdate(account));

    }

    public LiveData<Lcee<Object>> delete(AccountBean account) {
        return LiveDataObservableAdapter.fromObservableLcee(accountLds.delete(account));
    }

    public LiveData<Lcee<List<AccountBean>>> queryAll() {
        return LiveDataObservableAdapter.fromObservableLcee(accountLds.queryAll());
    }

}
