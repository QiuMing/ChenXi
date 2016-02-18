package com.hunter.chenxi.ui.activity;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.ui.view.interfaces.ILoginView;
import com.hunter.chenxi.utils.Utils;
import com.hunter.chenxi.vo.response.UserInfo;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements ILoginView, PlatformActionListener {
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
        ButterKnife.bind(this);
    }

    @Override
    public void initPresenter() {
        ButterKnife.bind(this);
    }

    @Override
    public void loginCallback(UserInfo userInfo) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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


    @OnClick(R.id.btnLogin)
    public void btnLogin_onClick() {
        Utils.toast("登录");
        //输入内容不为空，登录  逻辑
    }

    @OnClick(R.id.imgbtnWeChat)
    public void imgbtnWeChat_onClick() {
        pf = ShareSDK.getPlatform(this, Wechat.NAME);
        thirdPartyLogin(pf);
    }

    @OnClick(R.id.imgbtnQQ)
    public void imgbtnQQ_onClick() {
        pf = ShareSDK.getPlatform(this, QQ.NAME);
        thirdPartyLogin(pf);
    }

    @OnClick(R.id.imgbtnSinaWeibo)
    public void imgbtnSina_onClick() {
        pf = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        thirdPartyLogin(pf);
    }

    @OnClick(R.id.textForgetPass)
    public void textForgetPass_onClick() {
        Utils.toast("忘记密码");
    }


    /**
     * 第三方平台授权
     */
    private void thirdPartyLogin(Platform pf) {
        if (pf.isAuthValid()) {
            //获取UserInfo 逻辑
            Log.d("第三方登录", "已经授权,");
            return;
        }

        //pf.SSOSetting(true);
        pf.setPlatformActionListener(this);
        pf.showUser(null);
//        pf.authorize();
    }
}
