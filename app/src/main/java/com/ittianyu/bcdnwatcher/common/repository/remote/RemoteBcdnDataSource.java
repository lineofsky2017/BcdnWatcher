package com.ittianyu.bcdnwatcher.common.repository.remote;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.BcdnCommonBean;
import com.ittianyu.bcdnwatcher.common.bean.BindEthBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingAgainBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingStatusBean;
import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.common.bean.IndexBean;
import com.ittianyu.bcdnwatcher.common.bean.MinerBean;
import com.ittianyu.bcdnwatcher.common.bean.UserBean;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.bean.WithdrawHistoryBean;
import com.ittianyu.bcdnwatcher.common.repository.AccountDataSource;
import com.ittianyu.bcdnwatcher.common.repository.BcdnDataSource;
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

public class RemoteBcdnDataSource implements BcdnDataSource {
    private static final RemoteBcdnDataSource instance = new RemoteBcdnDataSource();

    private RemoteBcdnDataSource() {
    }

    public static RemoteBcdnDataSource getInstance() {
        return instance;
    }


    private BcdnApi bcdnApi = RetrofitFactory.getInstance().create(BcdnApi.class);
    private AccountDataSource accountLds = LocalAccountDataSource.getInstance();

    @Override
    public Observable<UserBean> login(String phone, String password, String areaCode) {
        return bcdnApi.login(phone, password, areaCode)
                        .compose(RxUtils.<UserBean>netScheduler());
    }

    @Override
    public Observable<IndexBean> queryTotalIncome(String phone, String token) {
        return bcdnApi.queryTotalIncome(phone, token)
                        .compose(RxUtils.<IndexBean>netScheduler());
    }

    @Override
    public Observable<IncomeBean> queryIncomeHistory(String phone, String token) {
        return bcdnApi.queryIncomeHistory(phone, token)
                        .compose(RxUtils.<IncomeBean>netScheduler());
    }

    @Override
    public Observable<MinerBean> queryMiners(String phone, String areaCode, String token) {
        return bcdnApi.queryMiners(phone, areaCode, token)
                        .compose(RxUtils.<MinerBean>netScheduler());
    }

    @Override
    public Observable<List<WatcherItemBean>> queryWatcherList() {
        return queryWatcherListRx();
    }

    @Override
    public Observable<BookingStatusBean> queryBookingStatus(String phone, String token) {
        return bcdnApi.queryBookingStatus(phone, token)
                .compose(RxUtils.<BookingStatusBean>netScheduler());
    }

    @Override
    public Observable<BookingAgainBean> bookingAgain(String phone, String token) {
        return bcdnApi.bookingAgain(phone, token)
                .compose(RxUtils.<BookingAgainBean>netScheduler());
    }

    @Override
    public Observable<BcdnCommonBean> bindCode(String phone, String token, String code) {
        return bcdnApi.bindCode(phone, token, code)
                .compose(RxUtils.<BcdnCommonBean>netScheduler());
    }

    @Override
    public Observable<BindEthBean> bindGateIo(String phone, String token, String ethAddress, String type) {
        return bcdnApi.bindGateIo(phone, token, ethAddress, type)
                .compose(RxUtils.<BindEthBean>netScheduler());
    }

    @Override
    public Observable<BindEthBean> bindEth(String phone, String token, String ethAddress, String type) {
        return bcdnApi.bindEth(phone, token, ethAddress, type)
                .compose(RxUtils.<BindEthBean>netScheduler());
    }

    @Override
    public Observable<BcdnCommonBean> getWithdrawVerifyCode(String phone, String areaCode) {
        return bcdnApi.getWithdrawVerifyCode(phone, areaCode)
                .compose(RxUtils.<BcdnCommonBean>netScheduler());
    }

    @Override
    public Observable<BcdnCommonBean> withdraw(String phone, String token, String amount, String checkCode) {
        return bcdnApi.withdraw(phone, token, amount, checkCode)
                .compose(RxUtils.<BcdnCommonBean>netScheduler());
    }

    @Override
    public Observable<WithdrawHistoryBean> queryWithdrawHistory(String phone, String token) {
        return bcdnApi.queryWithdrawHistory(phone, token)
                .compose(RxUtils.<WithdrawHistoryBean>netScheduler());
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
                        item.setToken(token);

                        // query miner info
                        List<MinerBean.DataBean.CodeListBean> miners = queryMiners(phone, areaCode, token).blockingFirst().getData().getCodeList();
                        if (null != miners) {
                            List<WatcherItemBean.MinerBean> minersList = new ArrayList<>(miners.size());
                            for (MinerBean.DataBean.CodeListBean code : miners) {
                                minersList.add(new WatcherItemBean.MinerBean(code.getCode(), code.getOnlineStatus() == 0));
                            }
                            item.setMiners(minersList);
                        }

                        // query income
                        IncomeBean.DataBean income = queryIncomeHistory(phone, token).blockingFirst().getData();

                        item.setIncomeHistory(income.getHistory());
                        item.setTotalIncome(income.getTotalIncome());
                        item.setYesterdayIncome(getYesterdayIncome(income.getHistory()));// 接口返回数据是前天的收入，这里需要手动计算
                    } catch (Exception e) {
                        Logger.e(e, e.getMessage());
                        item.setLogin(false);
                    }
                }

                return items;
            }
        }).compose(RxUtils.<List<WatcherItemBean>>netScheduler());
    }

    /**
     * 根据历史收益计算出昨日收益，忽略本地时间不对的情况
     * @param history
     * @return
     */
    private double getYesterdayIncome(List<IncomeBean.DataBean.HistoryBean> history) {
        double income = 0;
        if (CollectionUtils.isEmpty(history))
            return income;
        long now = System.currentTimeMillis();
        for (int i = history.size() - 1; i >= 0; i--) {
            IncomeBean.DataBean.HistoryBean item = history.get(i);
            long time = Long.parseLong(item.getDate()) * 1000;
            if (now - time > 1000L * 60 * 60 * 24) {// 和现在相差 1 天以上，说明是前天的收入
                break;
            }
            income += item.getIncome();
        }
        return income;
    }

}
