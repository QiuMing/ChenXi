package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hunter.chenxi.R;
import com.hunter.chenxi.app.BaseApplication;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.presenter.impl.RegisterPresenterImpl;
import com.hunter.chenxi.presenter.interfaces.IRegisterPresenter;
import com.hunter.chenxi.ui.view.interfaces.IRegisterView;
import com.hunter.chenxi.utils.Logger;
import com.hunter.chenxi.utils.NumberUtils;
import com.hunter.chenxi.utils.Utils;
import com.hunter.chenxi.vo.request.UserRequest;
import com.hunter.chenxi.vo.response.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements  IRegisterView {

    @Bind(R.id.btn_do_register)
    Button btnDoregiser;

    @Bind(R.id.btnSendMsgCode)
    Button btnSendMsgCode;

    @Bind(R.id.textTel)
    EditText tel;

    @Bind(R.id.textVerify)
    EditText textVerify;

    @Bind(R.id.textPassword)
    EditText txtPassword;

    private boolean isGetVerification = false;

    private IRegisterPresenter registerPresenter;
    @Override
    public void initContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initPresenter() {
        //初始化SSMDK ,并添加注册回调监听接口
        SMSSDK.initSDK(this, BaseApplication.APPKEY, BaseApplication.APPSECRET, true);
        //SMSSDK.registerEventHandler(eh);

        registerPresenter = new RegisterPresenterImpl(this);
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

    //事件处理器
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            int event = msg.arg1;   //获取事件类型
            int result = msg.arg2;  //事件结果
            Object data = msg.obj;

            Log.e("Event", "Event=" + event + "   result=" + result);

            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, UserInfoActivity.class));
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送",  Toast.LENGTH_SHORT).show();
                } else {
                     Toast.makeText(getApplicationContext(), "验证码发送验证异常",  Toast.LENGTH_SHORT).show();
                    ((Throwable) data).printStackTrace();
                }
            }
            String phone = tel.getText().toString();
            String paswd =txtPassword.getText().toString();
            Logger.i("请求 后台登陆");
            showProgress("正在注册  ");
            registerPresenter.register(new UserRequest(phone, paswd));

            //有问题，总是验证不成功
           // startActivity(new Intent(RegisterActivity.this, UserInfoActivity.class));
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
            String phone = tel.getText().toString();
            String inputCode = textVerify.getText().toString();
            Log.e("Chenxi","phone "+phone+"   code is"+inputCode);
            SMSSDK.submitVerificationCode("86",phone , inputCode);
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
                !TextUtils.isEmpty(txtPassword.getText().toString());
                //&& !TextUtils.isEmpty(textVerify.getText().toString()) && isGetVerification;

        if (notNull) {
            if (new NumberUtils(tel.getText().toString()).getFacilitatorType() == -1) {
                Utils.toast("请检查手机号码");
                return false;
            }

            //根据需求预留的功能：检测密码级别是否过低  这里简单判断下
            if (txtPassword.getText().toString().length() < 6) {
                Utils.toast("密码不能少于6位");
                return false;
            }
            return true;
        }
        Utils.toast("请输入完整");
        return false;
    }

    @Override
    public void registerCallBack(UserInfo userInfo) {
        Utils.toast("登陆成功");
        startActivity(new Intent(Utils.getContext(), UserInfoActivity.class));
        hideProgress();
        finish();
    }
}
