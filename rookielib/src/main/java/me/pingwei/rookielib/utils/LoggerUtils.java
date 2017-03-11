package me.pingwei.rookielib.utils;

import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import me.pingwei.rookielib.config.Config;


/**
 * Created by Administrator on 2015/10/8.
 */
public class LoggerUtils {
    public static final String TAG = Config.getProjectName();

    private static boolean isDebug = false;

    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * 设置是否为调试模式
     *
     * @param b
     * @return
     */
    public static void setDebug(boolean b) {
        isDebug = b;
        Logger.init(TAG)
                .logLevel(LogLevel.FULL)
                .isShowThreadInfo();

    }

    public static void e(String out) {
        if (isDebug && out.length() > 0) {
            Logger.e(TAG, out);
            out(out);
        }
    }

    public static void d(String tag, String out) {
        if (isDebug && out.length() > 0) {
            Logger.d(tag, out);
        }
    }

    public static void d(String out) {
        if (isDebug && out.length() > 0) {
            Logger.d(TAG, out);
        }
    }

    /**
     * 直接打印日志信息
     *
     * @param out
     */
    public static void out(String out) {
        if (isDebug && out.length() > 0) {
            Log.e(TAG, out);
        }
    }

    public static void json(String jsonStr) {
        if (isDebug && jsonStr.length() > 0) {
            Logger.json(jsonStr);
        }
    }
}
