package com.hunter.chenxi.bean.PartnerBean;

/**
 * 创建人：SunShine
 * 功能说明：
 */
public class Block {
    public int id;
    public String title;
    public String desc;
    public String pic_url;
    public String link_to;
    public String data_type;
    public boolean is_new;
    public String data;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getLink_to() {
        return link_to;
    }

    public void setLink_to(String link_to) {
        this.link_to = link_to;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public boolean is_new() {
        return is_new;
    }

    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", link_to='" + link_to + '\'' +
                ", data_type='" + data_type + '\'' +
                ", is_new=" + is_new +
                ", data='" + data + '\'' +
                '}';
    }



}
