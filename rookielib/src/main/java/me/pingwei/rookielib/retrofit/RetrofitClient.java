package me.pingwei.rookielib.retrofit;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;

import me.pingwei.rookielib.config.Config;
import me.pingwei.rookielib.utils.FileUtil;
import me.pingwei.rookielib.utils.LoggerUtils;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitClient {

    /**
     * 服务器地址
     */
    private static final String API_HOST = Config.API_HOST;
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("RxJava", message);
                }
            });
            //网络缓存路径文件
            String dirPath = FileUtil.getDirAbsolutPath(Config.RETROFIT_CACHE);
            File httpCacheDirectory = new File(dirPath);
            //通过拦截器设置缓存
            CacheInterceptor cacheInterceptor = new CacheInterceptor();
            OkHttpClient client = new OkHttpClient.Builder()
                    //设置缓存
                    .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                    //log请求参数
                    .addInterceptor(interceptor)
                    //网络请求缓存
                    .addInterceptor(cacheInterceptor)
                    .build();
            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

//    /**
//     * 对网络接口返回的Response进行分割操作
//     *
//     * @param response
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable<T> flatResponse(final Response<T> response) {
//        return Observable.create(new Observable.OnSubscribe<T>() {
//
//            @Override
//            public void call(Subscriber<? super T> subscriber) {
//                LoggerUtils.e(response.toString());
//                if (response.success()) {
//                    createData(response.getObject());
//                } else {
//                    Observable.error(new APIException(response.getCode(), response.getMessage()));
//                }
//            }
//        });
//    }


    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> flatResponse(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }

    /**
     * 自定义异常，当接口返回的{@link Response#flag}不为{@link Constant#OK}时，需要跑出此异常
     * eg：登陆时验证码错误；参数为传递等
     */
    public static class APIException extends Exception {
        public int code;
        public String message;

        public APIException(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }


    /**
     * <p/>
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     *
     * @param <T>
     * @return
     */
    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<Response<T>, T> applySchedulers() {

        return new Observable.Transformer<Response<T>, T>() {
            @Override
            public Observable<T> call(Observable<Response<T>> tObservable) {
                return tObservable.flatMap(new Func1<Response<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Response<T> result) {
                        LoggerUtils.e(new Gson().toJson(result));
                        if (result.success()) {
                            return flatResponse(result.getData());
                        } else {
                            return Observable.error(new APIException(result.getFlag(), result.getMsg()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 当{@link}中接口的注解为{@link retrofit2.http.Multipart}时，参数为{@link RequestBody}
     * 生成对应的RequestBody
     *
     * @param param
     * @return
     */
    protected RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    protected RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }

    /**
     * 已二进制传递图片文件，对图片文件进行了压缩
     *
     * @param path 文件路径
     * @return
     */
    protected RequestBody createPictureRequestBody(String path) {
        Bitmap bitmap = ClippingPicture.decodeResizeBitmapSd(path, 400, 800);
        return RequestBody.create(MediaType.parse("image/*"), ClippingPicture.bitmapToBytes(bitmap));
    }
}
