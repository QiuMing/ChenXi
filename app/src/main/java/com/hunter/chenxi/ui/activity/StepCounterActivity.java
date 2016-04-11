package com.hunter.chenxi.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.view.app.AbRotate3dAnimation;
import com.ab.view.progress.AbCircleProgressBar;
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
    private TextView tv_target_num, tv_target_time;// 目标步数
    private TextView tv_stepNumber;// 当前步数
    private TextView tv_distance;// 长度
    private TextView tv_calories;// 卡路里
    private TextView tv_timeNumber;// 当前时间
    private ImageView setButton;//设置
    private Button btn_stop;// 停止按钮
    private AbCircleProgressBar abCircleProgressBar01, abCircleProgressBar02;
    private Intent service = new Intent(Utils.getContext(), StepCounterService.class);
    private MsgReceiver msgReceiver;
    private int target_num, target_time;//目标步数 目标时间
    private RelativeLayout progressTextLayout01, progressTextLayout02;


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_step);
    }

    @Override
    public void initPresenter() {

//        判断服务是否开启
        Log.e("service", "服务：" + StepCounterService.serviceFLAG);
        //动态注册广播接收器
        if (msgReceiver == null) {
            msgReceiver = new MsgReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.hunter.chenxi.ui.activity.StepCounterActivity.RECEIVER");
            registerReceiver(msgReceiver, intentFilter);
        }
        target_num = Utils.getIntData("target_num", 6000);
        target_time = Utils.getIntData("target_time", 30);
    }

    @Override
    public void initView() {
        tv_show_step = (TextView) this.findViewById(R.id.frequency);
        tv_distance = (TextView) this.findViewById(R.id.distance);
        tv_calories = (TextView) this.findViewById(R.id.calorie);
        tv_stepNumber = (TextView) this.findViewById(R.id.stepNumber);
        tv_target_num = (TextView) this.findViewById(R.id.tv_target_num);
        tv_target_time = (TextView) this.findViewById(R.id.tv_target_time);
        tv_timeNumber = (TextView) this.findViewById(R.id.timeNumber);
        setButton = (ImageView) this.findViewById(R.id.setButton);

        group = (ViewGroup) findViewById(R.id.group);
        progressTextLayout01 = (RelativeLayout) findViewById(R.id.progressTextLayout01);
        progressTextLayout02 = (RelativeLayout) findViewById(R.id.progressTextLayout02);

        abCircleProgressBar01 = (AbCircleProgressBar) findViewById(R.id.circleProgressBar01);
        abCircleProgressBar02 = (AbCircleProgressBar) findViewById(R.id.circleProgressBar02);
        abCircleProgressBar01.setMax(target_num);//目标最大步数
        abCircleProgressBar02.setMax(target_time * 60);//有效最大时间
        abCircleProgressBar01.setProgress(0);
        abCircleProgressBar02.setProgress(0);
        tv_distance.setText(0 + "");
        tv_calories.setText(formatDouble(0d));
        tv_show_step.setText("0");
        tv_target_time.setText(target_time+"");

        abCircleProgressBar01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aa", "111");
                applyRotation(0, 0, 90);

            }
        });
        abCircleProgressBar02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aa", "222");
                applyRotation(-1, 360, 90);
            }
        });
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepCounterActivity.this, StepDetectorSettings.class);
                startActivity(intent);
            }
        });
        if (!StepCounterService.FLAG) {
            StepCounterService.serviceFLAG = true;
            startService(service);
        }
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

            /*case R.id.stop:
                if (StepCounterService.FLAG) {
                    StepDetector.CURRENT_SETP = 0;
                    StepCounterService.serviceFLAG = false;
                    stopService(service);
                }
                break;*/


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*
    * 通过Broadcast更新UI
    */
    public class MsgReceiver extends BroadcastReceiver {
        private long timer = 0;// 运动时间
        private int distance = 0;// 路程：米
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
            distance = (int) (intent.getDoubleExtra("distance", 0d));
            velocity = intent.getDoubleExtra("velocity", 0d);
            step_length = intent.getIntExtra("step_length", 0);
            total_step = intent.getIntExtra("total_step", 0);

            if (timer != 0) {
                // 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036
                calories = weight * distance * 1.036 * 0.001;//千卡转化成卡
                //速度velocity
                velocity = distance * 1000.0 / timer;
            } else {
                calories = 0.0;
                velocity = 0.0;
            }
            Log.e("aa", (int) (timer * 0.001) + "");//秒
            tv_show_step.setText(total_step + "");// 显示当前步数
            tv_calories.setText(formatDouble(calories));// 显示卡路里
            tv_distance.setText(distance + "");// 显示米数
            tv_timeNumber.setText(Utils.getFormatTime(timer));// 显示当前运行时间
            abCircleProgressBar01.setProgress(total_step);
            abCircleProgressBar02.setProgress((int) (timer * 0.001));
            tv_stepNumber.setText(total_step + "");
            tv_target_num.setText(target_num + "");
        }
    }


    /*
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

    private float centerX;
    private float centerY;
    private ViewGroup group;

    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the
     *                 list
     * @param start    the start angle at which the rotation must begin
     * @param end      the end angle of the rotation -1, 360, 90 0, 0, 90
     */
    private void applyRotation(int position, float start, float end) {
        centerX = group.getWidth() / 2.0f;
        centerY = group.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final AbRotate3dAnimation rotation = new AbRotate3dAnimation(start,
                end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        group.startAnimation(rotation);
    }

    /**
     * This class listens for the end of the first half of the animation. It
     * then posts a new action that effectively swaps the views when the
     * container is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int mPosition;

        private DisplayNextView(int position) {
            mPosition = position;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            Message ms = new Message();
            ms.arg1 = mPosition;
            handler.sendMessage(ms);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            AbRotate3dAnimation rotation;

            if (msg.arg1 > -1) {
                progressTextLayout01.setVisibility(View.GONE);
                abCircleProgressBar01.setVisibility(View.GONE);
                progressTextLayout02.setVisibility(View.VISIBLE);
                abCircleProgressBar02.setVisibility(View.VISIBLE);
                rotation = new AbRotate3dAnimation(90, 360, centerX, centerY,
                        310.0f, false);
            } else {
                progressTextLayout02.setVisibility(View.GONE);
                abCircleProgressBar02.setVisibility(View.GONE);
                abCircleProgressBar01.setVisibility(View.VISIBLE);
                progressTextLayout01.setVisibility(View.VISIBLE);
                rotation = new AbRotate3dAnimation(90, 0, centerX, centerY,
                        310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            group.startAnimation(rotation);

        }
    };
}
