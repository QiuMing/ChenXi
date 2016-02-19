package com.hunter.chenxi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.hunter.chenxi.app.BaseApplication;

import org.apache.http.message.BasicNameValuePair;

/**
 * 创建人：SunShine
 * 功能说明：自定义工具类
 */
public class Utils {

    public static Toast mToast;

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
     * pxz转换dip
     */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
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
    public static void start_Activity(Activity activity, Class<?> cls,
                                      BasicNameValuePair... name) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (name != null)
            for (int i = 0; i < name.length; i++) {
                intent.putExtra(name[i].getName(), name[i].getValue());
            }
        activity.startActivity(intent);
    }
    public static Drawable getDrawalbe(int id) {
        return getResource().getDrawable(id);
    }

}
