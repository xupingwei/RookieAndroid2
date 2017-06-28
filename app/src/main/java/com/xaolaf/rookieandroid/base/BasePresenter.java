package com.xaolaf.rookieandroid.base;

import java.util.HashMap;
import java.util.Map;

import me.pingwei.rookielib.retrofit.RetrofitClient;
import me.pingwei.rookielib.utils.LoggerUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xupingwei on 2017/4/10.
 */

public abstract class BasePresenter {

    public ApiStores apiStores = RetrofitClient.getRetrofit().create(ApiStores.class);
    public Map<String, String> paramsMap = new HashMap<>();

    //通用参数
    private void addPublicParams() {
//        String paramsStr = Remember.getString(RememerConstants.USER_INFO, "");
//        if (!("").equals(paramsStr)) {
//            LoginBean bean = new Gson().fromJson(paramsStr, LoginBean.class);
//            paramsMap.put("mc", bean.getMc());
//            paramsMap.put("storeId", String.valueOf(bean.getStoreId()));
//            paramsMap.put("opid", String.valueOf(bean.getOpid()));
//            paramsMap.put("terminal", "PHONE");
//            paramsMap.put("atoken", bean.getAtoken());
//            paramsMap.put("register", Remember.getString(RememerConstants.DEVICE_ID, UUID.randomUUID().toString()));
//            String nonceStr = SignUtils.random();
//            paramsMap.put("nonceStr", nonceStr);
//            paramsMap.put("sign", SignUtils.generateSign(bean, nonceStr));
//        }
    }

    public void addParams() {
        addPublicParams();
    }

    public void addParams(String key, String params) {
        addPublicParams();
        paramsMap.put(key, params);
    }

    public CompositeSubscription subscription;


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (subscription == null) {
            subscription = new CompositeSubscription();
        }
        subscription.add(observable
                .compose(RetrofitClient.applySchedulers())
                .subscribe(subscriber));
    }
    /*
    .compose(RetrofitClient.applySchedulers())
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    */

    public void addSubscription(Subscription sub) {
        if (subscription == null) {
            subscription = new CompositeSubscription();
        }
        subscription.add(sub);
    }

    public void onUnsubscribe() {
        LoggerUtils.d("onUnsubscribe");
        //取消注册，以避免内存泄露
        if (subscription != null && subscription.hasSubscriptions())
            subscription.unsubscribe();
    }
}
