package com.ittianyu.bcdnwatcher.features.watcher.bindeth;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.BindEthBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.repository.BcdnRepository;

/**
 * Created by 86839 on 2017/10/4.
 */

public class BindEthViewModel extends ViewModel {
    private BcdnRepository bcdnRepository = BcdnRepository.getInstance();

    public LiveData<Lcee<BindEthBean>> bindGateIo(String phone, String token, String ethAddress) {
        return bcdnRepository.bindGateIo(phone, token, ethAddress);
    }

    public LiveData<Lcee<BindEthBean>> bindEth(String phone, String token, String ethAddress) {
        return bcdnRepository.bindEth(phone, token, ethAddress);
    }
}
