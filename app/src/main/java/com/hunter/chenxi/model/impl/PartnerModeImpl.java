package com.hunter.chenxi.model.impl;


import android.util.Log;

import com.hunter.chenxi.bean.PartnerBean.Block;
import com.hunter.chenxi.bean.PartnerBean.Categories;
import com.hunter.chenxi.bean.PartnerBean.Events;
import com.hunter.chenxi.bean.PartnerBean.Sliders;
import com.hunter.chenxi.model.interfaces.IPartnerMode;
import com.hunter.chenxi.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * 创建人：SunShine
 * 功能说明：
 */
public class PartnerModeImpl implements IPartnerMode {

    private LinkedList list = new LinkedList();//解析后的数据

    private JSONArray array;
    private JSONArray block;
    private JSONArray event;
    private JSONObject obj;
    private JSONObject datas;

    @Override
    public LinkedList parseJson(String json) throws JSONException {
        Log.e("test", "parseJson");

        JSONObject jsonObject = new JSONObject(json);

        //slider数据解析
        array = (JSONArray) jsonObject.get("sliders");
        for (int i = 0; i < array.length(); i++) {
            Log.e("test", "sliders开始");
            obj = (JSONObject) array.get(i);
//            for (int j = 0; j < obj.length(); j++) {
                Log.e("test", "obj开始");
                Sliders sliders_data = new Sliders();
                sliders_data.setTitie(obj.get("title").toString());
                sliders_data.setUrl(obj.get("url").toString());
                sliders_data.setPic_url(obj.get("pic_url").toString());
                list.add(sliders_data);
                Log.e("test", "slider：" + sliders_data.toString());


//            }
        }

        //blocks数据解析
        array = (JSONArray) jsonObject.get("blocks");
        for (int i = 0; i < array.length(); i++) {//blocks的循环
            Log.e("test", "blocks开始");
            obj = (JSONObject) array.get(i);
            for (int j = 0; j < obj.length(); j++) {
                Log.e("test", "obj开始");
                block = (JSONArray) obj.get("block");
                for (int p = 0; p < block.length(); p++) {
                    Log.e("test", "block开始");
                    datas = (JSONObject) block.get(p);
                    Block block_data = new Block();
                    block_data.setId((int) datas.get("id"));
                    block_data.setTitle(datas.get("title").toString());
                    block_data.setDesc(datas.get("desc").toString());
                    block_data.setPic_url(datas.get("pic_url").toString());
                    block_data.setLink_to(datas.get("link_to").toString());
                    block_data.setData_type(datas.get("data_type").toString());
                    block_data.setIs_new((boolean) datas.get("is_new"));
                    block_data.setData(datas.get("data").toString());
                    //将data加入到返回数据中
                    list.add(block_data);

                }
            }
        }

        //categories数据解析
        array = (JSONArray) jsonObject.get("categories");
        for (int i = 0; i < array.length(); i++) {//categories的循环
            Log.e("test", "categories开始");
            obj = (JSONObject) array.get(i);
            for (int j = 0; j < obj.length() - 6; j++) {//ps:减六后才应该是循环的次数
                Log.e("test", "obj开始");
                Categories categories = new Categories();
                categories.setId((int) obj.get("id"));
                categories.setTitle(obj.get("title").toString());
                categories.setMore_url(obj.get("more_url").toString());
                categories.setShow_more((boolean) obj.get("show_more"));
                categories.setBanner_link(obj.get("banner_link").toString());
                categories.setBanner_pic_url(obj.get("banner_pic_url").toString());
                Log.e("test", "**" + categories.toString());

                //存categories
                list.add(categories);
                event = (JSONArray) obj.get("events");
                for (int p = 0; p < event.length(); p++) {//events循环
                    Log.e("test", "event开始");
                    datas = (JSONObject) event.get(p);
                    Events events_data = new Events();
                    events_data.setTitle(datas.get("title").toString());
                    events_data.setDesc(datas.get("desc").toString());
                    events_data.setPic_url(datas.get("pic_url").toString());
                    events_data.setUrl(datas.get("url").toString());
                    Log.e("test", "--" + events_data.toString());
                    //存events_data
                    list.add(events_data);

                }
            }
        }

        Log.e("test", "解析完成");
        return list;
    }

    @Override
    public String getJson() {
        Log.e("test", "getJson");
        //模拟数据
        String str = "";
        try {
            InputStream open = Utils.getContext().getResources().getAssets().open("TempJsonData.txt");
            str = Utils.inputStreamToString(open);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
