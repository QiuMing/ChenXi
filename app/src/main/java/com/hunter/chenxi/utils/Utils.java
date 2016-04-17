package com.hunter.chenxi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.hunter.chenxi.app.BaseApplication;

import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 创建人：SunShine
 * 功能说明：自定义工具类
 */
public class Utils {

    public static Toast mToast;
    public static SharedPreferences sharedPreferences = getContext().getSharedPreferences("userdata", Context.MODE_PRIVATE);
    public static SharedPreferences.Editor edit = sharedPreferences.edit();

    /**
     * Toast显示
     *
     * @param text 内容
     */

    public static void toast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(Utils.getContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    /**
     * 得到程序上资源
     *
     * @return
     */
    public static Resources getResource() {
        return BaseApplication.getApplication().getResources();
    }

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * 无论子线程还是主线程都更新UI，子线程中则用handler
     */
    public static void runOnUiThread(Runnable runnable) {
        // 如果是在主线程中运行
        if (BaseApplication.getMainTid() == android.os.Process.myTid()) {
            runnable.run();
        } else {
            //如果是子线程则在handler里执行
            BaseApplication.getHandler().post(runnable);
        }
    }

    /**
     * 得到字符串
     *
     * @param id 资源ID
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResource().getStringArray(id);
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = getResource().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(float spValue) {
        final float fontScale = getResource().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getDimens(int homePictureHeight) {
        return (int) getResource().getDimension(homePictureHeight);
    }


    /**
     * 打开Activity
     *
     * @param activity
     * @param cls
     * @param name
     */
    public static void start_Activity_with_value(Activity activity, Class<?> cls,
                                      BasicNameValuePair... name) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (name != null)
            for (int i = 0; i < name.length; i++) {
                intent.putExtra(name[i].getName(), name[i].getValue());
            }
        activity.startActivity(intent);
    }

    /**
     * 打开Activity
     *
     * @param activity
     * @param cls
     */
    public static void start_Activity(Activity activity, Class<?> cls ) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }

    public static Drawable getDrawalbe(int id) {
        return getResource().getDrawable(id);
    }

    public static String inputStreamToString(InputStream inputStream) {
        int b = 0;
        StringBuilder str = new StringBuilder();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            while ((b = inputStreamReader.read()) != -1) {
                str = str.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();

    }

    /**
     * 根据地址获取流
     */
    public static InputStream getImageImputStream(final String s) {
        final InputStream[] stream = {null};
        new Thread() {
            @Override
            public void run() {
                try {
                    stream[0] = new URL(s).openStream();
                   Log.e("---",stream[0].read() + "---");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return stream[0];
    }

    /**
     * 得到一个格式化的时间
     *
     * @param time 时间 毫秒
     * @return 时：分：秒：毫秒
     */
    public static String getFormatTime(long time) {
        time = time / 1000;
        long second = time % 60;
        long minute = (time % 3600) / 60;
        long hour = time / 3600;

        // 毫秒秒显示两位
        // String strMillisecond = "" + (millisecond / 10);
        // 秒显示两位
        String strSecond = ("00" + second)
                .substring(("00" + second).length() - 2);
        // 分显示两位
        String strMinute = ("00" + minute)
                .substring(("00" + minute).length() - 2);
        // 时显示两位
        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

        return strHour + ":" + strMinute + ":" + strSecond;
        // + strMillisecond;
    }
    /**
     *
     * @param key
     * @param value
     */
    public static void saveStringData(String key,String value){
        edit.putString(key,value);
        edit.commit();
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getStringData(String key,String def){
        return sharedPreferences.getString(key,def);
    }

    /**
     *
     * @param key
     * @param value
     * @return 是否存储成功
     */
    public static boolean saveIntgData(String key,int value){
        edit.putInt(key, value);
        return edit.commit();
    }

    /**
     *
     * @param key
     * @return
     */
    public static int getIntData(String key,int def){
        return sharedPreferences.getInt(key, def);
    }

    /**
     *
     * @param key
     * @param value
     * @return 是否存储成功
     */
    public static boolean saveBooleanData(String key,boolean value){
        edit.putBoolean(key, value);
        return edit.commit();
    }

    /**
     *
     * @param key
     * @return
     */
    public static boolean getBooleanData(String key,boolean def){
        return sharedPreferences.getBoolean(key, def);
    }

    /**
     *
      * @return
     */
    public static void clearUserData(){
        edit.clear();
        edit.commit();
    }
}
