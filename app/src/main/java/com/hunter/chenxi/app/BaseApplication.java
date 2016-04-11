package com.hunter.chenxi.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.instabug.library.IBGInvocationEvent;
import com.instabug.library.Instabug;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;
import java.util.Locale;

import cn.sharesdk.framework.ShareSDK;

/**
 * 自定义应用入口
 *
 * @author Ht
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    private static BaseApplication baseAPP;
    private static int mainTid;
    private static Handler handler;

  
    public static String APPKEY = "106cbc56fd1bc";
    public static String APPSECRET = "31c7f41151ccba2e6ebba8d9ecb466a9";


    @Override
    public void onCreate() {

        super.onCreate();

        new Instabug.Builder(this, "fc7cea91bb7e5761cf66f545fefd1329")
                .setInvocationEvent(IBGInvocationEvent.IBGInvocationEventShake)
                .build();

        LeakCanary.install(this);

        mInstance = this;

        initImageLoader();
        initScreenSize();

            baseAPP = this;
            mainTid = android.os.Process.myTid();
            handler = new Handler();
            ShareSDK.initSDK(this);

//        boolean mobDBFile = getApplicationContext().deleteDatabase("ThrowalbeLog.db");
//        Log.e("test", mobDBFile + "");
    }

    /**
     * 初始化imageloader
     */
    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "imageloader/Cache");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
                .cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .threadPoolSize(3)
                        // default
                .threadPriority(Thread.NORM_PRIORITY - 2)
                        // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                        // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                        // default
                .diskCache(new UnlimitedDiscCache(cacheDir))
                        // default
                .diskCacheSize(20 * 1024 * 1024).diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // default
                .defaultDisplayImageOptions(defaultOptions) // default
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    /**
     * 获取程序的上下文
     */
    public static Context getApplication() {
        return baseAPP;
    }

    /**
     * 得到handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 得到主线程ID
     */
    public static int getMainTid() {
        return mainTid;
    }

}
