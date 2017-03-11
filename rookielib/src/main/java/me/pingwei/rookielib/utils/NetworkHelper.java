package me.pingwei.rookielib.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by xupingwei on 2015/4/17.
 */
public class NetworkHelper {

    private static ConnectivityManager mConnectivityManager;
    private static NetworkInfo mNetworkInfo;

    /**
     * 判断是否有网络链接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (isNetworkInfoIsNull()) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断mNetworkInfo是否为空
     *
     * @return
     */
    private static boolean isNetworkInfoIsNull() {
        if (null == mNetworkInfo) {
            return false;
        }
        return true;
    }

//    /**
//     * 判断WIFI是否打开
//     *
//     * @param context
//     * @return
//     */
//    public static boolean is_Wifi_Enabled(Context context) {
//        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() ==
//                NetworkInfo.State.CONNECTED) || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
//    }

    /**
     * 判断网络是否为WIFI网络
     *
     * @param context
     * @return
     */
    public static boolean isWIFIEnabled(Context context) {
        if (isNetworkConnected(context) && isNetworkInfoIsNull()) {
            if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为手机网络
     *
     * @param context
     * @return
     */
    private static boolean isMobileNetwork(Context context) {
        if (isNetworkConnected(context) && isNetworkInfoIsNull()) {
            if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 联通3G
     *
     * @param context
     * @return
     */
    public static boolean isUTMSEnabled(Context context) {
        if (isMobileNetwork(context) && isNetworkInfoIsNull()) {
            if (mNetworkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_UMTS) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断GPS是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }
}
