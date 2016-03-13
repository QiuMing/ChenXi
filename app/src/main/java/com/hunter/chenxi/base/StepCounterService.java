package com.hunter.chenxi.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.util.Log;

import com.hunter.chenxi.ui.activity.StepDetector;
import com.hunter.chenxi.ui.activity.StepDetectorSettings;
import com.hunter.chenxi.utils.Utils;


//service负责后台的需要长期运行的任务
// 计步器服务
// 运行在后台的服务程序，完成了界面部分的开发后
// 就可以开发后台的服务类StepService
// 注册或注销传感器监听器，在手机屏幕状态栏显示通知，与StepActivity进行通信，走过的步数记到哪里了？？？
public class StepCounterService extends Service {

    public static Boolean FLAG = false;// 服务运行标志

    private SensorManager mSensorManager;// 传感器服务
    private StepDetector detector;// 传感器监听对象

    private PowerManager mPowerManager;// 电源管理服务
    private WakeLock mWakeLock;// 屏幕灯

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        FLAG = true;// 标记为服务正在运行

        // 创建监听器类，实例化监听对象
        detector = new StepDetector(this);

        // 获取传感器的服务，初始化传感器
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        // 注册传感器，注册监听器
        mSensorManager.registerListener(detector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        // 电源管理服务
        mPowerManager = (PowerManager) this
                .getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");
        mWakeLock.acquire();

        init();
        Log.e("service", "服务开启" );
        thread.start();
        thread.setName("StepDetectorService");

    }

    /**
     * 初始化
     */
    private void init() {
        StepDetector.CURRENT_SETP = 0;

        step_length = Utils.getIntData(StepDetectorSettings.STEP_LENGTH_VALUE, 70);
        weight = Utils.getIntData(StepDetectorSettings.WEIGHT_VALUE, 50);

        startTimer = System.currentTimeMillis();

        thread = new Thread() {//监听当前步数的变化
            @Override
            public void run() {
                super.run();

                while (serviceFLAG) {
                    SystemClock.sleep(300);//人类运动极限：走步频率不能小于0.2s
                    if (StepCounterService.FLAG) {
                        if (startTimer != System.currentTimeMillis()) {
                            timer = System.currentTimeMillis() - startTimer;
                        }
                        countDistance();
                        countStep();
                        intent.putExtra("startTimer", startTimer);
                        intent.putExtra("timer", timer);
                        intent.putExtra("calories", calories);
                        intent.putExtra("weight", weight);
                        intent.putExtra("distance", distance);
                        intent.putExtra("velocity", velocity);
                        intent.putExtra("step_length", step_length);
                        intent.putExtra("total_step", total_step);
                        sendBroadcast(intent);
                    }
                }
            }
        };
    }

    public static boolean serviceFLAG=true;
    private Thread thread;
    private long startTimer = 0;// 开始时间
    private long timer = 0;// 运动时间
    private Intent intent = new Intent("com.hunter.chenxi.ui.activity.StepCounterActivity.RECEIVER");
    private Double distance = 0.0;// 路程：米
    private Double calories = 0.0;// 热量：卡路里
    private Double velocity = 0.0;// 速度：米每秒
    private int step_length = 0;  //步长
    private int weight = 0;       //体重
    private int total_step = 0;   //走的总步数


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("service","服务停止");
        FLAG = false;// 服务停止
        if (detector != null) {
            mSensorManager.unregisterListener(detector);
        }

        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }

    /**
     * 计算行走的距离
     */
    private void countDistance() {
        if (StepDetector.CURRENT_SETP % 2 == 0) {
            distance = (StepDetector.CURRENT_SETP / 2) * 3 * step_length * 0.01;
        } else {
            distance = ((StepDetector.CURRENT_SETP / 2) * 3 + 1) * step_length * 0.01;
        }
    }

    /**
     * 实际的步数
     */
    private void countStep() {
        if (StepDetector.CURRENT_SETP % 2 == 0) {
            total_step = StepDetector.CURRENT_SETP;
        } else {
            total_step = StepDetector.CURRENT_SETP + 1;
        }

        total_step = StepDetector.CURRENT_SETP;
    }

}
