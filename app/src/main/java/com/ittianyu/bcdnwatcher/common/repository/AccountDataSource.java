package com.ittianyu.bcdnwatcher.common.repository;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 86839 on 2017/10/6.
 */

public interface AccountDataSource {
    Observable<Long> addOrUpdate(AccountBean account);

    Observable<Object> delete(AccountBean account);

    Observable<List<AccountBean>> queryAll();
}
