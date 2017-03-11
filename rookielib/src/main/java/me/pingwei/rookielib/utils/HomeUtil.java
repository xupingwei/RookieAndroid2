package me.pingwei.rookielib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xupingwei on 2016/10/25.
 */

public class HomeUtil {
    /**
     * 判断当前是否在桌面
     *
     * @param mContext
     * @return
     */
    public static boolean isHome(Context mContext) {
        List<String> homes = getHomes(mContext);
        ActivityManager mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return homes.contains(rti.get(0).topActivity.getPackageName());
    }

    private static List<String> getHomes(Context mContext) {
        List<String> packages = new ArrayList<String>();
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : resolveInfo) {
            packages.add(info.activityInfo.packageName);
            System.out.println(info.activityInfo.packageName);
        }
        return packages;
    }
}
