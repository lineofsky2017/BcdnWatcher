package com.ittianyu.bcdnwatcher;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.VersionBean;
import com.ittianyu.bcdnwatcher.common.repository.GithubRepository;

/**
 * Created by 86839 on 2017/10/4.
 */

public class MainViewModel extends ViewModel {
    private GithubRepository githubRepository = GithubRepository.getInstance();

    public LiveData<VersionBean> queryVersion() {
        return githubRepository.queryVersion();
    }

}
