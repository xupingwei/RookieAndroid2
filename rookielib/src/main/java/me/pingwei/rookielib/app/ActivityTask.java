package me.pingwei.rookielib.app;

import android.app.Activity;

import java.util.List;
import java.util.Stack;

/**
 * Created by xupingwei on 2015/7/17.
 */
public class ActivityTask {
    private static List<Activity> activityStack = new Stack<Activity>();

    private ActivityTask() {
    }

    public static List<Activity> getStackInstance() {
        return activityStack;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前的Activity（堆栈中最后一个压入的）
     *
     * @return
     */
    public static Activity currentActivity() {
        return activityStack.get(activityStack.size() - 1);
    }

    /**
     * 结束当前的Activity（堆栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        if (activityStack.size() != 0) {
            Activity activity = activityStack.remove(activityStack.size() - 1);
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public static void finishActivity(Activity activity) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity ac = activityStack.get(i);
            if (ac.equals(activity)) {
                activityStack.remove(ac);
                ac.finish();
            }
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public static void finishActivity(Activity activity, Class<?> aClass) {
        if (null != activity) {
            //解决ConcurrentModificationException
            synchronized (activity.getClass()) {
                if (activity.getClass().equals(aClass)) {
                    activityStack.remove(activity);
                }
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param aClass
     */
    public static void finishActivity(Class<?> aClass) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity.getClass().equals(aClass)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (null != activity) {
                activity.finish();
            }
        }
        activityStack.clear();
    }
}
