package com.hunter.chenxi.presenter.interfaces;

import com.hunter.chenxi.vo.request.UserRequest;

/**
 * Created by Administrator on 2015/10/20.
 */
public interface ILoginPresenter {
    /**
     * 登录
     * @param userRequest
     */
    void login(UserRequest userRequest);

    void register(UserRequest userRequest);
}
