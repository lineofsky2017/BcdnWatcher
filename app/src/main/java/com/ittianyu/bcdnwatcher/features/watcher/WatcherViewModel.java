package com.ittianyu.bcdnwatcher.features.watcher;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingAgainBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingStatusBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.repository.AccountRepository;
import com.ittianyu.bcdnwatcher.common.repository.BcdnRepository;

import java.util.List;

/**
 * Created by yu on 2017/10/4.
 */

public class WatcherViewModel extends ViewModel {
    private BcdnRepository bcdnRepository = BcdnRepository.getInstance();
    private AccountRepository accountRepository = AccountRepository.getInstance();

    public LiveData<Lcee<List<WatcherItemBean>>> getItems() {
        return bcdnRepository.queryWatcherList();
    }

    public LiveData<Lcee<Object>> deleteAccount(AccountBean account) {
        return accountRepository.delete(account);
    }

    public LiveData<Lcee<BookingStatusBean>> queryBookingStatus(String phone, String token) {
        return bcdnRepository.queryBookingStatus(phone, token);
    }

    public LiveData<Lcee<BookingAgainBean>> bookingAgain(String phone, String token) {
        return bcdnRepository.bookingAgain(phone, token);
    }

    public LiveData<Lcee<List<BookingAgainBean>>> batchBookingW(List<WatcherItemBean> items) {
        return bcdnRepository.batchBookingW(items);
    }
}
