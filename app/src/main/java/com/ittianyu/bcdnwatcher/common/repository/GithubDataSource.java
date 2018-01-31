package com.ittianyu.bcdnwatcher.common.repository;

import com.ittianyu.bcdnwatcher.common.bean.VersionBean;

import io.reactivex.Observable;

/**
 * Created by 86839 on 2017/10/6.
 */

public interface GithubDataSource {
    Observable<VersionBean> queryVersion();

}
