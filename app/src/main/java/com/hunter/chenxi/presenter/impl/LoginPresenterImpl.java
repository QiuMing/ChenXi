package com.hunter.chenxi.presenter.impl;

import android.text.TextUtils;

import com.hunter.chenxi.base.BasePresenter;
import com.hunter.chenxi.model.impl.UserModelImpl;
import com.hunter.chenxi.model.interfaces.IUserModel;
import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.presenter.interfaces.ILoginPresenter;
import com.hunter.chenxi.ui.view.interfaces.ILoginView;
import com.hunter.chenxi.utils.CommonUtils;
import com.hunter.chenxi.vo.request.LoginRequest;
import com.hunter.chenxi.vo.response.UserInfo;

public class LoginPresenterImpl extends BasePresenter<ILoginView> implements ILoginPresenter {

    IUserModel userModel;

    public LoginPresenterImpl(ILoginView view) {
        super(view);
    }

    @Override
    public void initModel() {
        userModel = new UserModelImpl();
    }

    @Override
    public void login(LoginRequest loginRequest) {
        if(TextUtils.isEmpty(loginRequest.username)){
            mView.showToast("用户名不能为空");
            return;
        }

        if(TextUtils.isEmpty(loginRequest.password)){
            mView.showToast("密码不能为空");
            return;
        }

        userModel.login(loginRequest, new TransactionListener() {
            @Override
            public void onSuccess(String data) {
                UserInfo userInfo = CommonUtils.getGson().fromJson(data, UserInfo.class);
                mView.loginCallback(userInfo);
            }


            public void  onFailure(){

            }
        });
    }
}
