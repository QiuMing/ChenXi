package com.hunter.chenxi.ui.activity;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.ui.view.interfaces.ILoginView;
import com.hunter.chenxi.vo.response.UserInfo;

public class LoginActivity extends BaseActivity implements ILoginView {

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_login);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loginCallback(UserInfo userInfo) {

    }
}
