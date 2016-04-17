package com.hunter.chenxi.ui.view.interfaces;

import com.hunter.chenxi.base.IBaseView;
import com.hunter.chenxi.vo.response.UserInfo;

/**
 * Created by Ming on 2016/4/17.
 */
public interface IRegisterView extends IBaseView {
    void registerCallBack(UserInfo userInfo);
}
