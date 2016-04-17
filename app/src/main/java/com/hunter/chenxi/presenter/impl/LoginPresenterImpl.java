package com.hunter.chenxi.presenter.impl;

import com.hunter.chenxi.base.BasePresenter;
import com.hunter.chenxi.model.impl.UserModelImpl;
import com.hunter.chenxi.model.interfaces.IUserModel;
import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.presenter.interfaces.ILoginPresenter;
import com.hunter.chenxi.ui.view.interfaces.ILoginView;
import com.hunter.chenxi.utils.CommonUtils;
import com.hunter.chenxi.utils.Logger;
import com.hunter.chenxi.vo.request.UserRequest;
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
    public void login(UserRequest userRequest) {
        userModel.login(userRequest, new TransactionListener() {
            @Override
            public void onSuccess(String data) {
                Logger.i("获取的的信息为" + data);
                UserInfo userInfo = CommonUtils.getGson().fromJson(data, UserInfo.class);
                Logger.i(CommonUtils.jsonObjectToSting(userInfo));
                mView.loginCallback(userInfo);
                String token = userInfo.getToken();
                userModel.setHeader("Bearer ",token);
            }

            public void  onFailure(int errorCode){
                Logger.e("登陆请求失败，服务器返回状态码为"+errorCode);
            }
        });
    }

    @Override
    public void register(UserRequest userRequest) {
        userModel.login(userRequest, new TransactionListener() {
            @Override
            public void onSuccess(String data) {
                Logger.i("获取的的信息为" + data);
                UserInfo userInfo = CommonUtils.getGson().fromJson(data, UserInfo.class);
                Logger.i(CommonUtils.jsonObjectToSting(userInfo));
                mView.loginCallback(userInfo);
                String token = userInfo.getToken();
                userModel.setHeader("Bearer ",token);
            }
            public void  onFailure(int errorCode){
                Logger.e("登陆请求失败，服务器返回状态码为"+errorCode);
            }
        });
    }


}
