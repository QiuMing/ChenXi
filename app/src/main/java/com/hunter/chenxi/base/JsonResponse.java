package com.hunter.chenxi.base;

import java.lang.reflect.Type;

import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.hunter.chenxi.utils.CommonUtils;

public class JsonResponse extends BaseResponse {

    /**
     * 解析json,获取响应对象
     *
     * @param json
     * @return
     */
    public static JsonResponse getResponse(String json) {
        JsonResponse mResponse = new JsonResponse();
        int code = 0;
        String msg = "";
        String data = "";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            boolean hasCode = jsonObject.has("code");
            if (hasCode) {
                code = jsonObject.getInt("code");
            }

            boolean hasMessage = jsonObject.has("message");
            if (hasMessage) {
                msg = jsonObject.getString("message");
            }

            boolean hasData = jsonObject.has("data");
            if (hasData) {
                data = jsonObject.getString("data");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mResponse.setCode(code);
        mResponse.setMsg(msg);
        mResponse.setData(data);

        return mResponse;
    }

    /**
     * 不解析json,直接作为响应的data封装后返回
     *
     * @param json
     * @return
     */
    public static JsonResponse getOnlyDataResponse(String json) {
        JsonResponse mResponse = new JsonResponse();

        mResponse.setData(json);

        return mResponse;
    }

    public <T> T getBean(Class<T> clazz) throws IllegalArgumentException,
            JsonSyntaxException {
        if (TextUtils.isEmpty(getData()))
            throw new IllegalArgumentException(
                    "In the JsonResponse, data can't be empty");

        T object = CommonUtils.getGson().fromJson(getData(), clazz);

        return object;
    }

    public <T> T getBeanList(Type typeOfT) throws IllegalArgumentException,
            JsonSyntaxException {
        if (TextUtils.isEmpty(getData()))
            throw new IllegalArgumentException(
                    "In the JsonResponse, data can't be empty");

        T object = CommonUtils.getGson().fromJson(getData(), typeOfT);

        return object;
    }
}
