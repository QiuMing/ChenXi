package com.hunter.chenxi.net;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Ming on 2016/3/13.
 */
public class AsyncHttpClientUtil extends AsyncHttpClient {

    private  static AsyncHttpClientUtil instance;

    public static AsyncHttpClientUtil getInstance(){
        if(instance == null){
            instance = new AsyncHttpClientUtil();
            // 设置连接超时时间
            instance.setConnectTimeout(BaseNetCenter.CONNECT_TIMEOUT);
            // 设置最大连接数
            instance.setMaxConnections(BaseNetCenter.MAX_CONNECTIONS);
            // 设置重连次数以及间隔时间
            instance.setMaxRetriesAndTimeout(BaseNetCenter.MAX_RETRIES, BaseNetCenter.RETRIES_TIMEOUT);
            // 设置响应超时时间
            instance.setResponseTimeout(BaseNetCenter.RESPONSE_TIMEOUT);

        }
        return instance;
    }


}
