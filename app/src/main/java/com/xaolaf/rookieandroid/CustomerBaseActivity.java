package com.xaolaf.rookieandroid;

import me.pingwei.rookielib.base.BaseActivity;
import me.pingwei.rookielib.retrofit.RetrofitClient;

/**
 * Created by xupingwei on 2017/2/22.
 */

public class CustomerBaseActivity extends BaseActivity {

    public ApiStores apiStores = RetrofitClient.getRetrofit().create(ApiStores.class);

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onResumeData() {

    }

    @Override
    public boolean isLogined() {
        return false;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public Object getUserInfo() {
        return null;
    }

    @Override
    public void setUserInfo(Object o) {

    }
}
