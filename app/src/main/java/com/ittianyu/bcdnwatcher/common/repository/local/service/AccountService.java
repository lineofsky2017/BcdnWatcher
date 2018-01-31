package com.ittianyu.bcdnwatcher.common.repository.local.service;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by 86839 on 2017/10/7.
 */

public interface AccountService {
    Observable<Long> addOrUpdate(AccountBean projects);

    Observable<Object> delete(AccountBean account);

    Observable<List<AccountBean>> queryAll();
}
