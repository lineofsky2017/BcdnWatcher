package com.ittianyu.bcdnwatcher.common.repository.remote;


import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.common.bean.IndexBean;
import com.ittianyu.bcdnwatcher.common.bean.MinerBean;
import com.ittianyu.bcdnwatcher.common.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by 86839 on 2017/10/6.
 */

public interface UserApi {
    // phoneNumber=xxxxxx&password=qqqqqqqqqqqqqqqqqqqqqqqq&areacode=86
    @FormUrlEncoded
    @POST("/login")
    Observable<UserBean> login(@Field("phoneNumber") String phone,
                                             @Field("password") String password,
                                             @Field("areacode") String areaCode);

    // phoneNumber=xxxxxxx&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTcwNTQ3OTMsImlzcyI6InRlc3QiLCJuYmYiOjE1MTcwMjU5OTN9.518JmoQYCjk5Uuvw87F0SvL2eETFEjPh6YwgFO0bOl8
    @FormUrlEncoded
    @POST("/index")
    Observable<IndexBean> queryTotalIncome(@Field("phoneNumber") String phone,
                                           @Field("token") String token);

    // phoneNumber=xxxxxxxxxx&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTcwNTQ3OTMsImlzcyI6InRlc3QiLCJuYmYiOjE1MTcwMjU5OTN9.518JmoQYCjk5Uuvw87F0SvL2eETFEjPh6YwgFO0bOl8
    @FormUrlEncoded
    @POST("/pocket/minnerIncomRecord")
    Observable<IncomeBean> queryIncomeHistory(@Field("phoneNumber") String phone,
                                              @Field("token") String token);

    // phoneNumber=xxxxxxxxxx&areacode=86&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTcwNTQ3OTMsImlzcyI6InRlc3QiLCJuYmYiOjE1MTcwMjU5OTN9.518JmoQYCjk5Uuvw87F0SvL2eETFEjPh6YwgFO0bOl8
    @FormUrlEncoded
    @POST("/mine/getMyMinnerCode")
    Observable<MinerBean> queryMiners(@Field("phoneNumber") String phone,
                                      @Field("areacode") String areaCode,
                                      @Field("token") String token);

}
