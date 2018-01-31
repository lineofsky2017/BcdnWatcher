package com.ittianyu.bcdnwatcher.common.repository.local;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.repository.AccountDataSource;
import com.ittianyu.bcdnwatcher.common.repository.local.service.AccountService;
import com.ittianyu.bcdnwatcher.common.repository.local.service.AccountServiceImpl;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 86839 on 2017/10/6.
 */

public class LocalAccountDataSource implements AccountDataSource {
    private static final LocalAccountDataSource instance = new LocalAccountDataSource();

    private LocalAccountDataSource() {
    }

    public static LocalAccountDataSource getInstance() {
        return instance;
    }


    private AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    public Observable<Long> addOrUpdate(AccountBean account) {
        return accountService.addOrUpdate(account);
    }

    @Override
    public Observable<Object> delete(AccountBean account) {
        return accountService.delete(account);
    }

    @Override
    public Observable<List<AccountBean>> queryAll() {
        return accountService.queryAll();
    }
}
