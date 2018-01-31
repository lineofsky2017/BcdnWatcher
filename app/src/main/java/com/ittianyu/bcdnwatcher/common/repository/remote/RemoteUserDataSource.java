package com.ittianyu.bcdnwatcher.common.repository.remote;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.common.bean.IndexBean;
import com.ittianyu.bcdnwatcher.common.bean.MinerBean;
import com.ittianyu.bcdnwatcher.common.bean.UserBean;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.repository.AccountDataSource;
import com.ittianyu.bcdnwatcher.common.repository.UserDataSource;
import com.ittianyu.bcdnwatcher.common.repository.local.LocalAccountDataSource;
import com.ittianyu.bcdnwatcher.common.utils.CollectionUtils;
import com.ittianyu.bcdnwatcher.common.utils.RxUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by yu on 2018/1/27.
 */

public class RemoteUserDataSource implements UserDataSource {
    private static final RemoteUserDataSource instance = new RemoteUserDataSource();

    private RemoteUserDataSource() {
    }

    public static RemoteUserDataSource getInstance() {
        return instance;
    }


    private UserApi userApi = RetrofitFactory.getInstance().create(UserApi.class);
    private AccountDataSource accountLds = LocalAccountDataSource.getInstance();

    @Override
    public Observable<UserBean> login(String phone, String password, String areaCode) {
        return userApi.login(phone, password, areaCode)
                        .compose(RxUtils.<UserBean>netScheduler());
    }

    @Override
    public Observable<IndexBean> queryTotalIncome(String phone, String token) {
        return userApi.queryTotalIncome(phone, token)
                        .compose(RxUtils.<IndexBean>netScheduler());
    }

    @Override
    public Observable<IncomeBean> queryIncomeHistory(String phone, String token) {
        return userApi.queryIncomeHistory(phone, token)
                        .compose(RxUtils.<IncomeBean>netScheduler());
    }

    @Override
    public Observable<MinerBean> queryMiners(String phone, String areaCode, String token) {
        return userApi.queryMiners(phone, areaCode, token)
                        .compose(RxUtils.<MinerBean>netScheduler());
    }

    @Override
    public Observable<List<WatcherItemBean>> queryWatcherList() {
        return queryWatcherListRx();
    }

    private Observable<List<WatcherItemBean>> queryWatcherListRx() {
        return RxUtils.fromCallable(new Callable<List<WatcherItemBean>>() {
            @Override
            public List<WatcherItemBean> call() throws Exception {
                List<AccountBean> accounts = accountLds.queryAll().blockingFirst();
                Logger.d("accounts:" + accounts);
                if (CollectionUtils.isEmpty(accounts))
                    return Collections.emptyList();

                List<WatcherItemBean> items = new ArrayList<>(accounts.size());
                for (AccountBean account: accounts) {
                    // get base info
                    String phone = account.getPhone();
                    String areaCode = account.getAreaCode();

                    // create item
                    WatcherItemBean item = new WatcherItemBean();
                    item.setPhone(phone);
                    item.setAreaCode(areaCode);
                    items.add(item);

                    // login
                    try {
                        UserBean user = login(phone, account.getPassword(), areaCode).blockingFirst();
                        UserBean.DataBean userData = user.getData();
                        if (null == userData) {
//                        throw new IllegalAccessException("login failed");
                            item.setLogin(false);
                            continue;
                        }

                        item.setLogin(true);
                        String token = userData.getToken();

                        // query miner info
                        MinerBean miners = queryMiners(phone, areaCode, token).blockingFirst();

                        List<WatcherItemBean.MinerBean> minersList = new ArrayList<>(miners.getData().getCodeList().size());
                        for (MinerBean.DataBean.CodeListBean code : miners.getData().getCodeList()) {
                            minersList.add(new WatcherItemBean.MinerBean(code.getCode(), code.getOnlineStatus() == 0));
                        }
                        item.setMiners(minersList);

                        // query income
                        IncomeBean.DataBean income = queryIncomeHistory(phone, token).blockingFirst().getData();

                        item.setTotalIncome(income.getTotalIncome());
                        item.setYesterdayIncome(income.getYestodayIncome());
                        item.setIncomeHistory(income.getHistory());
                    } catch (Exception e) {
                        Logger.e(e, e.getMessage());
                        item.setLogin(false);
                    }
                }

                return items;
            }
        }).compose(RxUtils.<List<WatcherItemBean>>netScheduler());
    }
}
