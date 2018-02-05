package com.ittianyu.bcdnwatcher.common.repository;

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

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 86839 on 2017/10/6.
 */

public interface BcdnDataSource {
    Observable<UserBean> login(String phone, String password, String areaCode);

    Observable<IndexBean> queryTotalIncome(String phone, String token);

    Observable<IncomeBean> queryIncomeHistory(String phone, String token);

    Observable<MinerBean> queryMiners(String phone, String areaCode, String token);

    Observable<List<WatcherItemBean>> queryWatcherList();

    Observable<BookingStatusBean> queryBookingStatus(String phone, String token);

    Observable<BookingAgainBean> bookingAgain(String phone, String token);

    Observable<BcdnCommonBean> bindCode(String phone, String token, String code);

    Observable<BindEthBean> bindGateIo(String phone, String token, String ethAddress, String type); // type=checkCoin

    Observable<BindEthBean> bindEth(String phone, String token, String ethAddress, String type); // type=checkCoin

    Observable<BcdnCommonBean> getWithdrawVerifyCode(String phone, String areaCode);

    Observable<BcdnCommonBean> withdraw(String phone, String token, String amount, String checkCode);

    Observable<WithdrawHistoryBean> queryWithdrawHistory(String phone, String token);
}
