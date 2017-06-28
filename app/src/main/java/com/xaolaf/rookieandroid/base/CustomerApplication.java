package com.xaolaf.rookieandroid.base;

import android.content.Context;

import me.pingwei.rookielib.app.CrachHandler;
import me.pingwei.rookielib.base.BaseApplication;
import me.pingwei.rookielib.config.Config;
import me.pingwei.rookielib.utils.FontUtil;
import me.pingwei.rookielib.utils.LoggerUtils;

/**
 * Created by xupingwei on 2017/2/22.
 */

public class CustomerApplication extends BaseApplication {

    public static Context appContext;

    @Override
    protected void init() {
        appContext = this;
        LoggerUtils.setDebug(true);   //设置Log日志是否为打印状态
        Config.setProjectPath("RookieAndroid");
        CrachHandler.getCrachHandlerInstance().init(appContext);
        /**
         * 更换项目字体
         */
        FontUtil.getInstance().replaceSystemDefaultFontFromAsset(appContext, "fonts/HYQiHei-50S.otf");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LoggerUtils.e("onLowMemory");
    }
}
