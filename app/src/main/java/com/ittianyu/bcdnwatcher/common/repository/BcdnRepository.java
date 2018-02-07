package com.ittianyu.bcdnwatcher.common.repository;

import android.arch.lifecycle.LiveData;

import com.ittianyu.bcdnwatcher.common.bean.BcdnCommonBean;
import com.ittianyu.bcdnwatcher.common.bean.BindEthBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingAgainBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingStatusBean;
import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.common.bean.IndexBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.MinerBean;
import com.ittianyu.bcdnwatcher.common.bean.UserBean;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.bean.WithdrawHistoryBean;
import com.ittianyu.bcdnwatcher.common.livedata.LiveDataObservableAdapter;
import com.ittianyu.bcdnwatcher.common.repository.remote.RemoteBcdnDataSource;

import java.util.List;


/**
 * Created by 86839 on 2017/10/6.
 */

public class BcdnRepository {
    private static final BcdnRepository instance = new BcdnRepository();

    private BcdnRepository() {
    }

    public static BcdnRepository getInstance() {
        return instance;
    }

    private BcdnDataSource bcdnRds = RemoteBcdnDataSource.getInstance();

    public LiveData<Lcee<UserBean>> login(String phone, String password, String areaCode) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.login(phone, password, areaCode));
    }

    public LiveData<Lcee<IndexBean>> queryTotalIncome(String phone, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.queryTotalIncome(phone, token));
    }

    public LiveData<Lcee<IncomeBean>> queryIncomeHistory(String phone, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.queryIncomeHistory(phone, token));
    }

    public LiveData<Lcee<MinerBean>> queryMiners(String phone, String areaCode, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.queryMiners(phone, areaCode, token));
    }

    public LiveData<Lcee<List<WatcherItemBean>>> queryWatcherList() {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.queryWatcherList());
    }

    public LiveData<Lcee<BookingStatusBean>> queryBookingStatus(String phone, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.queryBookingStatus(phone, token));
    }

    public LiveData<Lcee<BookingAgainBean>> bookingAgain(String phone, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.bookingAgain(phone, token));
    }

    public LiveData<Lcee<BcdnCommonBean>> bindCode(String phone, String token, String code) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.bindCode(phone, token, code));
    }

    public LiveData<Lcee<BindEthBean>> bindGateIo(String phone, String token, String ethAddress) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.bindGateIo(phone, token, ethAddress, "checkCoin"));
    }

    public LiveData<Lcee<BindEthBean>> bindEth(String phone, String token, String ethAddress) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.bindEth(phone, token, ethAddress, "checkCoin"));
    }

    public LiveData<Lcee<BcdnCommonBean>> getWithdrawVerifyCode(String phone, String areaCode) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.getWithdrawVerifyCode(phone, areaCode));
    }

    public LiveData<Lcee<BcdnCommonBean>> withdraw(String phone, String token, String amount, String checkCode) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.withdraw(phone, token, amount, checkCode));
    }

    public LiveData<Lcee<WithdrawHistoryBean>> queryWithdrawHistory(String phone, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.queryWithdrawHistory(phone, token));
    }

    public LiveData<Lcee<List<BookingAgainBean>>> batchBookingW(List<WatcherItemBean> items) {
        return LiveDataObservableAdapter.fromObservableLcee(bcdnRds.batchBookingW(items));
    }

}
