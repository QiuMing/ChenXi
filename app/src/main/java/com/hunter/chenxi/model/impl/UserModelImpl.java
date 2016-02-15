package com.hunter.chenxi.model.impl;

import com.hunter.chenxi.base.BaseModel;
import com.hunter.chenxi.model.interfaces.IUserModel;
import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.vo.request.LoginRequest;

/**
 * 用户模型实现类
 */
public class UserModelImpl extends BaseModel implements IUserModel{
    @Override
    public void login(LoginRequest loginRequest, TransactionListener transactionListener) {
        post("http://www.baidu.com", loginRequest, transactionListener);
    }
}
