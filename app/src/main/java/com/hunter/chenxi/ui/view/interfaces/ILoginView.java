package com.hunter.chenxi.ui.view.interfaces;

import com.hunter.chenxi.base.IBaseView;
import com.hunter.chenxi.vo.response.UserInfo;

/**
 * 用户登录View接口
 */
public interface ILoginView extends IBaseView {
    /**
     * 登录成功视图回调
     * @param userInfo
     */
    void loginCallback(UserInfo userInfo);
}
