package com.ittianyu.bcdnwatcher.common.repository.local.service;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.repository.local.dao.AccountDao;
import com.ittianyu.bcdnwatcher.common.repository.local.db.DBHelper;
import com.ittianyu.bcdnwatcher.common.utils.RxUtils;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by 86839 on 2017/10/7.
 */

public class AccountServiceImpl implements AccountService {
    private static final AccountServiceImpl instance = new AccountServiceImpl();

    private AccountServiceImpl() {
    }

    public static AccountServiceImpl getInstance() {
        return instance;
    }


    private AccountDao accountDao = DBHelper.getInstance().getDb().getAccountDao();

    @Override
    public Observable<Long> addOrUpdate(final AccountBean account) {
        return RxUtils.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return accountDao.addOrUpdate(account);
            }
        }).compose(RxUtils.<Long>netScheduler());
    }

    @Override
    public Observable<Object> delete(final AccountBean account) {
        return RxUtils.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                accountDao.delete(account);
                return new Object();
            }
        }).compose(RxUtils.<Object>netScheduler());
    }

    @Override
    public Observable<List<AccountBean>> queryAll() {
        return RxUtils.fromCallable(new Callable<List<AccountBean>>() {
            @Override
            public List<AccountBean> call() throws Exception {
                return accountDao.queryAll();
            }
        }).compose(RxUtils.<List<AccountBean>>netScheduler());
    }

}
