package me.pingwei.rookielib.utils;

import java.text.DecimalFormat;

/**
 * Created by xupingwei on 2016/3/26.
 */
public class StringUtil {

    /**
     * 取小数点后两位
     *
     * @param b
     * @return
     */
    public static String format2(double b) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(b);
    }
}
