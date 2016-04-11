package com.hunter.chenxi.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Ming on 2016/3/13.
 */
public class AsyncHttpClientUtil   {

    private  static AsyncHttpClient client;

    public static AsyncHttpClient getClient(){
        if(client == null){
            client = new  AsyncHttpClient();
            // 设置连接超时时间
            client.setConnectTimeout(BaseNetCenter.CONNECT_TIMEOUT);
            // 设置最大连接数
            client.setMaxConnections(BaseNetCenter.MAX_CONNECTIONS);
            // 设置重连次数以及间隔时间
            client.setMaxRetriesAndTimeout(BaseNetCenter.MAX_RETRIES, BaseNetCenter.RETRIES_TIMEOUT);
            // 设置响应超时时间
            client.setResponseTimeout(BaseNetCenter.RESPONSE_TIMEOUT);

        }
        return client;
    }


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }


     static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }

}
