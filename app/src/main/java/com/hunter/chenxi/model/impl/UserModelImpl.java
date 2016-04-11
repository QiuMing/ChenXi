package com.hunter.chenxi.model.impl;

import com.hunter.chenxi.app.Constants;
import com.hunter.chenxi.base.BaseModel;
import com.hunter.chenxi.model.interfaces.IUserModel;
import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.vo.request.LoginRequest;

/**
 * 用户模块 业务逻辑层 实现类
 */
public class UserModelImpl extends BaseModel implements IUserModel{
    @Override
    public void login(LoginRequest loginRequest, TransactionListener transactionListener) {
        post(Constants.LOGIN_URL, loginRequest, transactionListener);
    }

    @Override
    public void register(LoginRequest loginRequest, TransactionListener transactionListener) {
        post(Constants.REGISTER_URL, loginRequest, transactionListener);
    }
}
