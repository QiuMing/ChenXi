package com.hunter.chenxi.model.impl;

import com.hunter.chenxi.app.Constants;
import com.hunter.chenxi.base.BaseModel;
import com.hunter.chenxi.model.interfaces.IUserModel;
import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.utils.CommonUtils;
import com.hunter.chenxi.vo.request.UserRequest;

/**
 * 用户模块 业务逻辑层 实现类
 */
public class UserModelImpl extends BaseModel implements IUserModel{

    @Override
    public void login(UserRequest loginRequest, TransactionListener transactionListener) {
        post(Constants.LOGIN_URL, CommonUtils.jsonObjectToSting(loginRequest),transactionListener);
    }

    @Override
    public void register(UserRequest loginRequest, TransactionListener transactionListener) {
        post(Constants.REGISTER_URL, CommonUtils.jsonObjectToSting(loginRequest), transactionListener);
    }

    @Override
    public void setHeader(String name,String value){
        setHeader(name, value);
    }

    public void removeHeader(String header){
        removeHeader(header);
    }
}
