package me.pingwei.rookielib.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by xupingwei on 2015/4/17.
 */
public class StorageHelper {
    /**
     * 判断SDCard是否存在
     *
     * @return true:存在 false:不存在
     */
    private static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 判断SDCard是否可读写
     *
     * @return true:可读写  false:不可用
     */
    public static boolean IsCanUseSdCard() {
        if (existSDCard()) {
            try {
                return Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取SDCard根目录
     *
     * @return 返回SDCard路径
     */
    public static String getSDCardPath() {
        File sdDir = null;
        if (existSDCard() && IsCanUseSdCard()) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        } else {
            sdDir = Environment.getRootDirectory();
        }
        return sdDir.toString();
    }
//sim卡是否可读
//    public boolean isCanUseSim() {
//        try {
//            TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//
//            return TelephonyManager.SIM_STATE_READY == mgr
//                    .getSimState();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
