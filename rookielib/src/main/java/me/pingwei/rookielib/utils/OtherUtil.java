package me.pingwei.rookielib.utils;

/**
 * Created by Administrator on 2015/11/29.
 */
public class OtherUtil {
    private static long lastClickTime;

    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String numProcess(double oldNum) {
        String newNum = null;
        if (oldNum <= 9999) {
            newNum = String.valueOf((int) oldNum);
        } else if (oldNum > 9999 && oldNum < 20000) {
            newNum = "1万+";
        } else if (oldNum >= 20000 && oldNum < 30000) {
            newNum = "2万+";
        } else if (oldNum >= 30000 && oldNum < 40000) {
            newNum = "3万+";
        } else if (oldNum >= 40000 && oldNum < 50000) {
            newNum = "4万+";
        } else if (oldNum >= 50000) {
            newNum = "5万+";
        }

        return newNum;
    }

    public static String numProcess(int oldNum) {
        String newNum = null;
        if (oldNum <= 9999) {
            newNum = String.valueOf(oldNum);
        } else if (oldNum > 9999 && oldNum < 20000) {
            newNum = "1万+";
        } else if (oldNum >= 20000 && oldNum < 30000) {
            newNum = "2万+";
        } else if (oldNum >= 30000 && oldNum < 40000) {
            newNum = "3万+";
        } else if (oldNum >= 40000 && oldNum < 50000) {
            newNum = "4万+";
        } else if (oldNum >= 50000) {
            newNum = "5万+";
        }

        return newNum;
    }
}
