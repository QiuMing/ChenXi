package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.utils.Utils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements PlatformActionListener {
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

    @Bind(R.id.textName)
    TextView name;

    @Bind(R.id.textPass)
    TextView pass;

    private Platform pf;//第三方登录平台

    Intent intent;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initPresenter() {
        ButterKnife.bind(this);
        if (Utils.getBooleanData("loginde", false)) {
            //登录 请求服务器
            Utils.getStringData("userpass", "userpass is null");
            Utils.getStringData("usertel", "usertel is null");

            //跳转
            startActivity(new Intent(Utils.getContext(), MainActivity.class));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        Log.e("a", "onComplete");
        PlatformDb db = pf.getDb();
        Log.e("sharesdk use_id", db.getUserId()); //获取用户id
        Log.e("sharesdk use_name", db.getUserName());//获取用户名称
        Log.e("sharesdk use_icon", db.getUserIcon());//获取用户头像
        Log.e("sharesdk use_token", db.getToken());//获取用户Token
        Log.e("sharesdk use_Gender", db.getUserGender());//获取用户性别

        //Utils.toast("欢迎您");
        Utils.saveBooleanData("loginde", true);
        startActivity(new Intent(Utils.getContext(), UserInfoActivity.class));
        finish();
        if (GuideActivity.guideActivity != null)
            GuideActivity.guideActivity.finish();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Utils.saveBooleanData("loginde", false);
        Utils.toast("登录失败，请重试");
        Log.e("a", "onError  " + i);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e("a", "onCancel");
    }

    @OnClick(R.id.btnLogin)
    public void btnLogin_onClick() {
        String nameStr = name.getText().toString();
        String passStr = pass.getText().toString();
        Utils.toast("请求服务器-登录");
<<<<<<< HEAD
        //测试-跳转
        Utils.toast("测试需要-跳转");
        Utils.saveBooleanData("loginde", true);
        startActivity(new Intent(Utils.getContext(), UserInfoActivity.class));
        finish();
        if (GuideActivity.guideActivity != null)
            GuideActivity.guideActivity.finish();
=======
        startActivity(new Intent(Utils.getContext(), UserInfoActivityNew.class));
>>>>>>> 1c1ba6a410e14a974d2acdbe06217e6579c5902d
    }

    @OnClick(R.id.textForgetPass)
    public void textForgetPass_onClick() {
        Utils.toast("忘记密码");
    }

    @OnClick(R.id.imgbtnWeChat)
    public void imgbtnWeChat_onClick() {
        pf = ShareSDK.getPlatform(this, Wechat.NAME);
        thirdPartyLogin(pf);
    }

    @OnClick(R.id.imgbtnQQ)
    public void imgbtnQQ_onClick() {
        pf = ShareSDK.getPlatform(this, QZone.NAME);
        thirdPartyLogin(pf);
    }

    @OnClick(R.id.imgbtnSinaWeibo)
    public void imgbtnSina_onClick() {
        pf = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        thirdPartyLogin(pf);
    }

    /**
     * 第三方平台授权
     */
    private void thirdPartyLogin(Platform pf) {
        if (pf.isAuthValid()) {
            //获取UserInfo 逻辑
            Log.d("第三方登录", "已经授权,");
            pf.removeAccount();
            //return;
        }
        Log.d("第三方登录", "开始授权,");
        //pf.SSOSetting(true);
        pf.setPlatformActionListener(this);
        pf.showUser(null);
        //pf.authorize();
    }
}
