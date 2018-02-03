package com.ittianyu.bcdnwatcher.features.watcher;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
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

}
