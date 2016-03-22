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
    @Bind(R.id.btn_do_register)
    Button btnDoregiser;
    @Bind(R.id.btnSendMsgCode)
    Button btnSendMsgCode;
    @Bind(R.id.textTel)
    EditText tel;
    @Bind(R.id.textVerify)
    EditText textVerify;
    @Bind(R.id.textPassword)
    EditText texrPassword;

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
                        // 用户数据本地化
                        Utils.saveStringData("usertel", tel.getText().toString().trim());
                        Utils.saveStringData("userpass", texrPassword.getText().toString());
                        Utils.saveBooleanData("loginde", true);
                        //TODO 用户数据发送到服务器

                        if (GuideActivity.guideActivity != null)
                            GuideActivity.guideActivity.finish();

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
        btnSendMsgCode.getLayoutParams().height = Utils.sp2px(20 + 20);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @OnClick(R.id.btn_do_register)
    public void btnDoRegister_onClick() {
        if (checkFromData()) {
            //86固定为中国了，根据以后需求更改
            SMSSDK.submitVerificationCode("86", tel.getText().toString(), textVerify
                    .getText().toString());
        }else{
            Utils.toast("测试主流程为先,进入用户编辑信息页");
            startActivity(new Intent(RegisterActivity.this, UserInfoActivity.class));
            finish();
         }
        //finish();
    }

    @OnClick(R.id.btnSendMsgCode)
    public void sendMsgCodeOnClick() {
        isGetVerification = true;
        textVerify.setEnabled(true);
        if (new NumberUtils(tel.getText().toString()).getFacilitatorType() == -1) {
            Utils.toast("请输入手机号码");
            return;
        }
        SMSSDK.getVerificationCode("86", tel.getText().toString().trim());//发送验证码
        //60s倒计时
        myCountDownTimer(60);
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
                btnSendMsgCode.setEnabled(false);
                btnSendMsgCode.setBackgroundResource(R.color.edittext_border_gray);
                btnSendMsgCode.setText("" + l / 1000 + "秒后获取");
            }

            @Override
            public void onFinish() {
                btnSendMsgCode.setEnabled(true);
                btnSendMsgCode.setText("获取验证码");
                btnSendMsgCode.setBackgroundResource(R.drawable.btn_selected);
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
                !TextUtils.isEmpty(texrPassword.getText().toString()) &&
                !TextUtils.isEmpty(textVerify.getText().toString()) && isGetVerification;

        if (notNull) {
            if (new NumberUtils(tel.getText().toString()).getFacilitatorType() == -1) {
                Utils.toast("请检查手机号码");
                return false;
            }

            //根据需求预留的功能：检测密码级别是否过低  这里简单判断下
            if (texrPassword.getText().toString().length() < 6) {
                Utils.toast("密码不能少于6位");
                return false;
            }
            return true;
        }
        Utils.toast("请输入完整");
        return false;
    }
}
