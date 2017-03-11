package me.pingwei.rookielib.base;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tumblr.remember.Remember;

import org.litepal.LitePalApplication;

import java.io.File;

import me.pingwei.rookielib.config.Config;
import me.pingwei.rookielib.utils.FileUtil;
import me.pingwei.rookielib.utils.LoggerUtils;

/**
 * Created by xupingwei on 2016/4/14.
 */
public abstract class BaseApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //项目默认值
        LitePalApplication.initialize(this);

        //项目初始化
        init();

        FileUtil.createProjectDirection();  //创建项目文件夹
        Remember.init(getApplicationContext(), Config.getProjectName() + ".share");
        LoggerUtils.e("项目缓存文件名：" + Config.getProjectName());
        /**
         * 初始化ImageLoader
         */
        String dirPath = FileUtil.getDirAbsolutPath(Config.getCacheImage());
        File cacheDir = new File(dirPath);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(10)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 30 * 1000, 20 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);


    }

    protected abstract void init();
}
