package com.ittianyu.bcdnwatcher.common.repository.remote;

import com.ittianyu.bcdnwatcher.common.bean.VersionBean;
import com.ittianyu.bcdnwatcher.common.repository.GithubDataSource;
import com.ittianyu.bcdnwatcher.common.utils.RxUtils;

import io.reactivex.Observable;

/**
 * Created by yu on 2018/1/27.
 */

public class RemoteGithubDataSource implements GithubDataSource {
    private static final RemoteGithubDataSource instance = new RemoteGithubDataSource();

    private RemoteGithubDataSource() {
    }

    public static RemoteGithubDataSource getInstance() {
        return instance;
    }


    private GithubApi githubApi = RetrofitFactory.getInstance().create(GithubApi.class);

    @Override
    public Observable<VersionBean> queryVersion() {
        return githubApi.queryVersion()
                .compose(RxUtils.<VersionBean>netScheduler());
    }

}
