package com.hunter.chenxi.model.interfaces;

import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.vo.request.LoginRequest;

/**
 * 用户模型接口
 */
public interface IUserModel {
    void login(LoginRequest loginRequest, TransactionListener transactionListener);
    void register(LoginRequest loginRequest, TransactionListener transactionListener);
}
