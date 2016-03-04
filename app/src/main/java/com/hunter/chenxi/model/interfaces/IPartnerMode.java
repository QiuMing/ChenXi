package com.hunter.chenxi.model.interfaces;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 创建人：SunShine
 * 功能说明：
 */
public interface IPartnerMode {

    /**
     * 解析Json数据 返回ArrayList<HashMap<String, String>>
     */
    LinkedList parseJson(String json) throws JSONException;

    /**
     * 从服务器获得Json数据
     */
    String getJson();
}
