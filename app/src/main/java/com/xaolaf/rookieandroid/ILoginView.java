package com.xaolaf.rookieandroid;

/**
 * Created by xupingwei on 2017/6/28.
 */

public interface ILoginView {

    void onLoginSuccess(LoginBean bean);

    void onLoginFailed(String message);
}
