package com.xaolaf.rookieandroid;

import android.content.Context;

import com.xaolaf.rookieandroid.base.BasePresenter;

import me.pingwei.rookielib.retrofit.ApiCallback;

/**
 * Created by xupingwei on 2017/6/28.
 */

public class LoginPresenter extends BasePresenter {
    private Context mContext;
    private ILoginView iLoginView;

    public LoginPresenter(Context mContext, ILoginView iLoginView) {
        this.mContext = mContext;
        this.iLoginView = iLoginView;
    }


    public void login(String email, String pass, int role) {
        addSubscription(apiStores.userLogin(email, pass, role), new ApiCallback<LoginBean>(mContext, "登录中") {

            @Override
            public void onSuccess(LoginBean model) {
                iLoginView.onLoginSuccess(model);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                iLoginView.onLoginFailed(msg);
            }
        });
    }
}
