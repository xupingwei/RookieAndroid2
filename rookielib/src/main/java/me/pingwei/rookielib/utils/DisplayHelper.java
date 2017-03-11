package me.pingwei.rookielib.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by xupingwei on 2015/4/17.
 */
public class DisplayHelper {

    private static DisplayMetrics dm = new DisplayMetrics();

    public static int getWindowHeight(Context context) {
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
//        int screenWidth = dm.widthPixels;
        return screenHeight;
    }

    public static int getWindowWidth(Context context) {
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
//        int screenHeight = dm.heightPixels;
        return screenWidth;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
