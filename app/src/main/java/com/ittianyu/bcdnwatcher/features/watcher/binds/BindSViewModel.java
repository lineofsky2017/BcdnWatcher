package com.ittianyu.bcdnwatcher.features.watcher.binds;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.BcdnCommonBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.repository.BcdnRepository;

/**
 * Created by 86839 on 2017/10/4.
 */

public class BindSViewModel extends ViewModel {
    private BcdnRepository bcdnRepository = BcdnRepository.getInstance();

    public LiveData<Lcee<BcdnCommonBean>> bindCode(String phone, String token, String code) {
        return bcdnRepository.bindCode(phone, token, code);
    }
}
