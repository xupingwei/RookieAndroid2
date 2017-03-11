package me.pingwei.rookielib.config;

/**
 * Created by xupingwei on 2016/4/14.
 */
public class Config {

    public static String mProjectName = "RookieAndroid";
    public static String API_HOST = "https://www.queble.cn/queble/";   //服务器地址

    //图片缓存文件夹
    private static String cacheImage = "cache/";
    public static String RETROFIT_CACHE = "ret/";


    private static String cacheLog = "log/";

    public static final String LOGGER_FILE_NAME = "work.txt";

    public static void setApiHost(String apiHost) {
        API_HOST = apiHost;
    }

    /**
     * 返回项目名称
     *
     * @return
     */
    public static String getProjectName() {
        return mProjectName;
    }


    public static String getCacheLog() {
        return cacheLog;
    }

    /**
     * 获取图片缓存路径
     *
     * @return
     */
    public static String getCacheImage() {
        return cacheImage;
    }

    public static void setProjectPath(String pathName) {
        mProjectName = pathName;
    }


}
