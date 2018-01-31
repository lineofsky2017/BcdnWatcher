package com.ittianyu.bcdnwatcher.common.repository;

import android.arch.lifecycle.LiveData;

import com.ittianyu.bcdnwatcher.common.bean.VersionBean;
import com.ittianyu.bcdnwatcher.common.livedata.LiveDataObservableAdapter;
import com.ittianyu.bcdnwatcher.common.repository.remote.RemoteGithubDataSource;


/**
 * Created by 86839 on 2017/10/6.
 */

public class GithubRepository {
    private static final GithubRepository instance = new GithubRepository();

    private GithubRepository() {
    }

    public static GithubRepository getInstance() {
        return instance;
    }


    private GithubDataSource githubRds = RemoteGithubDataSource.getInstance();

    public LiveData<VersionBean> queryVersion() {
        return LiveDataObservableAdapter.fromObservable(githubRds.queryVersion());
    }

}
