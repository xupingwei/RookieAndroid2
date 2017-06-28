package me.pingwei.rookielib.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.pingwei.rookielib.config.Config;
import me.pingwei.rookielib.utils.FileUtil;
import me.pingwei.rookielib.utils.StorageHelper;


/**
 * Created by xupingwei on 2015/5/12.
 */
public class CrachHandler implements Thread.UncaughtExceptionHandler {
    private static String loggerName = Config.LOGGER_FILE_NAME;
    private static CrachHandler crachHandler = new CrachHandler();
    private static Context mContext;
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    private File file;


    private String loggerPath;

    private CrachHandler() {
    }

    public static CrachHandler getCrachHandlerInstance() {
        return crachHandler;
    }


    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        FileUtil.creatDirection(Config.getCacheLog());
        file = FileUtil.getFile(Config.getCacheLog() + loggerName);
        loggerPath = file.getAbsolutePath();
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("uncaughtException", "error : ", e);
            }
            //退出程序
            ActivityTask.finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        MobclickAgent.reportError(mContext, ex);
        saveCrashInfo2File(ex);
        return true;
    }


    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append("The Information of the Device:\n");
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        sb.append("\nThe Exception of the Information that the Error Occured\n");
        sb.append(formatter.format(new Date()) + "\n\n");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        sb.append("\n\n");
        try {
            String fileName = loggerName;
            if (StorageHelper.IsCanUseSdCard()) {
                FileOutputStream fos = new FileOutputStream(file, true);
                fos.write(sb.toString().getBytes());
                fos.close();
            } else if (FileUtil.exists(mContext, loggerPath)) {
                FileOutputStream fos = new FileOutputStream(file, true);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e("saveCrashInfo2File", "an error occured while writing file...", e);
        }
        return null;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("collectDeviceInfo", "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d("collectDeviceInfo", field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e("collectDeviceInfo", "an error occured when collect crash info", e);
            }
        }
    }
}
