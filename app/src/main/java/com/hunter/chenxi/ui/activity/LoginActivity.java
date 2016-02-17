package com.hunter.chenxi.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.ui.view.interfaces.ILoginView;
import com.hunter.chenxi.vo.response.UserInfo;

import java.util.HashMap;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements ILoginView, PlatformActionListener, View.OnClickListener {
    @Bind(R.id.imgbtnQQ)
    ImageButton qq;

    @Bind(R.id.imgbtnWeChat)
    ImageButton weChat;

    @Bind(R.id.imgbtnSinaWeibo)
    ImageButton sina;

    @Bind(R.id.btnLogin)
    Button login;

    @Bind(R.id.textForgetPass)
    TextView forgetPass;

    private Platform pf;//第三方登录平台

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        qq.setOnClickListener(LoginActivity.this);
        weChat.setOnClickListener(LoginActivity.this);
        sina.setOnClickListener(LoginActivity.this);
        login.setOnClickListener(LoginActivity.this);
        forgetPass.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void initPresenter() {
        ShareSDK.initSDK(getApplication());
    }

    @Override
    public void loginCallback(UserInfo userInfo) {

    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        Log.e("a", "onComplete");

        Log.e("sharesdk use_id", pf.getDb().getUserId()); //获取用户id
        Log.e("sharesdk use_name", pf.getDb().getUserName());//获取用户名称
        Log.e("sharesdk use_icon", pf.getDb().getUserIcon());//获取用户头像
        Log.e("sharesdk use_token", pf.getDb().getToken());//获取用户Token
        Log.e("sharesdk use_Gender", pf.getDb().getUserGender());//获取用户性别
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("a", "onError");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e("a", "onCancel");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgbtnQQ:
                pf = ShareSDK.getPlatform(LoginActivity.this, QQ.NAME);
                thirdPartyLogin(pf);
                break;
            case R.id.imgbtnWeChat:
                pf = ShareSDK.getPlatform(LoginActivity.this, Wechat.NAME);
                thirdPartyLogin(pf);
                break;
            case R.id.imgbtnSinaWeibo:
                pf = ShareSDK.getPlatform(LoginActivity.this, SinaWeibo.NAME);
                thirdPartyLogin(pf);
                break;
            case R.id.btnLogin:
                Log.e("a", "Login");
                break;
            case R.id.textForgetPass:
                Log.e("a", "Forget the pass");
                break;
        }
    }

    /**
     * 第三方平台授权
     */
    private void thirdPartyLogin(Platform pf) {
        if (pf.isAuthValid()) return;
        //授权
        pf.SSOSetting(true);
        pf.setPlatformActionListener(LoginActivity.this);
        pf.showUser(null);
    }
}
