package com.ittianyu.bcdnwatcher.features.addaccount;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.repository.AccountRepository;

/**
 * Created by 86839 on 2017/10/4.
 */

public class AddAccountViewModel extends ViewModel {
    private AccountRepository accountRepository = AccountRepository.getInstance();
    private MutableLiveData<AccountBean> ldAccount;
    private LiveData<Lcee<Long>> ldAddOrUpdate;

    public LiveData<Lcee<Long>> onAddOrUpdate() {
        if (null == ldAddOrUpdate) {
            ldAccount = new MutableLiveData<>();
            ldAddOrUpdate = Transformations.switchMap(ldAccount, new Function<AccountBean, LiveData<Lcee<Long>>>() {
                @Override
                public LiveData<Lcee<Long>> apply(AccountBean account) {
                    return accountRepository.addOrUpdate(account);
                }
            });
        }
        return ldAddOrUpdate;
    }

    public void addAccount(AccountBean account) {
        ldAccount.setValue(account);
    }

}
