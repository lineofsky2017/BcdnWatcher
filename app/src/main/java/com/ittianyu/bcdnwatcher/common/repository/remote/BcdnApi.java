package com.ittianyu.bcdnwatcher.common.repository.remote;


import com.ittianyu.bcdnwatcher.common.bean.BcdnCommonBean;
import com.ittianyu.bcdnwatcher.common.bean.BindEthBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingAgainBean;
import com.ittianyu.bcdnwatcher.common.bean.BookingStatusBean;
import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.common.bean.IndexBean;
import com.ittianyu.bcdnwatcher.common.bean.MinerBean;
import com.ittianyu.bcdnwatcher.common.bean.UserBean;
import com.ittianyu.bcdnwatcher.common.bean.WithdrawHistoryBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by 86839 on 2017/10/6.
 */

public interface BcdnApi {
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

    @FormUrlEncoded
    @POST("/index/reservationCode")
    Observable<BookingStatusBean> queryBookingStatus(@Field("phoneNumber") String phone,
                                                     @Field("token") String token);

    @FormUrlEncoded
    @POST("/index/reservationCode/getCode")
    Observable<BookingAgainBean> bookingAgain(@Field("phoneNumber") String phone,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST("/index/bindExtraMinnerCode")
    Observable<BcdnCommonBean> bindCode(@Field("phoneNumber") String phone,
                                        @Field("token") String token,
                                        @Field("code") String code);

    @FormUrlEncoded
    @POST("/index/reservationCode/bindethaddr/forGateio")
    Observable<BindEthBean> bindGateIo(@Field("phoneNumber") String phone,
                                       @Field("token") String token,
                                       @Field("ethAddress") String ethAddress,
                                       @Field("type") String type); // type=checkCoin

    @FormUrlEncoded
    @POST("/index/reservationCode/bindethaddr/forEth")
    Observable<BindEthBean> bindEth(@Field("phoneNumber") String phone,
                                       @Field("token") String token,
                                       @Field("ethAddress") String ethAddress,
                                       @Field("type") String type); // type=checkCoin

    @FormUrlEncoded
    @POST("/register/getCheckCode")
    Observable<BookingAgainBean> getWithdrawVerifyCode(@Field("phoneNumber") String phone,
                                              @Field("areaCode") String areaCode);

    @FormUrlEncoded
    @POST("/pocket/takeoutindex/takeout")
    Observable<BcdnCommonBean> withdraw(@Field("phoneNumber") String phone,
                                              @Field("token") String token,
                                              @Field("amount") String amount,
                                              @Field("checkCode") String checkCode);

    @FormUrlEncoded
    @POST("/pocket/takeoutindex/history")
    Observable<WithdrawHistoryBean> queryWithdrawHistory(@Field("phoneNumber") String phone,
                                                         @Field("token") String token);


}
