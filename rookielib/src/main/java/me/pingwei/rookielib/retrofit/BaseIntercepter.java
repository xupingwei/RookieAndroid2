package me.pingwei.rookielib.retrofit;

import com.google.gson.Gson;
import com.tumblr.remember.Remember;

import java.io.IOException;
import java.util.UUID;

import me.pingwei.rookielib.utils.LoggerUtils;
import okhttp3.*;
import okhttp3.Response;

/**
 * Created by xupingwei on 2017/5/16.
 */

public class BaseIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request.newBuilder()
                .addHeader("Content-Type", "Content-Type:application/json; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", "add cookies here")
                .build();
        LoggerUtils.d(request.headers().toString());
        Response response = chain.proceed(request);
        return response;
    }
}
