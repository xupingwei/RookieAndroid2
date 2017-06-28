package com.xaolaf.rookieandroid.base;

import com.xaolaf.rookieandroid.LoginBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiStores {

//    //加载天气
//    @GET("adat/sk/{cityId}.html")
//    Call<MainModel> loadDataByRetrofit(@Path("cityId") String cityId);
//
//    //加载天气
//    @GET("adat/sk/{cityId}.html")
//    Observable<MainModel> loadDataByRetrofitRxjava(@Path("cityId") String cityId);

    //d登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> userLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("role") int role);
}
