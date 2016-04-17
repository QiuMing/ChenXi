package com.hunter.chenxi.ui.activity;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.presenter.impl.LoginPresenterImpl;
import com.hunter.chenxi.presenter.interfaces.ILoginPresenter;
import com.hunter.chenxi.ui.view.interfaces.ILoginView;
import com.hunter.chenxi.vo.request.UserRequest;
import com.hunter.chenxi.vo.response.UserInfo;

/*
 * FastAndroid 自带例子，先不删除，以便参考学习
 */
public class FastAndroid_MainActivity extends BaseActivity implements ILoginView {
   /* @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.tv_content)
    TextView tvContent;*/

    ILoginPresenter loginAndRegisterPresenter;

    @Override
    public void initContentView() {
        // 设置布局文件
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
       // titleBar.setTitle("测试页面");
       // tvContent.setText("登录中...");
        UserRequest loginRequest = new UserRequest("","");
        loginRequest.phone = "";
        loginRequest.password = "";
        loginAndRegisterPresenter.login(loginRequest);
    }

    @Override
    public void initPresenter() {
        loginAndRegisterPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void loginCallback(UserInfo userInfo) {
      //  tvContent.setText(userInfo.toString());
    }
}
