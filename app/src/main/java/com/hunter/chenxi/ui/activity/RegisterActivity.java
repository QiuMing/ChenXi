package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.hunter.chenxi.R;
import com.hunter.chenxi.app.BaseApplication;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.utils.NumberUtils;
import com.hunter.chenxi.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.btnNext)
    Button next;
    @Bind(R.id.BtnSendVerify)
    Button btnverlfy;
    @Bind(R.id.textTel)
    EditText tel;
    @Bind(R.id.textVerify)
    EditText textVerify;
    @Bind(R.id.textPass)
    EditText texrPass;
    private boolean isGetVerification = false;


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initPresenter() {
        SMSSDK.initSDK(this, BaseApplication.APPKEY, BaseApplication.APPSECRET, true);
        SMSSDK.registerEventHandler(eh);
    }

    @Override
    public void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(eh);
    }

    @Override
    public void onPause() {
        super.onPause();
        SMSSDK.unregisterEventHandler(eh);
    }

    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            mHandler.sendMessage(msg);
        }

    };

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            int even = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            Log.e("Event", "Event=" + even + "   result=" + result);
            switch (even) {
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        Utils.toast("验证成功");
                        startActivity(new Intent(RegisterActivity.this, UserInfoActivity.class));
                        finish();
                    } else {
                        Utils.toast("验证失败");
                    }
                    break;
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        Utils.toast("获取验证码成功");
                        //默认的智能验证是开启的
                    } else {
                        Utils.toast("获取验证码失败");
                    }
                    break;
                default:
                    ((Throwable) data).printStackTrace();
            }

        }
    };

    @Override
    public void initView() {
        ButterKnife.bind(this);

        textVerify.setEnabled(false);
        btnverlfy.getLayoutParams().height = Utils.sp2px(20 + 20);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @OnClick(R.id.btnNext)
    public void btnNext_onClick() {
        if (checkFromData()) {
            SMSSDK.submitVerificationCode("86", tel.getText().toString(), textVerify
                    .getText().toString());
        }
//        startActivity(new Intent(RegisterActivity.this, UserInfoActivity.class));
//        finish();
    }

    @OnClick(R.id.BtnSendVerify)
    public void BtnSendVerify_onClick() {
        isGetVerification = true;
        textVerify.setEnabled(true);
        if (new NumberUtils(tel.getText().toString()).getFacilitatorType() == -1) {
            Utils.toast("请输入手机号码");
            return;
        }
        SMSSDK.getVerificationCode("86", tel.getText().toString().trim());//发送验证码
        //60s倒计时
        myCountDownTimer(3);
    }


    /**
     * 倒计时多少秒
     *
     * @param s 单位是秒
     */
    public void myCountDownTimer(int s) {
        new CountDownTimer(s * 1000, 1000) {
            @Override
            public void onTick(long l) {
                btnverlfy.setEnabled(false);
                btnverlfy.setBackgroundResource(R.color.edittext_border_gray);
                btnverlfy.setText("" + l / 1000 + "秒后获取");
            }

            @Override
            public void onFinish() {
                btnverlfy.setEnabled(true);
                btnverlfy.setText("获取验证码");
                btnverlfy.setBackgroundResource(R.drawable.btn_selected);
            }
        }.start();
    }


    /**
     * 检查表单数据
     *
     * @return 是否符合要求
     */
    private boolean checkFromData() {
        boolean notNull = !TextUtils.isEmpty(tel.getText().toString()) &&
                !TextUtils.isEmpty(texrPass.getText().toString()) &&
                !TextUtils.isEmpty(textVerify.getText().toString()) && isGetVerification;

        if (notNull) {
            if (new NumberUtils(tel.getText().toString()).getFacilitatorType() == -1) {
                Utils.toast("请检查手机号码");
                return false;
            }

            //根据需求预留的功能：检测密码级别是否过低  这里简单判断下
            if (texrPass.getText().toString().length() < 6) {
                Utils.toast("密码不能少于6位");
                return false;
            }
            return true;
        }
        Utils.toast("请输入完整");
        return false;
    }
}
