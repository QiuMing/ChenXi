package com.hunter.chenxi.ui.activity;

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
    //public static final String SETP_SHARED_PREFERENCES = "userdata";// 设置

    private Button btn_ok, btn_cancel;
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
        sensitivity = 10 - Utils.getIntData(SENSITIVITY_VALUE, 7);
        step_length = Utils.getIntData(STEP_LENGTH_VALUE, 70);
        weight = Utils.getIntData(WEIGHT_VALUE, 50);
    }

    @Override
    public void initView() {

        btn_ok = (Button) findViewById(R.id.btn_ok_step_stting);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_step_stting);
        tv_sensitivity_vlaue = (TextView) this.findViewById(R.id.sensitivity_value);
        tv_step_length_vlaue = (TextView) this.findViewById(R.id.step_lenth_value);
        tv_weight_value = (TextView) this.findViewById(R.id.weight_value);
        sb_sensitivity = (SeekBar) this.findViewById(R.id.seek_sensitivity);
        sb_step_length = (SeekBar) this.findViewById(R.id.seek_step_lenth);
        sb_weight = (SeekBar) this.findViewById(R.id.seek_weight);


        sb_sensitivity.setProgress(sensitivity);
        sb_step_length.setProgress((step_length - 40) / 5); //步长按钮在进度条上占得比例
        sb_weight.setProgress((weight - 30) / 2);
        tv_sensitivity_vlaue.setText(sensitivity + "");
        tv_step_length_vlaue.setText(step_length + "cm");
        tv_weight_value.setText(weight + "kg");


        btn_ok.setOnClickListener(new MyOnSeekBarChangeListener());
        btn_cancel.setOnClickListener(new MyOnSeekBarChangeListener());
        sb_sensitivity.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(R.id.seek_sensitivity));
        sb_step_length.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(R.id.seek_step_lenth));
        sb_weight.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(R.id.seek_weight));
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
                case R.id.seek_sensitivity:
                    sensitivity = progress;
                    tv_sensitivity_vlaue.setText(sensitivity + "");
                    break;
                case R.id.seek_step_lenth:
                    step_length = progress * 5 + 40;
                    tv_step_length_vlaue.setText(step_length
                            + "cm");
                    break;
                case R.id.seek_weight:
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
                case R.id.btn_ok_step_stting:
                    Utils.saveIntgData(SENSITIVITY_VALUE, 10 - sensitivity);
                    Utils.saveIntgData(STEP_LENGTH_VALUE, step_length);
                    Utils.saveIntgData(WEIGHT_VALUE, weight);
                    Utils.toast("保存成功！");
                    StepDetectorSettings.this.finish();
                    StepDetector.SENSITIVITY = 10 - sensitivity;

                    break;

                case R.id.btn_cancel_step_stting:
                    StepDetectorSettings.this.finish();
                    break;

                default:
                    break;
            }
        }
    }
}
