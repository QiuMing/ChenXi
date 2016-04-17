package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.presenter.impl.LoginPresenterImpl;
import com.hunter.chenxi.presenter.interfaces.ILoginPresenter;
import com.hunter.chenxi.ui.view.interfaces.ILoginView;
import com.hunter.chenxi.utils.Utils;
import com.hunter.chenxi.vo.request.UserRequest;
import com.hunter.chenxi.vo.response.UserInfo;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Ming on 2016/4/13.
 */
public class LoginActivity extends BaseActivity implements PlatformActionListener,ILoginView {
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

    @Bind(R.id.textPhone)
    TextView userPhone;

    @Bind(R.id.textPass)
    TextView pass;

    private Platform pf;

    ILoginPresenter loginPresenter;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {
        loginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick(R.id.btnLogin)
    public void btnLogin_onClick() {
        String userPhone = this.userPhone.getText().toString();
        String password = pass.getText().toString();

        if(TextUtils.isEmpty(userPhone)){
            Utils.toast("用户名不能为空");
            return;
        }

        if(TextUtils.isEmpty(password)){
            Utils.toast("密码不能为空");
            return;
        }

        Utils.saveBooleanData("loginde", true);
        showProgress("正在登陆");
        loginPresenter.login(new UserRequest(userPhone, password));
    }

    @OnClick(R.id.textForgetPass)
    public void textForgetPass_onClick() {
        Utils.toast("忘记密码功能带开发");
    }


    @Override
    public void loginCallback(UserInfo userInfo) {
        Utils.toast("登陆成功");
        hideProgress();
        startActivity(new Intent(Utils.getContext(), UserInfoActivity.class));
        finish();
    }


    /**---------------------------------------------
     *使用 ument 第三方登录需要实现的方法
     **-----------------------------------------------**/

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


    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        Log.e("a", "onComplete");
        PlatformDb db = pf.getDb();
        Log.e("sharesdk use_id", db.getUserId());
        Log.e("sharesdk use_name", db.getUserName());
        Log.e("sharesdk use_icon", db.getUserIcon());
        Log.e("sharesdk use_token", db.getToken());
        Log.e("sharesdk use_Gender", db.getUserGender());

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
