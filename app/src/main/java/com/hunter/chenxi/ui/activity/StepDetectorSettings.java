package com.hunter.chenxi.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.utils.Utils;

public class StepDetectorSettings extends BaseActivity {
    public static final String WEIGHT_VALUE = "weight_value";//体重

    public static final String STEP_LENGTH_VALUE = "step_length_value";// 步长

    public static final String SENSITIVITY_VALUE = "sensitivity_value";// 灵敏值

    public static final String SETP_SHARED_PREFERENCES = "setp_preferences";// 设置

    public static SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private Button ok, cancel;

    private TextView tv_sensitivity_vlaue, tv_step_length_vlaue, tv_weight_value;

    private SeekBar sb_sensitivity, sb_step_length, sb_weight;

    private int sensitivity = 0;
    private int step_length = 0;
    private int weight = 0;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_base_activity_settings);
    }

    @Override
    public void initPresenter() {

        if (sharedPreferences == null) {
            //主要是保存一些常用的配置比如窗口状态
            sharedPreferences = getSharedPreferences(SETP_SHARED_PREFERENCES,
                    MODE_PRIVATE);
        }

        editor = sharedPreferences.edit();
        //初始化数据
        sensitivity = 10 - sharedPreferences.getInt(SENSITIVITY_VALUE, 7);
        step_length = sharedPreferences.getInt(STEP_LENGTH_VALUE, 70);
        weight = sharedPreferences.getInt(WEIGHT_VALUE, 50);

    }

    @Override
    public void initView() {
        ok = (Button) findViewById(R.id.ok);
        cancel = (Button) findViewById(R.id.cancel);

        tv_sensitivity_vlaue = (TextView) this
                .findViewById(R.id.sensitivity_value);
        tv_step_length_vlaue = (TextView) this
                .findViewById(R.id.step_lenth_value);
        tv_weight_value = (TextView) this.findViewById(R.id.weight_value);

        sb_sensitivity = (SeekBar) this.findViewById(R.id.sensitivity);
        sb_step_length = (SeekBar) this.findViewById(R.id.step_lenth);
        sb_weight = (SeekBar) this.findViewById(R.id.weight);

        sb_sensitivity.setProgress(sensitivity);
        sb_step_length.setProgress((step_length - 40) / 5); //步长按钮在进度条上占得比例
        sb_weight.setProgress((weight - 30) / 2);

        tv_sensitivity_vlaue.setText(sensitivity + "");
        tv_step_length_vlaue.setText(step_length + "cm");
        tv_weight_value.setText(weight + "kg");
        //设置监听
        ok.setOnClickListener(new MyOnSeekBarChangeListener());
        cancel.setOnClickListener(new MyOnSeekBarChangeListener());
        sb_sensitivity.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(R.id.sensitivity));
        sb_step_length.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(R.id.step_lenth));
        sb_weight.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(R.id.weight));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
        private int view;

        public MyOnSeekBarChangeListener() {
        }

        public MyOnSeekBarChangeListener(int view) {
            this.view = view;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (view) {
                case R.id.sensitivity:
                    sensitivity = progress;
                    tv_sensitivity_vlaue.setText(sensitivity + "");
                    break;
                case R.id.step_lenth:
                    step_length = progress * 5 + 40;
                    tv_step_length_vlaue.setText(step_length
                            + "cm");
                    break;
                case R.id.weight:
                    weight = progress * 2 + 30;
                    tv_weight_value.setText(weight + "kg");
                    break;
                default:

            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ok:
                    editor.putInt(SENSITIVITY_VALUE, 10 - sensitivity);
                    editor.putInt(STEP_LENGTH_VALUE, step_length);
                    editor.putInt(WEIGHT_VALUE, weight);
                    editor.commit();

                    Utils.toast("保存成功！");

                    StepDetectorSettings.this.finish();
                    StepDetector.SENSITIVITY = 10 - sensitivity;
                    break;

                case R.id.cancel:
                    StepDetectorSettings.this.finish();
                    break;

                default:
                    break;
            }
        }
    }
}
