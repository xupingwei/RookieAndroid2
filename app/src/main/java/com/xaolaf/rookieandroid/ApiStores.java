package com.xaolaf.rookieandroid;

import me.pingwei.rookielib.config.Config;
import me.pingwei.rookielib.retrofit.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiStores {
    //baseUrl
    public static String API_SERVER_URL = Config.API_HOST = "";

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
    Observable<Response<LoginBean>> userLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("role") int role);
}
