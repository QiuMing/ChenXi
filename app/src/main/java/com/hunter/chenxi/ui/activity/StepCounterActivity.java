package com.hunter.chenxi.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.base.StepCounterService;
import com.hunter.chenxi.utils.Utils;

import java.text.DecimalFormat;


/**
 * 在android中Activity负责前台界面展示，service负责后台的需要长期运行的任务。
 */
public class StepCounterActivity extends BaseActivity {

    //定义文本框控件
    private TextView tv_show_step;// 步数
    private TextView tv_timer;// 运行时间
    private TextView tv_calories;// 卡路里
    private Button btn_start;// 开始按钮
    private Button btn_stop;// 停止按钮

    private Intent service = new Intent(Utils.getContext(), StepCounterService.class);
    private MsgReceiver msgReceiver;

    @Override
    public void initContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initPresenter() {

        //判断服务是否开启
        Log.e("service", "服务：" + StepCounterService.serviceFLAG);
        //动态注册广播接收器
        if (msgReceiver == null) {
            msgReceiver = new MsgReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.hunter.chenxi.ui.activity.StepCounterActivity.RECEIVER");
            registerReceiver(msgReceiver, intentFilter);
        }
    }

    @Override
    public void initView() {
        tv_show_step = (TextView) this.findViewById(R.id.show_step);
        tv_timer = (TextView) this.findViewById(R.id.timer);
        tv_calories = (TextView) this.findViewById(R.id.calories);
        btn_start = (Button) this.findViewById(R.id.start);
        btn_stop = (Button) this.findViewById(R.id.stop);

        tv_timer.setText(Utils.getFormatTime(0));
        tv_calories.setText(formatDouble(0d));
        tv_show_step.setText("0");
        btn_stop.setText("停止");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("service", "服务：" + StepCounterService.serviceFLAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("service", "服务：" + StepCounterService.serviceFLAG);
    }


    @Override
    protected void onDestroy() {
        //如果服务仍在运行
        if (StepCounterService.FLAG)
//            stopService(service);
            Utils.toast("后台记录您的行走记录...");
        unregisterReceiver(msgReceiver);
        super.onDestroy();
    }


    public void onClick(View view) {
        Intent service = new Intent(this, StepCounterService.class);
        switch (view.getId()) {
            case R.id.start:
                if (!StepCounterService.FLAG) {
                    StepCounterService.serviceFLAG = true;
                    startService(service);
                }
                break;

            case R.id.stop:
                if (StepCounterService.FLAG) {
                    StepDetector.CURRENT_SETP = 0;
                    StepCounterService.serviceFLAG = false;
                    stopService(service);
                }
                break;
            case R.id.setting:
                Intent intent = new Intent(this, StepDetectorSettings.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 通过Broadcast更新UI
     */
    public class MsgReceiver extends BroadcastReceiver {
        private long timer = 0;// 运动时间
        private Double distance = 0.0;// 路程：米
        private Double calories = 0.0;// 热量：卡路里
        private Double velocity = 0.0;// 速度：米每秒
        private int step_length = 0;  //步长
        private int weight = 0;       //体重
        private int total_step = 0;   //走的总步数
        private long startTimer = 0;// 开始时间

        @Override
        public void onReceive(Context context, Intent intent) {
            startTimer = intent.getLongExtra("startTimer", 0);
            timer = intent.getLongExtra("timer", 0);
            calories = intent.getDoubleExtra("calories", 0d);
            weight = intent.getIntExtra("weight", 0);
            distance = intent.getDoubleExtra("distance", 0d);
            velocity = intent.getDoubleExtra("velocity", 0d);
            step_length = intent.getIntExtra("step_length", 0);
            total_step = intent.getIntExtra("total_step", 0);

            if (timer != 0) {
                // 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036
                calories = weight * distance * 1.036 * 0.001;//千卡转化成卡
                //速度velocity
                velocity = distance * 1000 / timer;
            } else {
                calories = 0.0;
                velocity = 0.0;
            }

            tv_show_step.setText(total_step + "");// 显示当前步数
            tv_calories.setText(formatDouble(calories));// 显示卡路里
            tv_timer.setText(Utils.getFormatTime(timer));// 显示当前运行时间
        }
    }


    /**
     * 计算并格式化doubles数值，保留两位有效数字
     *
     * @param doubles
     * @return 返回当前路程
     */
    private String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals("0") ? "0.0" : distanceStr;
    }
}
