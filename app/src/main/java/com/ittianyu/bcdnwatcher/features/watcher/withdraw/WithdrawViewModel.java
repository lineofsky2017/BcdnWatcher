package com.ittianyu.bcdnwatcher.features.watcher.withdraw;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.BcdnCommonBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingAgainBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.repository.BcdnRepository;

/**
 * Created by 86839 on 2017/10/4.
 */

public class WithdrawViewModel extends ViewModel {
    private BcdnRepository bcdnRepository = BcdnRepository.getInstance();

    public LiveData<Lcee<BookingAgainBean>> getWithdrawVerifyCode(String phone, String areaCode) {
        return bcdnRepository.getWithdrawVerifyCode(phone, areaCode);
    }

    public LiveData<Lcee<BcdnCommonBean>> withdraw(String phone, String token, String amount, String checkCode) {
        return bcdnRepository.withdraw(phone, token, amount, checkCode);
    }
}
