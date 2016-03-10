package com.hunter.chenxi.bean.PartnerBean;

/**
 * 创建人：SunShine
 * 功能说明：
 */
public class Events {
    public String title;
    public String pic_url;
    public String desc;
    public String url;

    @Override
    public String toString() {
        return "Events{" +
                "title='" + title + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", desc='" + desc + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
