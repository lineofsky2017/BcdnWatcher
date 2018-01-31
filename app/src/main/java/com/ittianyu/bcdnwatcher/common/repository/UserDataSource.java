package com.ittianyu.bcdnwatcher.common.repository;

import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.common.bean.IndexBean;
import com.ittianyu.bcdnwatcher.common.bean.MinerBean;
import com.ittianyu.bcdnwatcher.common.bean.UserBean;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 86839 on 2017/10/6.
 */

public interface UserDataSource {
    Observable<UserBean> login(String phone, String password, String areaCode);

    Observable<IndexBean> queryTotalIncome(String phone, String token);

    Observable<IncomeBean> queryIncomeHistory(String phone, String token);

    Observable<MinerBean> queryMiners(String phone, String areaCode, String token);

    Observable<List<WatcherItemBean>> queryWatcherList();
}
