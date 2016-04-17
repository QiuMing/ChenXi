package com.hunter.chenxi.vo.request;

import com.hunter.chenxi.base.BaseRequest;

/**
 * 用户登录请求实体类
 */
public class UserRequest extends BaseRequest {


    public String phone;
    public String password;

    private UserProfile userProfile ;

    public UserRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
