package com.hunter.chenxi.model.interfaces;

import com.hunter.chenxi.net.TransactionListener;
import com.hunter.chenxi.vo.request.UserRequest;

/**
 * 用户模型接口
 */
public interface IUserModel {
    void login(UserRequest loginRequest, TransactionListener transactionListener);
    void register(UserRequest loginRequest, TransactionListener transactionListener);
    void setHeader(String name,String value);
    void removeHeader(String header);
}
