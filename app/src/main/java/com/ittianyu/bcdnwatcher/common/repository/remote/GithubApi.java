package com.ittianyu.bcdnwatcher.common.repository.remote;


import com.ittianyu.bcdnwatcher.common.bean.VersionBean;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by 86839 on 2017/10/6.
 */

public interface GithubApi {
    @GET("https://raw.githubusercontent.com/ItTianYuStudio/BcdnWatcher/master/version.json")
    Observable<VersionBean> queryVersion();

}
