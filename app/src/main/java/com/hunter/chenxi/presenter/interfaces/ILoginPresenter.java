package com.hunter.chenxi.presenter.interfaces;

import com.hunter.chenxi.vo.request.LoginRequest;

/**
 * Created by Administrator on 2015/10/20.
 */
public interface ILoginPresenter {
    /**
     * 登录
     * @param loginRequest
     */
    void login(LoginRequest loginRequest);
}
