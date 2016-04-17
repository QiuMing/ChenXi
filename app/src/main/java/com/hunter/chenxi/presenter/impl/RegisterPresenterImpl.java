package com.hunter.chenxi.presenter.impl;

import com.hunter.chenxi.base.BasePresenter;
import com.hunter.chenxi.model.impl.UserModelImpl;
import com.hunter.chenxi.model.interfaces.IUserModel;
import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.presenter.interfaces.IRegisterPresenter;
import com.hunter.chenxi.ui.view.interfaces.IRegisterView;
import com.hunter.chenxi.utils.CommonUtils;
import com.hunter.chenxi.utils.Logger;
import com.hunter.chenxi.vo.request.UserRequest;
import com.hunter.chenxi.vo.response.UserInfo;

/**
 * Created by Ming on 2016/4/17.
 */
public class RegisterPresenterImpl  extends BasePresenter<IRegisterView> implements IRegisterPresenter {

    IUserModel userModel;

    public RegisterPresenterImpl(IRegisterView view) {
        super(view);
    }

    @Override
    public void initModel() {
        userModel = new UserModelImpl();
    }

    @Override
    public void register(UserRequest userRequest) {
        userModel.register(userRequest, new TransactionListener() {
            @Override
            public void onSuccess(String data) {
                Logger.i("获取的的信息为" + data);
                UserInfo userInfo = CommonUtils.getGson().fromJson(data, UserInfo.class);
                Logger.i("注册请求返回的的数据为" + CommonUtils.jsonObjectToSting(userInfo));
                mView.registerCallBack(userInfo);
                String token = userInfo.getToken();
                userModel.setHeader("Bearer ", token);
            }

            public void onFailure(int errorCode) {
                Logger.e("注册请求失败，服务器返回状态码为" + errorCode);
                UserInfo userInfo = new UserInfo();

            }
        });
    }
}
