package com.ittianyu.bcdnwatcher.features.watcher.withdrawhistory;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.WithdrawHistoryBean;
import com.ittianyu.bcdnwatcher.common.repository.BcdnRepository;

/**
 * Created by yu on 2017/10/4.
 */

public class WithdrawHistoryViewModel extends ViewModel {
    private BcdnRepository bcdnRepository = BcdnRepository.getInstance();

    public LiveData<Lcee<WithdrawHistoryBean>> queryWithdrawHistory(String phone, String token) {
        return bcdnRepository.queryWithdrawHistory(phone, token);
    }
}
